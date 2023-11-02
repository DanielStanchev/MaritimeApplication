package com.example.maritimeapp.web;

import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.CertificateAddDto;
import com.example.maritimeapp.service.CertificateService;
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
@RequestMapping("/certificates")
@Secured(Role.ADMIN)
public class CertificateAddController {

    private final CertificateService certificateService;

    public CertificateAddController(CertificateService certificateService) {this.certificateService = certificateService;}


    @GetMapping("/add")
    public String addCertificate(){
        return "certificate-add";
    }

    @PostMapping("/add")
    public String addCertificateConfirm(@Valid CertificateAddDto certificateAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return certificateService.addCertificate(certificateAddDto, bindingResult, redirectAttributes);
    }

    @ModelAttribute
    public CertificateAddDto certificateAddDto() {
        return new CertificateAddDto();
    }

}
