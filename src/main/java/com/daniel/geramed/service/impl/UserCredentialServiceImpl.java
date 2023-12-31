package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.UserCredential;
import com.daniel.geramed.repository.UserCredentialRepository;
import com.daniel.geramed.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {
    private final UserCredentialRepository userCredentialRepository;
    @Override
    public UserCredential getByEmail(String email) {
        return userCredentialRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public UserCredential create(UserCredential userCredential) {
        return userCredentialRepository.saveAndFlush(userCredential);
    }

    @Override
    public UserCredential activate(String email) {
        UserCredential userCredential = getByEmail(email);
        userCredential.setIsActive(true);
        return userCredentialRepository.save(userCredential);
    }

    @Override
    public UserCredential deactivate(String email) {
        UserCredential userCredential = getByEmail(email);
        userCredential.setIsActive(false);
        return userCredentialRepository.save(userCredential);
    }
}
