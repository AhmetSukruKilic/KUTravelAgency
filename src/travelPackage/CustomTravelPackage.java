 package travelPackage;

import java.util.ArrayList;
import java.util.HashSet;

import entity.Entity;

public class CustomTravelPackage extends TravelPackage {
	private double finalCost;
	private double discount;
	private ArrayList<Entity> cancelledEntities;
	private static HashSet<CustomTravelPackage> customTravelPackages = new HashSet<CustomTravelPackage>();

	public CustomTravelPackage(ArrayList<Entity> currentEntities, ArrayList<Entity> canceledEntities, int day, String description, String name,
			double discount) { // name and description is 'Custom' and discount is 0
		super(currentEntities, "Custom", "Custom", day);
		this.cancelledEntities = canceledEntities;
		this.finalCost = baseCost;
		this.discount = 0;
		customTravelPackages.add(this);
	}

	@Override
	public double getBaseCost() {
		return finalCost;
	}

	@Override
	public double getFinalCost() {
		return finalCost;
	}

	@Override
	public double getDiscount() {
		return discount; // 0
	}

	/**
	 * @return the customTravelPackages
	 */
	public static HashSet<CustomTravelPackage> getCustomTravelPackages() {
		return customTravelPackages;
	}
	
	public static CustomTravelPackage returnTravelPackage(ArrayList<Entity> entities, int day, String name,
			String description) {
		for (CustomTravelPackage cPackage : customTravelPackages) {
			if (isSameTravelPackage(cPackage, entities, day, name, description)) {
				return cPackage;
			}
		}
		return null;
	}

	/**
	 * @return the canceledEntities
	 */
	public ArrayList<Entity> getCancelledEntities() {
		return cancelledEntities;
	}
	
	/**
	 * @return the activeEntities
	 */
	public ArrayList<Entity> getActiveEntities() {
		ArrayList<Entity> activeEntities = entities;
		return activeEntities;
	}
	
	public Entity getFlight() {
		return entities.get(0);
	}

	public Entity getTaxi() {
		return entities.get(0);
	}

	public Entity getHotel() {
		return entities.get(0);
	}

	public Entity getReturnTaxi() {
		return entities.get(0);
	}

	public Entity getReturnFlight() {
		return entities.get(0);
	}
	
	public void setFinalCost() {
		finalCost = baseCost;
	}

}
