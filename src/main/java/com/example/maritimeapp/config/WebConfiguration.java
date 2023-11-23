package com.example.maritimeapp.config;

import com.example.maritimeapp.service.interceptor.SeniorityBonusInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final SeniorityBonusInterceptor seniorityBonusInterceptor;

    public WebConfiguration(SeniorityBonusInterceptor seniorityBonusInterceptor) {
        this.seniorityBonusInterceptor = seniorityBonusInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(seniorityBonusInterceptor)
            .addPathPatterns("/contracts/{contractId}/pay-raise");

    }
}
