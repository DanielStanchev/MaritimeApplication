package com.example.maritimeapp.model.entity;


import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class Document extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum type;

    @Column(name = "desctiption", columnDefinition = "TEXT")
    private String description;

    @Column(name = "expity_date")
    private LocalDate expiryDate;

    @ManyToOne
    private User userDocuments;

    public Document() {
    }

    public DocumentTypeEnum getType() {
        return type;
    }

    public void setType(DocumentTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public User getUserDocuments() {
        return userDocuments;
    }

    public void setUserDocuments(User userDocuments) {
        this.userDocuments = userDocuments;
    }
}
