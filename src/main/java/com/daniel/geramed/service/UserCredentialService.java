package com.daniel.geramed.service;

import com.daniel.geramed.entity.UserCredential;

public interface UserCredentialService {
    UserCredential getByEmail(String email);
    UserCredential create(UserCredential userCredential);
}

