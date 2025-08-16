package reservation;

import travelPackage.*;
import users.Customer;

public class TravelPackageReservation extends Reservation {
	
	public TravelPackageReservation(TravelPackage travelPackage, String startDate, String endDate, Customer customer) {
		super(startDate, endDate, customer, travelPackage);
		
	}
	
	
}
