package com.daniel.geramed.util;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.of("Admin");
//        }
//
//        return Optional.ofNullable(authentication.getName());
        return Optional.ofNullable("Daniel Sipangkar");
    }
}

