package org.example.user.service;

import lombok.RequiredArgsConstructor;
import org.example.usecase.AdminUseCase;
import org.example.user.service.dto.request.AdminLoginServiceRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminUseCase adminUseCase;

    public void signup(AdminLoginServiceRequest adminLoginServiceRequest) {
        adminUseCase.save(adminLoginServiceRequest.toAdmin());
    }

}
