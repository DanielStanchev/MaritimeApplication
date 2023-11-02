package com.example.maritimeapp.web;


import com.example.maritimeapp.model.dto.CertificateAddDto;
import com.example.maritimeapp.model.dto.DocumentAddDto;
import com.example.maritimeapp.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {this.documentService = documentService;}

    @GetMapping("/add")
    public String addDocuments(){
        return "document-add";
    }

    @PostMapping("/add")
    public String addDocumentConfirm(@Valid DocumentAddDto documentAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return documentService.addDocument(documentAddDto, bindingResult, redirectAttributes);
    }

    @ModelAttribute
    public DocumentAddDto documentAddDto() {
        return new DocumentAddDto();
    }


}
