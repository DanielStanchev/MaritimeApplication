package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "voyages")
public class VoyageEntity extends BaseEntity {

    @Column(name = "loading_port")
    private String loadingPort;

    @Column(name = "discharging_port")
    private String dischargingPort;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "redelivery_date")
    private LocalDate redeliveryDate;

    @ManyToOne
    private ShipEntity ship;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private CargoEntity cargo;

    public VoyageEntity() {
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getRedeliveryDate() {
        return redeliveryDate;
    }

    public void setRedeliveryDate(LocalDate redeliveryDate) {
        this.redeliveryDate = redeliveryDate;
    }

    public ShipEntity getShip() {
        return ship;
    }

    public void setShip(ShipEntity ship) {
        this.ship = ship;
    }

    public CargoEntity getCargo() {
        return cargo;
    }

    public void setCargo(CargoEntity cargo) {
        this.cargo = cargo;
    }
}
