package com.example.maritimeapp.model.dto;

import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class PaidLeaveDto {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate dateFrom;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent
    private LocalDate dateTo;

    private UserDto employee;

    private PaidLeaveStatusEnum status;

    public PaidLeaveDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public UserDto getEmployee() {
        return employee;
    }

    public void setEmployee(UserDto employee) {
        this.employee = employee;
    }

    public PaidLeaveStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaidLeaveStatusEnum status) {
        this.status = status;
    }
}

