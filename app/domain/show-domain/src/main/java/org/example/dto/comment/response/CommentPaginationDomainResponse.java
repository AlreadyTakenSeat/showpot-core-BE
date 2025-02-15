package org.example.dto.comment.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CommentPaginationDomainResponse(
    List<CommentDomainResponse> data,
    boolean hasNext
) {

}
