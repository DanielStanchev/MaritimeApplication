package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ChangePositionHistoryDto;

import java.util.List;

public interface UserPositionHistoryService {

    /**
     * Return all Users whose company position has been changed
     */
    List<ChangePositionHistoryDto> getAllUsersWithChangedPosition();

    /**
     * Erase all position change history
     */
    void deleteAllPositionChangeHistory();


}
