package com.example.maritimeapp.service.impl;

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
    public String scheduleAPaidLeave(PaidLeaveDto paidLeaveDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, String username) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("paidLeaveDto", paidLeaveDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.paidLeaveDto", bindingResult);

            return "redirect:schedule-a-paid-leave";
        }

        PaidLeaveEntity paidLeaveToSubmit = modelMapper.map(paidLeaveDto, PaidLeaveEntity.class);

        UserEntity employee = userService.findUserByUsername(username)
            .orElse(null);
        paidLeaveToSubmit.setEmployee(employee);
        paidLeaveToSubmit.setStatus(PaidLeaveStatusEnum.PENDING);
        paidLeaveRepository.save(paidLeaveToSubmit);
        return "redirect:/users/paid-leave-status";
    }

    @Override
    public List<PaidLeaveDto> getPaidLeaveByUser(String username) {
        UserEntity employee = userService.findUserByUsername(username)
            .orElse(null);
        return paidLeaveRepository.findAllByEmployee(employee)
            .stream()
            .map(p -> modelMapper.map(p, PaidLeaveDto.class))
            .toList();
    }

    @Override
    public List<PaidLeaveDto> getAllPaidLeaveAssessments() {
        return paidLeaveRepository.findAll()
            .stream()
            .filter(p -> p.getStatus()
                .getDescription()
                .equals("Pending"))
            .map(p -> {
                PaidLeaveDto paidLeaveToShow = modelMapper.map(p, PaidLeaveDto.class);

                UserEntity possessor = userService.findUserByUsername(p.getEmployee()
                                                                          .getUsername())
                    .orElse(null);
                paidLeaveToShow.setEmployee(modelMapper.map(possessor, UserDto.class));
                return paidLeaveToShow;
            })
            .toList();

    }

    @Override
    public void changeStatusOfPaidLeaveAssessment(Long userId, PaidLeaveStatusEnum status) {

        final PaidLeaveEntity paidLeaveEntity = paidLeaveRepository.findById(userId)
            .orElseThrow(()->new IllegalArgumentException(String.format("User with ID %d does not exist",userId)));

        paidLeaveEntity.setStatus(status);
        paidLeaveRepository.save(paidLeaveEntity);
    }
}

