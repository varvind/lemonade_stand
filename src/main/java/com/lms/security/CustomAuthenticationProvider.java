package com.lms.security;



import java.text.MessageFormat;

import com.lms.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configure Authenticator Used to Verify User Sign-in
 * Compares given paramaters with encoded, database-counterparts
 * Set Security Context Authentication if successful.
 */
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bcryptPassEnc;

    /**
     * Define Authenticate Function for User sign-in.
     * @return Authentication token or throws exception
     */
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken res;
        log.info("Attempting to Authenticate UsernamePasswordAuthenticationToken");

        String authEmail = "";
        String authPassword = ""; //raw password found in Authentication parameter
        try {
            log.info("Attempting to Get User Credentials");
            authEmail = authentication.getName();
            authPassword = authentication.getCredentials().toString();
        } catch (Exception e) {
            log.error(MessageFormat.format("Error getting username:{0} and password:{1}",
                    authEmail, authPassword));
            throw new BadCredentialsException("Invalid Authentication Details");
        }

        if (!authentication.isAuthenticated()) {
            String dbPassword;
            try {
                log.info("Attempting to Load User from Database");
                UserDetails databaseUser = userDetailsService.loadUserByUsername(authEmail);
                dbPassword = databaseUser.getPassword();
                //TODO: getAuthorities
            } catch (UsernameNotFoundException e) {
                log.error(String.format("Exception Thrown Loading the User: " + e.getMessage()));
                throw new BadCredentialsException("Could Not Find User " + authEmail);
            }

            log.info("Verifying Password Match");
            if (bcryptPassEnc.matches(authPassword,dbPassword)) {
                log.info("Success!");

                res = new UsernamePasswordAuthenticationToken(
                        authEmail, authPassword, authentication.getAuthorities());
            } else {
                log.error("Given Password does not match database user password");
                throw new BadCredentialsException("Username and/or Password is Incorrect");
            }
        } else {
            log.info("Skipping Password Match");
            res = new UsernamePasswordAuthenticationToken(
                    authEmail, authentication.getCredentials(), authentication.getAuthorities());
        }

        return res;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    public String encode(String str) {
        return bcryptPassEnc.encode(str);
    }
}