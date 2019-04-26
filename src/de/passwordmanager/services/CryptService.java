package de.passwordmanager.services;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Base64;

import de.passwordmanager.crypt.Cryptor;
import de.passwordmanager.crypt.PasswordHasher;

/**
 * Service for encryption.
 * @author Moritz Schneider
 *
 */
public class CryptService {

    /**
     * Used for encrypting strings.
     */
    private Cryptor crypt;
    /**
     * Used for hashing strings.
     */
    private PasswordHasher hasher;

    /**
     * Class contructor
     */
    public CryptService() {
        this.crypt = new Cryptor();
        this.hasher = new PasswordHasher();
    }

    /**
     * Hashes a String to an encryption message consisting of the salt length, the salt and the hashed string.
     * @param password the string to be hashed.
     * @return the encryption message.
     */
    public String hashPasswordToMessage(String password) {
        byte[] hashed = this.hasher.createPasswordHash(password);
        byte[] salt = this.hasher.getSalt();
        ByteBuffer bb = ByteBuffer.allocate(1 + salt.length + hashed.length);
        bb.put((byte) salt.length);
        bb.put(salt);
        bb.put(hashed);
        byte[] message = bb.array();
        return encodeBytesBase64(message);
    }
    
    /**
     * Hashes the input string and checks if it is equal to a given hash.
     * @param password the string to be hashed.
     * @param encodedMessage the encryption message that it will be compared to.
     * @return true if it is equal, false if not.
     */
    public boolean checkPasswordHash(String password, String encodedMessage) {
        byte[] message = decodeStringBase64(encodedMessage);
        ByteBuffer bb = ByteBuffer.wrap(message);
        int saltLength = bb.get();
        byte[] salt = new byte[saltLength];
        bb.get(salt);
        byte[] hash = new byte[bb.remaining()];
        bb.get(hash);
        return checkHashedPassword(password, salt, hash);
    }

    /**
     * Hashes a password with random salt.
     * @param password the password.
     * @return the hash as a byte array.
     */
    public byte[] hashPassword(String password) {
        byte[] hashed = this.hasher.createPasswordHash(password);
        return hashed;
    }

    /**
     * Hashes the password with salt and checks if it is equal to an other hash.
     * @param password the password to be hashed.
     * @param salt the salt.
     * @param hash the hash it will be checked to.
     * @return true if it is equal, else false.
     */
    private boolean checkHashedPassword(String password, byte[] salt, byte[] hash) {
        byte[] hashed = this.hasher.createPasswordHash(password, salt);
        return Arrays.equals(hashed, hash);
    }

    /*public ObservableList<String> decryptList(List<String> list, String masterpassword) {
        ObservableList<String> decryptedList = FXCollections.observableArrayList();
        for (String str : list) {
            decryptedList.add(decryptMessage(decodeStringBase64(str), masterpassword));
        }
        return decryptedList;
    }

    public ObservableList<String> encryptList(List<String> list, String masterpassword) {
        ObservableList<String> encryptedList = FXCollections.observableArrayList();
        for (String str : list) {
            encryptedList.add(encodeBytesBase64(encryptToMessage(str, masterpassword)));
        }
        return encryptedList;
    }*/

    /**
     * Encode a string with Base64.
     * @param toEncode the string to encode.
     * @return the encoded string.
     */
    public String encodeBytesBase64(byte[] toEncode) {
        Base64.Encoder e = Base64.getEncoder();
        return e.encodeToString(toEncode);
    }

    /**
     * Decodes a Base64 encoded String.
     * @param toDecode the string to decode.
     * @return the decoded string.
     */
    public byte[] decodeStringBase64(String toDecode) {
        Base64.Decoder d = Base64.getDecoder();
        return d.decode(toDecode);
    }

    /**
     * Encrypts a string to an encryption message consisting of the length of the iv, the iv and the encrypted string.
     * @param toEncrypt the string to encrypt.
     * @param masterpassword the password used for encryption.
     * @return the encryption message as byte array.
     */
    public byte[] encryptToMessage(String toEncrypt, String masterpassword) {
        byte[] encrypted = this.crypt.encrypt(toEncrypt, masterpassword);
        byte[] iv = this.crypt.getIv();
        ByteBuffer bb = ByteBuffer.allocate(1 + iv.length + encrypted.length);
        bb.put((byte) iv.length);
        bb.put(iv);
        bb.put(encrypted);
        byte[] message = bb.array();
        return message;
    }

    /**
     * Decrypts an encryption message.
     * @param message the encryption message
     * @param masterpassword the password used for decryption.
     * @return the decrypted String.
     */
    public String decryptMessage(byte[] message, String masterpassword) {
        ByteBuffer bb = ByteBuffer.wrap(message);
        int ivLength = bb.get();
        if (ivLength != 16) {
            System.out.println("Invalid IV-Length!");
        }
        byte[] iv = new byte[ivLength];
        bb.get(iv);
        byte[] encrypted = new byte[bb.remaining()];
        bb.get(encrypted);
        String text = crypt.decrypt(encrypted, masterpassword, iv);
        return text;
    }
}
