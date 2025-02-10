package com.example.comment.controller.dto;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.example.dto.comment.request.CommentWriteDomainRequest;
import org.example.vo.CommentType;

public record CommentWriteApiRequest(
    @Parameter(description = "부모 댓글의 ID / 대댓글 작성 시 필수")
    UUID parentId,

    @Parameter(description = "댓글 내용")
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    String content,

    @Parameter(example = "SHOW", description = "댓글 타입")
    @NotNull(message = "댓글 타입을 입력해주세요.")
    CommentType commentType,

    @Parameter(description = "참조 ID / 댓글 타입이 SHOW일 경우에는 SHOW ID")
    @NotNull(message = "참조 ID를 입력해주세요.")
    UUID refId
) {

    public CommentWriteDomainRequest toServiceRequest() {
        return new CommentWriteDomainRequest(
            parentId,
            content,
            commentType,
            refId
        );
    }
}
