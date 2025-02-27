package com.example.comment.controller;

import com.example.comment.controller.dto.param.CommentApiParam;
import com.example.comment.controller.dto.request.CommentPaginationApiRequest;
import com.example.comment.controller.dto.request.CommentReportApiRequest;
import com.example.comment.controller.dto.request.CommentWriteApiRequest;
import com.example.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.CursorApiResponse;
import org.example.dto.response.PaginationApiResponse;
import org.example.dto.response.SuccessResponse;
import org.example.dto.response.SuccessResponse.Empty;
import org.example.security.dto.AuthenticatedInfo;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    public SuccessResponse<PaginationApiResponse<CommentApiParam>> writeComment(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @RequestBody @Valid CommentWriteApiRequest request
    ) {
        var commentApiParams = List.of(
            commentService.writeComment(request.toServiceRequest(), info.userId()));

        CursorApiResponse cursor = Optional.ofNullable(
                CursorApiResponse.getFirstElement(commentApiParams))
            .map(element -> CursorApiResponse.toCursorId(element.commentId()))
            .orElse(CursorApiResponse.noneCursor());

        return SuccessResponse.ok(
            PaginationApiResponse.<CommentApiParam>builder()
                .data(commentApiParams)
                .hasNext(false)
                .cursor(cursor)
                .build()
        );
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{refId}")
    @Operation(summary = "댓글 목록 조회")
    public SuccessResponse<PaginationApiResponse<CommentApiParam>> readComments(
        @AuthenticationPrincipal AuthenticatedInfo info,
        @PathVariable UUID refId,
        @Valid @ParameterObject CommentPaginationApiRequest request
    ) {
        var commentPagination = commentService.getComments(
            request.toServiceRequest(refId, info.userId()));

        CursorApiResponse cursor;
        if (request.isInverted()) {
            cursor = Optional.ofNullable(CursorApiResponse.getFirstElement(commentPagination.data()))
                .map(element -> CursorApiResponse.toCursorId(element.commentId()))
                .orElse(CursorApiResponse.noneCursor());
        } else {
            cursor = Optional.ofNullable(
                    CursorApiResponse.getLastElement(commentPagination.data()))
                .map(element -> CursorApiResponse.toCursorId(element.commentId()))
                .orElse(CursorApiResponse.noneCursor());
        }

        return SuccessResponse.ok(
            PaginationApiResponse.<CommentApiParam>builder()
                .data(commentPagination.data())
                .hasNext(commentPagination.hasNext())
                .cursor(cursor)
                .build()
        );
    }

}
