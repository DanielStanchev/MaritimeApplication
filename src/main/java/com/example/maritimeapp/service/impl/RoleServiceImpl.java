package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.RoleRepository;
import com.example.maritimeapp.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;



@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {this.roleRepository = roleRepository;}

    @Override
    @PostConstruct
    public void init() {
        initRoles();
    }


    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }


    private void initRoles() {
        if (roleRepository.count() != 0) {
            return;
        }

        Arrays.stream(RoleEnum.values())
            .forEach(roleEnum -> {
                RoleEntity role = new RoleEntity();
                role.setRole(roleEnum);

                roleRepository.save(role);
            });
    }
}
