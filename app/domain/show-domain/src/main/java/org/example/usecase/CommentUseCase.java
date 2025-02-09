package org.example.usecase;

import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.request.CommentReportDomainRequest;
import org.example.dto.comment.request.CommentWriteDomainRequest;
import org.example.entity.comment.Comment;
import org.example.entity.comment.Report;
import org.example.repository.comment.CommentRepository;
import org.example.repository.comment.report.ReportRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CommentUseCase {

    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;

    public void writeComment(CommentWriteDomainRequest request, UUID userId) {
        Comment comment = request.toComment(userId);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(UUID commentId, UUID userId) {
        Comment comment = findComment(commentId);
        comment.delete(userId);
    }

    public void reportComment(CommentReportDomainRequest request, UUID commentId, UUID userId) {
        Report report = request.toReport(userId, commentId);
        reportRepository.save(report);
    }

    private Comment findComment(UUID commentId) {
        return commentRepository.findByIdAndIsDeletedFalse(commentId)
            .orElseThrow(NoSuchElementException::new);
    }
}
