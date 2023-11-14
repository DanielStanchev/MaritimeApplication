package com.example.maritimeapp.model.entity;


import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import com.example.maritimeapp.model.entity.enums.StatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "documents")
public class DocumentEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private DocumentTypeEnum type;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne
    private UserEntity possessor;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
