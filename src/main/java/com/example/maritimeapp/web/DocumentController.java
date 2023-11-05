package com.example.maritimeapp.web;


import com.example.maritimeapp.model.dto.DocumentDto;
import com.example.maritimeapp.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    public String addDocumentConfirm(@Valid DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        return documentService.addDocument(documentDto, bindingResult, redirectAttributes);
    }

    @GetMapping("/show")
    public String allDocuments(Model model) {

        model.addAttribute("documents", documentService.getDocuments());

        return "all-documents";
    }

    @DeleteMapping("/{documentId}/remove")
    public String removeDocument(@PathVariable("documentId") Long documentId) {

        documentService.removeDocument(documentId);
        return "redirect:/documents/show";
    }



    @ModelAttribute
    public DocumentDto documentAddDto() {
        return new DocumentDto();
    }


}
