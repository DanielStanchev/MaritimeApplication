package com.example.maritimeapp.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContractDto {

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate startDate;


    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate disembarkDate;

    @Positive
    private BigDecimal salary;

    private UserDto employee;

    private ShipDto ship;

    public ContractDto() {}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDisembarkDate() {
        return disembarkDate;
    }

    public void setDisembarkDate(LocalDate disembarkDate) {
        this.disembarkDate = disembarkDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public UserDto getEmployee() {
        return employee;
    }

    public void setEmployee(UserDto employee) {
        this.employee = employee;
    }

    public ShipDto getShip() {
        return ship;
    }

    public void setShip(ShipDto ship) {
        this.ship = ship;
    }
}
