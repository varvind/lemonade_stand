package com.lms.service;

import com.lms.model.User;
import com.lms.repository.UserRepository;
import com.lms.security.CustomAuthenticationProvider;
import com.lms.security.model.AuthenticationRequest;
import com.lms.security.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder bcryptPassEnc;
    public String verifyUser(User user) {
        log.info("Verifying Valid User Information:\n" + user.toString());

        String errorMessage = "";
        if (Objects.equals(user.getFirstName(), "") || user.getFirstName() == null) {
            errorMessage += "firstname, ";
        }
        if (Objects.equals(user.getLastName(), "") || user.getLastName() == null) {
            errorMessage += "lastname, ";
        }
        if (Objects.equals(user.getPassword(), "") || user.getPassword() == null) {
            errorMessage += "password, ";
        }
        if (Objects.equals(user.getEmail(), "") || user.getEmail() == null) {
            errorMessage += "email, ";
        }
        if (userRepository.existsUserByEmail(user.getEmail())) {
            errorMessage += " Invalid Entry: Duplicate User. ";
        }

        if (!errorMessage.isEmpty()) {
            errorMessage = "Invalid or missing tokens: "
                    + errorMessage.substring(0, errorMessage.length() - 2) + ".";
        }
        return errorMessage;
    }

    public User saveUser(User reqUser) {
        try {
            log.info("Attempting to Save User to Database");
            String encodedPassword = bcryptPassEnc.encode(reqUser.getPassword());
            log.info("User Password Encrypted");
            reqUser.setPassword(encodedPassword);
            log.info("User Password Set");
            User user = userRepository.save(reqUser);
            log.info("User Saved in DB");
            return user;
        } catch (Exception e) {
            log.error("Error with creating/storing user, returning empty user object: ", e);
            return new User();
        }
    }


    public String getLoggedInUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserName = authentication.getName();
            if (currentUserName.equals("anonymousUser")) {
                log.warn("No User is Logged In");
                return "";
            }
            log.info(String.format("User In Current Context: %s", currentUserName));
            return currentUserName;
        } catch (Exception e) {
            log.error("Exception thrown When Looking in Security Context");
            return "";
        }
    }


    public String refreshSecurityContext(Authentication tok) {
        try {
            Authentication token = customAuthenticationProvider.authenticate(tok);
            SecurityContextHolder.getContext().setAuthentication(token);
            log.info("Successfully Updated the Security Context");

            return jwtUtil.generateToken(token.getName());
        } catch (BadCredentialsException e) {
            SecurityContextHolder.clearContext();
            log.error("Caught Exception When Authenticating Security Token:\n" + e.getMessage());
            throw e;
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.error("Caught Exception When Generating New JWT:\n" + e.getMessage());
        }
        return "";
    }


    public User getUser() {
        String email = getLoggedInUser();
        User user;
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmail(email);
        } else {
            return null;
        }
    }

}
