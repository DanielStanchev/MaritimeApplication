package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Register User
     * @param userDto The User data coming from UI used for registration
     * @param bindingResult Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes Pass data of HTTP request then redirecting
     */
    String register(UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    /**
     * Return User by id from DB
     * @param username The id of certain user
     */
    Optional<UserEntity> findUserByUsername(String username);

    /**
     *
     * Return all User from DB
     */
    List<UserEntity> findAll();

    /**
     * Return view of all employees/users
     */
    List<UserDto> getAllEmployees();

    /**
     * Change the company position of User and keep history of the change
     * @param userId The id of certain User
     * @param position The company position of the User
     */
    void changePositionOfUserAndKeepHistory(Long userId, PositionEnum position);

    Optional<UserEntity> findById(Long userId);
}
