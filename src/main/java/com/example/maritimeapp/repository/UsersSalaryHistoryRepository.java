package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.UserSalaryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersSalaryHistoryRepository extends JpaRepository<UserSalaryHistory, Long> {

    @Query("SELECT d FROM UserSalaryHistory d WHERE d.employee.id = :employeeId "
        + "AND d.id = (SELECT MAX(d2.id) FROM UserSalaryHistory d2 WHERE d2.employee.id = :employeeId)")
    Optional<UserSalaryHistory> findLatestByIdForEmployee(@Param("employeeId") Long employeeId);
}
