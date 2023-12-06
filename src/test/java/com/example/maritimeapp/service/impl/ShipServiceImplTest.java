package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.repository.ShipRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ShipServiceImplTest {

    @Mock
    private ShipRepository shipRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ShipServiceImpl shipServiceToTest;

    @Test
    void testAddValidShip() {
        ShipDto shipDto = getShipDto();

        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = shipServiceToTest.addShip(shipDto, bindingResult, redirectAttributes);

        assertEquals("redirect:show", result);
    }

    @Test
    void invalidShip() {
        ShipDto shipDto = new ShipDto();
        shipDto.setFlag(null);

        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = shipServiceToTest.addShip(shipDto, bindingResult, redirectAttributes);

        assertEquals("redirect:add", result);
    }



    @Test
    void testFindShipByShipName() {
        String shipName = "Kenan T";

        ShipEntity sampleShip = new ShipEntity();
        sampleShip.setName(shipName);
        sampleShip.setCapacity(33000);
        sampleShip.setId(2L);
        sampleShip.setRegistryDate(LocalDate.of(2013, 12, 12));
        sampleShip.setFlag("Malta");
        sampleShip.setAdditionalInfo("No info");

        when(shipRepository.findById(sampleShip.getId())).thenReturn(Optional.of(sampleShip));

        Optional<ShipEntity> foundShip = shipServiceToTest.findById(sampleShip.getId());

        assertNotNull(foundShip);
        assertEquals(shipName, foundShip.get().getName());
    }

    @Test
    void testGetShips() {
        List<ShipEntity> shipEntities = new ArrayList<>();
        shipEntities.add(getShip1());
        shipEntities.add(getShip2());

        ShipDto ship = getShipDto();

        when(shipRepository.findAll()).thenReturn(shipEntities);
        when(modelMapper.map(any(), eq(ShipDto.class))).thenReturn(ship);

        List<ShipDto> ships = shipServiceToTest.getShips();

        assertEquals(2, ships.size());
    }

    @Test
    void testRemoveShip() {
        Long shipIdToRemove = 6L;
        ShipEntity shipEntityToRemove = getShip2();
        shipEntityToRemove.setId(shipIdToRemove);

        when(shipRepository.findById(shipIdToRemove)).thenReturn(Optional.of(shipEntityToRemove));

        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        shipServiceToTest.removeShip(shipIdToRemove,redirectAttributes);

        verify(shipRepository, times(1)).delete(shipEntityToRemove);
    }


    private ShipEntity getShip2() {
        ShipEntity ship2 = new ShipEntity();
        ship2.setName("Ivan");
        ship2.setCapacity(33000);
        ship2.setId(3L);
        ship2.setRegistryDate(LocalDate.of(2013, 12, 12));
        ship2.setFlag("Malta");
        ship2.setAdditionalInfo("No info");
        return ship2;
    }

    private ShipEntity getShip1() {
        ShipEntity ship1 = new ShipEntity();
        ship1.setName("Misha");
        ship1.setCapacity(33000);
        ship1.setId(4L);
        ship1.setRegistryDate(LocalDate.of(2013, 12, 12));
        ship1.setFlag("Malta");
        ship1.setAdditionalInfo("No info");
        return ship1;
    }

    private ShipDto getShipDto() {
        ShipDto shipDto = new ShipDto();
        shipDto.setId(1L);
        shipDto.setName("Kenan T");
        shipDto.setCapacity(25000);
        shipDto.setRegistryDate(LocalDate.of(2015, 11, 12));
        shipDto.setAdditionalInfo("No additional info");
        shipDto.setFlag("Bulgaria");
        return shipDto;
    }
}