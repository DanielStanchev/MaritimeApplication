package com.example.maritimeapp.model.entity;


import com.example.maritimeapp.model.entity.enums.CargoTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "cargoes")
public class Cargo extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private CargoTypeEnum type;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "stowage_factor")
    private Integer stowageFactor;

    @OneToMany
    private Set<Voyage> voyages;

    public Cargo() {
    }

    public CargoTypeEnum getType() {
        return type;
    }

    public void setType(CargoTypeEnum type) {
        this.type = type;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Integer getStowageFactor() {
        return stowageFactor;
    }

    public void setStowageFactor(Integer stowageFactor) {
        this.stowageFactor = stowageFactor;
    }

    public Set<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(Set<Voyage> voyages) {
        this.voyages = voyages;
    }
}
