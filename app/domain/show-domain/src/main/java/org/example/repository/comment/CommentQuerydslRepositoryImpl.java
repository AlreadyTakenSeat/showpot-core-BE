
package org.example.repository.comment;

import static org.example.entity.comment.QComment.comment;
import static org.example.entity.comment.QReport.report;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.dto.comment.request.CommentPaginationDomainRequest;
import org.example.dto.comment.response.CommentDomainResponse;
import org.example.dto.comment.response.CommentPaginationDomainResponse;
import org.example.util.SliceUtil;
import org.example.vo.CommentType;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentQuerydslRepositoryImpl implements CommentQuerydslRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CommentPaginationDomainResponse findAllWithCursorPagination(
        CommentPaginationDomainRequest request) {

        List<CommentDomainResponse> result = jpaQueryFactory
            .select(
                Projections.constructor(
                    CommentDomainResponse.class,
                    comment.id,
                    comment.userId,
                    comment.parentId,
                    comment.content,
                    report.userId.when(request.userId()).then(true).otherwise(false),
                    comment.createdAt
                )
            )
            .from(comment)
            .leftJoin(report).on(report.commentId.eq(comment.id))
            .where(getWhereClauseInCursorPagination(request))
            .orderBy(getOrderSpecifier(request.isInverted()))
            .limit(request.size() + 1)
            .fetch();

        Slice<CommentDomainResponse> slice = SliceUtil.makeSlice(request.size(), result);

        return CommentPaginationDomainResponse.builder()
            .data(slice.getContent())
            .hasNext(slice.hasNext())
            .build();
    }

    private Predicate getWhereClauseInCursorPagination(CommentPaginationDomainRequest request) {
        BooleanExpression wherePredicate = getDefaultPredicateExpression(request.type(),
            request.refId());

        if (request.cursorId() == null) {
            return wherePredicate;
        }

        if (!request.isInverted()) {
            return wherePredicate.and(createRecentPredicate(request.cursorId()));
        }

        return wherePredicate.and(createPastPredicate(request.cursorId()));
    }

    private BooleanExpression getDefaultPredicateExpression(CommentType commentType, UUID refId) {
        return comment.commentType.eq(commentType).and(comment.refId.eq(refId));
    }

    private BooleanExpression createRecentPredicate(UUID cursorId) {
        Tuple cursor = getCursor(cursorId);

        LocalDateTime cursorValue = cursor.get(comment.createdAt);
        UUID cursorIdValue = cursor.get(comment.id);

        return comment.createdAt.gt(cursorValue)
            .or(
                comment.createdAt.eq(cursorValue)
                    .and(comment.id.gt(cursorIdValue))
            );
    }

    private BooleanExpression createPastPredicate(UUID cursorId) {
        Tuple cursor = getCursor(cursorId);

        LocalDateTime cursorValue = cursor.get(comment.createdAt);
        UUID cursorIdValue = cursor.get(comment.id);

        return comment.createdAt.lt(cursorValue)
            .or(
                comment.createdAt.eq(cursorValue)
                    .and(comment.id.lt(cursorIdValue))
            );
    }

    private OrderSpecifier<?>[] getOrderSpecifier(boolean isInverted) {
        if (isInverted) {
            return new OrderSpecifier<?>[]{
                comment.createdAt.desc(),
                comment.id.desc()
            };
        }

        return new OrderSpecifier<?>[]{
            comment.createdAt.asc(),
            comment.id.asc()
        };
    }

    private Tuple getCursor(UUID cursorId) {
        return jpaQueryFactory
            .select(comment.id, comment.createdAt)
            .from(comment)
            .where(comment.id.eq(cursorId))
            .fetchFirst();
    }
}
