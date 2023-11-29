package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.ContractDto;
import com.example.maritimeapp.service.ContractService;
import com.example.maritimeapp.service.ShipService;
import com.example.maritimeapp.service.UserSalaryHistoryService;
import com.example.maritimeapp.service.UserService;
import com.example.maritimeapp.util.SecurityUtl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    private final ContractService contractService;
    private final UserService userService;
    private final ShipService shipService;
    private final UserSalaryHistoryService userSalaryHistoryService;

    public ContractController(ContractService contractService, UserService userService, ShipService shipService,
                              UserSalaryHistoryService userSalaryHistoryService) {
        this.contractService = contractService;
        this.userService = userService;
        this.shipService = shipService;
        this.userSalaryHistoryService = userSalaryHistoryService;
    }

    @Secured(Role.ADMIN)
    @GetMapping("/add")
    public String add(Model model) {

        model.addAttribute("employees", userService.getAllEmployees());
        model.addAttribute("ships", shipService.getShips());

        return "contract-add";
    }

    @Secured(Role.ADMIN)
    @PostMapping("/add")
    public String addContractConfirm(@Valid ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return contractService.addContract(contractDto, bindingResult, redirectAttributes);
    }

    @Secured(Role.ADMIN)
    @GetMapping("/show")
    public String allContracts(Model model) {

        model.addAttribute("contracts", contractService.getAllContracts());

        return "all-contracts";
    }

    @Secured(Role.ADMIN)
    @DeleteMapping("/{contractId}/remove")
    public String removeContract(@PathVariable("contractId") Long contractId) {

        contractService.removeContract(contractId);
        return "redirect:/contracts/show";
    }

    @GetMapping("/show-my")
    public String showContractsByUser(Model model) {

        User loggedInUser = SecurityUtl.getLoggedInUser();

        List<ContractDto> userContacts = contractService.getContractsByUser(loggedInUser.getUsername());

        model.addAttribute("contractsByUser", userContacts);

        return "all-contracts-by-user";
    }

    @Secured(Role.ADMIN)
    @GetMapping("/pay-raise")
    public String showPayRaisePanel(Model model) {

        model.addAttribute("employees", contractService.getAllContracts());
        model.addAttribute("totalSalary", contractService.getAllContracts()
            .stream()
            .map(ContractDto::getSalary)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        model.addAttribute("getUsersWithRaise", userSalaryHistoryService.getAllUsersWithPayRaise());


        return "pay-raise";
    }


    @Secured(Role.ADMIN)
    @PatchMapping("/{contractId}/pay-raise")
    public String payRaiseToUser(@PathVariable("contractId") Long contractId,
                                 @RequestParam(value = "bonusAmount", required = false) BigDecimal bonusAmount) {

        logger.info(String.format("Bonus Amount %s", bonusAmount));
        contractService.payRaiseAndKeepHistory(contractId, bonusAmount);
        return "redirect:/contracts/pay-raise";
    }

    @Secured(Role.ADMIN)
    @DeleteMapping("/delete-history")
    public String deleteAllHistory() {

        userSalaryHistoryService.deleteAllPayRaiseHistory();
        return "redirect:/contracts/pay-raise";
    }

    @ModelAttribute
    public ContractDto contractDto() {
        return new ContractDto();
    }

}
