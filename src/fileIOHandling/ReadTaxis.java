package fileIOHandling;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entity.Taxi;

public class ReadTaxis {
	private static String filePath = "FinalKU_Travel_Agency_Dataset_Taxis.csv";

	public static void read() {
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine(); // first line
			while ((line = br.readLine()) != null) {
				String[] taxiInfo = line.split(",");

				if (taxiInfo.length == 5) {
					createNewTaxi(taxiInfo);
				} else {
					System.out.println("Invalid line format: " + line);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filePath);
		} catch (IOException e) {
			System.err.println("Error reading the file: " + filePath);
		}
	}

	private static void createNewTaxi(String[] taxiInfo) {
		new Taxi(taxiInfo); 
	}
}
