package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.DocumentDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface DocumentService {

    /**
     * Add document to DB
     * @param documentDto The document info that is coming from the UI
     * @param bindingResult Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes Pass data of HTTP request then redirecting
     * @param username The username of the logged User
     */
    String addDocument(DocumentDto documentDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,String username);

    /**
     * @param username The username of the logged User
     * @return All documents possessed by certain User
     */
    List<DocumentDto> getDocumentsByUsername(String username);

    /**
     * Remove document by ID
     * @param documentId ID of the document to be removed
     * @param username The username of the logged User
     */
    void removeDocument(Long documentId,String username);

    /**
     * Return all documents of all Users
     */
    List<DocumentDto> getAllDocuments();

    /**
     * Check if certain document expired and change status to Expired
     */
    void checkIfDocumentExpiredAndChangeStatus();
}
