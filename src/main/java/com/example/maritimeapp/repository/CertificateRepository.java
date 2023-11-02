package com.example.maritimeapp.repository;

import com.example.maritimeapp.model.entity.CertificateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<CertificateEntity,Long> {}
