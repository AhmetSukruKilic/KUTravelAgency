package entity;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;

import reservation.SingleReservations;

public abstract class Flight implements Entity {
	protected String cost;
	protected String ticketClass;
	protected String airlineName;
	protected String flightId;
	protected String firstDepartureTime;
	protected String lastArrivalTime;
	protected String availableSeats;
	private static HashSet<Flight> allFlights = new HashSet<>();

	public Flight(String flightId, String airlineName, String ticketClass, String cost, String firstDepartureTime,
			String lastArrivalTime, String availableSeats) {
		this.flightId = flightId;
		this.airlineName = airlineName;
		this.ticketClass = ticketClass;
		this.firstDepartureTime = firstDepartureTime;
		this.lastArrivalTime = lastArrivalTime;
		this.cost = cost;
		this.availableSeats = availableSeats;
		allFlights.add(this);
	}

	public abstract String getFirstDepartureTime();

	public abstract String getLastArrivalTime();

	public boolean isDirectFlight() {
		return this instanceof DirectFlight;
	}

	public boolean isConnectedFlight() {
		return this instanceof ConnectingFlight;
	}

	/**
	 * @return the cost
	 */
	public String getCost() {
		return cost;
	}

	public double getDoubleCost() throws NumberFormatException {
		try {
			return Double.parseDouble(cost);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("cost format cannot be converted to double at Flights");
		}
	}

	/**
	 * @return the ticketClass
	 */
	public String getTicketClass() {
		return ticketClass;
	}

	/**
	 * @return the airlineName
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @return the flightId
	 */
	public String getFlightId() {
		return flightId;
	}

	/**
	 * @return the allFlights
	 */
	public static HashSet<Flight> getAllFlights() {
		return allFlights;
	}

	/**
	 * @return the availableSeats
	 */
	public String getAvailableSeats() {
		return availableSeats;
	}

	public int getNumberAvailability() {
		try {
			return Integer.parseInt(availableSeats);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("availableSeats format cannot be converted to double at Flights");
		}
	}

	@Override
	public String getEntityShortUniqueInfo() {
		return "(" + airlineName + "|" + flightId + "|" + ticketClass + ")";
	}

	@Override
	public String getEntityShortUniqueInfoWithPlaces() {
		return getEntityShortUniqueInfo() + ", from " + getFromCity() + " to " + getToCity();
	}

	public static Entity returnEntity(String flightInfo) {
		for (Flight flight : allFlights) {
			if (flightInfo.equals(flight.getEntityShortUniqueInfo())) {
				return flight;
			}
		}
		System.err.println("No such entity: " + flightInfo);
		return null;
	}

	@Override
	public String toString() {
		return getEntityShortUniqueInfo();
	}

	public String getEntityForOptionPrefix() {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoubleCost());

	    return String.format("name: %s; class: %s; cost: %s$", 
	            airlineName, 
	            ticketClass, 
	            formattedCost);
	}


	public String getEntityForOption(LocalDate startDate, LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoubleCost());

	    return String.format("name: %s; class: %s; cost: %s$; (Empty fields: %d)", 
	            airlineName, 
	            ticketClass,
	            formattedCost, 
	            (SingleReservations.getNumberAvailiblity(this, startDate, endDate) > 0
	                ? SingleReservations.getNumberAvailiblity(this, startDate, endDate)
	                : 0)
	    );
	}
	
	
	public String getEntityForOptionGoBack(LocalDate startDate, LocalDate endDate) {
		return  "F: " + getEntityForOptionGo(startDate);
	}
	
	
	public String getEntityForOptionGo(LocalDate startDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoubleCost());

	    return String.format("name: %s; class: %s; cost: %s$; (Empty fields: %d)", 
	            airlineName, 
	            ticketClass,
	            formattedCost, 
	            (SingleReservations.getNumberAvailiblityForStartDate(this, startDate) > 0
	                ? SingleReservations.getNumberAvailiblityForStartDate(this, startDate)
	                : 0)
	    );
	}
	
	public String getEntityForOptionReturn(LocalDate endDate) {
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    numberFormat.setMinimumFractionDigits(2);
	    numberFormat.setMaximumFractionDigits(2);

	    String formattedCost = numberFormat.format(getDoubleCost());

	    return String.format("name: %s; class: %s; cost: %s$; (Empty fields: %d)", 
	            airlineName, 
	            ticketClass,
	            formattedCost, 
	            (SingleReservations.getNumberAvailiblityForEndDate(this, endDate) > 0
	                ? SingleReservations.getNumberAvailiblityForEndDate(this, endDate)
	                : 0)
	    );
	}


	public static Entity returnEntity(String flightInfo, String departureCity, String arrivalCity) {
		for (Flight flight : allFlights) {
			if (flightInfo.contains(flight.getEntityForOptionPrefix()) && flight.getFromCity().equals(departureCity)
					&& flight.getToCity().equals(arrivalCity)) {
				return flight;
			}
		}
		System.err.println("No such entity: " + flightInfo);
		return null;
	}

}
