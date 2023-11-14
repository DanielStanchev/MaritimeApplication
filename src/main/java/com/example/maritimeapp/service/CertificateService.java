package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.CertificateDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CertificateService {
    String addCertificate(CertificateDto certificateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void setNewStatusIfExpiredCertificate();
}
