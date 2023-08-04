package com.daniel.geramed.service;

import com.daniel.geramed.entity.Admin;

import java.util.Optional;

public interface AdminService {
    Admin create(Admin admin);
    Admin findById(String id);
}
