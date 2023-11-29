package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {

    List<ContractEntity> findAllByPossessor(UserEntity employee);


    @Query("SELECT c FROM ContractEntity c WHERE c.possessor.username = :username AND " +
        "(c.startDate BETWEEN :startDate AND :disembarkDate OR c.disembarkDate BETWEEN :startDate AND :disembarkDate)")
    Optional<ContractEntity> findIfThereIsAlreadyExistingContractForGivenDates(@Param("username") String username,
                                                                               @Param("startDate") LocalDate startDate,
                                                                               @Param("disembarkDate") LocalDate disembarkDate);
}
