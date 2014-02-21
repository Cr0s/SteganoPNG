package cr0s.steganopng;

import cr0s.nanoboard.image.ImageEncoder;
import cr0s.nanoboard.image.ImageWriteException;
import cr0s.nanoboard.stegano.EncryptionProvider;
import cr0s.nanoboard.util.ByteUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * SteganoPNG file encoding routine
 * @author Cr0s
 */
public class SteganoPNGEncode {
    public static void doEncode(String sourceFile, String file, String key, String output) {
        try {
            System.out.println("[*] Reading input file...");
            File img = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + sourceFile);
            BufferedImage in = ImageIO.read(img);
            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(in, 0, 0, null);
            g.dispose();
            
            System.out.println("[*] Reading message file...");
            byte[] srcBytes = ByteUtils.readBytesFromFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + file));

            System.out.println("[*] Encrypting message bytes with AES...");
            byte[] encryptedBytes = EncryptionProvider.encryptBytes(srcBytes, key.getBytes());
            
            System.out.println("[*] Writing encyrpted message to .PNG...");
            
            // Trick: convert from unsigned number to signed byte
            short[] srcS = new short[encryptedBytes.length];
            for (int i = 0; i < encryptedBytes.length; i++) {
                srcS[i] = (short) (encryptedBytes[i] & 0xFF);
            }
            
            ImageEncoder.writeBytesToImage(in, srcS, output, new Random(key.hashCode()));

            System.out.println("[OK] Steganographic .png \"" + output + "\" generated.");
        } catch (ImageWriteException | IOException ex) {
            System.out.println("[ERROR] " + ex.getMessage());
        }         
    }
}
