package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<DocumentEntity,Long> {
}
