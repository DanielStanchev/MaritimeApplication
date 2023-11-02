package com.example.maritimeapp.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ships")
public class ShipEntity extends BaseEntity{

    @Column(name = "name",nullable = false,unique = true)
    private String name;

    @Column(name = "flag",nullable = false)
    private String flag;

    @Column(name = "capacity",nullable = false)
    private Integer capacity;

    @Column(name = "registry_date",nullable = false)
    private LocalDate registryDate;

    @Column(name = "additional_info",columnDefinition = "TEXT")
    private String additionalInfo;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "userShip")
    private Set<UserEntity> crewMember;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "ship")
    private Set<CertificateEntity> certificates = new HashSet<>();

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

    public Set<UserEntity> getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(Set<UserEntity> crew) {
        this.crewMember = crew;
    }

    public Set<CertificateEntity> getCertificates() {
        return certificates;
    }

    public void setCertificates(Set<CertificateEntity> certificates) {
        this.certificates = certificates;
    }
}
