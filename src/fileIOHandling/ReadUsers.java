package fileIOHandling;

import java.io.*;
import java.util.*;

import generalMessages.*;
import users.*;

public final class ReadUsers {
	
	private static String filePath = "userInfo.txt";
	
	public static void read() {
		String line;
		try (FileReader fileReader = new FileReader(filePath)) {
			BufferedReader br = new BufferedReader(fileReader);
			while ((line = br.readLine()) != null) { // Read each line from the file
				String[] parts = line.split(","); // Split the line into parts based on the delimiter

				if (parts.length == 3) { // Ensure the line contains exactly three fields
					String name = decode(parts[0].trim());
					String password = decode(parts[1].trim());
					String role = parts[2].trim();

					createNewUser(name, password, role); // Create a new user
				} else if (parts.length != 0) {
					System.out.println("Invalid line format: " + line);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filePath);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error reading the file: " + filePath);
			e.printStackTrace();
		}
	}

	private static void createNewUser(String name, String password, String role) {
		try {
			if (role.equalsIgnoreCase("customer")) {
				new Customer(name, password);
			} else if (role.equalsIgnoreCase("admin")) {
				new Admin(name, password);
			} else {
				throw new InvalidObjectException("Invalid Role");
			}
		} catch (InvalidObjectException e) {
			new WarningMessage("Invalid Role", "No such role: " + role);
		}
	}

	private static String decode(String word) {
		return new String(Base64.getDecoder().decode(word));
	}
}
