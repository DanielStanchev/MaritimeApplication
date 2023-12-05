package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.AddContractDto;
import com.example.maritimeapp.model.dto.ContractDto;
import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import com.example.maritimeapp.service.ShipService;
import com.example.maritimeapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ContractServiceImplTest {

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ShipService shipService;

    @MockBean
    private UserService userService;

    @MockBean
    private UsersSalaryHistoryRepository usersSalaryHistoryRepository;

    @MockBean
    private ContractRepository contractRepository;

    @Test
    void testAddContract() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        AddContractDto contractDto = getAddContractDto();

        UserEntity employee = mock(UserEntity.class);
        RoleEntity adminE = new RoleEntity();
        adminE.setRole(RoleEnum.ADMIN);
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);
        employee.setId(1L);
        employee.setUsername("pesho");
        employee.setFirstName("pesho");
        employee.setLastName("pesho");
        employee.setPassword("pesho");
        employee.setEmail("pesho@localhost");
        employee.setPosition(PositionEnum.THIRD_OFFICER);
        employee.setRoles(Set.of(adminE, userE));
        employee.setContracts(Set.of(new ContractEntity(),new ContractEntity()));
        employee.setUserShip(new ShipEntity());

        ShipEntity shipEntity = mock(ShipEntity.class);
        shipEntity.setName("Misha");
        shipEntity.setCapacity(33000);
        shipEntity.setId(4L);
        shipEntity.setRegistryDate(LocalDate.of(2013, 12, 12));
        shipEntity.setFlag("Malta");
        shipEntity.setAdditionalInfo("No info");

        ContractEntity contractEntity = mock(ContractEntity.class);
        contractEntity.setId(8L);
        contractEntity.setSalary(BigDecimal.valueOf(5000));
        contractEntity.setNumberOfPayRaises(0);
        contractEntity.setPossessor(getUserEntity());
        contractEntity.setShip(new ShipEntity());

        when(modelMapper.map(contractDto, ContractEntity.class)).thenReturn(contractEntity);
        when(userService.findById(contractDto.getEmployeeId())).thenReturn(Optional.of(employee));
        when(shipService.findById(contractDto.getShipId())).thenReturn(Optional.of(shipEntity));

        when(employee.getContracts()).thenReturn(new HashSet<>());
        when(shipEntity.getContracts()).thenReturn(new HashSet<>());
        when(shipEntity.getCrewMember()).thenReturn(new HashSet<>());

        String result = contractServiceImpl.addContract(contractDto, bindingResult, redirectAttributes);

        assertEquals("redirect:/contracts/show", result);
    }

    @Test
    void payRaiseAndKeepHistory(){

        ContractEntity mockContract = new ContractEntity();
        mockContract.setId(1L);
        mockContract.setSalary(BigDecimal.valueOf(5000));
        mockContract.setNumberOfPayRaises(0);


        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(mockContract));

        contractServiceImpl.payRaise(1L, BigDecimal.valueOf(1000));

        assertEquals(BigDecimal.valueOf(6000), mockContract.getSalary());
        assertEquals(1, mockContract.getNumberOfPayRaises());

        verify(contractRepository, times(1)).save(mockContract);

        verify(usersSalaryHistoryRepository, times(1)).save(any(UserSalaryHistory.class));
    }


    @Test
    void testInvalidContact(){
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        AddContractDto contractDto = getAddContractDto();

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = contractServiceImpl.addContract(contractDto, bindingResult, redirectAttributes);

        assertEquals("redirect:add", result);

    }

    @Test
    void testGetContractsByUser() {

        ContractEntity contractEntity = getContractEntity1();


        when(userService.findUserByUsername(contractEntity.getPossessor()
                                                .getUsername())).thenReturn(Optional.of(contractEntity.getPossessor()));
        when(contractRepository.findAllByPossessor(contractEntity.getPossessor())).thenReturn(List.of(contractEntity));

        ContractDto contractDto = new ContractDto();
        contractDto.setId(4L);
        contractDto.setStartDate(LocalDate.of(2023, 12, 7));
        contractDto.setDisembarkDate(LocalDate.of(2024, 5, 12));
        contractDto.setShip(new ShipDto());
        contractDto.setSalary(BigDecimal.valueOf(13333));

        when(modelMapper.map(any(), eq(ContractDto.class))).thenReturn(contractDto);

        List<ContractDto> userContracts = contractServiceImpl.getContractsByUser(contractEntity.getPossessor()
                                                                                     .getUsername());

        assertFalse(userContracts.isEmpty());

    }

    @Test
    void testRemoveContract() {

        ContractEntity contractEntity = getContractEntity1();
        contractEntity.setPossessor(getUserEntity());

        when(contractRepository.findById(contractEntity.getId())).thenReturn(Optional.of(contractEntity));
        when(userRepository.findUserEntityByContractsContains(contractEntity)).thenReturn(contractEntity.getPossessor());
        contractServiceImpl.removeContract(contractEntity.getId());

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(contractRepository, times(1)).delete(contractEntity);
    }



    @Test
    void testGetAllContractsIfRepoIsEmpty() {

        List<ContractEntity> contractEntities = new ArrayList<>();
        assertTrue(contractServiceImpl.getAllContracts()
                       .isEmpty());
    }

    @Test
    void testGetAllContactsIfRepoIsNotEmpty() {
        ShipEntity ship = getShip();

        List<ContractEntity> contractEntities = new ArrayList<>();
        ContractEntity contact1 =getContractEntity1();
        contact1.setShip(ship);

        contractEntities.add(contact1);

        when(contractRepository.findAll()).thenReturn(contractEntities);

        UserEntity possessor = getUserEntity();

        when(userService.findById(anyLong())).thenReturn(Optional.of(possessor));
        when(shipService.findById(anyLong())).thenReturn(Optional.of(ship));

        ContractDto contractDto = new ContractDto();
        contractDto.setId(12L);
        contractDto.setShip(new ShipDto());
        contractDto.setDisembarkDate(LocalDate.of(2023, 12, 12));
        contractDto.setSalary(BigDecimal.valueOf(13000));
        contractDto.setStartDate(LocalDate.of(2023, 12, 7));

        when(modelMapper.map(any(), eq(ContractDto.class))).thenReturn(contractDto);

        assertEquals(1, contractServiceImpl.getAllContracts()
            .size());
    }

    private UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        RoleEntity adminE = new RoleEntity();
        adminE.setRole(RoleEnum.ADMIN);
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);

        user.setId(1L);
        user.setUsername("pesho");
        user.setFirstName("pesho");
        user.setLastName("pesho");
        user.setPassword("pesho");
        user.setEmail("pesho@localhost");
        user.setPosition(PositionEnum.THIRD_OFFICER);
        user.setRoles(Set.of(adminE, userE));
        user.setContracts(Set.of(new ContractEntity(),new ContractEntity()));
        user.setUserShip(new ShipEntity());
        return user;
    }

    private ContractEntity getContractEntity1() {
        ContractEntity contractEntity1 = new ContractEntity();
        contractEntity1.setId(8L);
        contractEntity1.setSalary(BigDecimal.valueOf(5000));
        contractEntity1.setNumberOfPayRaises(0);
        contractEntity1.setPossessor(getUserEntity());
        contractEntity1.setShip(new ShipEntity());
        return contractEntity1;
    }

    private ContractEntity getContractEntity2() {
        ContractEntity contractEntity2 = new ContractEntity();
        contractEntity2.setId(7L);
        contractEntity2.setSalary(BigDecimal.valueOf(5000));
        contractEntity2.setNumberOfPayRaises(0);
        contractEntity2.setPossessor(getUserEntity());
        contractEntity2.setShip(new ShipEntity());
        return contractEntity2;
    }

    private AddContractDto getAddContractDto() {
        AddContractDto contractDto = new AddContractDto();
        contractDto.setId(3L);
        contractDto.setShipId(4L);
        contractDto.setEmployeeId(5L);
        contractDto.setSalary(BigDecimal.valueOf(2000));
        contractDto.setDisembarkDate(LocalDate.of(2024, 9, 13));
        contractDto.setStartDate(LocalDate.of(2023, 12, 30));
        return contractDto;
    }

    private ShipEntity getShip(){
        ShipEntity ship = new ShipEntity();
        ship.setId(12L);
        ship.setContracts(Set.of(getContractEntity1(),getContractEntity2()));
        return ship;
    }
}