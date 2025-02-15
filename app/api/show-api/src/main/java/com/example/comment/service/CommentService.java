package com.example.comment.service;

import com.example.comment.controller.dto.param.CommentApiParam;
import com.example.comment.error.CommentError;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.request.CommentPaginationDomainRequest;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.dto.comment.request.CommentWriteDomainRequest;
import org.example.dto.comment.response.CommentDomainResponse;
import org.example.dto.response.PaginationServiceResponse;
import org.example.entity.User;
import org.example.exception.BusinessException;
import org.example.usecase.CommentUseCase;
import org.example.usecase.UserUseCase;
import org.example.util.DateTimeUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentUseCase commentUseCase;
    private final UserUseCase userUseCase;

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

    public PaginationServiceResponse<CommentApiParam> getComments(
        CommentPaginationDomainRequest request) {
        var commentsByPagination = commentUseCase.findCommentsByPagination(request);
        List<UUID> userIds = commentsByPagination.data().stream()
            .map(CommentDomainResponse::userId)
            .toList();
        List<User> users = userUseCase.findAllByUserIds(userIds);
        Map<UUID, User> userMap = users.stream()
            .collect(Collectors.toMap(User::getId, user -> user));

        List<CommentApiParam> commentApiParams = commentsByPagination.data().stream()
            .map(comment -> {
                User user = userMap.get(comment.userId());
                return CommentApiParam.builder()
                    .commentId(comment.commentId())
                    .parentId(comment.parentId())
                    .content(comment.content())
                    .isBlocked(comment.isBlocked())
                    .profileURL(user.getProfileUrl() == null ? null : user.getProfileUrl())
                    .userName(user.getNickname() == null ? null : user.getNickname())
                    .createdAt(DateTimeUtil.formatDateTime(comment.createdAt()))
                    .build();
            })
            .collect(Collectors.toList());

        Collections.reverse(commentApiParams);
        return PaginationServiceResponse.of(commentApiParams, commentsByPagination.hasNext());
    }
}
