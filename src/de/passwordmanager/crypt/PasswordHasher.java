package de.passwordmanager.crypt;

import org.bouncycastle.crypto.generators.SCrypt;

/**
 * Class for hashing passwords using SCrypt.
 * @author Moritz Schneider
 *
 */
public class PasswordHasher {
    
    /**
     * The number of salt bytes used to hash the password.
     */
    private final int SALT_BYTES = 32;
    /**
     * The workfactor in SCrypt.
     */
    private final int WORKFACTOR = 14;
    /**
     * The length of the hashed password. 32 bytes equal a 256 bit key.
     */
    private final int KEYLENGTH = 32;
    /**
     * The blocksize in SCrypt.
     */
    private final int BLOCKSIZE = 8;
    
    /**
     * The salt.
     */
    private byte[] salt;

    /**
     * Generates a password hash by generating a random salt.
     * @param password the password to hash.
     * @return the hashed password as a byte array.
     */
    public byte[] createPasswordHash(String password) {
        this.salt = CryptUtils.getRandomByteArray(SALT_BYTES);
        return createPasswordHash(password, this.salt);
    }
    
    /**
     * Generates a password using the given salt.
     * @param password the password to hash.
     * @param salt the salt.
     * @return the hashed password as a byte array.
     */
    public byte[] createPasswordHash(String password, byte[] salt) {
        byte[] bytes = password.getBytes();
        return SCrypt.generate(bytes, salt, powerOfTwo(this.WORKFACTOR), this.BLOCKSIZE, 1, this.KEYLENGTH);
    }
    
    /**
     * Gets the salt.
     * @return the salt.
     */
    public byte[] getSalt() {
        return this.salt;
    }
    
    /**
     * Generates the power of two.
     * @param power the power.
     * @return a power of two.
     */
    private int powerOfTwo(int power) {
        int base = 2;
        for(int i = 1; i < power; i++) {
            base = base * 2;
        }
        return base;
    }
}
