package com.daniel.geramed.service;

import com.daniel.geramed.entity.Role;
import com.daniel.geramed.entity.constant.ERole;

public interface RoleService {
    Role getOrSave(ERole role);
}
