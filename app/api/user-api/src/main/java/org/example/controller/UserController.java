package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.dto.request.LoginApiRequest;
import org.example.controller.dto.response.LoginApiResponse;
import org.example.controller.dto.response.ReissueApiResponse;
import org.example.controller.dto.response.UserProfileApiResponse;
import org.example.dto.response.SuccessResponse;
import org.example.dto.response.SuccessResponse.Empty;
import org.example.security.dto.AuthenticatedInfo;
import org.example.security.dto.TokenParam;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "회원가입 / 로그인")
    public SuccessResponse<LoginApiResponse> signUp(@Valid @RequestBody LoginApiRequest request) {
        TokenParam token = userService.login(request.toServiceType());

        return SuccessResponse.ok(LoginApiResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    @Operation(
        summary = "로그아웃",
        responses = {
            @ApiResponse(responseCode = "200", description = "Empty Data")
        }
    )
    public SuccessResponse<Empty> logout(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        userService.logout(info.userId(), info.accessToken());
        return SuccessResponse.emptyData();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/withdrawal")
    @Operation(
        summary = "회원탈퇴",
        responses = {
            @ApiResponse(responseCode = "200", description = "Empty Data")
        }
    )
    public SuccessResponse<Empty> withdraw(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        userService.withdraw(info.userId(), info.accessToken());
        return SuccessResponse.emptyData();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/reissue")
    @Operation(
        summary = "토큰 재발급",
        parameters = {
            @Parameter(
                    in = ParameterIn.HEADER,
                    name = "Refresh",
                    description = "리프레시 토큰",
                required = true)
            }
    )
    public SuccessResponse<ReissueApiResponse> reissue(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        TokenParam reissueToken = userService.reissue(info.userId(), info.refreshToken());

        return SuccessResponse.ok(ReissueApiResponse.builder()
                .accessToken(reissueToken.accessToken())
                .refreshToken(reissueToken.refreshToken())
                .build());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile")
    @Operation(summary = "회원 정보")
    public SuccessResponse<UserProfileApiResponse> profile(
        @AuthenticationPrincipal AuthenticatedInfo info
    ) {
        var profile = userService.findUserProfile(info.userId());

        return SuccessResponse.ok(UserProfileApiResponse.from(profile));
    }
}
