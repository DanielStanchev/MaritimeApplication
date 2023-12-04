package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.AddContractDto;
import com.example.maritimeapp.model.dto.ContractDto;
import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import com.example.maritimeapp.service.ContractService;
import com.example.maritimeapp.service.ShipService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ContractServiceImpl implements ContractService {

    private final ModelMapper modelMapper;
    private final ContractRepository contractRepository;
    private final UserService userService;
    private final ShipService shipService;
    private final UsersSalaryHistoryRepository usersSalaryHistoryRepository;
    private final UserRepository userRepository;

    public ContractServiceImpl(ModelMapper modelMapper, ContractRepository contractRepository, UserService userService, ShipService shipService,
                               UsersSalaryHistoryRepository usersSalaryHistoryRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.contractRepository = contractRepository;
        this.userService = userService;
        this.shipService = shipService;
        this.usersSalaryHistoryRepository = usersSalaryHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String addContract(AddContractDto addContractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addContractDto", addContractDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addContractDto", bindingResult);

            return "redirect:add";
        }

        ContractEntity contractToSave = modelMapper.map(addContractDto, ContractEntity.class);

        UserEntity employee = userService.findById(addContractDto.getEmployeeId())
            .orElseThrow(() -> new IllegalArgumentException(String.format("Employee with ID: %d does not exist.",
                                                                         addContractDto.getEmployeeId())));

        ContractEntity existingContract =
            contractRepository.findIfThereIsAlreadyExistingContract(employee.getUsername(), contractToSave.getStartDate(),
                                                                    contractToSave.getDisembarkDate()).orElse(null);

        if (existingContract != null) {
            redirectAttributes.addFlashAttribute("message", "Employee is still under ongoing contract!");
            return "redirect:add";
        }

        ShipEntity currentShip = shipService.findById(addContractDto.getShipId())
            .orElseThrow(() -> new IllegalArgumentException(String.format("Ship with ID: %d does not exist.", addContractDto.getShipId())));
        Set<ContractEntity> contractsByUser = employee.getContracts();
        contractsByUser.add(contractToSave);
        Set<ContractEntity> contractsByShip = currentShip.getContracts();
        contractsByShip.add(contractToSave);
        Set<UserEntity> crew = currentShip.getCrewMember();
        crew.add(employee);
        employee.setUserShip(currentShip);
        contractToSave.setPossessor(employee);
        contractToSave.setShip(currentShip);


        contractRepository.save(contractToSave);
        userRepository.save(employee);
        return "redirect:/contracts/show";
    }

    @Override
    public List<ContractDto> getAllContracts() {

        return contractRepository.findAll()
            .stream()
            .map(c -> {
                ContractDto contractToShow = modelMapper.map(c, ContractDto.class);

                UserEntity possessor = userService.findUserByUsername(c.getPossessor()
                                                                          .getUsername())
                    .orElse(null);

                ShipEntity ship = shipService.findShipByShipName(c.getShip()
                                                                     .getName());

                contractToShow.setEmployee(modelMapper.map(possessor, UserDto.class));
                contractToShow.setShip(modelMapper.map(ship, ShipDto.class));
                return contractToShow;

            })
            .toList();

    }

    @Override
    public void removeContract(Long contractId) {
        ContractEntity contract = contractRepository.findById(contractId)
            .orElseThrow(()->new IllegalArgumentException(String.format("Contract with ID %d does not exist",contractId)));

        UserEntity user = userRepository.findUserEntityByContractsContains(contract);

        user.setUserShip(null);

        userRepository.save(user);
        contractRepository.delete(contract);

    }

    @Override
    public List<ContractDto> getContractsByUser(String username) {

        UserEntity employee = userService.findUserByUsername(username)
            .orElse(null);
        return contractRepository.findAllByPossessor(employee)
            .stream()
            .map(c -> {
                ContractDto contractToShow = modelMapper.map(c, ContractDto.class);
                contractToShow.setShip(modelMapper.map(c.getShip(), ShipDto.class));
                return contractToShow;
            })
            .toList();
    }

    @Override
    public void payRaiseAndKeepHistory(Long contractId, BigDecimal bonusAmount) {

        final ContractEntity contract = contractRepository.findById(contractId)
            .orElseThrow(()-> new IllegalArgumentException(String.format("Contract with ID %d does not exist",contractId)));

        UserSalaryHistory changeOfSalaryHistory = new UserSalaryHistory();
        changeOfSalaryHistory.setPreviousSalary(contract.getSalary());

        contract.setSalary(contract.getSalary()
                               .add(bonusAmount));
        contract.setNumberOfPayRaises(contract.getNumberOfPayRaises() + 1);
        contractRepository.save(contract);
        changeOfSalaryHistory.setNewSalary(contract.getSalary());
        changeOfSalaryHistory.setDateOfChange(LocalDate.now());
        changeOfSalaryHistory.setEmployees(contract.getPossessor());
        usersSalaryHistoryRepository.save(changeOfSalaryHistory);
    }
}
