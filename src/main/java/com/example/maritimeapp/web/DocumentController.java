package com.example.maritimeapp.web;


import com.example.maritimeapp.constants.Role;
import com.example.maritimeapp.model.dto.DocumentDto;
import com.example.maritimeapp.service.DocumentService;
import com.example.maritimeapp.util.SecurityUtl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
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
import java.util.List;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {this.documentService = documentService;}

    @GetMapping("/add")
    public String addDocuments() {
        return "document-add";
    }

    @PostMapping("/add")
    public String addDocumentConfirm(@Valid DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        User loggedInUser = SecurityUtl.getLoggedInUser();
        return documentService.addDocument(documentDto, bindingResult, redirectAttributes,loggedInUser.getUsername());
    }

    @GetMapping("/show")
    public String allDocuments(Model model) {

        User loggedInUser = SecurityUtl.getLoggedInUser();
        List<DocumentDto> userDocuments = documentService.getDocumentsByUsername(loggedInUser.getUsername());

        model.addAttribute("documents", userDocuments);

        return "all-documents";
    }

    @DeleteMapping("/{documentId}/remove")
    public String removeDocument(@PathVariable("documentId") Long documentId) {
        User loggedInUser = SecurityUtl.getLoggedInUser();
        documentService.removeDocument(documentId,loggedInUser.getUsername());
        return "redirect:/documents/show";
    }

    @Secured(Role.ADMIN)
    @GetMapping("/show-all")
    public String allDocumentsToAdmin(Model model) {

        model.addAttribute("documents", documentService.getAllDocuments());

        return "all-documents-to-admin";
    }

    @ModelAttribute
    public DocumentDto documentAddDto() {
        return new DocumentDto();
    }


}
