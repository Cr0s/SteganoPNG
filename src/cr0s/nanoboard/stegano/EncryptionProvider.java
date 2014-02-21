/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cr0s.nanoboard.stegano;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Cryptography utilities:
 *
 * Encrypt and Decrypt routines Hashsum generation
 *
 * @author Cr0s
 */
public class EncryptionProvider {

    public static final String ALGO = "AES";
    public static final String MODE = "CBC";
    public static final String PADDING = "PKCS5Padding";
    public static final int KEY_LENGTH = 16;
    
    /**
     * Encrypt bytes with AES by specified key
     * @param bytes bytes to crypt
     * @param keyValue encryption key
     * @return 
     */
    public static byte[] encryptBytes(byte[] bytes, byte[] keyValue) {
        try {
            byte[] keyValuePad = new byte[KEY_LENGTH];
            System.arraycopy(keyValue, 0, keyValuePad, 0, keyValue.length);
            
            // Init random IV based on key hash
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            Random rng = new Random(new String(keyValue).hashCode());
            rng.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);            
            
            Key key;
            key = new SecretKeySpec(keyValuePad, ALGO);
            Cipher c = Cipher.getInstance(ALGO + "/" + MODE + "/" + PADDING);
            c.init(Cipher.ENCRYPT_MODE, key, ivspec);
            
            byte[] encVal = c.doFinal(bytes);
            return encVal;
        } catch (InvalidKeyException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * Decrypt bytes with AES by specified key
     * @param bytes bytes to decrypt
     * @param keyValue encryption key
     * @return 
     */
    public static byte[] decryptBytes(byte[] bytes, byte[] keyValue) {
        try {
            // Init random IV based on key hash
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            Random rng = new Random(new String(keyValue).hashCode());
            rng.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);
    
            byte[] keyValuePad = new byte[KEY_LENGTH];
            System.arraycopy(keyValue, 0, keyValuePad, 0, keyValue.length);
            
            Key key;
            key = new SecretKeySpec(keyValuePad, ALGO);          
            Cipher c = Cipher.getInstance(ALGO + "/" + MODE + "/" + PADDING);
            c.init(Cipher.DECRYPT_MODE, key, ivspec);
            
            byte[] encVal = c.doFinal(bytes);
            return encVal;
        } catch (InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static byte[] sha512(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(bytes);

            byte byteData[] = md.digest();

            return byteData;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptionProvider.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        return null;
    }
}
