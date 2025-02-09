package org.example.dto.comment.request;

import java.util.UUID;
import org.example.entity.comment.Report;
import org.example.vo.ReportType;

public record CommentReportDomainRequest(
    ReportType reportType
) {

    public Report toReport(UUID userId, UUID commentId) {
        return Report.builder()
            .reportType(reportType)
            .userId(userId)
            .commentId(commentId)
            .build();
    }
}
