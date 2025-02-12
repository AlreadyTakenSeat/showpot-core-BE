package org.example.util;

import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomNumber {

    private static final Random random = new Random();

    public static int getRandomNumber(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min 값은 max 값보다 작거나 같아야 합니다.");
        }
        return random.nextInt((max - min) + 1) + min;
    }
}
