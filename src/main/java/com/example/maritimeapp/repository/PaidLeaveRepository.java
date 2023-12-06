package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.PaidLeaveEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaidLeaveRepository extends JpaRepository<PaidLeaveEntity,Long> {

    List<PaidLeaveEntity> findAllByEmployee(UserEntity employee);

    @Query("FROM PaidLeaveEntity WHERE status = com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum.PENDING")
    List<PaidLeaveEntity> findAllPendingPaidLeaveRequests();

    @Query("SELECT p FROM PaidLeaveEntity p WHERE p.employee.id = :id AND " +
        "(p.dateFrom BETWEEN :dateFrom AND :dateTo OR p.dateTo BETWEEN :dateFrom AND :dateTo)")
    Optional<PaidLeaveEntity> findIfThereIsAlreadyExistingPaidLeave(@Param("id") Long id,
                                                                    @Param("dateFrom") LocalDate dateFrom,
                                                                    @Param("dateTo") LocalDate dateTo);
}
