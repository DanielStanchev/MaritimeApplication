package com.example.maritimeapp.model.entity;


import com.example.maritimeapp.model.entity.enums.CargoTypeEnum;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "cargoes")
public class CargoEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private CargoTypeEnum type;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "stowage_factor")
    private Integer stowageFactor;

    @OneToMany(mappedBy = "cargo")
    private Set<VoyageEntity> voyages;

    public CargoEntity() {
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

    public Set<VoyageEntity> getVoyages() {
        return voyages;
    }

    public void setVoyages(Set<VoyageEntity> voyages) {
        this.voyages = voyages;
    }
}
