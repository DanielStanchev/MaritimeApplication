package com.example.maritimeapp.service;

import com.example.maritimeapp.model.dto.PaidLeaveDto;
import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface PaidLeaveService {
    String scheduleAPaidLeave(PaidLeaveDto paidLeaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, String username);

    List<PaidLeaveDto> getPaidLeaveByUser(String username);

    List<PaidLeaveDto> getAllPaidLeaveAssessments();

    void changeStatusOfPaidLeaveAssessment(Long userId, PaidLeaveStatusEnum status);
}
