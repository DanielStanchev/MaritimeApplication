package com.example.maritimeapp.service.impl;

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
    public String addDocument(DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("documentDto", documentDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.documentDto", bindingResult);

            return "redirect:add";
        }

        DocumentEntity documentToSave = modelMapper.map(documentDto, DocumentEntity.class);

        documentToSave.setType(documentDto.getDocumentType());

        UserEntity possessor = userService.findUserByUsername(username)
            .orElse(null);
        documentToSave.setPossessor(possessor);
        documentToSave.setStatus(StatusEnum.VALID);
        documentRepository.save(documentToSave);
        return "redirect:/documents/show";
    }

    @Override
    public List<DocumentDto> getDocumentsByUsername(String username) {

        UserEntity possessor = userService.findUserByUsername(username)
            .orElse(null);

        return documentRepository.findAllByPossessor(possessor)
            .stream()
            .map(d -> {
                DocumentDto docToShow = modelMapper.map(d, DocumentDto.class);
                docToShow.setDocumentType(d.getType());
                return docToShow;
            })
            .toList();
    }

    @Override
    public void removeDocument(Long documentId,String username) {

        DocumentEntity document = documentRepository.findById(documentId)
            .orElse(null);

        if (document != null) {
            if (document.getPossessor().getUsername().equals(username)) {
                documentRepository.delete(document);
            } else {
                throw new SecurityException("User does not have permission to delete this document");
            }
        } else {
            throw new NullPointerException("Document not found");
        }
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentRepository.findAll()
            .stream()
            .map(d -> {
                UserEntity possessor = userService.findUserByUsername(d.getPossessor()
                                                                          .getUsername())
                    .orElse(null);

                DocumentDto documentToShow = modelMapper.map(d, DocumentDto.class);
                documentToShow.setDocumentType(d.getType());
                documentToShow.setPossessor(modelMapper.map(possessor, UserDto.class));
                return documentToShow;
            })
            .toList();
    }

    @Override
    public void checkIfDocumentExpiredAndChangeStatus() {
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
