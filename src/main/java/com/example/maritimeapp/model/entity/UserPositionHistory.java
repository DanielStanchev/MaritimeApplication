package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users_position_history")
public class UserPositionHistory extends BaseEntity {

    @Column(name = "previous_position", nullable = false)
    private String previousPosition;

    @Column(name = "new_position", nullable = false)
    private String newPosition;

    @Column(name = "date_of_change", nullable = false)
    private LocalDate dateOfChange;

    @ManyToOne
    private UserEntity employees;

    public UserPositionHistory() {
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

    public UserEntity getEmployees() {
        return employees;
    }

    public void setEmployees(UserEntity employees) {
        this.employees = employees;
    }
}
