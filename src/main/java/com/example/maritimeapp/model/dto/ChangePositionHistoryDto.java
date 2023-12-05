package com.example.maritimeapp.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ChangePositionHistoryDto {

    private Long id;

    private String previousPosition;

    private String newPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfChange;

    private UserDto user;

    public ChangePositionHistoryDto() {
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
