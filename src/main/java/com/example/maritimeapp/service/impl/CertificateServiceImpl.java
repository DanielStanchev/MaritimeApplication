package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.CertificateAddDto;
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
    public String addCertificate(CertificateAddDto certificateAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("certificateAddDto", certificateAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.certificateAddDto", bindingResult);

            return "redirect:add";
        }

        CertificateEntity certificateToSave = modelMapper.map(certificateAddDto, CertificateEntity.class);

        ShipEntity currentShip = shipService.findShipByShipName(certificateAddDto.getShipName());

        Set<CertificateEntity> certificates = currentShip.getCertificates();
        certificateToSave.setShip(currentShip);
        certificates.add(certificateToSave);

        certificateRepository.save(certificateToSave);

        return "redirect:/";
    }
}

