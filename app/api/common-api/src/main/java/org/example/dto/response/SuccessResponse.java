package org.example.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public record SuccessResponse<T>(
    @Schema(description = "HTTP 상태 코드", defaultValue = "200")
    int code,
    @Schema(description = "응답 메시지", defaultValue = "success")
    String message,
    @Schema(description = "응답 데이터")
    T data
) {

    private static final String SUCCESS_STATUS = "success";

    private SuccessResponse(HttpStatus httpStatus, T data) {
        this(httpStatus.value(), SUCCESS_STATUS, data);
    }

    public static <T> SuccessResponse<T> ok(T data) {
        return new SuccessResponse<>(HttpStatus.OK, data);
    }

    public static SuccessResponse<Empty> emptyData() {
        return new SuccessResponse<>(HttpStatus.OK, Empty.getInstance());
    }

    public static <T> SuccessResponse<T> created(T data) {
        return new SuccessResponse<>(HttpStatus.CREATED, data);
    }

    public static <T> SuccessResponse<T> of(HttpStatus httpStatus, T data) {
        return new SuccessResponse<>(httpStatus, data);
    }

    @JsonSerialize
    public static final class Empty {

        private static final Empty INSTANCE = new Empty();

        private Empty() {
        }

        public static Empty getInstance() {
            return INSTANCE;
        }
    }
}
