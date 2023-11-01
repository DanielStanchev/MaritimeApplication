package com.example.maritimeapp.model.dto;

import com.example.maritimeapp.model.entity.enums.PositionEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterDto {

    @Size(min = 3,max = 20)
    private String username;

    @Size(min = 3,max = 20)
    private String firstName;

    @Size(min = 3,max = 20)
    private String lastName;

    @Email
    private String email;

    @Size(min = 3)
    private String password;

    @Size(min = 3,max = 20)
    private String confirmPassword;

    @NotNull
    private PositionEnum position;

    public UserRegisterDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }
}

