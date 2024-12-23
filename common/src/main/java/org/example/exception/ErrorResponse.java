package org.example.exception;

public record ErrorResponse(
    int code,
    String errorId,
    String errorCode,
    String message
) {

    private ErrorResponse(BusinessErrorResponseBuilder builder) {
        this(
            builder.error.getHttpStatus(),
            builder.errorId,
            builder.error.getErrorCode(),
            builder.error.getClientMessage()
        );
    }

    private ErrorResponse(MessageCustomErrorResponseBuilder builder) {
        this(
            builder.error.getHttpStatus(),
            builder.errorId,
            builder.error.getErrorCode(),
            builder.message
        );
    }

    public static BusinessErrorResponseBuilder businessErrorResponseBuilder() {
        return new BusinessErrorResponseBuilder();
    }

    public static MessageCustomErrorResponseBuilder messageCustomErrorResponseBuilder() {
        return new MessageCustomErrorResponseBuilder();
    }

    public static class BusinessErrorResponseBuilder {

        private String errorId;
        private BusinessError error;

        protected BusinessErrorResponseBuilder() {
        }

        public BusinessErrorResponseBuilder errorId(String errorId) {
            this.errorId = errorId;
            return this;
        }

        public BusinessErrorResponseBuilder error(BusinessError error) {
            this.error = error;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    public static class MessageCustomErrorResponseBuilder {

        private String errorId;
        private String message;
        private BusinessError error;

        protected MessageCustomErrorResponseBuilder() {
        }

        public MessageCustomErrorResponseBuilder errorId(String errorId) {
            this.errorId = errorId;
            return this;
        }

        public MessageCustomErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageCustomErrorResponseBuilder error(BusinessError error) {
            this.error = error;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

    @Override
    public String toString() {
        return String.format(
            "{ \"code\": %d, \"errorId\": \"%s\", \"errorCode\": \"%s\", \"message\": \"%s\" }",
            code,
            errorId,
            errorCode,
            message
        );
    }

}
