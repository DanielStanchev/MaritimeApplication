package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.DocumentAddDto;
import com.example.maritimeapp.model.entity.DocumentEntity;
import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.DocumentTypeEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import com.example.maritimeapp.repository.DocumentRepository;
import com.example.maritimeapp.service.DocumentService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.Arrays;

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
    public String addDocument(DocumentAddDto documentAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("documentAddDto", documentAddDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.documentAddDto", bindingResult);

            return "redirect:add";
        }

        DocumentEntity documentToSave = modelMapper.map(documentAddDto, DocumentEntity.class);

        documentToSave.setType(documentAddDto.getDocument());

        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity possessor = userService.findUserByUserName(loggedInUser.getUsername());
        documentToSave.setPossessor(possessor);

        documentRepository.save(documentToSave);

        return "redirect:/";
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
}
