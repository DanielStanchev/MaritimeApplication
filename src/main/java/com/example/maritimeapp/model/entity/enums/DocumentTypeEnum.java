package com.example.maritimeapp.model.entity.enums;

public enum DocumentTypeEnum {
    CERTIFICATE_FOR_OFFICERS("Certificate for Officers"),
    ORDINARY_CERTIFICATE("Ordinary Certificate");

    private final String description;

    DocumentTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
