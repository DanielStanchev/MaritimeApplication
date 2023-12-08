package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserByUsername(String username);

    @Query("FROM UserEntity WHERE username = :username OR email = :email")
    Optional<UserEntity> findUserByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

    UserEntity findUserEntityByContractsContains(ContractEntity contract);

    @Query("FROM UserEntity WHERE LOWER(username) LIKE %:criteria% OR LOWER(firstName) LIKE %:criteria% OR LOWER(lastName) LIKE %:criteria%")
    List<UserEntity> searchEmployees(@Param("criteria") String criteria);
}
