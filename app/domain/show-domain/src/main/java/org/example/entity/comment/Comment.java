package org.example.entity.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.BaseEntity;
import org.example.vo.CommentType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Column(name = "ref_id", nullable = false)
    private UUID refId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "parent_id")
    private UUID parentId;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "comment_type", nullable = false)
    private CommentType commentType;

    @Builder
    private Comment(
        UUID refId,
        UUID userId,
        UUID parentId,
        String content,
        CommentType commentType
    ) {
        this.refId = refId;
        this.userId = userId;
        this.parentId = parentId;
        this.content = content;
        this.commentType = commentType;
    }

    public void delete(UUID userId) {
        if (!isWriter(userId)) {
            throw new IllegalArgumentException();
        }
        super.softDelete();
        this.content = "삭제된 댓글입니다.";
    }

    private boolean isWriter(UUID userId) {
        return this.userId.equals(userId);
    }
}
