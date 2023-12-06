package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.CertificateDto;
import com.example.maritimeapp.model.entity.CertificateEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import com.example.maritimeapp.repository.CertificateRepository;
import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final ModelMapper modelMapper;
    private final CertificateRepository certificateRepository;
    private final ShipService shipService;

    public CertificateServiceImpl(ModelMapper modelMapper, CertificateRepository certificateRepository, ShipService shipService) {
        this.modelMapper = modelMapper;
        this.certificateRepository = certificateRepository;
        this.shipService = shipService;
    }

    @Override
    public String addCertificate(CertificateDto certificateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("certificateDto", certificateDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.certificateDto", bindingResult);

            return "redirect:add";
        }

        CertificateEntity certificateToSave = modelMapper.map(certificateDto, CertificateEntity.class);

        ShipEntity currentShip = shipService.findById(certificateDto.getShipId())
            .orElseThrow(() -> new IllegalArgumentException(String.format("No ship with ID: %d exists", certificateDto.getShipId())));

        Set<CertificateEntity> certificates = currentShip.getCertificates();
        certificates.add(certificateToSave);

        certificateToSave.setShip(currentShip);
        certificateToSave.setStatus(StatusEnum.VALID);

        certificateRepository.save(certificateToSave);
        return "redirect:/ships/show";
    }

    @Override
    public void changeCertificateStatusIfExpired() {
        certificateRepository.findAll()
            .forEach(this::updateStatusIfExpired);
    }

    private void updateStatusIfExpired(CertificateEntity certificate) {
        LocalDate expiry = certificate.getExpiryDate();

        if (expiry.isBefore(LocalDate.now())) {
            certificate.setStatus(StatusEnum.EXPIRED);
            certificateRepository.save(certificate);
        }
    }
}

