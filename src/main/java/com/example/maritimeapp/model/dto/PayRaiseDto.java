package com.example.maritimeapp.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class PayRaiseDto {

    private Long id;

    private String previousSalary;

    private String newSalary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfChange;

    private UserDto userName;

    public PayRaiseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreviousSalary() {
        return previousSalary;
    }

    public void setPreviousSalary(String previousSalary) {
        this.previousSalary = previousSalary;
    }

    public String getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(String newSalary) {
        this.newSalary = newSalary;
    }

    public LocalDate getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDate dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public UserDto getUserName() {
        return userName;
    }

    public void setUserName(UserDto userName) {
        this.userName = userName;
    }
}
