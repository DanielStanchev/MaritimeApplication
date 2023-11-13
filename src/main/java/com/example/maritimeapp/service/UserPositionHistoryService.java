package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ChangePositionDto;
import com.example.maritimeapp.model.dto.PayRaiseDto;

import java.util.List;

public interface UserPositionHistoryService {

    List<ChangePositionDto> getAllUsersWithChangedPosition();

    void deleteAllPositionChangeHistory();

}
