import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

public class FileEncryptorDecryptorTest {

    @Test
    public void testEncryptAndDecryptFile() throws Exception {
        FileEncryptorDecryptor fileEncryptor = new FileEncryptorDecryptor();

        // Create a temporary input file with some content
        String inputFilePath = createTempFile("test_input.txt", "Hello, World!");

        // Generate a random key for encryption
        String key = fileEncryptor.generateRandomKey();

        // Encrypt the file
        String encryptedFilePath = inputFilePath + ".enc";
        fileEncryptor.encryptFile(inputFilePath, key);

        // Ensure the encrypted file exists and is not empty
        File encryptedFile = new File(encryptedFilePath);
        assertTrue(encryptedFile.exists(), "Encrypted file should exist");
        assertTrue(encryptedFile.length() > 0, "Encrypted file should not be empty");

        // Decrypt the encrypted file
        String decryptedFilePath = inputFilePath;
        fileEncryptor.decryptFile(encryptedFilePath, key);

        // Ensure the decrypted file exists and is not empty
        File decryptedFile = new File(decryptedFilePath);
        assertTrue(decryptedFile.exists(), "Decrypted file should exist");
        assertTrue(decryptedFile.length() > 0, "Decrypted file should not be empty");

        // Read the content of the decrypted file
        String decryptedContent = readFile(decryptedFilePath);

        // Assert that the decrypted content matches the original content
        assertEquals("Hello, World!", decryptedContent, "Decrypted content should match the original content");

        // Clean up temporary files
        encryptedFile.delete();
        decryptedFile.delete();
    }

    private String createTempFile(String fileName, String content) throws Exception {
        File file = File.createTempFile(fileName, null);
        Files.write(file.toPath(), content.getBytes());
        return file.getAbsolutePath();
    }

    private String readFile(String filePath) throws Exception {
        return new String(Files.readAllBytes(new File(filePath).toPath()));
    }
}
