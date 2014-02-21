package cr0s.steganopng;

/**
 *
 * @author user
 */
public class SteganoPNG {
    public static final String VERSION = "1.0a";
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }
        
        if (args.length >= 1) {
            String keyArg = args[0];
            
            if (keyArg.equals("-c")) 
            {
                if (args.length != 5) {
                    System.out.println("Invalid arguments count!");
                    System.out.println("Usage:\n java -jar SteganoPNG.jar -c source.png yourfile.ext KEY output.png");
                    return;
                }
                
                String sourceFile = args[1];
                String file = args[2];
                String key = args[3];
                String output = args[4];
                
                SteganoPNGEncode.doEncode(sourceFile, file, key, output);
            } else if (keyArg.equals("-d")) {
                if (args.length != 4) {
                    System.out.println("Invalid arguments count!");
                    System.out.println("Usage:\n java -jar SteganoPNG.jar -d stegano.png KEY output.ext");
                    return;
                }
                
                String inputFile = args[1];
                String key = args[2];
                String output = args[3];
                
                SteganoPNGDecode.doDecode(inputFile, key, output);                
            } else {
                printUsage();
            }
        }
    }
    
    private static void printUsage() {
        System.out.println(" = SteganoPNG v" + VERSION + " =");
        System.out.println(" A simple tool to steganographically encode your file into .PNG image.\n");
        System.out.println("Usage: ");
        System.out.println(" java -jar SteganoPNG.jar -c source.png yourfile.ext KEY output.png");
        System.out.println(" java -jar SteganoPNG.jar -d stegano.png KEY output.ext\n");
        
        System.out.println("Parameters:");
        System.out.println(" -c - encrypts your file by specified KEY and encode it into .PNG");
        System.out.println(" -d - try to decrypt and extract file from steganographic .PNG\n");
        
        System.out.println("Exmaples:");
        System.out.println(" java -jar SteganoPNG.jar -c picture.png hidden_message.txt SecREtKeyPassWord wallpaper.png");
        System.out.println(" java -jar SteganoPNG.jar -d wallpaper.png SecREtKeyPassWord hidden_message.txt");
    }
}
