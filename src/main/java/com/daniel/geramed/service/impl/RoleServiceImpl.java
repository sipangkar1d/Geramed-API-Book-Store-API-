package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.Role;
import com.daniel.geramed.entity.constant.ERole;
import com.daniel.geramed.repository.RoleRepository;
import com.daniel.geramed.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.save(Role.builder().role(role).build()));
    }

}
