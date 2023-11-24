package com.example.maritimeapp.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ChangePositionDto {

    private Long id;

    private String previousPosition;

    private String newPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfChange;

    private UserDto userName;

    public ChangePositionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(String previousPosition) {
        this.previousPosition = previousPosition;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
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
