package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<DocumentEntity,Long> {}
