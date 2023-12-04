package com.example.maritimeapp.model.entity.enums;

public enum StatusEnum {
    VALID("Valid"),
    EXPIRED("Expired");

    private final String description;

    StatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
