package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.AddPaidLeaveDto;
import com.example.maritimeapp.model.dto.PaidLeaveDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.service.PaidLeaveService;
import com.example.maritimeapp.service.UserPositionHistoryService;
import com.example.maritimeapp.service.UserService;
import com.example.maritimeapp.util.SecurityUtl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
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
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserPositionHistoryService userPositionHistoryService;
    private final PaidLeaveService paidLeaveService;

    public UserController(UserService userService, UserPositionHistoryService userPositionHistoryService, PaidLeaveService paidLeaveService) {
        this.userService = userService;
        this.userPositionHistoryService = userPositionHistoryService;
        this.paidLeaveService = paidLeaveService;
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
    public String promoteUser(@PathVariable("userId") Long userId, @RequestParam(value = "position", required = false) PositionEnum position) {
        userService.changePositionOfUser(userId, position);

        return "redirect:/users/promote";
    }

    @Secured(Role.ADMIN)
    @DeleteMapping("/delete")
    public String deleteAllHistory() {

        userPositionHistoryService.deleteAllPositionChangeHistory();

        return "redirect:/users/promote";
    }

    @GetMapping("/schedule-a-paid-leave")
    public String scheduleAPaidLeave() {
        return "schedule-a-paid-leave";
    }

    @PostMapping("/schedule-a-paid-leave")
    public String scheduleAPaidLeaveConfirm(@Valid AddPaidLeaveDto addPaidLeaveDto, BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {
        User loggedInUser = SecurityUtl.getLoggedInUser();
        return paidLeaveService.schedulePaidLeave(addPaidLeaveDto, bindingResult, redirectAttributes, loggedInUser.getUsername());
    }

    @GetMapping("paid-leave-status")
    public String showMyPaidLeave(Model model) {

        User loggedInUser = SecurityUtl.getLoggedInUser();

        List<PaidLeaveDto> paidLeaveByEmployee = paidLeaveService.getPaidLeaveByUser(loggedInUser.getUsername());

        model.addAttribute("paidLeaveByEmployee", paidLeaveByEmployee);
        return "paid-leave-status";
    }

    @Secured(Role.ADMIN)
    @GetMapping("show-all-paid-leave")
    public String showAllPaidLeave(Model model) {
        model.addAttribute("assessments", paidLeaveService.getAllPaidLeaveAssessments());
        model.addAttribute("allStatuses", paidLeaveService.showStatuses());
        return "show-all-paid-leave";
    }

    @Secured(Role.ADMIN)
    @PatchMapping("/{userId}/show-all-paid-leave")
    public String changeStatusOfPaidLeave(@PathVariable("userId") Long userId,
                                          @RequestParam(value = "status", required = false) PaidLeaveStatusEnum status) {

        paidLeaveService.changeStatusOfPaidLeaveAssessment(userId, status);

        return "redirect:/users/show-all-paid-leave";
    }


    @ModelAttribute
    AddPaidLeaveDto addPaidLeaveDto() {
        return new AddPaidLeaveDto();
    }

    @ModelAttribute
    UserDto userRegisterDto() {
        return new UserDto();
    }
}
