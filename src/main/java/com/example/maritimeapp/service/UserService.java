package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface UserService {
    String register(UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void initAdmin();

    UserEntity findUserByUserName(String username);

    List<UserEntity> findAll();

    List<UserDto> getAllEmployees();
}
