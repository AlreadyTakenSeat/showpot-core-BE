package org.example.dto.comment.request;

import java.util.UUID;
import org.example.entity.comment.Comment;
import org.example.vo.CommentType;

public record CommentWriteDomainRequest(
    UUID parentId,
    String content,
    CommentType commentType,
    UUID refId
) {

    public Comment toComment(UUID userId) {
        return Comment.builder()
            .parentId(parentId)
            .content(content)
            .commentType(commentType)
            .refId(refId)
            .userId(userId)
            .build();
    }
}
