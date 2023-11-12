package com.example.maritimeapp.model.entity.enums;

public enum PositionEnum {
    MASTER("Master"),
    CHIEF_OFFICER("Chief Officer"),
    SECOND_OFFICER("Second Officer"),
    THIRD_OFFICER("Third Officer"),
    CHIEF_MECHANIC("Chief Mechanic"),
    SECOND_MECHANIC("Second Mechanic"),
    THIRD_MECHANIC("Third Mechanic"),
    ORDINARY_SEAMAN("Ordinary Seaman"),
    COOK("Cook"),
    OILER("Oiler");

    private final String description;

    PositionEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
