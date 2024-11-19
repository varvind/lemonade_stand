package com.lms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "user")
public class Networth {

    @Id
    private String id;
    private int month;
    private int year;
    private Date last_updated;
    private int current_amount;
    private List<Source> sources;

    // Default constructor with all fields initialized to null
    public Networth() {
        this.id = null;
        this.month = 0;
        this.year = 0;
        this.last_updated = null;
        this.current_amount = 0;
        this.sources = null;
    }

    // Parameterized constructor
    public Networth(String id, int month, int year, Date last_updated, int current_amount, List<Source> sources) {
        this.id = id;
        this.month = month;
        this.year = year;
        this.last_updated = last_updated;
        this.current_amount = current_amount;
        this.sources = sources;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public int getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(int current_amount) {
        this.current_amount = current_amount;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public void addSource(Source source) {
        sources.add(source);
    }
}
