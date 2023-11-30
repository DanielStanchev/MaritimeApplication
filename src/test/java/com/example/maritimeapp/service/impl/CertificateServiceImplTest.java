package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.CertificateDto;
import com.example.maritimeapp.model.dto.ShipDto;
import com.example.maritimeapp.model.entity.CertificateEntity;
import com.example.maritimeapp.model.entity.ShipEntity;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import com.example.maritimeapp.repository.CertificateRepository;
import com.example.maritimeapp.service.ShipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ShipService shipService;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Test
    void testAddCertificateWithNoErrors() {

        CertificateDto certificateDto = new CertificateDto();
        ShipDto shipDto = new ShipDto();
        shipDto.setName("Kenan T");
        certificateDto.setShip(shipDto);

        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        ShipEntity mockShip = new ShipEntity();
        when(shipService.findShipByShipName(any(String.class))).thenReturn(mockShip);

        CertificateEntity mockCertificateEntity = new CertificateEntity();
        when(modelMapper.map(any(CertificateDto.class), any(Class.class))).thenReturn(mockCertificateEntity);

        when(certificateRepository.save(any(CertificateEntity.class))).thenReturn(mockCertificateEntity);

        String result = certificateService.addCertificate(certificateDto, bindingResult, redirectAttributes);

        assertEquals("redirect:/", result);
    }

    @Test
    void testAddInvalidCertificate() {
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        CertificateDto certificateDto = new CertificateDto();
        ShipDto shipDto = new ShipDto();
        shipDto.setName("Kenan T");
        certificateDto.setShip(shipDto);


        when(bindingResult.hasErrors()).thenReturn(true);
        String result = certificateService.addCertificate(certificateDto, bindingResult, redirectAttributes);

        assertEquals("redirect:add", result);

    }

    @Test
    void testSetNewStatusIfExpired() {
        LocalDate now = LocalDate.now();

        CertificateEntity expiredCertificate = new CertificateEntity();
        expiredCertificate.setId(1L);
        expiredCertificate.setExpiryDate(now.minusDays(1));

        CertificateEntity validCertificate = new CertificateEntity();
        validCertificate.setId(2L);
        validCertificate.setExpiryDate(now.plusDays(1));

        List<CertificateEntity> certificates = new ArrayList<>();
        certificates.add(expiredCertificate);
        certificates.add(validCertificate);

        when(certificateRepository.findAll()).thenReturn(certificates);

        certificateService.checkIfCertificateExpiredAndChangeStatus();

        assertEquals(StatusEnum.EXPIRED, expiredCertificate.getStatus());
        assertNotEquals(StatusEnum.EXPIRED, validCertificate.getStatus());

        verify(certificateRepository, times(1)).save(expiredCertificate);
    }
}

