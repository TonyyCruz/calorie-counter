package com.anthony.calorie_counter.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class SimpleFake {
    public static String randomString(int size) {
        return RandomStringUtils.randomAlphabetic(size);
    }

    public static int randomInteger() {
        Random random = new Random();
        return random.nextInt(1, 10001);
    }

    public static Long randomLong() {
        String randomValue = String.valueOf(randomInteger());
        return Long.valueOf(randomValue);
    }

    public static int randomInteger(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static String randomString() {
        final int minimumCharacters = 2;
        final int maximumCharacters = 10;
        int characters = randomInteger(minimumCharacters, maximumCharacters);
        return RandomStringUtils.randomAlphabetic(characters);
    }

    public static String randomString(int minimumCharacters, int maximumCharacters) {
        int characters = randomInteger(minimumCharacters, maximumCharacters);
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
        return firstName() + " " + lastName();
    }

    public static String email(){
        return randomString() + "@email.com";
    }

    public static String randomSymbol() {
        String[] symbols = new String[] {"!", "@", "#", "$", "%", "", "&", "*", "(", ")", "_", "=", "+", ".", "?", ">", "<"};
        int position = randomInteger(0, symbols.length - 1);
        return symbols[position];
    }

    public static String password(int minimumSize) {
        Random random = new Random();
        int randomInt = random.nextInt();
        return upperFirstLetterCase(upperFirstLetterCase(randomString(minimumSize - 2))) + randomInt + randomSymbol();
    }
}
