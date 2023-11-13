package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.PayRaiseDto;

import java.util.List;

public interface UserSalaryHistoryService {
    List<PayRaiseDto> getAllUsersWithPayRaise();

    void deleteAllPayRaiseHistory();
}
