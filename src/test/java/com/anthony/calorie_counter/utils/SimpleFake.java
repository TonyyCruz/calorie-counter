package com.anthony.calorie_counter.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class SimpleFake {
    public static String randomString(int size) {
        return RandomStringUtils.randomAlphabetic(size).toLowerCase();
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
        return RandomStringUtils.randomAlphabetic(characters).toLowerCase();
    }

    public static String randomString(int minimumCharacters, int maximumCharacters) {
        int characters = randomInteger(minimumCharacters, maximumCharacters);
        return RandomStringUtils.randomAlphabetic(characters).toLowerCase();
    }

    public static String upperFirstLetterCase(String word) {
        String firstCharacter = String.valueOf(word.charAt(0)).toUpperCase();
        return firstCharacter + word.substring(1);
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
    
    public static String phoneNumber() {
        int[] prefixes = new int[] { 11, 12, 13 ,14, 22, 24, 27, 28, 31, 32, 33, 34, 35, 37, 38, 41, 42, 43, 44, 45, 46, 47, 51, 53, 54, 55, 61, 62, 63, 91, 93, 99 };
        String digit = String.valueOf(prefixes[randomInteger(0, prefixes.length - 1)]);
        String firstNumberGroup = String.valueOf(randomInteger(1000, 9999));
        String secondNumberGroup = String.valueOf(randomInteger(1000, 9999));
        return "(%s) 9%s-%s".formatted(digit, firstNumberGroup, secondNumberGroup);
    }
}
