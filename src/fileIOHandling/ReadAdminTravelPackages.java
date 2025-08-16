package fileIOHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entity.*;
import travelPackage.*;

public class ReadAdminTravelPackages {
	private static String filePath = "adminTravelPackages.txt";

	public static void read() {
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((line = br.readLine()) != null) {
				String[] packageInfo = line.split(",");

				if (packageInfo.length == 5) {
					createNewAdminTravelPackage(packageInfo);
				} else {
					System.out.println("Invalid line format: " + line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + filePath);
		}
	}

	private static void createNewAdminTravelPackage(String[] packageInfo) {
		ArrayList<Entity> entities = createEntities(packageInfo[0]);
		double discount;
		int day;
		String name, description;
		try {
			day = Integer.parseInt(packageInfo[1]);
			name = packageInfo[2];
			description = packageInfo[3];
			discount = Double.parseDouble(packageInfo[4]);

			AdminTravelPackage aPackage = new AdminTravelPackage(entities, day, description, name, discount);
			aPackage.addToAdminTravelPackageSystem();

		} catch (NumberFormatException e) {
			System.err.println("packageInfo[1]: " + packageInfo[1] + " or " + "packageInfo[4]: " + packageInfo[4]
					+ " format is not valid to parsedouble");
		} catch (Exception e) {
			System.err.println("An error occured while creating travel package in reading travel packages");
		}
	}

	private static ArrayList<Entity> createEntities(String entityInfos) {
		String[] entityArray = entityInfos.split("\\+");
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try {
			for (String entityInfo : entityArray) {
				if (entityInfo.substring(0, 6).equals("Flight")) {
					Entity entity = Flight.returnEntity(entityInfo.substring(7));
					entities.add(entity);
				} else if (entityInfo.substring(0, 4).equals("Taxi")) {
					Entity entity = Taxi.returnEntity(entityInfo.substring(5));
					entities.add(entity);
				} else if (entityInfo.substring(0, 5).equals("Hotel")) {
					Entity entity = Hotel.returnEntity(entityInfo.substring(6));
					entities.add(entity);
				} else {
					System.err.println(entityInfo + " is not valid entityInfo, in reading travelPackages");
				}
			}
		} catch (Exception e) {
			System.err.println("An error occured while reading the travelPackages.txt in createEntities()");
		}

		return entities;
	}

}
