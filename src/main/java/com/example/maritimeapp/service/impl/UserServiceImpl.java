package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.UserRegisterDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.service.RoleService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public String register(UserRegisterDto userRegisterDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterDto.getPassword()
            .equals(userRegisterDto.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDto", bindingResult);

            return "redirect:register";
        }

        UserEntity user = checkIfExistingUser(userRegisterDto);

        if (user == null) {
            UserEntity userToSave = modelMapper.map(userRegisterDto, UserEntity.class);
            userToSave.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            userToSave.setRegistryDate(LocalDateTime.now());
            userToSave.setPosition(userRegisterDto.getPosition());
            userToSave.setRoles(roleService.findAll()
                                    .stream()
                                    .filter(r -> r.getRole() != RoleEnum.ADMIN)
                                    .toList());

            userRepository.save(userToSave);
            return "redirect:login";
        }

        return "redirect:register";
    }


    private UserEntity checkIfExistingUser(UserRegisterDto userRegisterDto) {
        return userRepository.findUserByEmail(userRegisterDto.getEmail())
            .orElse(null);
    }

}
