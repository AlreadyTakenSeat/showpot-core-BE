package com.example.comment.service;

import com.example.comment.error.CommentError;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.dto.comment.request.CommentWriteDomainRequest;
import org.example.exception.BusinessException;
import org.example.usecase.CommentUseCase;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentUseCase commentUseCase;

    public void writeComment(CommentWriteDomainRequest request, UUID userId) {
        commentUseCase.writeComment(request, userId);
    }

    public void deleteComment(UUID commentId, UUID userId) {
        try {
            commentUseCase.deleteComment(commentId, userId);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(CommentError.COMMENT_AUTHOR_DIFFERENT_ERROR);
        }
    }

    public void reportComment(CommentReportDomainRequest request, UUID commentId, UUID userId) {
        try {
            commentUseCase.reportComment(request, commentId, userId);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(CommentError.COMMENT_AUTHOR_REPORT_ERROR);
        }
    }
}
