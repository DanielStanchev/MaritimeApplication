package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.CertificateDto;
import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.repository.ShipRepository;
import com.example.maritimeapp.service.ShipService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    private final ShipRepository shipRepository;
    private final ModelMapper modelMapper;

    public ShipServiceImpl(ShipRepository shipRepository, ModelMapper modelMapper) {
        this.shipRepository = shipRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String addShip(ShipDto shipDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("shipDto", shipDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.shipDto", bindingResult);

            return "redirect:add";
        }

        ShipEntity shipToSave = modelMapper.map(shipDto, ShipEntity.class);

        shipRepository.save(shipToSave);

        return "redirect:show";
    }

    @Override
    public List<ShipDto> getShips() {
        return shipRepository.findAll()
            .stream()
            .map(ship -> {
                ShipDto shipDto = modelMapper.map(ship, ShipDto.class);
                List<CertificateDto> certificatesToShow = ship.getCertificates().stream()
                    .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                    .toList();
                shipDto.setCertificates(certificatesToShow);
                return shipDto;
            })
            .toList();
    }

    @Override
    public void removeShip(Long shipId, RedirectAttributes redirectAttributes) {
        ShipEntity ship = shipRepository.findById(shipId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Ship with ID %d does not exist", shipId)));

        if (!ship.getContracts().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                                                 "Ship has assigned employee contracts. Before delete you should first manage the contracts !");
        } else {
            shipRepository.delete(ship);
        }
    }

    @Override
    public Optional<ShipEntity> findById(Long shipId) {
        return shipRepository.findById(shipId);
    }

}

