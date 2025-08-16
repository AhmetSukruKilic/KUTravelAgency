package entity;

import cities.City;

public class DirectFlight extends Flight {

	private String departureCity;
	private String arrivalCity;

	public DirectFlight(String[] directFlightInfo) {
		super(directFlightInfo[0], directFlightInfo[1], directFlightInfo[6], directFlightInfo[7], directFlightInfo[4],
				directFlightInfo[5], directFlightInfo[8]);  // flightID, Airline, Ticket Class, Price, Departure Time, Arrival Time, Available Seats
		this.departureCity = directFlightInfo[2]; // departureCity
		this.arrivalCity = directFlightInfo[3]; // arrivalCity
		
		City.addEntityCity(this);
	}

	@Override
	public String getFromCity() {
		return departureCity;
	}

	@Override
	public String getToCity() {
		return arrivalCity;
	}

	/**
	 * @return the firstDepartureTime
	 */
	public String getFirstDepartureTime() {
		return firstDepartureTime;
	}

	/**
	 * @return the lastArrivalTime
	 */
	public String getLastArrivalTime() {
		return lastArrivalTime;
	}

}
