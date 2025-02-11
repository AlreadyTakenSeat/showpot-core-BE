package org.example.dto.comment.request;

import java.util.UUID;
import org.example.entity.comment.Report;
import org.example.vo.ReportType;

public record CommentReportDomainRequest(
    ReportType reportType,
    String directInput
) {

    public Report toReport(UUID userId, UUID commentId) {
        if (directInput != null) {
            return Report.builder()
                .reportType(reportType)
                .directInput(directInput)
                .userId(userId)
                .commentId(commentId)
                .build();
        }

        return Report.builder()
            .reportType(reportType)
            .userId(userId)
            .commentId(commentId)
            .build();
    }
}
