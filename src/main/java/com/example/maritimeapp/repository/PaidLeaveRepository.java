package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.PaidLeaveEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PaidLeaveRepository extends JpaRepository<PaidLeaveEntity,Long> {
    List<PaidLeaveEntity> findAllByEmployee(UserEntity employee);
}
