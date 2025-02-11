package com.example.comment.controller;

import com.example.comment.controller.dto.CommentReportApiRequest;
import com.example.comment.controller.dto.CommentWriteApiRequest;
import com.example.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.SuccessResponse;
import org.example.dto.response.SuccessResponse.Empty;
import org.example.security.dto.AuthenticatedInfo;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
@Tag(name = "댓글")
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    @Operation(summary = "댓글 작성")
    public SuccessResponse<Empty> writeComment(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @RequestBody @Valid CommentWriteApiRequest request
    ) {
        commentService.writeComment(request.toServiceRequest(), info.userId());

        return SuccessResponse.emptyData();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제")
    public SuccessResponse<Empty> deleteComment(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @PathVariable UUID commentId
    ) {
        commentService.deleteComment(commentId, info.userId());

        return SuccessResponse.emptyData();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{commentId}/report")
    @Operation(summary = "댓글 신고/차단")
    public SuccessResponse<Empty> reportComment(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @PathVariable UUID commentId,
        @RequestBody @Valid CommentReportApiRequest request
    ) {
        commentService.reportComment(request.toServiceRequest(), commentId, info.userId());

        return SuccessResponse.emptyData();
    }
}
