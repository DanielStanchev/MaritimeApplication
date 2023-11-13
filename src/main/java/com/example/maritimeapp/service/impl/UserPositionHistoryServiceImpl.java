package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ChangePositionDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.repository.UserPositionHistoryRepository;
import com.example.maritimeapp.service.UserPositionHistoryService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPositionHistoryServiceImpl implements UserPositionHistoryService {

    private final UserPositionHistoryRepository userPositionHistoryRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserPositionHistoryServiceImpl(UserPositionHistoryRepository userPositionHistoryRepository, ModelMapper modelMapper, UserService userService) {
        this.userPositionHistoryRepository = userPositionHistoryRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }


    @Override
    public List<ChangePositionDto> getAllUsersWithChangedPosition() {
        return userPositionHistoryRepository.findAll()
            .stream()
            .map(u -> {

                UserEntity user = userService.findUserByUsername(u.getEmployees()
                                                                     .getUsername())
                    .orElse(null);

                ChangePositionDto changedUser = modelMapper.map(u, ChangePositionDto.class);
                changedUser.setUserName(modelMapper.map(user, UserDto.class));
                return changedUser;
            })
            .toList();
    }

    @Override
    public void deleteAllPositionChangeHistory() {
        userPositionHistoryRepository.deleteAll();
    }

}
