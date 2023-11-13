package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.ChangePositionDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.service.UserPositionHistoryService;
import com.example.maritimeapp.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserPositionHistoryService userPositionHistoryService;

    public UserController(UserService userService, UserPositionHistoryService userPositionHistoryService) {
        this.userService = userService;
        this.userPositionHistoryService = userPositionHistoryService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return userService.register(userDto, bindingResult, redirectAttributes);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailure(Model model) {

        model.addAttribute("bad_credentials", "true");

        return "login";
    }

    @Secured(Role.ADMIN)
    @GetMapping("/promote")
    public String showPromotePanel(Model model) {

        model.addAttribute("totalEmployeesInCompany", userService.getAllEmployees());
        model.addAttribute("positions", PositionEnum.values());
        model.addAttribute("getUpdatedUsers", userPositionHistoryService.getAllUsersWithChangedPosition());

        return "promote-position";
    }


    @Secured(Role.ADMIN)
    @PatchMapping("/{userId}/promote")
    public String promoteUser(@PathVariable("userId") Long userId,
     @RequestParam(value = "position", required = false) PositionEnum position) {

        userService.changePositionOfUserAndKeepHistory(userId, position);

        return "redirect:/users/promote";
    }

    @Secured(Role.ADMIN)
    @DeleteMapping("/delete")
    public String deleteAllHistory() {

        userPositionHistoryService.deleteAllPositionChangeHistory();

        return "redirect:/users/promote";
    }


    @ModelAttribute
    UserDto userRegisterDto() {
        return new UserDto();
    }

    @ModelAttribute
    ChangePositionDto changePositionDto(){
        return  new ChangePositionDto();
    }

}
