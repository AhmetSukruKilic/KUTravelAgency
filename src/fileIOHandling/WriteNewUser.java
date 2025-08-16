package fileIOHandling;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

import generalMessages.*;

public final class WriteNewUser {

	private static String filePath = "userInfo.txt";

    private WriteNewUser() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    public static void write(String name, String password, String role) {
        try (FileWriter writer = new FileWriter(filePath, true)) { 
            writer.write(encode(name) + "," + encode(password) + "," + role + "\n");
            new AcceptedMessage("Welcome New User " + name, "Name and password saved successfully!");
        } catch (IOException e) {
            new WarningMessage("Fatal Error", "An error occurred while writing name and password to the file!");
            e.printStackTrace();
        }
    }

    private static String encode(String word) {
        return Base64.getEncoder().encodeToString(word.getBytes());
    }
}
