package com.example.comment.controller.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotNull;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.vo.ReportType;

public record CommentReportApiRequest(
    @Parameter(
            example = "(GRAFFITI, PORNOGRAPHY, COMMERCIAL_AD, IMPERSONATION, PROFANITY, ETC, BLOCKING) 중 하나",
            description = "신고 타입")
    @NotNull(message = "신고 타입을 입력해주세요.")
    ReportType reportType
) {
    public CommentReportDomainRequest toServiceRequest() {
        return new CommentReportDomainRequest(reportType);
    }
}
