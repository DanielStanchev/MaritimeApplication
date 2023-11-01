package com.example.maritimeapp.service;

import com.example.maritimeapp.model.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    List<RoleEntity> findAll();

    void initRoles();
}
