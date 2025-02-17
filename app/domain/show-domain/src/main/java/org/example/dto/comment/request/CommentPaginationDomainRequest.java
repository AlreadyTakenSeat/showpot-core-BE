package org.example.dto.comment.request;

import java.util.UUID;
import lombok.Builder;
import org.example.vo.CommentType;

@Builder
public record CommentPaginationDomainRequest(
    CommentType type,
    UUID refId,
    UUID userId,
    boolean isInverted,
    UUID cursorId,
    Integer size
) {

}
