package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ChangePositionDto;

import java.util.List;

public interface UserPositionHistoryService {

    /**
     * Return all Users whose company position has been changed
     */
    List<ChangePositionDto> getAllUsersWithChangedPosition();

    /**
     * Erase all position change history
     */
    void deleteAllPositionChangeHistory();


}
