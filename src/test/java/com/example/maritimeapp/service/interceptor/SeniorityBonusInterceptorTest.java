package com.example.maritimeapp.service.interceptor;

import com.example.maritimeapp.model.entity.ContractEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.UserSalaryHistory;
import com.example.maritimeapp.repository.ContractRepository;
import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.repository.UsersSalaryHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class SeniorityBonusInterceptorTest {

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersSalaryHistoryRepository usersSalaryHistoryRepository;

    @InjectMocks
    private SeniorityBonusInterceptor interceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testContractIdNull() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        verifyNoInteractions(contractRepository, userRepository, usersSalaryHistoryRepository);
    }

    @Test
    void testPostHandleFivePayRaises() throws Exception {

        ContractEntity contractEntity = new ContractEntity();
        contractEntity.setId(1L);
        contractEntity.setNumberOfPayRaises(5);
        contractEntity.setSalary(BigDecimal.valueOf(500));

        when(contractRepository.findById(anyLong())).thenReturn(Optional.of(contractEntity));

        UserEntity employee = new UserEntity();
        when(userRepository.findUserEntityByContractsContains(contractEntity)).thenReturn(employee);

        UserSalaryHistory salaryHistory = new UserSalaryHistory();
        salaryHistory.setNewSalary(BigDecimal.valueOf(1000));
        when(usersSalaryHistoryRepository.findLatestByIdForEmployee(employee.getId())).thenReturn(Optional.of(salaryHistory));

        MockHttpServletRequest request = new MockHttpServletRequest();
        Map<String, String> pathVariables = new HashMap<>();
        pathVariables.put("contractId", String.valueOf(contractEntity.getId()));
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, pathVariables);

        MockHttpServletResponse response = new MockHttpServletResponse();
        ModelAndView modelAndView = new ModelAndView();

        interceptor.postHandle(request, response, null, modelAndView);

        verify(contractRepository, times(1)).save(contractEntity);
        verify(usersSalaryHistoryRepository, times(1)).save(salaryHistory);
    }
}