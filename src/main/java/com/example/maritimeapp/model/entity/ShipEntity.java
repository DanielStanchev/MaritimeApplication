package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "ships")
public class ShipEntity extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "flag")
    private String flag;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "registry_date")
    private LocalDate registryDate;

    @Column(name = "additional_info",columnDefinition = "TEXT")
    private String additionalInfo;

    @OneToMany
    private Set<UserEntity> crew;

    @OneToMany
    private Set<CertificateEntity> certificates;

    public ShipEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDate getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(LocalDate registryDate) {
        this.registryDate = registryDate;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Set<UserEntity> getCrew() {
        return crew;
    }

    public void setCrew(Set<UserEntity> crew) {
        this.crew = crew;
    }

    public Set<CertificateEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<CertificateEntity> certificates) {
        this.certificates = certificates;
    }
}
