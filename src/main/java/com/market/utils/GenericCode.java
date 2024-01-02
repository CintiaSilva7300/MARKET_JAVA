package com.market.utils;

import java.util.Random;

public class GenericCode {
	public static String generateCode(String prefix) {
		String randomPart = generateRandomNumericString(6);
        String code = prefix + randomPart;
        return code;
    }

    private static String generateRandomNumericString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return stringBuilder.toString();
    }
}
