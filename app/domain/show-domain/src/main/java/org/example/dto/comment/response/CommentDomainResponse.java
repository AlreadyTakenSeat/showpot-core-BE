package org.example.dto.comment.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentDomainResponse(
    UUID commentId,
    UUID userId,
    UUID parentId,
    String content,
    boolean isBlocked,
    LocalDateTime createdAt
) {

}
