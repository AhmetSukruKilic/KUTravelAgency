package travelPackage;

import java.util.ArrayList;
import java.util.HashSet;

import entity.Entity;

public class SingleEntityTravelPackage extends TravelPackage {
	private double discount;
	private double finalCost;
	private static HashSet<SingleEntityTravelPackage> singleEntityTravelPackages = new HashSet<SingleEntityTravelPackage>();

	public SingleEntityTravelPackage(ArrayList<Entity> entities, int day, String description, String name,
			double discount) { // name and description is 'SingleEntity' and discount is 0) {
		super(entities, "SingleEntity", "SingleEntity", day);
		this.discount = 0;
		this.finalCost = baseCost;
		singleEntityTravelPackages.add(this);
	}

	@Override
	public double getDiscount() {
		return discount;
	}

	@Override
	public double getBaseCost() {
		return finalCost;
	}

	@Override
	public double getFinalCost() {
		return finalCost;
	}

	/**
	 * @return the singleEntityTravelPackages
	 */
	public static HashSet<SingleEntityTravelPackage> getSingleEntityTravelPackages() {
		return singleEntityTravelPackages;
	}

	public static SingleEntityTravelPackage returnTravelPackage(ArrayList<Entity> entities, int day, String name,
			String description) {
		for (SingleEntityTravelPackage sPackage : singleEntityTravelPackages) {
			if (isSameTravelPackage(sPackage, entities, day, name, description)) {
				return sPackage;
			}
		}
		return null;
	}

}
