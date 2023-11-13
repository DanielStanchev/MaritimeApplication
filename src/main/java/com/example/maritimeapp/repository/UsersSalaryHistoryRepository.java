package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.UserSalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersSalaryHistoryRepository extends JpaRepository<UserSalaryHistory,Long> {}
