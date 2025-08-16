package users;

import java.time.LocalDate;
import java.util.HashSet;

import generalMessages.WarningMessage;
import reservation.Reservation;

public class Customer extends User{
	
	private static int trackCustomerCount = 17351;
	private static HashSet<Customer> validCustomers = new HashSet<Customer>();

	
	public Customer(String name, String password) {
		super(name, password, "Customer", trackCustomerCount += 17);
		validCustomers.add(this);
	}

	
	/**
	 * @return the validCustomers
	 */
	public static HashSet<Customer> getValidCustomers() {
		return validCustomers;
	}

	public static Customer returnCustomerFromName(String name) {
		for(User user: validUsers) {
			if (user.isCustomer() && user.getName().equals(name)) 
				return (Customer) user;
		}
		System.err.println("no such customer with name: " + name);
		return null;
	}
	
	
	public boolean isBusy(LocalDate startDate, LocalDate endDate) {
		HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(this);
		
		if (activeReservations == null) {
			return false;
		}
		
		for (Reservation rsv: activeReservations) {
			if (rsv.containsDate(startDate,endDate)) {
				new WarningMessage("customer is busy", "customer " + name + " is busy in those days");
				return true;
			}
		}
		return false;
	}
}
