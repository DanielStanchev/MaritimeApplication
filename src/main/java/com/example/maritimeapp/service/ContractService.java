package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.ContractDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

public interface ContractService {

    /**
     * Add contract to a certain user
     * @param contractDto Contract info that is coming from the UI
     * @param bindingResult Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes Pass data of HTTP request then redirecting
     */
    String addContract(ContractDto contractDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    /**
     * Return all contracts of all Users
     */
    List<ContractDto> getAllContracts();

    /**
     * Remove contract from User
     * @param contractId Certain contract of User
     */
    void removeContract(Long contractId);

    /**
     * All contracts assigned to a certain User
     * @param username The username of the logged User
     */
    List<ContractDto> getContractsByUser(String username);

    /**
     * Raise the salary of User and keep history of the raise
     * @param contractId Certain contract of User
     * @param bonusAmount The amount that is to be given on top the salary of certain User
     */
    void payRaiseAndKeepHistory(Long contractId, BigDecimal bonusAmount);
}
