package com.example.maritimeapp.model.entity;


import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class DocumentEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private DocumentTypeEnum type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne
    private UserEntity possessor;

    public DocumentEntity() {
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

    public UserEntity getPossessor() {
        return possessor;
    }

    public void setPossessor(UserEntity userDocuments) {
        this.possessor = userDocuments;
    }
}
