package com.example.maritimeapp.service.interceptor;

import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;


@Component
public class seniorityBonusInterceptor implements HandlerInterceptor {

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final UsersSalaryHistoryRepository usersSalaryHistoryRepository;

    public seniorityBonusInterceptor(ContractRepository contractRepository, UserRepository userRepository,
                                     UsersSalaryHistoryRepository usersSalaryHistoryRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;

        this.usersSalaryHistoryRepository = usersSalaryHistoryRepository;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables == null || pathVariables.get("contractId") == null) {
            System.out.println("ContractId is null");
            return;
        }

        final Long contractId = Long.valueOf(pathVariables.get("contractId"));

        final ContractEntity contractNeeded = contractRepository.findById(contractId)
            .orElse(null);


        if (contractNeeded != null && contractNeeded.getNumberOfPayRaises() == 5) {

            final UserEntity employee = userRepository.findUserEntityByContractsContains(contractNeeded);

            final UserSalaryHistory salaryToUpdate = usersSalaryHistoryRepository.findLatestByIdForEmployee(employee.getId()).orElse(null);

            contractNeeded.setSalary(contractNeeded.getSalary()
                                         .add(BigDecimal.valueOf(500)));

            salaryToUpdate.setNewSalary(contractNeeded.getSalary());


            contractRepository.save(contractNeeded);
            usersSalaryHistoryRepository.save(salaryToUpdate);

            System.out.println("Bonus for 2nd pay raise given!");
        }
    }
}