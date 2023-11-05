package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DocumentRepository extends JpaRepository<DocumentEntity,Long> {

    List<DocumentEntity> findAllByPossessor(UserEntity possessor);
}
