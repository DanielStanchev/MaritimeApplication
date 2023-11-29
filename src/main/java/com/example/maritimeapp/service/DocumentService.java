package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.DocumentDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface DocumentService {
    String addDocument(DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,String username);

    void initDocs();

    List<DocumentDto> getDocumentsByUsername(String username);

    /**
     * Remove document by ID
     * @param documentId ID of the document to be removed
     */
    void removeDocument(Long documentId,String username);

    List<DocumentDto> getAllDocuments();

    /**
     * Expires document which expiration date has passed.
     */
    void expireDocuments();
}
