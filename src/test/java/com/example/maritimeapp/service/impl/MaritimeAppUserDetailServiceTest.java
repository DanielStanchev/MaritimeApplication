package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MaritimeAppUserDetailServiceTest {

    private MaritimeAppUserDetailService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new MaritimeAppUserDetailService(mockUserRepository);
    }

    @Test
    void testMock() {

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Pesho");

        when(mockUserRepository.findUserByEmail("test@test")).thenReturn(Optional.of(userEntity));

        Optional<UserEntity> userOpt = mockUserRepository.findUserByEmail("test@test");

        UserEntity user = userOpt.get();

        Assertions.assertEquals("Pesho", user.getFirstName());

    }

    @Test
    void userNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> serviceToTest.loadUserByUsername("Pesho"));
    }

    @Test
    void userFoundException() {

        UserEntity testUser = createTestUser();

        when(mockUserRepository.findUserByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

       UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getUsername());

       Assertions.assertNotNull(userDetails);
       Assertions.assertEquals(testUser.getUsername(),userDetails.getUsername());
       Assertions.assertEquals(testUser.getPassword(),userDetails.getPassword());

    }

    private static UserEntity createTestUser() {

        UserEntity user = new UserEntity();
        RoleEntity adminE = new RoleEntity();
        adminE.setRole(RoleEnum.ADMIN);
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);

        user.setFirstName("Pesho");
        user.setLastName("Peshov");
        user.setUsername("pesho");
        user.setPassword("pesho");
        user.setEmail("pesho@pesho");
        user.setRoles(List.of(adminE, userE));

        return user;
    }


}
