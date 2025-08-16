package travelPackage;

import java.util.ArrayList;

import entity.*;

public abstract class TravelPackage {
	protected ArrayList<Entity> entities;
	protected String startHour;		
	protected String endHour;
	protected boolean doesContainDirectFlight;
	protected boolean doesContainConnectingFlight;
	protected String name;
	protected String description;
	protected int day;
	protected double baseCost;
	protected double distanceToHotel;
	protected boolean isTravelinInYourCity;

	public TravelPackage(ArrayList<Entity> entities, String name, String description, int day) {
		this.description = description;
		this.name = name;
		this.entities = entities;
		this.day = day;
		distanceToHotel = 0;
		initializeStartHour();
		initializeEndHour();
		initializeDoesContainFlight();
		initializeDistanceToHotel();
		initializeBaseCost();
		initializeIsTravelinInYourCity();
	}

	private void initializeIsTravelinInYourCity() {
		if (startHour.equals("no start") && endHour.equals("no end")) {
			isTravelinInYourCity = true;
			return;
		}
		
		if (! (startHour.equals("no start") && endHour.equals("no end")) ) {
			isTravelinInYourCity = false;
			return;
		}
			
		if (startHour.equals("no start") || endHour.equals("no end"))
			System.err.println("there is no such travel package that has only one of 'no end' or 'no start'");
	}

	public void initializeDistanceToHotel() {
		for (final Entity entity : entities) {
			if (entity instanceof Hotel) {
				distanceToHotel = ((Hotel) entity).getDoubleDistanceToAirport();
				break;
			}
		}
	}

	private double initializeBaseCost() {
		baseCost = 0;
		for (final Entity entity : entities) {
			if (entity instanceof Flight) {
				baseCost += ((Flight) entity).getDoubleCost();
			}
			if (entity instanceof Taxi) {
				baseCost += ((Taxi) entity).getDoubleBaseFare() + ((Taxi) entity).getDoublePerKmRate() * distanceToHotel;
			}
			if (entity instanceof Hotel) {
				distanceToHotel = ((Hotel) entity).getDoubleDistanceToAirport();
				baseCost += ((Hotel) entity).getDoubleCost() * day;
			}
		}
		return baseCost;
	}

	private void initializeStartHour() {
		try {
			Entity firstFlight = entities.get(entities.size() - 1);

			if (firstFlight instanceof Flight)
				startHour = ((Flight) firstFlight).getFirstDepartureTime();
			else
				startHour = "no start";
		} catch (NullPointerException e) {
			throw new NullPointerException("Null list at DiscountedTravelPackage");
		}

	}

	private void initializeEndHour() throws NullPointerException {
		try {
			Entity lastFlight = entities.get(entities.size() - 1);

			if (lastFlight instanceof Flight)
				endHour = ((Flight) lastFlight).getLastArrivalTime();
			else
				endHour = "no end";
		} catch (NullPointerException e) {
			throw new NullPointerException("Null list at DiscountedTravelPackage");
		}

	}

	private void initializeDoesContainFlight() {
		try {
			for (Entity entity : entities) {
				if (entity instanceof DirectFlight)
					doesContainDirectFlight = true;
				if (entity instanceof ConnectingFlight)
					doesContainConnectingFlight = true;
			}
		} catch (NullPointerException e) {
			throw new NullPointerException("Null list at DiscountedTravelPackage");
		}
	}

	/**
	 * @return the entities
	 */
	public ArrayList<Entity> getEntities() {
		return entities;
	}

	/**
	 * @return the discount
	 */
	public abstract double getDiscount();

	/**
	 * @return the baseCost
	 */
	public abstract double getBaseCost();

	/**
	 * @return the finalCost
	 */
	public abstract double getFinalCost();

	/**
	 * @return the startHour
	 */
	public String getStartHour() {
		return startHour;
	}

	/**
	 * @return the endHour
	 */
	public String getEndHour() {
		return endHour;
	}

	/**
	 * @return the doesContainDirectFlight
	 */
	public boolean doesContainDirectFlight() {
		return doesContainDirectFlight;
	}

	/**
	 * @return the doesContainConnectingFlight
	 */
	public boolean doesContainConnectingFlight() {
		return doesContainConnectingFlight;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	protected static String getEntityInfo(ArrayList<Entity> entities, int day) {
		String rstEntities = "";

		for (Entity entity : entities) {
			rstEntities += " " + entity;
		}

		return  "[" + rstEntities + "." + day + "]";
	}
		
	protected static String travelPackageEntityInfo(TravelPackage tPackage) {
		String rstEntities = "";

		for (Entity entity : tPackage.getEntities()) {
			rstEntities += " " + entity.toString();
		}

		return  "[" + rstEntities + "." + tPackage.getDay() + "]";
	}
	
	protected static boolean isSameEntitiesAndDay(TravelPackage tPackage, ArrayList<Entity> entities, int day) {
		if (tPackage == null || entities == null) return false;
		
		if (travelPackageEntityInfo(tPackage).equals(getEntityInfo(entities,day))) 
			return true;
			
		return false;
	}
	

	protected static boolean isSameNameAndDescription(String name, String description, TravelPackage tPackage) {
		return tPackage.getName().equals(name) && tPackage.getDescription().equals(description);
	}
	
	protected static boolean isSameTravelPackage(TravelPackage tPackage, ArrayList<Entity> entities, int day, String name, String description) {
		return isSameEntitiesAndDay(tPackage, entities, day) && isSameNameAndDescription(name, description, tPackage);
	}
	
	
	public static ArrayList<Entity> returnEntityForm(String[] entityStrings, String departureCity, String arrivalCity) {
		ArrayList<Entity> entities = new ArrayList<Entity>();

		entities.add(Flight.returnEntity(entityStrings[0], departureCity, arrivalCity));
		entities.add(Taxi.returnEntity(entityStrings[1], arrivalCity));
		entities.add(Hotel.returnEntity(entityStrings[2], arrivalCity));
		entities.add(Taxi.returnEntity(entityStrings[3], arrivalCity));
		entities.add(Flight.returnEntity(entityStrings[4], arrivalCity, departureCity));

		return entities;
	}
	
	public static ArrayList<Entity> returnEntityForm(String entityString, String departureCity, String arrivalCity) {
		ArrayList<Entity> entities = new ArrayList<Entity>();

		if (entityString.startsWith("H:")) {
			entities.add(Hotel.returnEntity(entityString.substring(2), arrivalCity));
		}
		if (entityString.startsWith("T:")) {
			entities.add(Taxi.returnEntity(entityString.substring(2), arrivalCity));
		}
		if (entityString.startsWith("F:")) {
			entities.add(Flight.returnEntity(entityString.substring(2), departureCity, arrivalCity));
		}
		
		return entities;
	}
	
	
	
	public double setBaseCost() {
		this.baseCost = 0;
		for (final Entity entity : entities) {
			if (entity instanceof Flight) {
				baseCost += ((Flight) entity).getDoubleCost();
			}
			if (entity instanceof Taxi) {
				baseCost += ((Taxi) entity).getDoubleBaseFare() + ((Taxi) entity).getDoubleBaseFare() * distanceToHotel;
			}
			if (entity instanceof Hotel) {
				distanceToHotel = ((Hotel) entity).getDoubleDistanceToAirport();
				baseCost += ((Hotel) entity).getDoubleCost() * day;
			}
		}
		return baseCost;
	}

	public static double findDistanceToHotel(ArrayList<Entity> entities) {
		double distanceToHotel = 0;
		for (final Entity entity : entities) {
			if (entity instanceof Hotel) {
				distanceToHotel = ((Hotel) entity).getDoubleDistanceToAirport();
				return distanceToHotel;
			}
		}
		return 0;
	}

	
	public static double totalCostOfEntities(ArrayList<Entity> entities, int day) {
		double baseCost = 0;
		double distanceToHotel = findDistanceToHotel(entities);
		
		for (final Entity entity : entities) {
			if (entity instanceof Flight) {
				baseCost += ((Flight) entity).getDoubleCost();
			}
			if (entity instanceof Taxi) {
				baseCost += ((Taxi) entity).getDoubleBaseFare() + ((Taxi) entity).getDoublePerKmRate() * distanceToHotel;
			}
			if (entity instanceof Hotel) {
				baseCost += ((Hotel) entity).getDoubleCost() * day;
			}
		}
		return baseCost;
	}
	
	
}
