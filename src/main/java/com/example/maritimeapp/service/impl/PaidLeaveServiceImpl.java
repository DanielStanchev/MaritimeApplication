package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.dto.AddPaidLeaveDto;
import com.example.maritimeapp.model.dto.PaidLeaveDto;
import com.example.maritimeapp.model.dto.UserDto;
import com.example.maritimeapp.model.entity.PaidLeaveEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PaidLeaveStatusEnum;
import com.example.maritimeapp.repository.PaidLeaveRepository;
import com.example.maritimeapp.service.PaidLeaveService;
import com.example.maritimeapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Service
public class PaidLeaveServiceImpl implements PaidLeaveService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PaidLeaveRepository paidLeaveRepository;

    public PaidLeaveServiceImpl(ModelMapper modelMapper, UserService userService, PaidLeaveRepository paidLeaveRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.paidLeaveRepository = paidLeaveRepository;
    }

    @Override
    public String schedulePaidLeave(AddPaidLeaveDto addPaidLeaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                    String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addPaidLeaveDto", addPaidLeaveDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addPaidLeaveDto", bindingResult);

            return "redirect:schedule-a-paid-leave";
        }

        PaidLeaveEntity paidLeaveToSubmit = modelMapper.map(addPaidLeaveDto, PaidLeaveEntity.class);

        if(paidLeaveToSubmit.getDateFrom().isAfter(paidLeaveToSubmit.getDateTo())){
            redirectAttributes.addFlashAttribute("invalidEndDate", "End date should be before starting date !");
            return "redirect:schedule-a-paid-leave";
        }

        UserEntity employee = userService.findUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s does not exist", username)));

        PaidLeaveEntity existingPaidLeave = paidLeaveRepository.findIfThereIsAlreadyExistingPaidLeave(employee.getId(), paidLeaveToSubmit.getDateFrom(),
                                                                                                  paidLeaveToSubmit.getDateTo())
            .orElse(null);

        if (existingPaidLeave != null) {
            redirectAttributes.addFlashAttribute("invalidPaidLeave", "Employee has already scheduled a paid leave within selected dates!");
            return "redirect:schedule-a-paid-leave";
        }

        paidLeaveToSubmit.setEmployee(employee);
        paidLeaveToSubmit.setStatus(PaidLeaveStatusEnum.PENDING);
        paidLeaveRepository.save(paidLeaveToSubmit);

        return "redirect:/users/paid-leave-status";
    }

    @Override
    public List<PaidLeaveDto> getPaidLeaveByUser(String username) {
        UserEntity employee = userService.findUserByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s does not exist", username)));

        return paidLeaveRepository.findAllByEmployee(employee)
            .stream()
            .map(p -> modelMapper.map(p, PaidLeaveDto.class))
            .toList();
    }

    @Override
    public List<PaidLeaveDto> getAllPaidLeaveAssessments() {
        return paidLeaveRepository.findAllPendingPaidLeaveRequests()
            .stream()
            .map(p -> {
                PaidLeaveDto paidLeaveToShow = modelMapper.map(p, PaidLeaveDto.class);
                paidLeaveToShow.setEmployee(modelMapper.map(p.getEmployee(), UserDto.class));

                return paidLeaveToShow;
            })
            .toList();
    }

    @Override
    public void changeStatusOfPaidLeaveAssessment(Long paidLeaveEntityId, PaidLeaveStatusEnum status) {
        final PaidLeaveEntity paidLeaveEntity = paidLeaveRepository.findById(paidLeaveEntityId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("Paid leave entry with ID: %d does not exist", paidLeaveEntityId)));

        paidLeaveEntity.setStatus(status);
        paidLeaveRepository.save(paidLeaveEntity);
    }

    @Override
    public List<PaidLeaveStatusEnum> showStatuses() {
        return Arrays.stream(PaidLeaveStatusEnum.values())
            .filter(p -> !PaidLeaveStatusEnum.PENDING.equals(p))
            .toList();
    }
}

