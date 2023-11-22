package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.repository.RoleRepository;
import com.example.maritimeapp.service.impl.RoleServiceImpl;
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
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Mock the behavior of roleRepository.findAll
        List<RoleEntity> expectedRoles = Arrays.asList(new RoleEntity(), new RoleEntity());
        when(roleRepository.findAll()).thenReturn(expectedRoles);

        // Call the findAll method
        List<RoleEntity> result = roleService.findAll();

        // Verify that findAll returns the expected list of roles
        assertEquals(expectedRoles, result);
    }
}