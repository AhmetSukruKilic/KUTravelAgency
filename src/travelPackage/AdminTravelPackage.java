package travelPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

import entity.Entity;
import entity.Flight;
import entity.Hotel;
import entity.Taxi;
import reservation.SingleReservations;

public class AdminTravelPackage extends TravelPackage {
	private double discount;
	private double finalCost;
	private static HashSet<AdminTravelPackage> adminTravelPackages = new HashSet<AdminTravelPackage>();

	public AdminTravelPackage(ArrayList<Entity> entities, int day, String description, String name, double discount) {
		super(entities, name, description, day);
		this.discount = discount;
		this.finalCost = baseCost * (100 - discount) / 100;
	}

	@Override
	public double getDiscount() {
		return discount;
	}

	@Override
	public double getBaseCost() {
		return baseCost;
	}

	@Override
	public double getFinalCost() {
		return finalCost;
	}

	/**
	 * @return the discountedTravelPackages
	 */
	public static HashSet<AdminTravelPackage> getAdminTravelPackages() {
		return adminTravelPackages;
	}

	public static AdminTravelPackage returnTravelPackage(ArrayList<Entity> entities, int day, String name,
			String description) {
		for (AdminTravelPackage dPackage : adminTravelPackages) {
			if (isSameTravelPackage(dPackage, entities, day, name, description)) {
				return dPackage;
			}
		}
		System.err.println("No such admin travel package error in AdminTravelPackage");
		return null;
	}

	public static AdminTravelPackage isThereAdminTravelPackage(ArrayList<Entity> entities, int day) {
		for (AdminTravelPackage aPackage : adminTravelPackages) {
			if (isSameEntitiesAndDay(aPackage, entities, day)) {
				return aPackage;
			}
		}
		return null;
	}

	public void addToAdminTravelPackageSystem() {
		adminTravelPackages.add(this);
	}

	public String adminTravelPackageInfo(LocalDate startDate, LocalDate endDate) {
		String rst = "  Itinerary:\r\n" + getActiveEntities() + "  Package Details:\r\n" + "    Name: " + getName()
				+ "\r\n" + "    Description: " + getDescription() + "\r\n" + "    Duration: " + getDay() + " days\r\n"
				+ "    Base Cost: " + String.format(Locale.US, "%.2f", getBaseCost()) + "\r\n" + "    Discount: "
				+ String.format(Locale.US, "%.2f", getDiscount()) + "\r\n" + "    Total Cost: "
				+ String.format(Locale.US, "%.2f", getFinalCost()) + "\r\n" + "    Availability: "
				+ calculateAvailability(startDate, endDate);
		return rst;
	}

	private int calculateAvailability(LocalDate startDate, LocalDate endDate) {
		int minn = Integer.MAX_VALUE;
		boolean isFlight = true;
		boolean isTaxi = true;
		for (Entity entity : getEntities()) {

			if (entity instanceof Flight) {
				if (isFlight) {
					Math.min(SingleReservations.getNumberAvailiblityForStartDate(entity, startDate), minn);
					isFlight = false;
				} else {
					Math.min(SingleReservations.getNumberAvailiblityForEndDate(entity, endDate), minn);
				}
			}

			if (entity instanceof Hotel) {
				if (isTaxi) {
					Math.min(SingleReservations.getNumberAvailiblityForStartDate(entity, startDate), minn);
					isTaxi = false;
				} else {
					Math.min(SingleReservations.getNumberAvailiblityForEndDate(entity, endDate), minn);
				}
			}

			if (entity instanceof Taxi) {
				minn = Math.min(SingleReservations.getNumberAvailiblity(entity, startDate, endDate), minn);

			}
		}

		return minn;
	}

	private String getActiveEntities() {
		ArrayList<Entity> entities = getEntities();

		String activeEntities = "";

		for (Entity entity : entities) {

			if (entity instanceof Flight) {
				activeEntities += "    " + "Flight: " + ((Flight) entity).getEntityShortUniqueInfoWithPlaces();
			}
			if (entity instanceof Taxi) {
				activeEntities += "    " + "Taxi: " + ((Taxi) entity).getEntityShortUniqueInfoWithPlaces();
			}
			if (entity instanceof Hotel) {
				activeEntities += "    " + "Hotel: " + ((Hotel) entity).getEntityShortUniqueInfoWithPlaces();
			}
			activeEntities += "\r\n";
		}

		return activeEntities;
	}

	public static boolean isThereSameNameAdminTravelPackage(String name) {
		for (AdminTravelPackage aPackage: adminTravelPackages) {
			if (aPackage.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

}
