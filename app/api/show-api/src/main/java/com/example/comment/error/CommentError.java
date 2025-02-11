package com.example.comment.error;

import org.example.exception.BusinessError;

public enum CommentError implements BusinessError {

    COMMENT_AUTHOR_REPORT_ERROR {
        @Override
        public int getHttpStatus() {
            return 400;
        }

        @Override
        public String getErrorCode() {
            return "CMT-001";
        }

        @Override
        public String getClientMessage() {
            return "댓글 저자는 자신의 댓글을 신고/차단할 수 없습니다.";
        }

        @Override
        public String getLogMessage() {
            return "댓글 저자가 본인의 댓글을 신고/차단 요청함";
        }
    },

    COMMENT_AUTHOR_DIFFERENT_ERROR {
        @Override
        public int getHttpStatus() {
            return 400;
        }

        @Override
        public String getErrorCode() {
            return "CMT-002";
        }

        @Override
        public String getClientMessage() {
            return "댓글 저자가 아닙니다.";
        }

        @Override
        public String getLogMessage() {
            return "댓글 저자가 아닌 신원이 요청함";
        }
    }
}
