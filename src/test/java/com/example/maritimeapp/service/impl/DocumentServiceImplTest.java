package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.AddDocumentDto;
import com.example.maritimeapp.model.dto.DocumentDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import com.example.maritimeapp.repository.DocumentRepository;
import com.example.maritimeapp.service.UserService;
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
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private DocumentServiceImpl documentServiceToTest;


    @Test
    void testAddDocumentWithInvalidInput() {

        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        AddDocumentDto addDocumentDto = new AddDocumentDto();
        DocumentEntity document = new DocumentEntity();
        document.setId(1L);
        document.setPossessor(new UserEntity());

        String result = documentServiceToTest.addDocument(addDocumentDto, bindingResult, redirectAttributes, document.getPossessor()
            .getUsername());

        assertEquals("redirect:add", result);
    }

    @Test
    void testAddDocumentSuccess() {

        AddDocumentDto addDocumentDto = getAddDocumentDto();

        DocumentEntity document = getDocumentEntity1();

        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        when(modelMapper.map(any(AddDocumentDto.class), eq(DocumentEntity.class))).thenReturn(new DocumentEntity());

        when(userService.findUserByUsername(anyString())).thenReturn(Optional.of(document.getPossessor()));

        when(documentRepository.save(any(DocumentEntity.class))).thenReturn(new DocumentEntity());

        String result = documentServiceToTest.addDocument(addDocumentDto, bindingResult, redirectAttributes, document.getPossessor().getUsername());

        assertEquals("redirect:/documents/show", result);
    }

    @Test
    void testGetDocumentsByUsername() {
        DocumentEntity documentEntity = getDocumentEntity1();

        when(userService.findUserByUsername(documentEntity.getPossessor()
                                                .getUsername())).thenReturn(Optional.of(documentEntity.getPossessor()));
        when(documentRepository.findAllByPossessor(documentEntity.getPossessor())).thenReturn(List.of(documentEntity));

        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(4L);

        when(modelMapper.map(any(), eq(DocumentDto.class))).thenReturn(documentDto);

        List<DocumentDto> userDocuments = documentServiceToTest.getDocumentsByUsername(documentEntity.getPossessor()
                                                                                     .getUsername());

        assertFalse(userDocuments.isEmpty());

    }

    @Test
    void testRemoveDocument() {
        Long documentIdToRemove = 1L;
        DocumentEntity documentEntity = new DocumentEntity();
        UserEntity user = new UserEntity();
        user.setUsername("Ivan");
        documentEntity.setPossessor(user);

        when(documentRepository.findById(documentIdToRemove)).thenReturn(Optional.of(documentEntity));

        documentServiceToTest.removeDocument(documentIdToRemove,documentEntity.getPossessor().getUsername());

        verify(documentRepository, times(1)).delete(documentEntity);
    }

    @Test
    void testGetAllDocumentsIfRepoEmpty(){
        List<DocumentEntity> docs = new ArrayList<>();
        assertTrue(documentServiceToTest.getAllDocuments()
                       .isEmpty());
    }

    @Test
    void testGetAllDocumentsIfRepoIsNotEmpty(){

        List<DocumentEntity> docs = new ArrayList<>();
        docs.add(getDocumentEntity1());
        docs.add(getDocumentEntity2());

        when(documentRepository.findAll()).thenReturn(docs);

        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(1L);

        when(modelMapper.map(any(), eq(DocumentDto.class))).thenReturn(documentDto);

        assertEquals(2, documentServiceToTest.getAllDocuments()
            .size());
    }

    @Test
    void testExpireDocuments() {
        LocalDate now = LocalDate.now();

        DocumentEntity expiredDoc = new DocumentEntity();
        expiredDoc.setId(3L);
        expiredDoc.setExpiryDate(now.minusDays(2));

        DocumentEntity validDoc = new DocumentEntity();
        validDoc.setId(4L);
        validDoc.setExpiryDate(now.plusDays(2));

        List<DocumentEntity> documents = new ArrayList<>();
        documents.add(expiredDoc);
        documents.add(validDoc);

        when(documentRepository.findAll()).thenReturn(documents);

        documentServiceToTest.changeDocumentStatusIfExpired();

        assertEquals(StatusEnum.EXPIRED, expiredDoc.getStatus());
        assertNotEquals(StatusEnum.EXPIRED, validDoc.getStatus());

        verify(documentRepository, times(1)).save(expiredDoc);
    }

    private DocumentEntity getDocumentEntity1() {
        DocumentEntity documentEntity1 = new DocumentEntity();
        documentEntity1.setId(3L);
        documentEntity1.setStatus(StatusEnum.VALID);
        documentEntity1.setType(DocumentTypeEnum.CERTIFICATE_FOR_OFFICERS);
        documentEntity1.setPossessor(getUserEntity());
        documentEntity1.setDescription("No desc");
        documentEntity1.setExpiryDate(LocalDate.of(2024, 11, 10));
        documentEntity1.setIssueDate(LocalDate.of(2010, 11, 10));
        return documentEntity1;
    }

    private DocumentEntity getDocumentEntity2() {
        DocumentEntity documentEntity2 = new DocumentEntity();
        documentEntity2.setId(5L);
        documentEntity2.setStatus(StatusEnum.VALID);
        documentEntity2.setType(DocumentTypeEnum.CERTIFICATE_FOR_OFFICERS);
        documentEntity2.setPossessor(getUserEntity());
        documentEntity2.setDescription("No desc");
        documentEntity2.setExpiryDate(LocalDate.of(2024, 11, 10));
        documentEntity2.setIssueDate(LocalDate.of(2010, 11, 10));
        return documentEntity2;
    }

    private AddDocumentDto getAddDocumentDto() {
        AddDocumentDto documentDto = new AddDocumentDto();

        documentDto.setId(1L);
        documentDto.setType(DocumentTypeEnum.CERTIFICATE_FOR_OFFICERS);
        documentDto.setStatus(StatusEnum.VALID);
        documentDto.setDescription("No description");
        documentDto.setExpiryDate(LocalDate.of(2024, 11, 10));
        documentDto.setIssueDate(LocalDate.of(2010, 11, 10));

        return documentDto;
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
        return user;
    }



}
