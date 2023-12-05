package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.AddPaidLeaveDto;
import com.example.maritimeapp.model.dto.PaidLeaveDto;
import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface PaidLeaveService {

    /**
     * Schedule a paid leave and submit for approval
     * @param addPaidLeaveDto The paid leave data that is coming from the UI
     * @param bindingResult Manage data binding validation errors between a user input and validation annotations
     * @param redirectAttributes Pass data of HTTP request then redirecting
     * @param username The username of the logged User
     */
    String schedulePaidLeave(AddPaidLeaveDto addPaidLeaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, String username);

    /**
     * Show the paid leave sheets of certain User
     * @param username The username of the logged User
     */
    List<PaidLeaveDto> getPaidLeaveByUser(String username);

    /**
     * Return all paid leave assessments of all Users
     */
    List<PaidLeaveDto> getAllPaidLeaveAssessments();

    /**
     * Change the status of the paid leave assessment
     * @param userId The ID of a certain user
     * @param status The status change that is coming from the UI
     */
    void changeStatusOfPaidLeaveAssessment(Long userId, PaidLeaveStatusEnum status);

    /**
     * Return the appropriate status to Admin
     */
    List<PaidLeaveStatusEnum> showStatuses();
}
