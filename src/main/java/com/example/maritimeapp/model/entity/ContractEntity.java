package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contracts")
public class ContractEntity extends BaseEntity{

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "disembark_date")
    private LocalDate disembarkDate;

    @Column(name = "salary")
    private BigDecimal salary;

    @ManyToOne
    private UserEntity possessor;

    @ManyToOne
    private ShipEntity ship;

    @Column(name = "number_of_pay_raises")
    private int numberOfPayRaises;

    public ContractEntity() {
    }

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

    public UserEntity getPossessor() {
        return possessor;
    }

    public void setPossessor(UserEntity possessor) {
        this.possessor = possessor;
    }

    public ShipEntity getShip() {
        return ship;
    }

    public void setShip(ShipEntity ship) {
        this.ship = ship;
    }

    public int getNumberOfPayRaises() {
        return numberOfPayRaises;
    }

    public void setNumberOfPayRaises(int numberOfPayRaises) {
        this.numberOfPayRaises = numberOfPayRaises;
    }
}
