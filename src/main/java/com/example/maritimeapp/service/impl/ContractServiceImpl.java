package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ContractDto;
import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.service.ContractService;
import com.example.maritimeapp.service.ShipService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;


@Service
public class ContractServiceImpl implements ContractService {

    private final ModelMapper modelMapper;
    private final ContractRepository contractRepository;
    private final UserService userService;
    private final ShipService shipService;

    public ContractServiceImpl(ModelMapper modelMapper, ContractRepository contractRepository, UserService userService, ShipService shipService) {
        this.modelMapper = modelMapper;
        this.contractRepository = contractRepository;
        this.userService = userService;
        this.shipService = shipService;
    }

    @Override
    public String addContract(ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contractDto", contractDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contractDto", bindingResult);

            return "redirect:add";
        }

        ContractEntity contractToSave = modelMapper.map(contractDto, ContractEntity.class);

        UserEntity employee = userService.findUserByUserName(contractDto.getEmployee()
                                                                 .getUsername());

        ShipEntity currentShip = shipService.findShipByShipName(contractDto.getShip()
                                                                    .getName());

        Set<ContractEntity> contractsByUser = employee.getContracts();
        contractsByUser.add(contractToSave);

        Set<ContractEntity> contractsByShip = currentShip.getContracts();
        contractsByShip.add(contractToSave);


        contractToSave.setPossessor(employee);
        contractToSave.setShip(currentShip);

        contractRepository.save(contractToSave);

        return "redirect:/";
    }

}
