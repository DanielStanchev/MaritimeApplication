package com.example.maritimeapp.model.entity.enums;

public enum PaidLeaveStatusEnum {

    PENDING("Pending"),
    APPROVED("Approved"),
    NOT_APPROVED("Not approved");

    private final String description;

    PaidLeaveStatusEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
