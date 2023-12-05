package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ChangePositionHistoryDto;
import com.example.maritimeapp.model.dto.UserDto;
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

    public UserPositionHistoryServiceImpl(UserPositionHistoryRepository userPositionHistoryRepository, ModelMapper modelMapper) {
        this.userPositionHistoryRepository = userPositionHistoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ChangePositionHistoryDto> getAllUsersWithChangedPosition() {
        return userPositionHistoryRepository.findAll()
            .stream()
            .map(userPositionHistory -> {
                ChangePositionHistoryDto changePositionHistoryDto = modelMapper.map(userPositionHistory, ChangePositionHistoryDto.class);
                changePositionHistoryDto.setUser(modelMapper.map(userPositionHistory.getEmployee(), UserDto.class));

                return changePositionHistoryDto;
            })
            .toList();
    }

    @Override
    public void deleteAllPositionChangeHistory() {
        userPositionHistoryRepository.deleteAll();
    }
}
