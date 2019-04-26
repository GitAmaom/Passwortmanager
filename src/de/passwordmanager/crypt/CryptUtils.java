package de.passwordmanager.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility class for cryptography.
 * @author Moritz Schneider
 *
 */
public class CryptUtils {

    /**
     * Creates a random byte array.
     * @param size the size of the array.
     * @return a byte array filled with random bytes.
     */
    public static byte[] getRandomByteArray(int size) {
        byte[] bytes = new byte[size];
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            sr = new SecureRandom();
        }
        sr.nextBytes(bytes);
        return bytes;
    }
    
    /**
     * Performs a digest using a specified algorithm on a given byte array.
     * @param ALGORITHM the hashing algorithm used.
     * @param bytes the byte array on which the digest is performed.
     * @return the digest of the bytes.
     * @throws NoSuchAlgorithmException if the algorithm doesn't exist.
     */
    public static byte[] performDigest(final String ALGORITHM, byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        return md.digest(bytes);
    }
}
