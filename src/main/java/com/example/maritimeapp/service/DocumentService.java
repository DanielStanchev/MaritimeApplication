package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.DocumentAddDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface DocumentService {
    String addDocument(DocumentAddDto documentAddDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    void initDocs();
}
