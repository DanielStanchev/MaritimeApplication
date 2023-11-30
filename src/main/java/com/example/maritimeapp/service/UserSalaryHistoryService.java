package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.PayRaiseDto;

import java.util.List;

public interface UserSalaryHistoryService {

    /**
     * Return all Users whose salary has been raised
     */
    List<PayRaiseDto> getAllUsersWithPayRaise();

    /**
     * Erase all salary raise history
     */
    void deleteAllPayRaiseHistory();
}
