package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.DocumentDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import com.example.maritimeapp.model.entity.enums.StatusEnum;
import com.example.maritimeapp.repository.DocumentRepository;
import com.example.maritimeapp.service.DocumentService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
            redirectAttributes.addFlashAttribute("documentAddDto", documentDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.documentAddDto", bindingResult);

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
    @PostConstruct
    public void initDocs() {
        if (documentRepository.count() != 0) {
            return;
        }

        Arrays.stream(DocumentTypeEnum.values())
            .forEach(docEnum -> {
                DocumentEntity document = new DocumentEntity();
                document.setType(docEnum);

                documentRepository.save(document);
            });
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
    public void removeDocument(Long documentId) {

        DocumentEntity document = documentRepository.findById(documentId)
            .orElse(null);
        documentRepository.delete(document);
    }

    @Override
    public List<DocumentDto> getAllDocuments() {
        return documentRepository.findAll()
            .stream()
            .skip(2)
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
    public void expireDocuments() {
        documentRepository.findAll().stream()
            .skip(2)
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
