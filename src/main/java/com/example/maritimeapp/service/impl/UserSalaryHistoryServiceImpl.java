package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.PayRaiseDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import com.example.maritimeapp.service.UserSalaryHistoryService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSalaryHistoryServiceImpl implements UserSalaryHistoryService {

    private final UsersSalaryHistoryRepository usersSalaryHistoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserSalaryHistoryServiceImpl(UsersSalaryHistoryRepository usersSalaryHistoryRepository, ModelMapper modelMapper, UserService userService) {
        this.usersSalaryHistoryRepository = usersSalaryHistoryRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<PayRaiseDto> getAllUsersWithPayRaise() {
        return usersSalaryHistoryRepository.findAll()
            .stream()
            .map(u -> {

                UserEntity user = userService.findUserByUsername(u.getEmployees()
                                                                     .getUsername())
                    .orElse(null);

                PayRaiseDto changedUser = modelMapper.map(u, PayRaiseDto.class);
                changedUser.setUserName(modelMapper.map(user, UserDto.class));
                return changedUser;
            })
            .toList();
    }

    @Override
    public void deleteAllPayRaiseHistory() {
        usersSalaryHistoryRepository.deleteAll();
    }
}
