package com.example.maritimeapp.model.dto;

import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class DocumentDto {

    private Long id;

    @NotNull
    private DocumentTypeEnum type;

    private String description;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate issueDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate expiryDate;

    private UserDto possessor;
    
    private StatusEnum status;

    public DocumentDto() {
    }

    public DocumentTypeEnum getType() {
        return type;
    }

    public void setType(DocumentTypeEnum documentType) {
        this.type = documentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserDto getPossessor() {
        return possessor;
    }

    public void setPossessor(UserDto possessor) {
        this.possessor = possessor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
