package cr0s.steganopng;

import cr0s.nanoboard.image.ImageEncoder;
import cr0s.nanoboard.stegano.EncryptionProvider;
import cr0s.nanoboard.util.ByteUtils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * SteganoPNG file decoding routine
 *
 * @author Cr0s
 */
public class SteganoPNGDecode {
    public static void doDecode(String inputFile, String key, String output) {
        try {
            System.out.println("[*] Reading input file...");
            File img = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + inputFile);
            BufferedImage in = ImageIO.read(img);
            //BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);
            //Graphics2D g = newImage.createGraphics();
            //g.drawImage(in, 0, 0, null);
            //g.dispose();
            
            System.out.println("[*] Reading hidden bytes from .PNG...");
            
            // Trick: convert from unsigned short to signed byte
            short[] s = ImageEncoder.readBytesFromImage(in, new Random(key.hashCode()));
            byte[] b = new byte[s.length];
            for (int i = 0; i < b.length; i++) {
                b[i] = (byte)(s[i] & 0xFF);
            }
            
            System.out.println("[*] Decrypting bytes with AES...");
            byte[] decryptedBytes = EncryptionProvider.decryptBytes(b, key.getBytes());
            
            System.out.println("[*] Writing output to \"" + output + "\"...");
            ByteUtils.writeBytesToFile(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + output), decryptedBytes);
        
            System.out.println("[OK] Made attempt to extract hidden data from \"" + inputFile + "\" into \"" + output + "\"");
        } catch (IOException ex) {
            Logger.getLogger(SteganoPNGDecode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
