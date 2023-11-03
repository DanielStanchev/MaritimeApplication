package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.repository.ShipRepository;
import com.example.maritimeapp.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;
    private final ModelMapper modelMapper;

    public ShipServiceImpl(ShipRepository shipRepository, ModelMapper modelMapper) {
        this.shipRepository = shipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String add(ShipDto shipDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shipAddDto", shipDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shipAddDto", bindingResult);

            return "redirect:add";
        }

        ShipEntity shipToSave = modelMapper.map(shipDto, ShipEntity.class);

        shipRepository.save(shipToSave);

        return "redirect:show";
    }

    @Override
    public ShipEntity findShipByShipName(String name) {

        return shipRepository.findShipEntitiesByName(name)
            .orElse(null);
    }

    @Override
    public List<ShipDto> getShips() {

        return shipRepository.findAll()
            .stream()
            .map(s -> modelMapper.map(s, ShipDto.class))
            .toList();
    }

    @Override
    public void removeShip(Long shipId) {

        ShipEntity ship = shipRepository.findById(shipId).orElse(null);

       shipRepository.delete(ship);
    }
}

