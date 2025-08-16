package fileIOHandling;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import entity.*;
import reservation.Reservation;
import users.Customer;

public class ReadUserLogs {
	private static String filePath = "userLogs.txt";

	public static void read() {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			Customer customer = null;
			ArrayList<Entity> activeEntities = new ArrayList<>();
			ArrayList<Entity> cancelledEntities = new ArrayList<>();
			String packageName = "";
			String description = "";
			int duration = 0;
			LocalDate startDate = null, endDate = null;
			double discount = 0.0;
			boolean isReadingPackage = false;

			while ((line = br.readLine()) != null) {
				line = line.trim();

				try {
					if (line.startsWith("Customer: ")) {
						String customerName = line.replace("Customer:", "").trim();
						customer = Customer.returnCustomerFromName(customerName);
						if (customer == null) {
							throw new IllegalArgumentException("Customer not found: " + customerName);
						}
					} else if (line.startsWith("Flight:")) {
						String flightInfo = line.replace("Flight:", "").trim();
						flightInfo = flightInfo.split(",")[0];
						activeEntities.add(Flight.returnEntity(flightInfo));
					} else if (line.startsWith("Taxi:")) {
						String taxiInfo = line.replace("Taxi:", "").trim();
						taxiInfo = taxiInfo.split(",")[0];
						activeEntities.add(Taxi.returnEntity(taxiInfo));
					} else if (line.startsWith("Hotel:")) {
						String hotelInfo = line.replace("Hotel:", "").trim();
						hotelInfo = hotelInfo.split(",")[0];
						activeEntities.add(Hotel.returnEntity(hotelInfo));
					} else if (line.startsWith("Old")) {
						processCancelledEntity(line, cancelledEntities);
					} 
					 else if (line.startsWith("Package Details:")) {
						isReadingPackage = true;
					} else if (isReadingPackage && line.startsWith("Name:")) {
						packageName = line.replace("Name:", "").trim();
					} else if (isReadingPackage && line.startsWith("Description:")) {
						description = line.replace("Description:", "").trim();
					} else if (isReadingPackage && line.startsWith("Duration:")) {
						duration = Integer.parseInt(line.replace("Duration:", "").trim().replace(" days", ""));
					} else if (isReadingPackage && line.startsWith("Start:")) {
						startDate = LocalDate.parse(line.replace("Start:", "").trim().replace("(", "").replace(")", ""));
					} else if (isReadingPackage && line.startsWith("End:")) {
						endDate = LocalDate.parse(line.replace("End:", "").trim().replace("(", "").replace(")", ""));
					} else if (isReadingPackage && line.startsWith("Discount:")) {
						discount = Double.parseDouble(line.replace("Discount:", "").trim());
					} else if (line.startsWith("Status:")) {
						line = line.replace("Status: ", "").trim();

						if (line.equals("Active")) {
							processReservation(activeEntities, cancelledEntities, packageName, description, duration,
									discount, startDate, endDate, customer);
						} else {
							cancelReservation(activeEntities, cancelledEntities, packageName, description, duration,
									discount, startDate, endDate, customer);
						}

						// Reset for the next log entry
						activeEntities = new ArrayList<>();
						cancelledEntities = new ArrayList<>();
						isReadingPackage = false;
					}
				} catch (IllegalArgumentException e) {
					System.err.println("Warning: " + e.getMessage());
				} catch (Exception e) {
					System.err.println("Error processing line: " + line);
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: File not found at " + filePath);
		} catch (IOException e) {
			System.err.println("Error reading the file: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("An unexpected error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}


	private static void processCancelledEntity(String line, ArrayList<Entity> cancelledEntities) {
		try {
			String restLine = line.replace("Old ", "").trim().replace("- Cancelled", "").trim();
			restLine = restLine.split(",")[0];
			if (line.startsWith("Flight:")) {
				cancelledEntities.add(Flight.returnEntity(restLine));
			} else if (line.startsWith("Taxi:")) {
				cancelledEntities.add(Taxi.returnEntity(restLine));
			} else if (line.startsWith("Hotel:")) {
				cancelledEntities.add(Hotel.returnEntity(restLine));
			}
		} catch (Exception e) {
			System.err.println("Error processing cancelled entity: " + line);
			e.printStackTrace();
		}
	}

	

	private static void processReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> cancelledEntities,
			String packageName, String description, int duration, double discount, LocalDate startDate,
			LocalDate endDate, Customer customer) {
		try {
			if (customer == null) {
				throw new IllegalArgumentException("Customer is null. Cannot process reservation.");
			}

			if (activeEntities.isEmpty()) {
				throw new IllegalArgumentException("No active entities found for reservation.");
			}

			Reservation.makeActiveReservation(activeEntities, cancelledEntities, packageName, description, duration,
					discount, startDate.toString(), endDate.toString(), customer);
		} catch (Exception e) {
			System.err.println("Error creating reservation for customer: " + customer);
			e.printStackTrace();
		}
	}
	
	
	private static void cancelReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> cancelledEntities,
			String packageName, String description, int duration, double discount, LocalDate startDate,
			LocalDate endDate, Customer customer) {
		try {
			if (customer == null) {
				throw new IllegalArgumentException("Customer is null. Cannot process reservation.");
			}

			if (activeEntities.isEmpty()) {
				throw new IllegalArgumentException("No active entities found for reservation.");
			}

			Reservation.makeCancelReservation(activeEntities, cancelledEntities, packageName, description, duration,
					discount, startDate.toString(), endDate.toString(), customer);
		} catch (Exception e) {
			System.err.println("Error creating reservation for customer: " + customer);
			e.printStackTrace();
		}
	}
     
}
