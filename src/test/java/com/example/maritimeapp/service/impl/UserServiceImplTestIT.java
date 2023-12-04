package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.UserPositionHistoryRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.service.RoleService;
import com.example.maritimeapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTestIT {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPositionHistoryRepository userPositionHistoryRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userServiceToTest;


    @Test
    void testRegisterConfirmWithCheckingIfUserNotExists() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        UserDto userDto = getUserDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(any(UserDto.class), eq(UserEntity.class))).thenReturn(new UserEntity());
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        String result = userServiceToTest.register(userDto, bindingResult, redirectAttributes);
        assertEquals("redirect:login", result);
    }

    @Test
    void testRegisterWithInvalidData() {
        UserDto invalidUserDto = new UserDto();
        invalidUserDto.setPassword("password");
        invalidUserDto.setConfirmPassword("differentPassword");
        String result = userServiceToTest.register(invalidUserDto, mock(BindingResult.class), mock(RedirectAttributes.class));
        assertEquals("redirect:register", result);
    }

    @Test
    void testRegisterFailWithoutCheckingIfUserNotExists() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        UserDto userDto = getUserDto();
        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(any(UserDto.class), eq(UserEntity.class))).thenReturn(new UserEntity());
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(new UserEntity()));
        String result = userServiceToTest.register(userDto, bindingResult, redirectAttributes);
        assertEquals("redirect:register", result);
    }

    @Test
    void getAllEmployees() {
        List<UserEntity> allEmployees = new ArrayList<>();
        allEmployees.add(getUserEntity());
        when(userRepository.findAll()).thenReturn(allEmployees);
        UserDto user = getUserDto();
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(user);
        assertEquals(1, userServiceToTest.getAllEmployees().size());
    }

    @Test
    void getUserByUsername(){
        UserEntity user = getUserEntity();
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        UserEntity toReturn = userRepository.findUserByUsername(user.getUsername()).orElse(null);
        assertEquals(user.getUsername(),toReturn.getUsername());
    }

    @Test
    void changePositionOfUserAndKeepHistory() {
        UserEntity user = getUserEntity();
        PositionEnum newPosition = PositionEnum.SECOND_OFFICER;
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserEntity existingUser = userRepository.findById(user.getId())
            .orElse(null);
        userServiceToTest.changePositionOfUserAndKeepHistory(existingUser.getId(), newPosition);
        assertEquals(newPosition.getDescription(), user.getPosition()
            .getDescription());
    }

    private UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(8L);
        userDto.setUsername("gabriela");
        userDto.setEmail("gabriela@localhost");
        userDto.setPassword("gabriela");
        userDto.setFirstName("gabriela");
        userDto.setLastName("gabriela");
        userDto.setConfirmPassword("gabriela");
        userDto.setPosition(PositionEnum.MASTER);
        return userDto;
    }

    private UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);
        user.setId(1L);
        user.setUsername("pesho");
        user.setFirstName("pesho");
        user.setLastName("pesho");
        user.setPassword("pesho");
        user.setEmail("pesho@localhost");
        user.setPosition(PositionEnum.THIRD_OFFICER);
        user.setRoles(Set.of(userE));
        return user;
    }

}
