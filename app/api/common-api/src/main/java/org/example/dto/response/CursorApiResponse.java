package org.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record CursorApiResponse(

    @Schema(description = "조회한 데이터의 처음 Cursor Id")
    Object firstId,

    @Schema(description = "조회한 데이터의 마지막 Cursor Id")
    Object lastId,

    @Schema(description = "조회한 데이터의 Cursor Value")
    Object value
) {

    public static CursorApiResponse toCursorResponse(Object id, Object value) {
        return new CursorApiResponse(id, null, value);
    }

    public static CursorApiResponse toCursorResponse(Object firstId, Object lastId, Object value) {
        return new CursorApiResponse(firstId, lastId, value);
    }

    public static CursorApiResponse toCursorId(Object id) {
        return new CursorApiResponse(id, null, null);
    }

    public static CursorApiResponse noneCursor() {
        return new CursorApiResponse(null, null, null);
    }

    public static <T> T getLastElement(List<T> list) {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public static <T> T getFirstElement(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }
}
