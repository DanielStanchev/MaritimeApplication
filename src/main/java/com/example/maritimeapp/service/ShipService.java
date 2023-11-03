package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ShipService {
    String add(ShipDto songAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    ShipEntity findShipByShipName(String name);

    List<ShipDto> getShips();

    void removeShip(Long shipId);
}
