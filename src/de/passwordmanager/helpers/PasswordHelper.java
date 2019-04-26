package de.passwordmanager.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import de.passwordmanager.entities.GeneratorSetting;
import de.passwordmanager.entities.TimeName;

/**
 * 
 * @author Moritz Schneider
 *
 */
public class PasswordHelper {

    /**
     * Char array which contains all uppercase letters.
     */
    public static final char[] ULETTERS = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * Char array which contains all lowercase letters.
     */
    public static final char[] LLETTERS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    /**
     * Char array which contains all digits.
     */
    public static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Char array which contains 32 special characters.
     */
    public static final char[] SPECIAL = { '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
            ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~' };

    /**
     * Generates a password using the specified GeneratorSetting.
     * @param setting the GeneratorSetting used to generate a password.
     * @return the password as a String.
     */
    public static String generatePassword(GeneratorSetting setting) {
        return generatePassword(setting.getUpperCaseSelected(), setting.getLowerCaseSelected(),
                setting.getDigitsSelected(), setting.getSpecialCharactersSelected(), setting.getOwnCharactersText(),
                setting.getNumberOfChars());
    }

    /**
     * Generates a random password using the specified settings.
     * 
     * @param USE_ULETTERS boolean which sets the use of uppercase letters.
     * @param USE_LLETTERS boolean which sets the use of lowercase letters.
     * @param USE_DIGITS   boolean which sets the use of digit letters.
     * @param USE_SPECIAL  boolean which sets the use of special characters.
     * @param OWN_CHARS    String where own characters can be put for password
     *                     generation.
     * @param LENGTH       length of the generated password.
     * @return the generated password as a String.
     */
    public static String generatePassword(final boolean USE_ULETTERS, final boolean USE_LLETTERS,
            final boolean USE_DIGITS, final boolean USE_SPECIAL, final String OWN_CHARS, final int LENGTH) {
        HashSet<Character> charpool = new HashSet<Character>();
        if (USE_ULETTERS) {
            addArrayToSet(charpool, ULETTERS);
        }
        if (USE_LLETTERS) {
            addArrayToSet(charpool, LLETTERS);
        }
        if (USE_DIGITS) {
            addArrayToSet(charpool, DIGITS);
        }
        if (USE_SPECIAL) {
            addArrayToSet(charpool, SPECIAL);
        }
        addArrayToSet(charpool, OWN_CHARS.toCharArray());
        if (charpool.isEmpty()) {
            return "";
        }
        String password = "";
        for (int i = 0; i < LENGTH; i++) {
            password += randomElementFromSet(charpool);
        }
        return password;
    }

    /**
     * Generates a random password using the specified settings.
     * 
     * @param USE_ULETTERS boolean which sets the use of uppercase letters.
     * @param USE_LLETTERS boolean which sets the use of lowercase letters.
     * @param USE_DIGITS   boolean which sets the use of digit letters.
     * @param USE_SPECIAL  boolean which sets the use of special characters.
     * @param LENGTH       length of the generated password.
     * @return the generated password as a String.
     */
    public static String generatePassword(final boolean USE_ULETTERS, final boolean USE_LLETTERS,
            final boolean USE_DIGITS, final boolean USE_SPECIAL, final int LENGTH) {
        return generatePassword(USE_ULETTERS, USE_LLETTERS, USE_DIGITS, USE_SPECIAL, "", LENGTH);
    }

    /**
     * Calculates the password strength of a given password-
     * 
     * @param password the password of which the strength will be calculated.
     * @return a feedback of how strong the password.
     */

    public static String getPasswordStrength(String password) {
        if (password.equals("")) {
            return "nicht vorhanden";
        }
        int bits = calculateBitLength(password);
        if (bits <= 20) {
            return "Katastrophal";
        }
        if (bits <= 40) {
            return "Armselig";
        }
        if (bits <= 50) {
            return "Schwach";
        }
        if (bits <= 60) {
            return "Ausreichend";
        }
        if (bits <= 77) {
            return "Normal";
        }
        if (bits <= 130) {
            return "Stark";
        }
        if (bits <= 140) {
            return "Fantastisch";
        }
        if (bits <= 200) {
            return "Sehr stark";
        }
        return "Overkill";
    }

    /**
     * Calculates the bits of possible passwords from a given password.
     * 
     * @param password the input password.
     * @return the bitcount as int.
     */
    public static int calculateBitLength(final String password) {
        return calculatePossiblePasswords(password).bitLength();
    }

    /**
     * Calculates the number of possible passwords from a given password.
     * 
     * @param password the input password.
     * @return the number of possible passwords as BigInteger.
     */
    public static BigInteger calculatePossiblePasswords(final String password) {
        int possiblePasswords = getCharpoolSize(password);
        BigInteger result = new BigInteger("0");
        for (int i = 0; i < password.length(); i++) {
            BigInteger big = new BigInteger(Integer.toString(possiblePasswords));
            BigInteger temp = new BigInteger(Integer.toString(possiblePasswords));
            for (int j = 0; j < i; j++) {
                big = big.multiply(temp);
            }
            result = result.add(big);
        }
        return result;
    }

    /**
     * Calculates the size of the character pool of a given password.
     * 
     * @param password the password.
     * @return the size of the character pool.
     */
    public static int getCharpoolSize(String password) {
        HashSet<Character> charpool = new HashSet<Character>();
        if (containsCharset(password, ULETTERS)) {
            addArrayToSet(charpool, ULETTERS);
        }
        if (containsCharset(password, LLETTERS)) {
            addArrayToSet(charpool, LLETTERS);
        }
        if (containsCharset(password, DIGITS)) {
            addArrayToSet(charpool, DIGITS);
        }
        if (containsCharset(password, SPECIAL)) {
            addArrayToSet(charpool, SPECIAL);
        }
        for (char c : password.toCharArray()) {
            if (!charpool.contains(c)) {
                charpool.add(c);
            }
        }
        return charpool.size();
    }

    /**
     * Determines if a String has a character that is contained in a given charset.
     * 
     * @param string  the string.
     * @param charset the charset as an array of characters.
     * @return true if the charset contains a character of the password else false.
     */
    public static boolean containsCharset(final String string, final char[] charset) {
        for (char c : string.toCharArray()) {
            if (charInCharset(c, charset)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Counts the number of characters in a String that are in a specific charset.
     * 
     * @param string the string to be count.
     * @param charset the given charset.
     * @return the number of characters of the charset in the string.
     */
    public static int countCharacters(final String string, final char[] charset) {
        int count = 0;
        for (char c : string.toCharArray()) {
            if (charInCharset(c, charset)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts the number of uppercase letters in a String.
     * 
     * @param string the string.
     * @return the number of uppercase letters.
     */
    public static int numOfUppercaseLetters(final String string) {
        return countCharacters(string, ULETTERS);
    }

    /**
     * Counts the number of lowercase letters in a String.
     * 
     * @param string the string.
     * @return the number of lowercase letters.
     */
    public static int numOfLowercaseLetters(final String string) {
        return countCharacters(string, LLETTERS);
    }

    /**
     * Counts the number of digits in a String.
     * 
     * @param string the string.
     * @return the number of digits.
     */
    public static int numOfDigits(final String string) {
        return countCharacters(string, DIGITS);
    }

    /**
     * Counts the number of special characters in a String.
     * 
     * @param string the string.
     * @return the number of special characters.
     */
    public static int numOfSpecialCharacters(final String string) {
        return countCharacters(string, SPECIAL);
    }

    /**
     * Counts the number of own characters in a String.
     * 
     * @param string the string.
     * @return the number of own characters.
     */
    public static int numOfCustomCharacters(final String string) {
        return string.length() - numOfUppercaseLetters(string) - numOfLowercaseLetters(string) - numOfDigits(string)
                - numOfSpecialCharacters(string);
    }

    /**
     * Determines if a character is in a given charset.
     * 
     * @param c       the character.
     * @param charset the charset as an array of characters.
     * @return true if the char is in the charset else false.
     */
    public static boolean charInCharset(final char c, final char[] charset) {
        return Arrays.binarySearch(charset, c) >= 0;
    }

    /**
     * Calculates how many seconds it would take for a bruteforce-attack guess the
     * correct password given a number of guesses per second.
     * 
     * @param password   the password to crack.
     * @param numGuesses the number of guesses per second the bruteforce-attack
     *                   has.
     * @return the number of seconds as a BigDecimal.
     */

    public static BigDecimal calculateCrackTime(String password, int numGuesses) {
        BigDecimal i = new BigDecimal(calculatePossiblePasswords(password));
        BigDecimal guesses = new BigDecimal(numGuesses);
        return i.divide(guesses, RoundingMode.HALF_UP);
    }

    /**
     * Converts an arbitrary amount of seconds to a printable time estimation.
     * 
     * @param input the number of seconds as BigDecimal.
     * @return a printable estimation.
     */

    public static String convertSecondsToPrintableTime(BigDecimal input) {
        if (hasEnoughSeconds(input, TimeName.AEONS)) {
            return getPrintableTimeString(input, TimeName.AEONS);
        }
        if (hasEnoughSeconds(input, TimeName.MEGANNUMS)) {
            return getPrintableTimeString(input, TimeName.MEGANNUMS);
        }
        if (hasEnoughSeconds(input, TimeName.MILLENIALS)) {
            return getPrintableTimeString(input, TimeName.MILLENIALS);
        }
        if (hasEnoughSeconds(input, TimeName.CENTURIES)) {
            return getPrintableTimeString(input, TimeName.CENTURIES);
        }
        if (hasEnoughSeconds(input, TimeName.YEARS)) {
            return getPrintableTimeString(input, TimeName.YEARS);
        }
        if (hasEnoughSeconds(input, TimeName.MONTHS)) {
            return getPrintableTimeString(input, TimeName.MONTHS);
        }
        if (hasEnoughSeconds(input, TimeName.WEEKS)) {
            return getPrintableTimeString(input, TimeName.WEEKS);
        }
        if (hasEnoughSeconds(input, TimeName.DAYS)) {
            return getPrintableTimeString(input, TimeName.DAYS);
        }
        if (hasEnoughSeconds(input, TimeName.HOURS)) {
            return getPrintableTimeString(input, TimeName.HOURS);
        }
        if (hasEnoughSeconds(input, TimeName.MINUTES)) {
            return getPrintableTimeString(input, TimeName.MINUTES);
        }
        return getPrintableTimeString(input, TimeName.SECONDS);
    }

    /**
     * Converts a large number of seconds to a timestring.
     * @param seconds the number of seconds.
     * @param timeunit the unit of time which it will be converted to.
     * @return a String of time.
     */
    private static String getPrintableTimeString(BigDecimal seconds, TimeName timeunit) {
        if (timeunit.equals(TimeName.AEONS)
                && TimeCalculator.convertSecondsTo(timeunit, seconds).toString().length() > 5) {
            return "Ewig";
        }
        if (TimeCalculator.convertSecondsTo(timeunit, seconds).toString().equals("0")) {
            return "Sofort";
        }
        return "Circa " + TimeCalculator.convertSecondsTo(timeunit, seconds) + " " + timeunit.getName();
    }

    private static boolean hasEnoughSeconds(BigDecimal seconds, TimeName timeunit) {
        return seconds.subtract(timeunit.getNumOfSeconds()).signum() != -1;
    }

    /**
     * Adds a chararray to a set of characters.
     * 
     * @param SET   the set to which characters are added.
     * @param ARRAY the array of characters.
     */
    private static void addArrayToSet(final Set<Character> SET, final char[] ARRAY) {
        for (char c : ARRAY) {
            SET.add(c);
        }
    }

    /**
     * Gets a random element from a set.
     * 
     * @param set the set of elements.
     * @return A random element of the set.
     */
    private static <T> T randomElementFromSet(Set<T> set) {
        Random r = new Random();
        int rand = r.nextInt(set.size());
        Iterator<T> iter = set.iterator();
        for (int i = 0; i < rand; i++) {
            iter.next();
        }
        return iter.next();
    }
}
