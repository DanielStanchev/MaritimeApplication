package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.DocumentDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface DocumentService {
    String addDocument(DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void initDocs();

    List<DocumentDto> getDocumentsByUser();

    void removeDocument(Long documentId);

    List<DocumentDto> getAllDocuments();
}
