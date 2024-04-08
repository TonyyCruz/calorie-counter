package com.anthony.calorie_counter.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class SimpleFakeData {
    private static final int MIN_RANGE = 2;
    private static final int MAX_RANGE = 10;

    public static String randomString(int size) {
        return RandomStringUtils.randomAlphabetic(size);
    }

    public static String randomString() {
        Random random = new Random();
        int characters = random.nextInt(MAX_RANGE - MIN_RANGE) + MIN_RANGE;
        return RandomStringUtils.randomAlphabetic(characters);
    }

    public static String upperFirstLetterCase(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public static String firstName() {
        return upperFirstLetterCase(randomString());
    }

    public static String lastName() {
        return upperFirstLetterCase(randomString());
    }

    public static String fullName() {
        return upperFirstLetterCase(randomString()) + " " + upperFirstLetterCase(randomString());
    }

    public static String email(){
        return randomString() + "@email.com";
    }

    public static String password() {
        Random random = new Random();
        int randomInt = random.nextInt();
        return upperFirstLetterCase(randomString()) + randomInt + "*";
    }
}
