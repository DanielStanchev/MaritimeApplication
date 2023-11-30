package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.CertificateDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface CertificateService {

    /**
     * Add certificate to a certain ship
     * @param certificateDto Certificate info that is coming from the UI
     * @param bindingResult Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes  Pass data of HTTP request then redirecting
     */
    String addCertificate(CertificateDto certificateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);


    /**
     * Check if certain certificate expired and change status to Expired
     */
    void checkIfCertificateExpiredAndChangeStatus();
}
