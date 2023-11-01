package com.example.maritimeapp.init;

import com.example.maritimeapp.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements CommandLineRunner {

    private final RoleService roleService;

    public RoleInit(RoleService roleService) {this.roleService = roleService;}

    @Override
    public void run(String... args) throws Exception {

        roleService.initRoles();

    }
}
