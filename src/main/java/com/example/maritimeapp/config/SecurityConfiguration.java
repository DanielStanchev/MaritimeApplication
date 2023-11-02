package com.example.maritimeapp.config;

import com.example.maritimeapp.repository.UserRepository;
import com.example.maritimeapp.service.impl.MaritimeAppUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(
                // Define which urls are visible by which users
                authorizeRequests -> authorizeRequests
                    // All static resources which are situated in js, images, css are available for anyone
                    .requestMatchers(PathRequest.toStaticResources()
                                         .atCommonLocations())
                    .permitAll()
                    // Allow anyone to see the home page, the registration page and the login form
                    .antMatchers("/", "/users/login", "/users/register", "/users/login-error")
                    .permitAll()
                    // all other requests are authenticated.
                    .anyRequest()
                    .authenticated())
            .formLogin(formLogin -> formLogin
                // redirect here when we access something which is not allowed.
                // also this is the page where we perform login.
                .loginPage("/users/login")
                // The names of the input fields (in our case in login.html)
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error"))
            .logout(logout -> logout
                // the URL where we should POST something in order to perform the logout
                .logoutUrl("/users/logout")
                // where to go when logged out?
                .logoutSuccessUrl("/")
                // invalidate the HTTP session
                .invalidateHttpSession(true))
            .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        // This service translates the mobilele users and roles
        // to representation which spring security understands.
        return new MaritimeAppUserDetailService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

}