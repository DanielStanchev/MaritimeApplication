package com.example.maritimeapp.service;

import com.example.maritimeapp.model.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    /**
     *
     * Return all Roles persisted in the DB
     */
    List<RoleEntity> findAll();
}
