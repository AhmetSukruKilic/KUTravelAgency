package fileIOHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import entity.*;
import travelPackage.*;

public class ReadTravelPackages {
	private static String filePath = "travelPackages.txt";

	public static void read() {
		String line;
  //TODO make this file read only admin package
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			while ((line = br.readLine()) != null) {
				String[] packageInfo = line.split(",");

				if (packageInfo.length == 5) {
					createNewTravelPackage(packageInfo);
				} else {
					System.out.println("Invalid line format: " + line);
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading the file: " + filePath);
		}
	}

	private static void createNewTravelPackage(String[] packageInfo) {
		ArrayList<Entity> entities = createEntities(packageInfo[0]);
		double discount;
		int day;
		String name, description;
		try {
			day = Integer.parseInt(packageInfo[1]);
			name = packageInfo[2];
			description = packageInfo[3];
			discount = Double.parseDouble(packageInfo[4]);

			if (isCustomTravelPackage(name, description, discount)) {
				new CustomTravelPackage(entities, day, description, name, discount);
				
			} else if (isSingleEntityTravelPackage(name, description, discount, entities, day)) {
				new SingleEntityTravelPackage(entities, day, description, name, discount);
				
			} else if (isError(name, description, discount, entities, day)) {
				System.err.println("Package is not valid for name, description, discount,day: " + name + " " + description
						+ " " + discount + " " + day);
			} else {
				new AdminTravelPackage(entities, day, name, description, discount);
			}

		} catch (NumberFormatException e) {
			System.err.println("packageInfo[1]: " + packageInfo[1] + " or " + "packageInfo[4]: " + packageInfo[4]
					+ " format is not valid to parsedouble");
		} catch (Exception e) {
			System.err.println("An error occured while creating travel package in reading travel packages");
		}
	}

	private static boolean isSingleEntityTravelPackage(String name, String description, double discount,
			ArrayList<Entity> entities, double day) {
		if (name.equals("SingleEntity") && name.equals("SingleEntity") && discount == 0 && entities.size() == 1)
			if (entities.get(0) instanceof Hotel) {
				return day != 0;
			} else {
				return day == 0;
			}
		return false;
	}

	private static boolean isCustomTravelPackage(String name, String description, double discount) {
		return name.equals("Custom") && description.equals("Custom") && discount == 0;
	}

	private static boolean isError(String name, String description, double discount, ArrayList<Entity> entities,
			int day) {
		if (name.equals("Custom") || description.equals("Custom") || name.equals("SingleEntity")
				|| name.equals("SingleEntity") || entities.size() == 1) {
			return true;
		}

		else {
			if (name.equals("SingleEntity") && name.equals("SingleEntity") && discount == 0 && entities.size() == 1)
				if (entities.get(0) instanceof Hotel) {
					return day == 0;
				} else {
					return day != 0;
				}
			return false;
		}
	}

	private static ArrayList<Entity> createEntities(String entityInfos) {
		String[] entityArray = entityInfos.split("\\+");
		ArrayList<Entity> entities = new ArrayList<Entity>();
		try {
			for (String entityInfo : entityArray) {
				if (entityInfo.substring(1, 7).equals("Flight")) {
					Entity entity = Flight.returnEntity(entityInfo);
					entities.add(entity);
				} else if (entityInfo.substring(1, 5).equals("Taxi")) {
					Entity entity = Taxi.returnEntity(entityInfo);
					entities.add(entity);
				} else if (entityInfo.substring(1, 6).equals("Hotel")) {
					Entity entity = Hotel.returnEntity(entityInfo);
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
