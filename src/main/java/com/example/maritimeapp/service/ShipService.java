package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.CertificateAddDto;
import com.example.maritimeapp.model.dto.ShipAddDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ShipService {
    String add(ShipAddDto songAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    ShipEntity findShipByShipName(String name);
}
