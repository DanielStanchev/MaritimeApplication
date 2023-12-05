package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.CertificateDto;
import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.ShipService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/certificates")
@Secured(Role.ADMIN)
public class CertificateController {

    private final CertificateService certificateService;
    private final ShipService shipService;

    public CertificateController(CertificateService certificateService, ShipService shipService) {
        this.certificateService = certificateService;
        this.shipService = shipService;
    }

    @GetMapping("/add")
    public String addCertificate(Model model) {
        model.addAttribute("ships", shipService.getShips());

        return "certificate-add";
    }

    @PostMapping("/add")
    public String addCertificateConfirm(@Valid CertificateDto certificateDto, BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        return certificateService.addCertificate(certificateDto, bindingResult, redirectAttributes);
    }

    @ModelAttribute
    public CertificateDto certificateAddDto() {
        return new CertificateDto();
    }
}
