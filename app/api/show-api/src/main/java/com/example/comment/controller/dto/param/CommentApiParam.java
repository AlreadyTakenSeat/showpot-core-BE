package com.example.comment.controller.dto.param;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CommentApiParam(

    @Schema(description = "댓글 ID")
    UUID commentId,

    @Schema(description = "부모 댓글 ID")
    UUID parentId,

    @Schema(description = "댓글 내용")
    String content,

    @Schema(description = "차단 여부")
    boolean isBlocked,

    @Schema(description = "사용자 프로필 URL")
    String profileURL,

    @Schema(description = "사용자 이름")
    String userName,

    @Schema(description = "댓글 생성 시간")
    String createdAt
) {

}
