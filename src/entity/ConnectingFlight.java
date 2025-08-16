package entity;

import java.time.LocalDate;

import cities.City;

public class ConnectingFlight extends Flight {

	private String stopOverArrivalTime;
	private String stopOverDepartureTime;
	private String departureCity;
	private String stopOverCity;
	private String arrivalCity;

	public ConnectingFlight(String[] connectingFlightInfo) {
		super(connectingFlightInfo[0], connectingFlightInfo[1], connectingFlightInfo[6], connectingFlightInfo[7],
				connectingFlightInfo[11], connectingFlightInfo[14], connectingFlightInfo[8]); // flightID, Airline, Ticket Class, Price, Departure Time, Arrival Time, Available Seats
		this.stopOverArrivalTime = connectingFlightInfo[12];
		this.stopOverDepartureTime = connectingFlightInfo[13];
		
		
		this.departureCity = connectingFlightInfo[2]; // departureCity
		this.arrivalCity = connectingFlightInfo[10]; // arrivalCity
		this.stopOverCity = connectingFlightInfo[9]; // stopovercity
		
		City.addEntityCity(this);
	}

	public String getStopOverArrivalTime() {
		return stopOverArrivalTime;
	}

	
	public String getStopOverDepartureTime() {
		return stopOverDepartureTime;
	}

	@Override
	public String getFromCity() {
		return departureCity;
	}

	@Override
	public String getToCity() {
		return arrivalCity;
	}

	public String getStopOverCity() {
		return stopOverCity;
	}
	
	@Override
	public String getFirstDepartureTime() {
		return firstDepartureTime;
	}
	@Override
	public String getLastArrivalTime() {
		return lastArrivalTime;
	}
	
	@Override
	public String getEntityForOption(LocalDate startDate, LocalDate endDate) {
	    return super.getEntityForOption(startDate, endDate) + ", Stop Over: " + stopOverCity;
	}
	
	@Override
	public String getEntityForOptionGo(LocalDate startDate) {
	    return super.getEntityForOptionGo(startDate) + ", Stop Over: " + stopOverCity;
	}
	
	@Override
	public String getEntityForOptionReturn(LocalDate endDate) {
	    return super.getEntityForOptionReturn(endDate) + ", Stop Over: " + stopOverCity;
	}
	public String getEntityForOptionGoBack(LocalDate startDate, LocalDate endDate) {
		return super.getEntityForOptionGoBack(startDate, endDate) + ", Stop Over: " + stopOverCity;
	}

}
