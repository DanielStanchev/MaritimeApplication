package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

 class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;


    @Test
    void testFindAll() {

        MockitoAnnotations.openMocks(this);

        List<RoleEntity> expectedRoles = Arrays.asList(new RoleEntity(), new RoleEntity());
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        List<RoleEntity> result = roleService.findAll();

        assertEquals(expectedRoles, result);
    }
}