package com.example.comment.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.dto.comment.request.CommentWriteDomainRequest;
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
        commentUseCase.deleteComment(commentId, userId);
    }

    public void reportComment(CommentReportDomainRequest request, UUID commentId, UUID userId) {
        commentUseCase.reportComment(request, commentId, userId);
    }
}
