package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.UserPositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPositionHistoryRepository extends JpaRepository<UserPositionHistory, Long> {}
