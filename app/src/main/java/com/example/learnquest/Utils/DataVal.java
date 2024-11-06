package com.example.learnquest.Utils;

import java.util.regex.Pattern;

public class DataVal {

    public static boolean isValidLuhn(String number) {
        // Check if the input is a 13-digit number
        if (number == null || number.length() != 13 || !number.matches("\\d+")) {
            return false;
        }

        int sum = 0;
        boolean isDouble = false;

        // Process each digit from right to left
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            // Double every second digit
            if (isDouble) {
                digit *= 2;
                // If doubling results in a two-digit number, subtract 9 to sum the digits
                if (digit > 9) {
                    digit -= 9;
                }
            }

            // Add the digit to the sum
            sum += digit;
            isDouble = !isDouble; // Toggle doubling for the next digit
        }

        // The number is valid if the sum is a multiple of 10
        return sum % 10 == 0;
    }

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }



    private static final String NAME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ'\\- ]{2,50}$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_REGEX);

    public static boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        return NAME_PATTERN.matcher(name).matches();
    }


}
