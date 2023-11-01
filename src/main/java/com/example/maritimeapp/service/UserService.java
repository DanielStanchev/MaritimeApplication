package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.UserRegisterDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserService {
    String register(UserRegisterDto userRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
