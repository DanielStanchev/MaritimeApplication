package com.example.maritimeapp.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "voyages")
public class Voyage extends BaseEntity{

    @Column(name = "loading_port")
    private String loadingPort;

    @Column(name = "discharging_port")
    private String dischargingPort;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @Column(name = "redelivery_date")
    private LocalDateTime redeliveryDate;

    @ManyToOne
    private Ship ship;

    @ManyToOne
    private Cargo cargo;

    public Voyage() {
    }

    public String getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(String loadingPort) {
        this.loadingPort = loadingPort;
    }

    public String getDischargingPort() {
        return dischargingPort;
    }

    public void setDischargingPort(String dischargingPort) {
        this.dischargingPort = dischargingPort;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getRedeliveryDate() {
        return redeliveryDate;
    }

    public void setRedeliveryDate(LocalDateTime redeliveryDate) {
        this.redeliveryDate = redeliveryDate;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
