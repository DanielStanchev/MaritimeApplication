package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserPositionHistory;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UserPositionHistoryRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.service.RoleService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserPositionHistoryRepository userPositionHistoryRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService,
                           UserPositionHistoryRepository userPositionHistoryRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.userPositionHistoryRepository = userPositionHistoryRepository;
    }

    @PostConstruct
    private void init() {
        initAdmin();
    }

    @Override
    public String register(UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userDto.getPassword().equals(userDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userDto", userDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userDto", bindingResult);

            return "redirect:register";
        }

        UserEntity existingUser = checkIfExistingUser(userDto);
        if (existingUser != null) {
            redirectAttributes.addFlashAttribute("existingUser", "User with such username or email already exists!");
            return "redirect:register";
        }

        UserEntity userToSave = modelMapper.map(userDto, UserEntity.class);
        userToSave.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userToSave.setRegistryDate(LocalDateTime.now());
        userToSave.setPosition(userDto.getPosition());
        userToSave.setRoles(roleService.findAll()
                                .stream()
                                .filter(r -> r.getRole()
                                    .equals(RoleEnum.USER))
                                .collect(Collectors.toSet()));

        userRepository.save(userToSave);
        return "redirect:login";
    }

    @Override
    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDto> getAllEmployees() {
        List<UserEntity> employees = userRepository.findAll();

        List<UserDto> employeesToShow = new ArrayList<>();

        for (UserEntity employee : employees) {
            if (employee.getRoles()
                .stream()
                .anyMatch(r -> r.getRole()
                    .equals(RoleEnum.ADMIN))) {
                continue;
            }
            UserDto user = modelMapper.map(employee, UserDto.class);
            employeesToShow.add(user);
        }

        return employeesToShow;
    }

    @Override
    public void changePositionOfUser(Long userId, PositionEnum position) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException(String.format("User with ID %d doest not exist", userId)));

        UserPositionHistory changeOfPositionHistory = new UserPositionHistory();
        changeOfPositionHistory.setPreviousPosition(user.getPosition().getDescription());
        user.setPosition(position);
        userRepository.save(user);

        changeOfPositionHistory.setNewPosition(user.getPosition().getDescription());
        changeOfPositionHistory.setDateOfChange(LocalDate.now());
        changeOfPositionHistory.setEmployee(user);
        userPositionHistoryRepository.save(changeOfPositionHistory);
    }

    @Override
    public Optional<UserEntity> findById(Long userId) {
        return userRepository.findById(userId);
    }

    private void initAdmin() {
        if (userRepository.count() != 0) {
            return;
        }

        UserEntity userAdmin = new UserEntity();
        userAdmin.setUsername("Daniel");
        userAdmin.setFirstName("Daniel");
        userAdmin.setLastName("Stanchev");
        userAdmin.setEmail("daniel@daniel");
        userAdmin.setPassword(passwordEncoder.encode("daniel"));
        userAdmin.setRoles(roleService.findAll()
                               .stream()
                               .filter(r -> r.getRole()
                                   .equals(RoleEnum.ADMIN))
                               .collect(Collectors.toSet()));
        userAdmin.setRegistryDate(LocalDateTime.now());
        userAdmin.setPosition(PositionEnum.MASTER);
        userRepository.save(userAdmin);
    }


    private UserEntity checkIfExistingUser(UserDto userDto) {
        return userRepository.findUserByUsernameOrEmail(userDto.getUsername(), userDto.getEmail())
            .orElse(null);
    }

}
