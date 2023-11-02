package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipRepository extends JpaRepository<ShipEntity,Long> {

    Optional<ShipEntity> findShipEntitiesByName(String name);
}
