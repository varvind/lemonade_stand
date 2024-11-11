package com.lms.security.model;

public class AuthenticationRequest {
    private String email;
    private String password;

    /**
     * Default Constructor.
     */
    public AuthenticationRequest() {
        this.email = "";
        this.password = "";
    }

    public AuthenticationRequest(String username, String password) {
        this.email = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
