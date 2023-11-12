package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ChangePositionDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.repository.UserHistoryRepository;
import com.example.maritimeapp.service.UserHistoryService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {

    private final UserHistoryRepository userHistoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserHistoryServiceImpl(UserHistoryRepository userHistoryRepository, ModelMapper modelMapper, UserService userService) {this.userHistoryRepository = userHistoryRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public List<ChangePositionDto> getAllUsersWithChangedPosition() {
        return userHistoryRepository.findAll().stream().map(u -> {

            UserEntity user = userService.findUserByUsername(u.getEmployees().getUsername()).orElse(null);

       ChangePositionDto changedUser =  modelMapper.map(u,ChangePositionDto.class);
       changedUser.setUserName(modelMapper.map(user, UserDto.class));
       return changedUser;
        }).toList();
    }

    @Override
    public void deleteAllHistory() {
        userHistoryRepository.deleteAll();
    }
}
