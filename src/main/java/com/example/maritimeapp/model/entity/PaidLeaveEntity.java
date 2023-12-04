package com.example.maritimeapp.model.entity;

import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "paid_leave")
public class PaidLeaveEntity extends BaseEntity{

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @ManyToOne
    private UserEntity employee;

    @Enumerated(EnumType.STRING)
    private PaidLeaveStatusEnum status;

    public PaidLeaveEntity() {
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

    public UserEntity getEmployee() {
        return employee;
    }

    public void setEmployee(UserEntity employee) {
        this.employee = employee;
    }

    public PaidLeaveStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaidLeaveStatusEnum status) {
        this.status = status;
    }
}
