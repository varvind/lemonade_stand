package com.lms.security.model;


import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticationResponse {
    private String email = "";

    private String message = "";

    private String accessToken = "";

    private Collection<GrantedAuthority> roles = null;

    private Exception ex = null;

    //private String refreshToken;

    /**
     * Default Constructor.
     */
    public AuthenticationResponse() { }

    /**
     * Main Constructor.
     */
    public AuthenticationResponse(
            String email,
            String accessToken,
            Collection<GrantedAuthority> roles) {
        this.message = "Bearer Token: Access JWT";
        this.email = email;
        this.accessToken = accessToken;
        this.roles = roles;
    }

    public AuthenticationResponse(String email) {
        this.message = "Already Logged In";
        this.email = email;
    }

    public AuthenticationResponse(String message, Exception e) {
        this.message = message;
        this.ex = e;
    }

    public String getEmail() {
        return email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Role Getter.
     */
    public String getRoles() {
        if (roles != null) {
            return roles.toString();
        }
        return "";
    }

    /**
     * Exception Getter.
     */
    public String getException() {
        if (ex != null) {
            return ex.toString();
        }
        return "";
    }
}

