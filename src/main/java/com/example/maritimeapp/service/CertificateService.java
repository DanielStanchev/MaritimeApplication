package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.CertificateAddDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CertificateService {
    String addCertificate(CertificateAddDto certificateAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
