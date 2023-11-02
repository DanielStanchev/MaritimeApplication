package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.ShipAddDto;
import com.example.maritimeapp.service.ShipService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/ships")
@Secured(Role.ADMIN)
public class ShipAddController {

    private final ShipService shipService;

    public ShipAddController(ShipService shipService) {this.shipService = shipService;}

    @GetMapping("/add")
    public String add() {
        return "ship-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ShipAddDto shipAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return shipService.add(shipAddDto, bindingResult, redirectAttributes);
    }

    @ModelAttribute
    public ShipAddDto shipAddDto() {
        return new ShipAddDto();
    }


}
