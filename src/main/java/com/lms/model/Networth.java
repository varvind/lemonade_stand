package com.lms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "user")
public class Networth {

    @Id
    private String id;
    private Date date_created;
    private Date last_updated;
    private double current_amount;
    private List<Source> sources;

    // Default constructor with all fields initialized to null
    public Networth() {
        this.id = null;
        this.date_created = new Date();
        this.last_updated = new Date();
        this.current_amount = 0;
        this.sources = null;
    }

    // Parameterized constructor
    public Networth(String id, int current_amount, List<Source> sources) {
        this.id = id;
        this.date_created = new Date();
        this.last_updated = new Date();
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

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(Date last_updated) {
        this.last_updated = last_updated;
    }

    public double getCurrent_amount() {
        return current_amount;
    }

    public void setCurrent_amount(double current_amount) {
        this.current_amount = current_amount;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public void addSource(Source source) {
        current_amount += source.getAmount();
        last_updated = new Date();
        sources.add(source);
    }


}
