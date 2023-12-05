package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.AddDocumentDto;
import com.example.maritimeapp.model.dto.DocumentDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import com.example.maritimeapp.repository.DocumentRepository;
import com.example.maritimeapp.service.DocumentService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;


    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper, UserService userService) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public String addDocument(AddDocumentDto addDocumentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDocumentDto", addDocumentDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addDocumentDto", bindingResult);

            return "redirect:add";
        }

        DocumentEntity documentToSave = modelMapper.map(addDocumentDto, DocumentEntity.class);

        UserEntity possessor = userService.findUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s does not exist", username)));

        documentToSave.setPossessor(possessor);
        documentToSave.setStatus(StatusEnum.VALID);
        documentRepository.save(documentToSave);
        return "redirect:/documents/show";
    }

    @Override
    public List<DocumentDto> getDocumentsByUsername(String username) {
        UserEntity possessor = userService.findUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s does not exist", username)));

        return documentRepository.findAllByPossessor(possessor)
            .stream()
            .map(d -> modelMapper.map(d, DocumentDto.class))
            .toList();
    }

    @Override
    public void removeDocument(Long documentId, String username) {
        DocumentEntity document = documentRepository.findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Document with ID: %d does not exist.", documentId)));

        if (document.getPossessor()
            .getUsername()
            .equals(username)) {
            documentRepository.delete(document);
        } else {
            throw new SecurityException("User does not have permission to delete this document");
        }
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentRepository.findAll()
            .stream()
            .map(d -> {
                DocumentDto documentToShow = modelMapper.map(d, DocumentDto.class);
                documentToShow.setPossessor(modelMapper.map(d.getPossessor(), UserDto.class));

                return documentToShow;
            })
            .toList();
    }

    @Override
    public void changeDocumentStatusIfExpired() {
        documentRepository.findAll()
            .forEach(this::updateStatusIfExpired);
    }

    private void updateStatusIfExpired(DocumentEntity document) {
        LocalDate expiry = document.getExpiryDate();
        if (expiry.isBefore(LocalDate.now())) {
            document.setStatus(StatusEnum.EXPIRED);
            documentRepository.save(document);
        }
    }
}
