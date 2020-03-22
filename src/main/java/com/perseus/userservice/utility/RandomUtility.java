package com.perseus.userservice.utility;

import java.util.Random;

public class RandomUtility {
    private static final Random rand = new Random();

    public static Integer getRandomInteger() {
        return rand.nextInt(Integer.MAX_VALUE) + 1;
    }
}
