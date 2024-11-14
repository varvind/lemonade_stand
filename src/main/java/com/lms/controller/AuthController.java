package com.lms.controller;

import com.lms.model.User;
import com.lms.security.model.AuthenticationRequest;
import com.lms.security.model.AuthenticationResponse;
import com.lms.security.util.JWTUtil;
import com.lms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Attempting to POST /login");

        try {
            Authentication token = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword());
            log.info("Generating Security Context and JWT");
            String jwt = userService.refreshSecurityContext(token);
            if (jwt.equals("")) {
                log.warn("Error Generating JWT");
                AuthenticationResponse res = new AuthenticationResponse();
                res.setMessage("Unknown Error Generating JWT");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
            }
            AuthenticationResponse res = new AuthenticationResponse(
                    authenticationRequest.getEmail(),
                    jwt,
                    new ArrayList<GrantedAuthority>());

            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (BadCredentialsException e) {
            log.error("Failure to login user, returning 'Unauthorized': ", e.getMessage());
            SecurityContextHolder.clearContext();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new AuthenticationResponse("Bad Credentials", e));
        } catch (Exception e) {
            log.error("Unknown Exception: ", e.getMessage()
                    + ", returning 'Internal Server Error'");
            SecurityContextHolder.clearContext();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new AuthenticationResponse("Unknown Error",e));
        }
    }


    @RequestMapping(method = RequestMethod.POST, value = "/create/user",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> createUser(@RequestBody User reqUser) {
        log.info("Attempting to request POST '/create/user'");
        Map<String, String> returnJson = new HashMap<>();

        String verifyUserMessage = userService.verifyUser(reqUser);
        if (!Objects.equals(verifyUserMessage, "")) {
            log.error("Error in user request, returning 'Bad Request'");
            returnJson.put("error", verifyUserMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(returnJson);
        }

        String unhashedPass = reqUser.getPassword();
        User newUser = userService.saveUser(reqUser);
        String username = newUser.getEmail();
        if (Objects.equals(username,"")) {
            log.error("Error with DB insert");
            returnJson.put("error",
                    "Error Saving User to Database, returning 'Internal Service Error'");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnJson);
        }

        try {
            log.info("Generating Security Context and JWT");
            String jwt = jwtUtil.generateToken(reqUser.getEmail());
            if (jwt == "") {
                log.warn("Error Generating JWT");
                returnJson.put("error", "Error Generating JWT");
            } else {
                log.info("Successfully logged in user with id: " + newUser.getId());
                returnJson.put("bearerToken", jwt);
            }
        } catch (Exception e) {
            log.error("Unknown Error thrown when authenticating new security token:\n"
                    + e.getMessage());
            returnJson.put("error", "Error Generating JWT");
        }

        log.info("User successfully created");
        returnJson.put("message", "Successfully Created User: " + newUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(returnJson);
    }
}