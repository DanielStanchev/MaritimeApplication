package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ChangePositionDto;

import java.util.List;

public interface UserHistoryService {
    List<ChangePositionDto> getAllUsersWithChangedPosition();

    void deleteAllHistory();
}
