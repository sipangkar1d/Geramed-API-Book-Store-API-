package com.daniel.geramed.config;

import com.daniel.geramed.entity.Admin;
import com.daniel.geramed.entity.Role;
import com.daniel.geramed.entity.UserCredential;
import com.daniel.geramed.entity.constant.ERole;
import com.daniel.geramed.repository.UserCredentialRepository;
import com.daniel.geramed.security.BCryptUtils;
import com.daniel.geramed.service.AdminService;
import com.daniel.geramed.service.RoleService;
import com.daniel.geramed.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppInit implements CommandLineRunner {
    private final AdminService adminService;
    private final RoleService roleService;
    private final BCryptUtils bCryptUtils;
    private final UserCredentialService userCredentialService;

    @Value(value = "${geramed.email}")
    String email;
    @Value(value = "${geramed.password}")
    String password;

    @Override
    public void run(String... args) throws Exception {
        Optional<Admin> admin = adminService.findByEmail(email);
        if (admin.isEmpty()) {
            log.info("Creating first admin.");
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = userCredentialService.create(UserCredential.builder()
                    .email(email)
                    .password(bCryptUtils.hashPassword(password))
                    .roles(List.of(role))
                    .build());

            adminService.create(Admin.builder()
                    .email(userCredential.getEmail())
                    .name(userCredential.getEmail().substring(0, userCredential.getEmail().indexOf("@")))
                    .userCredential(userCredential)
                    .build());
        }
        log.info("First Admin was created.");
    }
}
