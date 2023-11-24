package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ChangePositionDto;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserPositionHistory;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UserPositionHistoryRepository;
import com.example.maritimeapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserPositionHistoryServiceImplTest {

    @Mock
    private UserPositionHistoryRepository userPositionHistoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserPositionHistoryServiceImpl userPositionHistoryService;

    @Test
    void testGetAllUsersWithChangedPosition() {

        List<UserPositionHistory> users = new ArrayList<>();
        users.add(getUserPositionHistory());

        when(userPositionHistoryRepository.findAll()).thenReturn(users);

        UserEntity user = getUserEntity();
        when(userService.findUserByUsername(anyString())).thenReturn(Optional.of(user));

        ChangePositionDto changePositionDto = new ChangePositionDto();
        changePositionDto.setId(1L);

        when(modelMapper.map(any(), eq(ChangePositionDto.class))).thenReturn(changePositionDto);

        assertEquals(1, userPositionHistoryService.getAllUsersWithChangedPosition()
            .size());
    }

    @Test
    void testDeleteAllPositionChangeHistory() {
        userPositionHistoryService.deleteAllPositionChangeHistory();

        verify(userPositionHistoryRepository, times(1)).deleteAll();
    }


    private UserPositionHistory getUserPositionHistory() {
        UserPositionHistory userPositionHistory = new UserPositionHistory();
        userPositionHistory.setId(8L);
        userPositionHistory.setDateOfChange(LocalDate.of(2024, 9, 12));
        userPositionHistory.setPreviousPosition(PositionEnum.COOK.getDescription());
        userPositionHistory.setNewPosition(PositionEnum.OILER.getDescription());
        userPositionHistory.setEmployees(getUserEntity());
        return userPositionHistory;

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
        user.setRoles(List.of(adminE, userE));
        return user;
    }
}