package com.example.comment.controller.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.vo.ReportType;

public record CommentReportApiRequest(
    @Parameter(
            example = "(GRAFFITI, PORNOGRAPHY, COMMERCIAL_AD, IMPERSONATION, PROFANITY, ETC, BLOCKING) 중 하나",
            description = "신고 타입")
    @NotNull(message = "신고 타입을 입력해주세요.")
    ReportType reportType,

    @Size(max = 255, message = "직접 입력 내용은 최대 255자까지 입력 가능합니다.")
    @NotBlank
    @NotEmpty
    String directInput
) {
    public CommentReportDomainRequest toServiceRequest() {
        if (directInput != null) {
            return new CommentReportDomainRequest(reportType, directInput);
        }

        return new CommentReportDomainRequest(reportType, null);
    }
}
