package org.example.repository.comment;

import org.example.dto.comment.request.CommentPaginationDomainRequest;
import org.example.dto.comment.response.CommentPaginationDomainResponse;

public interface CommentQuerydslRepository {

    CommentPaginationDomainResponse findAllWithCursorPagination(CommentPaginationDomainRequest request);
}
