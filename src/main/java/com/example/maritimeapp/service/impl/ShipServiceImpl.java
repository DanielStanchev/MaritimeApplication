package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ShipAddDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.repository.ShipRepository;
import com.example.maritimeapp.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;
    private final ModelMapper modelMapper;

    public ShipServiceImpl(ShipRepository shipRepository, ModelMapper modelMapper) {this.shipRepository = shipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String add(ShipAddDto shipAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shipAddDto", shipAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shipAddDto", bindingResult);

            return "redirect:add";
        }

        ShipEntity shipToSave = modelMapper.map(shipAddDto, ShipEntity.class);

        shipRepository.save(shipToSave);

        return "redirect:/";
    }

    @Override
    public ShipEntity findShipByShipName(String name) {

        return shipRepository.findShipEntitiesByName(name).orElse(null);
    }
}

