package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface ShipService {

    /**
     * Save ship in the DB
     *
     * @param shipAddDto         The ship data coming from the UI
     * @param bindingResult      Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes Pass data of HTTP request then redirecting
     */
    String addShip(ShipDto shipAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    /**
     * Return all ships in the DB
     */
    List<ShipDto> getShips();

    /**
     * Remove ship by id from DB
     *
     * @param shipId The id of a certain ship
     */
    void removeShip(Long shipId,RedirectAttributes redirectAttributes);

    /**
     * Return ship from the DB by her ID
     *
     * @param shipId The ID of the ship
     */
    Optional<ShipEntity> findById(Long shipId);
}
