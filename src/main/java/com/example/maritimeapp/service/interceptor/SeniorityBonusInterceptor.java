package com.example.maritimeapp.service.interceptor;

import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

@Component
public class SeniorityBonusInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SeniorityBonusInterceptor.class);

    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final UsersSalaryHistoryRepository usersSalaryHistoryRepository;

    public SeniorityBonusInterceptor(ContractRepository contractRepository, UserRepository userRepository,
                                     UsersSalaryHistoryRepository usersSalaryHistoryRepository) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.usersSalaryHistoryRepository = usersSalaryHistoryRepository;
    }


    @Override
    @SuppressWarnings("unchecked")
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if (pathVariables == null || pathVariables.get("contractId") == null) {
            logger.info("ContractId is null");
            return;
        }

        final Long contractId = Long.valueOf(pathVariables.get("contractId"));
        final ContractEntity contractNeeded = contractRepository.findById(contractId)
            .orElse(null);

        if (contractNeeded != null && contractNeeded.getNumberOfPayRaises() == 5) {
            final UserEntity employee = userRepository.findUserEntityByContractsContains(contractNeeded);
            final UserSalaryHistory salaryToUpdate = usersSalaryHistoryRepository.findLatestByIdForEmployee(employee.getId()).
                orElseThrow(() -> new IllegalStateException("User salary history cannot be null."));

            contractNeeded.setSalary(contractNeeded.getSalary().add(BigDecimal.valueOf(500)));
            salaryToUpdate.setNewSalary(contractNeeded.getSalary());

            contractRepository.save(contractNeeded);
            usersSalaryHistoryRepository.save(salaryToUpdate);

            logger.info("Bonus for 5th pay raise given!");
        }
    }
}