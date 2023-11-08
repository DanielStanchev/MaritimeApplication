package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ContractDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface ContractService {
    String addContract(ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    List<ContractDto> getAllContracts();

    void removeContract(Long contractId);

    List<ContractDto> getContractsByUser();
}
