package org.example.repository.comment;

import java.util.Optional;
import java.util.UUID;
import org.example.entity.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Optional<Comment> findByIdAndIsDeletedFalse(UUID commentId);
}
