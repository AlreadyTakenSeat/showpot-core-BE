package com.example.comment.controller.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Max;
import java.util.UUID;
import org.example.dto.comment.request.CommentPaginationDomainRequest;
import org.example.vo.CommentType;

public record CommentPaginationApiRequest(
    @Parameter(description = "댓글 타입", required = true)
    CommentType type,

    @Parameter(description = "스크롤 방향", required = true)
    boolean isInverted,

    @Parameter(description = "이전 페이지네이션의 cursorId / 최초 조회라면 null")
    UUID cursorId,

    @Parameter(example = "30")
    @Max(value = 30, message = "조회하는 데이터의 최대 개수는 30입니다.")
    Integer size
) {

    public CommentPaginationApiRequest {
        if (size == null) {
            size = 30;
        }
    }

    public CommentPaginationDomainRequest toServiceRequest(UUID refId, UUID userId) {
        return CommentPaginationDomainRequest.builder()
            .type(type)
            .refId(refId)
            .userId(userId)
            .isInverted(isInverted)
            .cursorId(cursorId)
            .size(size)
            .build();
    }
}
