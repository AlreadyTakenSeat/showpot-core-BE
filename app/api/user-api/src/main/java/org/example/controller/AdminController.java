package org.example.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.dto.request.AdminLoginApiRequest;
import org.example.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/signup")
    public void signup(
        @Valid @RequestBody AdminLoginApiRequest adminLoginApiRequest
    ) {
        adminService.signup(adminLoginApiRequest.toServiceRequest());
    }

}
