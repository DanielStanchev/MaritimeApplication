package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.ShipEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<ShipEntity,Long> {
}
