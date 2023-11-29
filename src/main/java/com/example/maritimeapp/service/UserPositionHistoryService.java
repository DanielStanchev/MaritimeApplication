package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ChangePositionDto;

import java.util.List;

public interface UserPositionHistoryService {

    List<ChangePositionDto> getAllUsersWithChangedPosition();

    void deleteAllPositionChangeHistory();


}
