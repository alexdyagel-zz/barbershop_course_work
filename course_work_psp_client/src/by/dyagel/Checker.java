package by.dyagel;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class with check-methods for validating info which is send by user
 */
public class Checker {
    public static final int FIRST_HOUR = 0;
    public static final int LAST_HOUR = 24;
    public static final int FIRST_MINUTE = 0;
    public static final int LAST_MINUTER = 60;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+375|80)(29|25|44|33)([0-9]{7})$");

    public static final Pattern VALID_FIO_REGEX = Pattern.compile("([А-ЯЁA-Z][а-яёa-z]+[\\-\\s]?){3,}");

    public static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");

    public static boolean isEmailCorrect(String Email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(Email);
        return matcher.find();
    }

    public static boolean isPhoneNumberCorrect(String phoneNumber) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);
        return matcher.find();
    }

    public static boolean isFIOCorrect(String FIO) {
        Matcher matcher = VALID_FIO_REGEX.matcher(FIO);
        return matcher.find();
    }

    public static boolean isPasswordCorrect(String password) {
        Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public static boolean isPositive(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isStringConvertibleToNumeric(String string) {
        try {
            BigDecimal bd = BigDecimal.valueOf(Double.parseDouble(string));
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public static boolean areHoursCorrect(int hours) {
        return (FIRST_HOUR <= hours) && (hours < LAST_HOUR);
    }

    public static boolean areMinutesCorrect(int minutes) {
        return (FIRST_MINUTE <= minutes) && (minutes < LAST_MINUTER);
    }
}
