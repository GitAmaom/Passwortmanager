package de.passwordmanager.crypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The purpose of this class is to encrypt or decrypt String messages using
 * AES-128.
 *
 * @author schneiderm
 * @since 1.0
 *
 */
public class Cryptor {

    /**
     * The secret key specification that contains the hashed master password.
     */
    private SecretKeySpec sks;

    /**
     * The initialization vector specification that contains the initialization
     * vector.
     */
    private IvParameterSpec iv;

    /**
     * The hashfunction to hash the master password.
     */
    private static final String HASHFUNCTION = "SHA-256";

    /**
     * The encryption used.
     */
    private static final String ENCRYPTION = "AES";

    /**
     * The full transformation applied to encrypt the data.
     */
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    /**
     * The encoding used to convert between Strings and bytes.
     */
    private static final String ENCODING = "UTF-8";

    /**
     * Method for encrypting text. Uses the AES-128 in CBC-mode to encrypt data
     * and uses PKCS5Padding.
     *
     * @param message The data that gets encrypted.
     * @param password The masterpassword which is used for the encryption.
     * @return byte[] An array of encrypted data.
     */
    public byte[] encrypt(final String message, final String password) {
        try {
            final byte[] data = message.getBytes(ENCODING);
            final Cipher cypher = Cipher.getInstance(TRANSFORMATION);
            generateMasterkey(password);
            generateIv();
            cypher.init(Cipher.ENCRYPT_MODE, this.sks, this.iv);
            return cypher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[] {};
    }

    /**
     * Method for decrypting bytes. Uses the AES-128 in CBC-mode to decrypt data
     * and uses PKCS5Padding.
     *
     * @param encrypted The encrypted bytearray.
     * @param password The masterpassword which is used for the decryption.
     * @param initVector The initialization vector.
     * @return String The decrypted message.
     */

    public String decrypt(final byte[] encrypted, final String password,
            final byte[] initVector) {
        try {
            final Cipher cypher = Cipher.getInstance(TRANSFORMATION);
            generateMasterkey(password);
            final IvParameterSpec ivspec = new IvParameterSpec(initVector);
            cypher.init(Cipher.DECRYPT_MODE, this.sks, ivspec);
            byte[] decrypted = cypher.doFinal(encrypted);
            return new String(decrypted, ENCODING);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Private method to generate a random initialization vector.
     */

    private void generateIv() {
        final byte[] initVector = CryptUtils.getRandomByteArray(16);
        this.iv = new IvParameterSpec(initVector);
    }

    /**
     * Private method to generate the SecretKeySpec using the masterpassword.
     *
     * @param password the masterpassword that is used to generate the
     *                 SecretKey.
     */

    private void generateMasterkey(final String password) {
        try {
            final byte[] keybytes = CryptUtils.performDigest(HASHFUNCTION,
                    password.getBytes(ENCODING));
            this.sks = new SecretKeySpec(keybytes, ENCRYPTION);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter method for iv.
     *
     * @return byte[] the initialization vector used for the last encryption.
     */
    public byte[] getIv() {
        return iv.getIV();
    }

}
