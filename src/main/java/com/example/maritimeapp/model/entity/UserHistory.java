package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "users_history")
public class UserHistory extends BaseEntity{

    @Column(name = "previous_position")
    private String previousPosition;

    @Column(name = "new_position")
    private String newPosition;

    @Column(name = "previous_salary")
    private BigDecimal previousSalary;

    @Column(name = "new_salary")
    private BigDecimal newSalary;

    @Column(name = "date_of_change",nullable = false)
    private LocalDate dateOfChange;

    @ManyToOne
    private UserEntity employees;

    public UserHistory() {
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

    public BigDecimal getPreviousSalary() {
        return previousSalary;
    }

    public void setPreviousSalary(BigDecimal previousSalary) {
        this.previousSalary = previousSalary;
    }

    public BigDecimal getNewSalary() {
        return newSalary;
    }

    public void setNewSalary(BigDecimal newSalary) {
        this.newSalary = newSalary;
    }

    public LocalDate getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(LocalDate dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public UserEntity getEmployees() {
        return employees;
    }

    public void setEmployees(UserEntity employees) {
        this.employees = employees;
    }
}
