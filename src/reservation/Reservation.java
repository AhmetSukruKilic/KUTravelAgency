package reservation;

import java.io.InvalidObjectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import entity.*;
import generalMessages.WarningMessage;
import travelPackage.*;
import users.Customer;

public abstract class Reservation {
	private LocalDate startDate;
	private LocalDate endDate;
	private Customer customer;
	private boolean isActiveReservation;
	private TravelPackage travelPackage;
	private static HashMap<Customer, HashSet<Reservation>> activeCustomerReservations = new HashMap<Customer, HashSet<Reservation>>();
	private static HashMap<Customer, HashSet<Reservation>> canceledCustomerReservations = new HashMap<Customer, HashSet<Reservation>>();

	protected Reservation(String startDate, String endDate, Customer customer, TravelPackage travelPackage) {
		this.startDate = LocalDate.parse(startDate);
		this.endDate = LocalDate.parse(endDate);
		this.customer = customer;
		this.travelPackage = travelPackage;
	}

	/**
	 * @return the ToCity
	 */
	public String getToCity() {
		return travelPackage.getEntities().get(0).getToCity();
	}

	/**
	 * @return the FromCity
	 */
	public String getFromCity() {
		return travelPackage.getEntities().get(0).getFromCity();
	}

	/**
	 * @return the travelPackage
	 */
	public TravelPackage getTravelPackage() {
		return travelPackage;
	}

	/**
	 * @return the entities
	 */
	public ArrayList<Entity> getEntities() {
		return travelPackage.getEntities();
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}

	/**
	 * @return the startHour
	 */
	public String getStartHour() {
		return travelPackage.getStartHour();
	}

	/**
	 * @return the endHour
	 */
	public String getEndHour() {
		return travelPackage.getEndHour();
	}

	public boolean isActiveReservation() {
		return isActiveReservation;
	}

	public String getAllTimeInfo() {
		return "Time [startDate=" + startDate + ", endDate=" + endDate + ", startHour=" + getStartHour() + ", endHour="
				+ getStartHour() + "]";
	}

	public String getReservationTimeInfo() {
		return "(" + startDate + " | " + endDate + ")";
	}

	/**
	 * @return the activeCustomerReservations for Customer
	 */
	public static HashSet<Reservation> getActiveCustomerReservations(Customer customer) {
		return activeCustomerReservations.get(customer);
	}

	/**
	 * @return the canceledCustomerReservations for Customer
	 */
	public static HashSet<Reservation> getCanceledCustomerReservations(Customer customer) {
		return canceledCustomerReservations.get(customer);
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	private static void addReservationToActiveCustomerReservations(Reservation reservation, Customer customer) {
		if (!activeCustomerReservations.containsKey(customer)) {
			activeCustomerReservations.put(customer, new HashSet<>());
		}

		if (activeCustomerReservations.get(customer).contains(reservation)) {
			new WarningMessage("Already has that reservation ",
					customer.getName() + "customer already has this reservation");
			return;
		}
		SingleReservations.addReservationToSingleReservations(reservation);
		reservation.isActiveReservation = true;
		activeCustomerReservations.get(customer).add(reservation);
	}

	private static void addReservationToCancelledCustomerReservations(Reservation reservation, Customer customer) {
		if (!canceledCustomerReservations.containsKey(customer)) {
			canceledCustomerReservations.put(customer, new HashSet<>());
		}

		if (canceledCustomerReservations.get(customer).contains(reservation)) {
			new WarningMessage("Already has that reservation ",
					customer.getName() + "customer already canceled this reservation");
			return;
		}
		SingleReservations.deleteTravelPackageFromSingleReservations(reservation);
		reservation.isActiveReservation = false;
		canceledCustomerReservations.get(customer).add(reservation);
	}

	public static void makeActiveReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> canceledEntities,
			String name, String description, int day, double discount, String startDate, String endDate,
			String customerName) throws InvalidObjectException {

		Customer customer = Customer.returnCustomerFromName(customerName);

		makeActiveReservation(activeEntities, canceledEntities, name, description, day, discount, startDate, endDate,
				customer);
	}

	public static void makeActiveReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> canceledEntities,
			String name, String description, int day, double discount, String startDate, String endDate,
			Customer customer) throws InvalidObjectException {

		TravelPackage travelPackage = null;

		if (isCustomTravelPackage(name, description, discount)) {
			travelPackage = new CustomTravelPackage(activeEntities, canceledEntities, day, description, name, discount);
		}

		else if (isSingleEntityTravelPackage(name, description, discount, activeEntities, day)) {
			travelPackage = new SingleEntityTravelPackage(activeEntities, day, description, name, discount);
		}

		else if (isError(name, description, discount, activeEntities, day, canceledEntities)) {
			throw new InvalidObjectException("Package is not valid for name, description, discount,day: " + name + " "
					+ description + " " + discount + " " + day);
		}

		else {
			travelPackage = new AdminTravelPackage(activeEntities, day, description, name, discount);
		}

		makeActiveReservation(travelPackage, startDate, endDate, customer);
	}

	public static void makeActiveReservation(TravelPackage travelPackage, String startDate, String endDate,
			Customer customer) {
		TravelPackageReservation reservation = new TravelPackageReservation(travelPackage, startDate, endDate,
				customer);
		addReservationToActiveCustomerReservations(reservation, customer);
	}

	public static void makeActiveReservation(TravelPackage travelPackage, String startDate, String endDate,
			String startHour, String endHour, String customerName) {
		Customer customer = Customer.returnCustomerFromName(customerName);
		makeActiveReservation(travelPackage, startDate, endDate, customer);
	}

	public static void makeActiveReservation(Reservation reservation, Customer customer) {
		addReservationToActiveCustomerReservations(reservation, customer);
	}

	public static void cancelReservation(Reservation reservation, Customer customer) {
		addReservationToCancelledCustomerReservations(reservation, customer);
	}

	public static void cancelReservation(Reservation reservation, String customerName) {
		Customer customer = Customer.returnCustomerFromName(customerName);
		cancelReservation(reservation, customer);
	}

	public static void makeCancelReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> canceledEntities,
			String name, String description, int day, double discount, String startDate, String endDate,
			String customerName) throws InvalidObjectException {

		Customer customer = Customer.returnCustomerFromName(customerName);

		makeCancelReservation(activeEntities, canceledEntities, name, description, day, discount, startDate, endDate,
				customer);
	}

	public static void makeCancelReservation(ArrayList<Entity> activeEntities, ArrayList<Entity> canceledEntities,
			String name, String description, int day, double discount, String startDate, String endDate,
			Customer customer) throws InvalidObjectException {

		TravelPackage travelPackage = null;

		if (isCustomTravelPackage(name, description, discount)) {
			travelPackage = new CustomTravelPackage(activeEntities, canceledEntities, day, description, name, discount);
		}

		else if (isSingleEntityTravelPackage(name, description, discount, activeEntities, day)) {
			travelPackage = new SingleEntityTravelPackage(activeEntities, day, description, name, discount);
		}

		else if (isError(name, description, discount, activeEntities, day, canceledEntities)) {
			throw new InvalidObjectException("Package is not valid for name, description, discount,day: " + name + " "
					+ description + " " + discount + " " + day);
		}

		else {
			travelPackage = new AdminTravelPackage(activeEntities, day, description, name, discount);
		}

		makeCancelReservation(travelPackage, startDate, endDate, startDate, endDate, customer);
	}

	public static void makeCancelReservation(TravelPackage travelPackage, String startDate, String endDate,
			String startHour, String endHour, Customer customer) {
		TravelPackageReservation reservation = new TravelPackageReservation(travelPackage, startDate, endDate,
				customer);
		addReservationToCancelledCustomerReservations(reservation, customer);
	}

	public static void makeCancelReservation(TravelPackage travelPackage, String startDate, String endDate,
			String startHour, String endHour, String customerName) {
		Customer customer = Customer.returnCustomerFromName(customerName);
		makeCancelReservation(travelPackage, startDate, endDate, startHour, endHour, customer);
	}

	public static void makeCancelReservation(Reservation reservation, Customer customer) {
		addReservationToCancelledCustomerReservations(reservation, customer);
	}

	private static boolean isError(String name, String description, double discount, ArrayList<Entity> entities,
			int day, ArrayList<Entity> cancelledEntities) {
		if (name.equals("Custom") || description.equals("Custom") || name.equals("SingleEntity")
				|| description.equals("SingleEntity") || entities.size() == 1 || !cancelledEntities.isEmpty()) {
			return true;
		}

		else {
			if (name.equals("SingleEntity") && name.equals("SingleEntity") && discount == 0 && entities.size() == 1)
				if (entities.get(0) instanceof Hotel) {
					return day == 0;
				} else {
					return day != 0;
				}
			return false;
		}
	}

	private static boolean isSingleEntityTravelPackage(String name, String description, double discount,
			ArrayList<Entity> entities, double day) {
		return (name.equals("SingleEntity") && description.equals("SingleEntity") && discount == 0
				&& entities.size() == 1);
	}

	private static boolean isCustomTravelPackage(String name, String description, double discount) {
		return name.equals("Custom") && description.equals("Custom") && discount == 0;
	}

	public String toString() {
		StringBuilder rst = new StringBuilder();
		rst.append("Customer: ").append(customer.getName()).append("\r\n").append("  Current Itinerary:\r\n")
				.append(getActiveEntities()).append(getCancelledEntities()).append("  Package Details:\r\n")
				.append("    Name: ").append(travelPackage.getName()).append("\r\n").append("    Description: ")
				.append(travelPackage.getDescription()).append("\r\n").append("    Duration: ")
				.append(travelPackage.getDay()).append(" days\r\n").append("    Reservation Dates:\r\n")
				.append("      Start: (").append(startDate).append(")\r\n").append("      End: (").append(endDate)
				.append(")\r\n").append("    Base Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getBaseCost())).append("\r\n")
				.append("    Discount: ").append(String.format(Locale.US, "%.2f", travelPackage.getDiscount()))
				.append("\r\n").append("    Total Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getFinalCost())).append("\r\n")
				.append("    Status: ").append(isActiveReservation ? "Active"
						: "Cancelled, Cancellation Fee: " + calculateCancellationFee() + "$");
		return rst.toString();
	}

	public String reservationInfo() {
		StringBuilder rst = new StringBuilder();
		rst.append("Customer: ").append(customer.getName()).append("\r\n").append("  Current Itinerary:\r\n")
				.append(getActiveEntities()).append(getCancelledEntities()).append("  Package Details:\r\n")
				.append("    Name: ").append(travelPackage.getName()).append("\r\n").append("    Description: ")
				.append(travelPackage.getDescription()).append("\r\n").append("    Duration: ")
				.append(travelPackage.getDay()).append(" days\r\n").append("    Reservation Dates:\r\n")
				.append("      Start: (").append(startDate).append(")\r\n").append("      End: (").append(endDate)
				.append(")\r\n").append("    Base Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getBaseCost())).append("\r\n")
				.append("    Discount: ").append(String.format(Locale.US, "%.2f", travelPackage.getDiscount()))
				.append("\r\n").append("    Total Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getFinalCost())).append("\r\n")
				.append("    Status: ").append(isActiveReservation ? "Active"
						: "Cancelled, Cancellation Fee: " + calculateCancellationFee() + "$");
		return rst.toString();
	}

	public String reservationInfoWithoutStatus() {
		StringBuilder rst = new StringBuilder();
		rst.append("Customer: ").append(customer.getName()).append("\r\n").append("  Current Itinerary:\r\n")
				.append(getActiveEntities()).append(getCancelledEntities()).append("  Package Details:\r\n")
				.append("    Name: ").append(travelPackage.getName()).append("\r\n").append("    Description: ")
				.append(travelPackage.getDescription()).append("\r\n").append("    Duration: ")
				.append(travelPackage.getDay()).append(" days\r\n").append("    Reservation Dates:\r\n")
				.append("      Start: (").append(startDate).append(")\r\n").append("      End: (").append(endDate)
				.append(")\r\n").append("    Base Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getBaseCost())).append("\r\n")
				.append("    Discount: ").append(String.format(Locale.US, "%.2f", travelPackage.getDiscount()))
				.append("\r\n").append("    Total Cost: ")
				.append(String.format(Locale.US, "%.2f", travelPackage.getFinalCost())).append("\r\n");
		return rst.toString();
	}

	public double calculateCancellationFee() {
		// Assume the base cost of the travel package is already available
		double baseCost = travelPackage.getBaseCost();

		// Parse the start hour to include time in calculations
		int startHour = getIntStartHour();
		int startMinute = getIntStartMinute();

		// Calculate the datetime of start date with the start hour
		LocalDateTime startDateTime = startDate.atTime(startHour, startMinute);

		// Calculate the hours remaining until the start date and time
		long hoursUntilStart = java.time.Duration.between(LocalDateTime.of(2025, 1, 1, 0, 0), startDateTime).toHours();

		double cancellationFeePercentage;

		if (hoursUntilStart > 72) {
			cancellationFeePercentage = 0.0; // No penalty, full refund
		} else if (hoursUntilStart > 48) {
			cancellationFeePercentage = 15.0; // 15% cancellation fee
		} else {
			cancellationFeePercentage = 30.0; // 30% cancellation fee
		}

		// Calculate the cancellation fee as a percentage of the base cost
		double cancellationFee = (cancellationFeePercentage / 100) * baseCost;

		return cancellationFee;
	}

	public int getIntStartHour() {
		return Integer.parseInt(getStartHour().split(":")[0]);
	}

	public int getIntStartMinute() {
		return Integer.parseInt(getStartHour().split(":")[1]);
	}

	private String getCancelledEntities() {
		if (!(travelPackage instanceof CustomTravelPackage)) {
			return "";
		}
		ArrayList<Entity> entities = ((CustomTravelPackage) travelPackage).getCancelledEntities();

		String cancelledEntities = "";

		for (Entity entity : entities) {

			if (entity instanceof Flight) {
				cancelledEntities += "    Old " + "Flight: " + ((Flight) entity).getEntityShortUniqueInfoWithPlaces();
			}
			if (entity instanceof Taxi) {
				cancelledEntities += "    Old " + "Taxi: " + ((Taxi) entity).getEntityShortUniqueInfoWithPlaces();
			}
			if (entity instanceof Hotel) {
				cancelledEntities += "    Old " + "Hotel: " + ((Hotel) entity).getEntityShortUniqueInfoWithPlaces();
			}
			cancelledEntities += " - Cancelled\r\n";
		}

		return cancelledEntities;
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

	public boolean containsDate(LocalDate startDate2, LocalDate endDate2) {

		LocalDate currentStartDate = startDate;
		LocalDate currentEndDate = endDate;

		// Tarih aralıklarının çakışma durumlarını kontrol et
		if ((startDate2.isEqual(currentStartDate) || startDate2.isAfter(currentStartDate))
				&& startDate2.isBefore(currentEndDate)) {
			return true;
		}
		if ((endDate2.isEqual(currentStartDate) || endDate2.isAfter(currentStartDate))
				&& endDate2.isBefore(currentEndDate)) {
			return true;
		}
		if (startDate2.isBefore(currentStartDate) && endDate2.isAfter(currentEndDate)) {
			return true;
		}

		return false;
	}

	public String getName() {
		return travelPackage.getName();
	}

	public String getDescription() {
		return travelPackage.getDescription();
	}

	public Entity getFlight() {
		if (getTravelPackage() instanceof CustomTravelPackage) {
			return ((CustomTravelPackage) getTravelPackage()).getFlight();
		}
		return null;
	}

	public Entity getTaxi() {
		if (getTravelPackage() instanceof CustomTravelPackage) {
			return ((CustomTravelPackage) getTravelPackage()).getTaxi();
		}
		return null;
	}

	public Entity getHotel() {
		if (getTravelPackage() instanceof CustomTravelPackage) {
			return ((CustomTravelPackage) getTravelPackage()).getHotel();
		}
		return null;
	}

	public Entity getReturnTaxi() {
		if (getTravelPackage() instanceof CustomTravelPackage) {
			return ((CustomTravelPackage) getTravelPackage()).getReturnTaxi();
		}
		return null;
	}

	public Entity getReturnFlight() {
		if (getTravelPackage() instanceof CustomTravelPackage) {
			return ((CustomTravelPackage) getTravelPackage()).getReturnFlight();
		}
		return null;
	}

	public String getTotalCost() {
		return String.format(Locale.US, "%.2f", travelPackage.getBaseCost());
	}

	public void newFlight(Entity entity) {
		int i = 0;
		if (!(getTravelPackage() instanceof CustomTravelPackage)) {
			System.err.println("Not usable for this package");
			return; // İşlemi durdur
		}

		// Eğer aynı entity zaten listede varsa, hiçbir işlem yapma
		if (getEntities().get(i).getEntityShortUniqueInfo().equals(entity.getEntityShortUniqueInfo())) {
			return;
		}

		// Eski entity'yi al
		Entity oldEntity = ((CustomTravelPackage) travelPackage).getActiveEntities().get(i);

		// Eski entity'yi aktiflerden kaldır ve iptal edilenlere ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().remove(i); // Eski entity'yi kaldır
		((CustomTravelPackage) travelPackage).getCancelledEntities().add(oldEntity);

		// Yeni entity'yi aktif listeye ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().add(i, entity);

		// Maliyetleri güncelle
		travelPackage.setBaseCost();
		((CustomTravelPackage) travelPackage).setFinalCost();
	}

	public void newReturnFlight(Entity entity) {
		int i = 4;
		if (!(getTravelPackage() instanceof CustomTravelPackage)) {
			System.err.println("Not usable for this package");
		}

		if (getEntities().get(i).getEntityShortUniqueInfo().equals(entity.getEntityShortUniqueInfo())) {
			return;
		}

		Entity oldEntity = ((CustomTravelPackage) travelPackage).getActiveEntities().get(i);

		// Eski entity'yi aktiflerden kaldır ve iptal edilenlere ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().remove(i); // Eski entity'yi kaldır
		((CustomTravelPackage) travelPackage).getCancelledEntities().add(oldEntity);

		// Yeni entity'yi aktif listeye ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().add(i, entity);

		// Maliyetleri güncelle
		travelPackage.setBaseCost();
		((CustomTravelPackage) travelPackage).setFinalCost();
	}

	public void newHotel(Entity entity) {
		int i = 2;
		if (!(getTravelPackage() instanceof CustomTravelPackage)) {
			System.err.println("Not usable for this package");
		}

		if (getEntities().get(i).getEntityShortUniqueInfo().equals(entity.getEntityShortUniqueInfo())) {
			return;
		}
		Entity oldEntity = ((CustomTravelPackage) travelPackage).getActiveEntities().get(i);

		// Eski entity'yi aktiflerden kaldır ve iptal edilenlere ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().remove(i); // Eski entity'yi kaldır
		((CustomTravelPackage) travelPackage).getCancelledEntities().add(oldEntity);

		// Yeni entity'yi aktif listeye ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().add(i, entity);

		travelPackage.initializeDistanceToHotel();
		// Maliyetleri güncelle
		travelPackage.setBaseCost();
		((CustomTravelPackage) travelPackage).setFinalCost();
	}

	public void newTaxi(Entity entity) {
		int i = 1;
		if (!(getTravelPackage() instanceof CustomTravelPackage)) {
			System.err.println("Not usable for this package");
		}

		if (getEntities().get(i).getEntityShortUniqueInfo().equals(entity.getEntityShortUniqueInfo())) {
			return;
		}
		Entity oldEntity = ((CustomTravelPackage) travelPackage).getActiveEntities().get(i);

		// Eski entity'yi aktiflerden kaldır ve iptal edilenlere ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().remove(i); // Eski entity'yi kaldır
		((CustomTravelPackage) travelPackage).getCancelledEntities().add(oldEntity);

		// Yeni entity'yi aktif listeye ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().add(i, entity);

		// Maliyetleri güncelle
		travelPackage.setBaseCost();
		((CustomTravelPackage) travelPackage).setFinalCost();
	}

	public void newReturnTaxi(Entity entity) {
		int i = 3;
		if (!(getTravelPackage() instanceof CustomTravelPackage)) {
			System.err.println("Not usable for this package");
		}

		if (getEntities().get(i).getEntityShortUniqueInfo().equals(entity.getEntityShortUniqueInfo())) {
			return;
		}
		Entity oldEntity = ((CustomTravelPackage) travelPackage).getActiveEntities().get(i);

		// Eski entity'yi aktiflerden kaldır ve iptal edilenlere ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().remove(i); // Eski entity'yi kaldır
		((CustomTravelPackage) travelPackage).getCancelledEntities().add(oldEntity);

		// Yeni entity'yi aktif listeye ekle
		((CustomTravelPackage) travelPackage).getActiveEntities().add(i, entity);

		// Maliyetleri güncelle
		travelPackage.setBaseCost();
		((CustomTravelPackage) travelPackage).setFinalCost();
	}

	public static LinkedHashMap<Customer, Double> sortedExpensesForCustomers() {
		HashMap<Customer, Double> rst = new HashMap<Customer, Double>();

		for (Customer customer : activeCustomerReservations.keySet()) {
			for (Reservation reservation : getActiveCustomerReservations(customer)) {
				rst.put(customer, rst.getOrDefault(customer, 0.0) + reservation.getTravelPackage().getFinalCost());
			}
		}

		for (Customer customer : canceledCustomerReservations.keySet()) {
			for (Reservation reservation : getCanceledCustomerReservations(customer)) {
				rst.put(customer, rst.getOrDefault(customer, 0.0) + reservation.calculateCancellationFee());
			}
		}

		return sortRstForCost(rst);
	}

	private static LinkedHashMap<Customer, Double> sortRstForCost(HashMap<Customer, Double> rst) {
		List<Map.Entry<Customer, Double>> sortedList = new ArrayList<>(rst.entrySet());

		Collections.sort(sortedList, (e1, e2) -> {
			int compare = e2.getValue().compareTo(e1.getValue());
			if (compare == 0) {
				return e1.getKey().getName().compareTo(e2.getKey().getName());
			}
			return compare;
		});

		// Sıralanmış sonuçları LinkedHashMap'e ekle
		LinkedHashMap<Customer, Double> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Customer, Double> entry : sortedList) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}
}
