package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.service.ShipService;
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
@RequestMapping("/ships")
@Secured(Role.ADMIN)
public class ShipController {

    private final ShipService shipService;

    public ShipController(ShipService shipService) {this.shipService = shipService;}

    @GetMapping("/add")
    public String add() {
        return "ship-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid ShipDto shipDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return shipService.add(shipDto, bindingResult, redirectAttributes);
    }

    @GetMapping("/show")
    public String allShips(Model model) {

        model.addAttribute("ships", shipService.getShips());

        return "all-ships";
    }

    @DeleteMapping ("/{shipId}/remove")
    public String removeShip(@PathVariable("shipId") Long shipId) {

        shipService.removeShip(shipId);
        return "redirect:/ships/show";
    }

    @ModelAttribute
    public ShipDto shipAddDto() {
        return new ShipDto();
    }


}
