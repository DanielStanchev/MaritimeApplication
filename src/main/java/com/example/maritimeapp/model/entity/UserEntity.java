package com.example.maritimeapp.model.entity;

import com.example.maritimeapp.model.entity.enums.PositionEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "registry_date", nullable = false)
    private LocalDateTime registryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private PositionEnum position;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "possessor")
    private Set<DocumentEntity> documents = new HashSet<>();

    @OneToMany(mappedBy = "possessor", fetch = FetchType.EAGER)
    private Set<ContractEntity> contracts = new HashSet<>();

    @ManyToOne
    private ShipEntity userShip;

    public UserEntity() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistryDate() {
        return registryDate;
    }

    public void setRegistryDate(LocalDateTime registryDate) {
        this.registryDate = registryDate;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public Set<DocumentEntity> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<DocumentEntity> documents) {
        this.documents = documents;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ShipEntity getUserShip() {
        return userShip;
    }

    public void setUserShip(ShipEntity ship) {
        this.userShip = ship;
    }

    public Set<ContractEntity> getContracts() {
        return contracts;
    }

    public void setContracts(Set<ContractEntity> contract) {
        this.contracts = contract;
    }
}
