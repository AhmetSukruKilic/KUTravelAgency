package fileIOHandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import entity.*;
import travelPackage.*;

public class WriteAdminTravelPackages {
	private static String filePath = "adminTravelPackages.txt";

	public static void write(TravelPackage travelPackage) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
			ArrayList<Entity> entities = travelPackage.getEntities();
			int day = travelPackage.getDay();
			double discount = travelPackage.getDiscount();
			String name = travelPackage.getName(), description = travelPackage.getDescription();
			
			for (int i=0; i<entities.size(); i++) {
				String entityInfo = entities.get(i).getEntityShortUniqueInfo();
				String entityType = findEntityType(entities.get(i));
				bw.write(entityType + ":" + entityInfo);
				
				if (i != entities.size() - 1) bw.write("+");
			}
			bw.write("," + day);
			bw.write("," + name);
			bw.write("," + description);
			bw.write("," + discount + "\n");
			
		} catch (IOException e) {
			System.err.println("Error writing to the file: " + filePath);
		} catch (Exception e) {
			System.err.println("An Error occured while writing to the file: " + filePath);
		}
	}

	private static String findEntityType(Entity entity) {
		if (entity instanceof Flight) {
			return "Flight";
		}
		if (entity instanceof Taxi) {
			return "Taxi";
		}
		if (entity instanceof Hotel) {
			return "Hotel";
		}
		return null;
	}
	
}
