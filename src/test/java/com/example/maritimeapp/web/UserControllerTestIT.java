package com.example.maritimeapp.web;


import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.model.entity.enums.PositionEnum;
import com.example.maritimeapp.model.entity.enums.RoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testRegistrationPageShown() throws Exception {
        mockMvc.perform(get("/users/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
    }

    @Test
    void testRegistration() throws Exception {

        mockMvc.perform(

                MockMvcRequestBuilders.post("/users/register")
                    .param("username", "pesho")
                    .param("firstName", "pesho")
                    .param("lastName", "pesho")
                    .param("email", "pesho@pesho")
                    .param("password", "pesho")
                    .param("confirmPassword", "pesho")
                    .param("position", "MASTER")
                    .with(csrf())

            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("login"));

    }


    @Test
    void testRegistrationFail() throws Exception {

        mockMvc.perform(
        MockMvcRequestBuilders.post("/users/register")
            .param("username", "pesho")
            .param("firstName", "pesho")
            .param("lastName", "pesho")
            .param("email", " ")
            .param("password", "pesho")
            .param("confirmPassword", "pesho")
            .param("position", "MASTER")
            .with(csrf())
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("register"));
    }

    @Test
    void testLoginPageShown() throws Exception{

        mockMvc.perform(get("/users/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"));

    }


    private UserEntity getUserEntity() {
        UserEntity user = new UserEntity();
        RoleEntity adminE = new RoleEntity();
        adminE.setRole(RoleEnum.ADMIN);
        RoleEntity userE = new RoleEntity();
        userE.setRole(RoleEnum.USER);

        user.setId(3L);
        user.setUsername("pesho");
        user.setFirstName("pesho");
        user.setLastName("pesho");
        user.setPassword("pesho");
        user.setEmail("pesho@localhost");
        user.setPosition(PositionEnum.THIRD_OFFICER);
        user.setRoles(List.of(adminE, userE));
        return user;
    }


}
