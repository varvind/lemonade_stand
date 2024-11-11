package com.lms.model;

public class Source {

    private int amount;
    private String name;
    private SourceType type;

    // Default constructor initializing fields to default values
    public Source() {
        this.amount = 0;
        this.name = null;
        this.type = null;
    }

    // Parameterized constructor
    public Source(int amount, String name, SourceType type) {
        this.amount = amount;
        this.name = name;
        this.type = type;
    }

    // Getters and setters
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    // Enum for different source types (e.g., salary, investment, etc.)
    public enum SourceType {
        CASH,
        INVESTMENT,
        BUSINESS,
        HOME,
        OTHER
    }
}
