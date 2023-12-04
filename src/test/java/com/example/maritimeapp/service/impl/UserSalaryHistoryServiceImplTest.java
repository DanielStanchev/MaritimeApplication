package com.example.maritimeapp.service.impl;


import com.example.maritimeapp.model.dto.PayRaiseDto;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import com.example.maritimeapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserSalaryHistoryServiceImplTest {

    @Mock
    private UsersSalaryHistoryRepository usersSalaryHistoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserSalaryHistoryServiceImpl userSalaryHistoryService;


    @Test
    void getAllUsersWithPayRaise() {

        List<UserSalaryHistory> users = new ArrayList<>();
        users.add(getUserSalaryHistory());

        when(usersSalaryHistoryRepository.findAll()).thenReturn(users);

        UserEntity user = getUserEntity();
        when(userService.findUserByUsername(anyString())).thenReturn(Optional.of(user));

        PayRaiseDto payRaiseDto = new PayRaiseDto();
        payRaiseDto.setId(1L);

        when(modelMapper.map(any(), eq(PayRaiseDto.class))).thenReturn(payRaiseDto);

        assertEquals(1, userSalaryHistoryService.getAllUsersWithPayRaise()
            .size());
    }

    @Test
    void deleteAllPayRaiseHistory() {
        userSalaryHistoryService.deleteAllPayRaiseHistory();

        verify(usersSalaryHistoryRepository, times(1)).deleteAll();
    }

    private UserSalaryHistory getUserSalaryHistory() {
        UserSalaryHistory user = new UserSalaryHistory();
        user.setId(8L);
        user.setDateOfChange(LocalDate.of(2024, 9, 12));
        user.setPreviousSalary(BigDecimal.valueOf(1000));
        user.setNewSalary(BigDecimal.valueOf(2000));
        user.setEmployees(getUserEntity());
        return user;

    }

    private UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        RoleEntity adminE = new RoleEntity();
        adminE.setRole(RoleEnum.ADMIN);
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);

        user.setId(1L);
        user.setUsername("pesho");
        user.setFirstName("pesho");
        user.setLastName("pesho");
        user.setPassword("pesho");
        user.setEmail("pesho@localhost");
        //user.setPosition(PositionEnum.THIRD_OFFICER);
        user.setRoles(Set.of(adminE, userE));
        return user;
    }
}