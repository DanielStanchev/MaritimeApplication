package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String register(UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void initAdmin();

    Optional<UserEntity> findUserByUsername(String username);

    List<UserEntity> findAll();

    List<UserDto> getAllEmployees();

    void changePositionOfUserAndKeepHistory(Long userId, PositionEnum position);

}
