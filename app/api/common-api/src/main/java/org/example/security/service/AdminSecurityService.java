package org.example.security.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Admin;
import org.example.usecase.AdminUseCase;
import org.example.vo.UserRoleApiType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSecurityService implements UserDetailsService {

    private final AdminUseCase adminUseCase;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminUseCase.findByEmail(email);

        return new User(
            admin.getEmail(),
            passwordEncoder.encode(admin.getPassword()),
            UserRoleApiType.ADMIN.getAdminAuthority()
        );
    }
}
