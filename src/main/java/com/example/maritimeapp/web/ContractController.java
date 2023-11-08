package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.ContractDto;
import com.example.maritimeapp.service.ContractService;
import com.example.maritimeapp.service.ShipService;
import com.example.maritimeapp.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private final ContractService contractService;
    private final UserService userService;
    private final ShipService shipService;

    public ContractController(ContractService contractService, UserService userService, ShipService shipService) {
        this.contractService = contractService;
        this.userService = userService;
        this.shipService = shipService;
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
    public String showContractsByUser(Model model){

        model.addAttribute("contractsByUser",contractService.getContractsByUser());

        return "all-contracts-by-user";
    }

    @ModelAttribute
    public ContractDto contractDto() {
        return new ContractDto();
    }

}
