package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ContractDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ContractService {
    String addContract(ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

}
