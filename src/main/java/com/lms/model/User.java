package com.lms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Networth> networthData;
    private Networth currentNetworth;

    // Default constructor with all fields initialized to null
    public User() {
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.networthData = new ArrayList<>();
        this.currentNetworth = null;
    }

    // Parameterized constructor
    public User(String id, String firstName, String lastName, String email, String password, List<Networth> networthData, Networth currentNetworth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.networthData = networthData;
        this.currentNetworth = currentNetworth;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Networth> getNetworthData() {
        return networthData;
    }

    public void setNetworthData(List<Networth> networthData) {
        this.networthData = networthData;
    }

    public Networth getCurrentNetworth() {
        return currentNetworth;
    }

    public void setCurrentNetworth(Networth currentNetworth) {
        this.currentNetworth = currentNetworth;
    }

    public void addNetworth(Networth networth) {
        networthData.add(networth);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", networthData=" + networthData +
                ", currentNetworth='" + currentNetworth + '\'' +
                '}';
    }
}
