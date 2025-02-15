package org.example.util;

import java.util.concurrent.ThreadLocalRandom;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomNumber {

    public static int getRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min 값은 max 값보다 작거나 같아야 합니다.");
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
