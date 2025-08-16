package fileIOHandling;

import java.io.*;
import entity.*;

public final class ReadHotels {

	private static String filePath = "FinalKU_Travel_Agency_Dataset_Hotels.csv";

	public static void read() {
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			line = br.readLine(); // first line
			while ((line = br.readLine()) != null) {
				String[] hotelInfo = line.split(",");

				if (hotelInfo.length == 6) {
					createNewHotel(hotelInfo);
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

	private static void createNewHotel(String[] hotelInfo) {
		new Hotel(hotelInfo);
	}

}
