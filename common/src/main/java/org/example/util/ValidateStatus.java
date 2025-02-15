package org.example.util;

import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidateStatus {

    public static <T> List<T> checkNullOrEmpty(List<T> list) {
        return (list == null || list.isEmpty()) ? List.of() : list;
    }

}
