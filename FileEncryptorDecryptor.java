import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class FileEncryptorDecryptor {

    public static void welcome() {
        // Clear the console screen (if supported)
        clearConsole();

        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
		System.out.print("______ _ _        _____                            _                   __ ______                           _             \n");
        System.out.print("|  ___(_) |      |  ___|                          | |                 / / |  _  \\                         | |            \n");
        System.out.print("| |_   _| | ___  | |__ _ __   ___ _ __ _   _ _ __ | |_ ___  _ __     / /  | | | |___  ___ _ __ _   _ _ __ | |_ ___  _ __ \n");
        System.out.print("|  _| | | |/ _ \\ |  __| '_ \\ / __| '__| | | | '_ \\| __/ _ \\| '__|   / /   | | | / _ \\/ __| '__| | | | '_ \\| __/ _ \\| '__|\n");
        System.out.print("| |   | | |  __/ | |__| | | | (__| |  | |_| | |_) | || (_) | |     / /    | |/ /  __/ (__| |  | |_| | |_) | || (_) | |   \n");
        System.out.print("\\_|   |_|_|\\___| \\____/_| |_|\\___|_|   \\__, | .__/ \\__\\___/|_|    /_/     |___/ \\___|\\___|_|   \\__, | .__/ \\__\\___/|_|   \n");
        System.out.print("                                        __/ | |                                                 __/ | |                  \n");
        System.out.print("                                       |___/|_|                                                |___/|_|                  \n");
        System.out.print(" _   _ _   _ _ _ _                                                                                                       \n");
        System.out.print("| | | | | (_) (_) |                                                                                                      \n");
        System.out.print("| | | | |_ _| |_| |_ _   _                                                                                               \n");
        System.out.print("| | | | __| | | | __| | | |                                                                                              \n");
        System.out.print("| | | | |_| | | | |_| |_| |                                                                                              \n");
        System.out.print(" \\___/ \\__|_|_|_|\\__|\\__, |                                                                                              \n");
        System.out.print("                      __/ |                                                                                              \n");
        System.out.print("                     |___/                                                                                               \n");
		System.out.println("---------------------------------------------------------------------------------------------------------------------------");
		
    }

    public static void main(String[] args) {
        welcome();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Encrypt a file");
            System.out.println("2. Decrypt a file");
            System.out.println("3. Help");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1/2/3/4): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            if (choice == 4) {
                System.out.println("Exiting the program.");
                break; // Exit the program
            } else if (choice == 3) {
                printHelp();
            } else if (choice == 1 || choice == 2) {
                System.out.print("Enter the path of the input file: ");
                String inputFile = scanner.nextLine();

                try {
                    if (choice == 1) {
                        handleEncryption(inputFile);
                    } else {
                        handleDecryption(inputFile);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    public static void handleEncryption(String inputFile) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to generate a random key? (yes/no): ");
        String generateKeyChoice = scanner.nextLine();

        String key;
        if (generateKeyChoice.equalsIgnoreCase("yes")) {
            key = generateRandomKey();
            System.out.println("Generated Key: " + key);
            System.out.println("Please keep this key securely. You will need it for decryption.");
        } else {
            System.out.print("Enter the secret key: ");
            key = scanner.nextLine();
        }

        encryptFile(inputFile, key);
        System.out.println("File encrypted successfully. The original file has been replaced with the encrypted file.");
    }

    public static void handleDecryption(String inputFile) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the secret key: ");
        String key = scanner.nextLine();

        decryptFile(inputFile, key);
        System.out.println("File decrypted successfully. The original file has been replaced with the decrypted file.");
    }

    public static String generateRandomKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[16]; // 128 bits
        random.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public static void encryptFile(String inputFile, String key) throws Exception {
       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);
        byte[] encryptedBytes = cipher.doFinal(inputBytes);
        inputStream.close();
         
	   String inputFile1 = inputFile + ".enc";
		 
        FileOutputStream outputStream = new FileOutputStream(inputFile1);
        outputStream.write(encryptedBytes);
        outputStream.close();
		
		File originalFile = new File(inputFile);
        if (originalFile.exists() && originalFile.delete()) {
        
    } else {
        
    }
    }

    public static void decryptFile(String inputFile, String key) throws Exception {
        Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        FileInputStream inputStream = new FileInputStream(inputFile);
        byte[] inputBytes = new byte[(int) new File(inputFile).length()];
        inputStream.read(inputBytes);
        byte[] decryptedBytes = cipher.doFinal(inputBytes);
        inputStream.close();
		
        String inputFile2 = inputFile.replace(".enc", "");
		
        FileOutputStream outputStream = new FileOutputStream(inputFile2);
        outputStream.write(decryptedBytes);
        outputStream.close();
		
		File originalFile = new File(inputFile);
    if (originalFile.exists() && originalFile.delete()) {
        
    } else {
        
    }
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void printHelp() {
        clearConsole();
        System.out.println("------------------------------------------------------------");
        System.out.println("File Encryption and Decryption Tool Help");
        System.out.println("------------------------------------------------------------");
        System.out.println("This tool allows you to encrypt and decrypt files using AES encryption.");
        System.out.println("1. To encrypt a file, choose option 1 and provide the input file path.");
        System.out.println("   You can choose to generate a random key or provide your own key.");
        System.out.println("2. To decrypt a file, choose option 2 and provide the input file path along with the key.");
        System.out.println("3. To display this help message again, choose option 3.");
        System.out.println("------------------------------------------------------------");
		
    }
}
