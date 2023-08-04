package com.daniel.geramed.service.impl;

import com.daniel.geramed.entity.*;
import com.daniel.geramed.entity.constant.ERole;
import com.daniel.geramed.model.request.AuthRequest;
import com.daniel.geramed.model.response.LoginResponse;
import com.daniel.geramed.model.response.RegisterResponse;
import com.daniel.geramed.repository.UserCredentialRepository;
import com.daniel.geramed.security.BCryptUtils;
import com.daniel.geramed.security.JwtUtils;
import com.daniel.geramed.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;
    private final BCryptUtils bCryptUtils;
    private final UserCredentialRepository userCredentialRepository;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final StoreService storeService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            adminService.create(Admin.builder()
                    .email(userCredential.getEmail())
                    .name(userCredential.getEmail().substring(0, userCredential.getEmail().indexOf("@")))
                    .userCredential(userCredential)
                    .build());

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }
    }

    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_CUSTOMER);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            customerService.create(Customer.builder()
                    .email(userCredential.getEmail())
                    .name(userCredential.getEmail().substring(0, userCredential.getEmail().indexOf("@")))
                    .userCredential(userCredential)
                    .build());


            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }
    }

    @Override
    public RegisterResponse registerStore(AuthRequest request) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_STORE);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            storeService.create(Store.builder()
                    .email(userCredential.getEmail())
                    .name(userCredential.getEmail().substring(0, userCredential.getEmail().indexOf("@")))
                    .isActive(true)
                    .userCredential(userCredential)
                    .build());

            return RegisterResponse.builder()
                    .email(userCredential.getEmail())
                    .build();
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetailsImpl userDetail = (UserDetailsImpl) authenticate.getPrincipal();

        List<String> roles = userDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String token = jwtUtils.generateToken(userDetail.getEmail());
        return LoginResponse.builder()
                .email(userDetail.getEmail())
                .roles(roles)
                .token(token)
                .build();
    }
}
