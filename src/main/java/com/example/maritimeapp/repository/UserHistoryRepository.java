package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory,Long> {}
