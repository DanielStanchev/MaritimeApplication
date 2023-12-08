package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final UserService userService;

    public SearchController(UserService userService) {this.userService = userService;}

    @Secured(Role.ADMIN)
    @GetMapping("/employees")
    public List<UserDto> searchUsers(@RequestParam String criteria) {
        if(!StringUtils.hasText(criteria)) {
            return userService.findAllEmployees();
        }

        return userService.searchEmployees(criteria);
    }
}
