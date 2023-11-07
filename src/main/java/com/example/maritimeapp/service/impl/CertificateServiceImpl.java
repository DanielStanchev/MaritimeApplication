package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.CertificateDto;
import com.example.maritimeapp.model.entity.CertificateEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.repository.CertificateRepository;
import com.example.maritimeapp.service.CertificateService;
import com.example.maritimeapp.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final ModelMapper modelMapper;
    private final ShipService shipService;

    public CertificateServiceImpl(CertificateRepository certificateRepository, ModelMapper modelMapper, ShipService shipService) {
        this.certificateRepository = certificateRepository;
        this.modelMapper = modelMapper;
        this.shipService = shipService;
    }

    @Override
    public String addCertificate(CertificateDto certificateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("certificateAddDto", certificateDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.certificateAddDto", bindingResult);

            return "redirect:add";
        }

        CertificateEntity certificateToSave = modelMapper.map(certificateDto, CertificateEntity.class);

        ShipEntity currentShip = shipService.findShipByShipName(certificateDto.getShip().getName());

        Set<CertificateEntity> certificates = currentShip.getCertificates();
        certificates.add(certificateToSave);

        certificateToSave.setShip(currentShip);

        certificateRepository.save(certificateToSave);

        return "redirect:/";
    }
}

