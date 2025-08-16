package customerPages;

import guiManagment.*;
import reservation.Reservation;
import reservation.TravelPackageReservation;
import travelPackage.AdminTravelPackage;
import travelPackage.CustomTravelPackage;
import travelPackage.TravelPackage;
import users.Customer;
import javax.swing.*;
import cities.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import customizedGuiObjects.*;
import entity.*;
import fileIOHandling.WriteUserLogs;
import generalMessages.AcceptedMessage;

public class CustomReservationPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Customer customer;
	private String COLOR = "gold";

	public CustomReservationPage(CustomizedJPanel panel, Customer currentUser) {
		customer = currentUser;

		initializeLocation();
		initializeSize(panel, 900, 600);
		adjustPanelView(panel);
		getContentPane().add(panel);

		// Add the components for all steps in a single screen
		addAllStepComponents(panel);

		setLocationRelativeTo(null);
	}

	@Override
	public void initializeLocation() {
		setTitle("Create Your Reservation Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
	}

	@Override
	public void initializeSize(CustomizedJPanel panel, int x, int y) {
		panel.setBounds(0, 0, x, y);
		setSize(x, y);
	}

	@Override
	public void adjustPanelView(CustomizedJPanel panel) {
		panel.removeAll();
		panel.setBackgroundImage("customReservationPage2.jpg", 50);
		panel.repaint();
	}

	private void addAllStepComponents(CustomizedJPanel panel) {
		JPanel stepsPanel = new JPanel();
		stepsPanel.setLayout(null);
		stepsPanel.setBounds(0, 50, 900, 600);
		stepsPanel.setOpaque(false);

		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		if(PageDataBase.isThereNextPage())panel.add(nextPageButton);

		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		if(PageDataBase.isTherePreviousPage())panel.add(previousPageButton);

		JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
		JButton makeReservation = CustomizedJButtons.makeReservationsPageButton(COLOR);
		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logoutButton = CustomizedJButtons.logOutButton();

		JButton menuBar = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, customerMainPageButton,
				makeReservation, logoutButton, shutDownButton);
		panel.add(menuBar);

		// Departure and Arrival City ComboBoxes
		JComboBox<String> departureCityBox = new JComboBox<>(
				new HashSet<String>(FromCity.getFromCities()).toArray(new String[0]));
		departureCityBox.setBounds(250, 25, 200, 30);
		stepsPanel.add(createLabel("Departure City", 50, 25));
		stepsPanel.add(departureCityBox);

		JComboBox<String> arrivalCityBox = new JComboBox<>(
				new HashSet<String>(ToCity.getToCities()).toArray(new String[0]));
		arrivalCityBox.setBounds(250, 75, 200, 30);
		stepsPanel.add(createLabel("Arrival City", 50, 75));
		stepsPanel.add(arrivalCityBox);

		// Flight, Taxi, Hotel, Return Taxi, Return Flight ComboBoxes
		JComboBox<String> flightBox = new JComboBox<>();
		flightBox.setBounds(250, 225, 610, 30); // Moved down
		stepsPanel.add(createLabel("Flight", 50, 225));
		stepsPanel.add(flightBox);

		JComboBox<String> taxiBox = new JComboBox<>();
		taxiBox.setBounds(250, 275, 610, 30); // Moved down
		stepsPanel.add(createLabel("Taxi", 50, 275));
		stepsPanel.add(taxiBox);

		JComboBox<String> hotelBox = new JComboBox<>();
		hotelBox.setBounds(250, 325, 610, 30); // Moved down
		stepsPanel.add(createLabel("Hotel", 50, 325));
		stepsPanel.add(hotelBox);

		JComboBox<String> returnTaxiBox = new JComboBox<>();
		returnTaxiBox.setBounds(250, 375, 610, 30);
		stepsPanel.add(createLabel("Return Taxi", 50, 375));
		stepsPanel.add(returnTaxiBox);

		JComboBox<String> returnFlightBox = new JComboBox<>();
		returnFlightBox.setBounds(250, 425, 610, 30);
		stepsPanel.add(createLabel("Return Flight", 50, 425));
		stepsPanel.add(returnFlightBox);

		// Add Start Date Components
		JComboBox<Integer> startDayBox = new JComboBox<>();
		JComboBox<Integer> startMonthBox = new JComboBox<>();
		JComboBox<Integer> startYearBox = new JComboBox<>();

		for (int i = 1; i <= 31; i++) {
			startDayBox.addItem(i);
		}
		for (int i = 1; i <= 12; i++) {
			startMonthBox.addItem(i);
		}
		int currentYear = 2025;
		for (int i = currentYear; i <= currentYear + 5; i++) {
			startYearBox.addItem(i);
		}

		startDayBox.setBounds(250, 125, 60, 30); // Positioned above Flight ComboBox
		startMonthBox.setBounds(320, 125, 60, 30);
		startYearBox.setBounds(390, 125, 80, 30);

		stepsPanel.add(createLabel("Start Date", 50, 125));
		stepsPanel.add(startDayBox);
		stepsPanel.add(startMonthBox);
		stepsPanel.add(startYearBox);

		// Add Duration Spinner
		JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1)); // Min: 1 day, Max: 30 days
		durationSpinner.setBounds(250, 175, 60, 30);
		stepsPanel.add(createLabel("Duration (days)", 50, 175));
		stepsPanel.add(durationSpinner);

		// Complete Button
		JButton completeButton = new JButton("Complete Reservation");
		completeButton.setBounds(300, 475, 200, 30);
		completeButton.addActionListener(e -> {
			if (!validateSelections(departureCityBox, arrivalCityBox, flightBox, taxiBox, hotelBox, returnTaxiBox,
					returnFlightBox)) {
				JOptionPane.showMessageDialog(panel, "Please make a valid selection for all fields.");
				return;
			}

			// Get the selected dates
			LocalDate startDate = getSelectedDate(startDayBox, startMonthBox, startYearBox);
			int duration = (int) durationSpinner.getValue();
			LocalDate endDate = startDate.plusDays(duration);

			// Show reservation details
			StringBuilder reservationDetails = new StringBuilder("Total Cost: $")
					.append(calculateTotalCost(new String[] { (String) flightBox.getSelectedItem(), (String) taxiBox.getSelectedItem(),
							(String) hotelBox.getSelectedItem(), (String) returnTaxiBox.getSelectedItem(),
							(String) returnFlightBox.getSelectedItem() }, duration, (String) departureCityBox.getSelectedItem(), (String) arrivalCityBox.getSelectedItem()))
					.append("\nStart Date: ").append(startDate).append("\nEnd Date: ").append(endDate)
					.append("\nDuration: ").append(duration).append(" days").append("\n\nDo you confirm?");
			int confirmation = JOptionPane.showConfirmDialog(panel, reservationDetails.toString(),
					"Confirm Reservation", JOptionPane.YES_NO_OPTION);

			if (confirmation == JOptionPane.YES_OPTION) {
				confirmReservation(panel, startDate, endDate,
						new String[] { (String) flightBox.getSelectedItem(), (String) taxiBox.getSelectedItem(),
								(String) hotelBox.getSelectedItem(), (String) returnTaxiBox.getSelectedItem(),
								(String) returnFlightBox.getSelectedItem() },
						(String) departureCityBox.getSelectedItem(), (String) arrivalCityBox.getSelectedItem(),
						duration);

			} else {
				JOptionPane.showMessageDialog(panel, "Reservation Cancelled.");
			}
		});
		stepsPanel.add(completeButton);

		// Date Change Listener
		ActionListener dateChangeListener = e -> updateOptions(departureCityBox, arrivalCityBox, flightBox, taxiBox,
				hotelBox, returnTaxiBox, returnFlightBox, startDayBox, startMonthBox, startYearBox, durationSpinner);

		// Add ActionListeners to the date selection boxes
		startDayBox.addActionListener(dateChangeListener);
		startMonthBox.addActionListener(dateChangeListener);
		startYearBox.addActionListener(dateChangeListener);
		durationSpinner.addChangeListener(e -> dateChangeListener.actionPerformed(null));

		departureCityBox.addActionListener(dateChangeListener);
		arrivalCityBox.addActionListener(dateChangeListener);

		panel.add(stepsPanel);
	}

	private void confirmReservation(CustomizedJPanel panel, LocalDate startDate, LocalDate endDate,
			String[] entityStrings, String departureCity, String arrivalCity, int day) {
		try {
			if (customer.isBusy(startDate, endDate)) {
				return;
			}
			if (!validateEntityOrder(entityStrings)) {
				return;
			}

			ArrayList<Entity> entities = TravelPackage.returnEntityForm(entityStrings, departureCity, arrivalCity);
			TravelPackage tPackage = AdminTravelPackage.isThereAdminTravelPackage(entities, day);
			Reservation reservation;

			if (tPackage != null) {
				new AcceptedMessage("Good News",
						"Good news there was a discount package for the same package discount is applied: "
								+ tPackage.getDiscount() + "%");
			} else {
				tPackage = new CustomTravelPackage(entities, new ArrayList<Entity>(), day, "Custom", "Custom", 0);
			}

			reservation = new TravelPackageReservation(tPackage, startDate.toString(), endDate.toString(), customer);
			Reservation.makeActiveReservation(reservation, customer);
			WriteUserLogs.write(reservation);

			JOptionPane.showMessageDialog(panel, "Reservation Confirmed!\nThank you for choosing us.");
		} catch (Exception e) {
			System.err.println("An error occured while confirming reservation in CustomReservations");
		}
	}

	private boolean validateEntityOrder(String[] entityStrings) {
		try {
			if (!(entityStrings[0].contains("class"))) {
				System.err.println("wrong order1 in customreservations");
				return false;
			}
			if (!(entityStrings[1].contains("rate"))) {
				System.err.println("wrong order2 in customreservations");
				return false;
			}
			if (!(entityStrings[2].contains("airport"))) {
				System.err.println("wrong order3 in customreservations");
				return false;
			}
			if (!(entityStrings[3].contains("rate"))) {
				System.err.println("wrong order4 in customreservations");
				return false;
			}
			if (!(entityStrings[4].contains("class"))) {
				System.err.println("wrong order5 in customreservations");
				return false;
			}
		} catch (Exception e) {
			System.err.println("out of index error in customreservations");
			return false;
		}
		return true;
	}


	private boolean validateSelections(JComboBox<String> departureCityBox, JComboBox<String> arrivalCityBox,
			JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
			JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
		return departureCityBox.getSelectedItem() != null && arrivalCityBox.getSelectedItem() != null
				&& !departureCityBox.getSelectedItem().toString().isEmpty()
				&& !arrivalCityBox.getSelectedItem().toString().isEmpty()
				&& !departureCityBox.getSelectedItem().toString().equals(arrivalCityBox.getSelectedItem().toString());
	}

	private JLabel createLabel(String text, int x, int y) {
		JLabel label = CustomizedJLabels.messageLabel(text);
		label.setBounds(x, y, 200, 30);
		return label;
	}

	private void updateOptions(JComboBox<String> departureBox, JComboBox<String> arrivalBox,
			JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
			JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox, JComboBox<Integer> startDayBox,
			JComboBox<Integer> startMonthBox, JComboBox<Integer> startYearBox, JSpinner durationSpinner) {

		String departureCity = (String) departureBox.getSelectedItem();
		String arrivalCity = (String) arrivalBox.getSelectedItem();

		if (departureCity == null || arrivalCity == null) {
			resetComboBoxes(flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox);
			return;
		}

		LocalDate startDate = getSelectedDate(startDayBox, startMonthBox, startYearBox);
		int duration = 0;
		try {
			duration = (int) durationSpinner.getValue();
		} catch (Exception e) {
			System.err.println("error while casting duration to int, in custom reservations");
		}

		LocalDate endDate = startDate.plusDays(duration);

		HashSet<Flight> flightsGo = City.getDepartureCityFlightEntities(departureCity, arrivalCity, startDate, endDate);
		HashSet<Flight> flightsBack = City.getArrivalCityFlightEntities(departureCity, arrivalCity, startDate, endDate);
		HashSet<Taxi> taxis = City.getArrivalCityTaxiEntities(arrivalCity, startDate, endDate);
		HashSet<Taxi> returnTaxis = City.getArrivalCityReturnTaxiEntities(arrivalCity, startDate, endDate);
		HashSet<Hotel> hotels = City.getArrivalCityHotelEntities(arrivalCity, startDate, endDate);

		flightBox.setModel(new DefaultComboBoxModel<>(flightsGo.stream()
				.map(flight -> flight.getEntityForOptionGo(startDate)).toArray(String[]::new)));
		taxiBox.setModel(new DefaultComboBoxModel<>(
				taxis.stream().map(taxi -> taxi.getEntityForOptionGo(startDate)).toArray(String[]::new)));
		hotelBox.setModel(new DefaultComboBoxModel<>(
				hotels.stream().map(hotel -> hotel.getEntityForOption(startDate, endDate)).toArray(String[]::new)));
		returnTaxiBox.setModel(new DefaultComboBoxModel<>(
				returnTaxis.stream().map(taxi -> taxi.getEntityForOptionReturn(endDate)).toArray(String[]::new)));
		returnFlightBox.setModel(new DefaultComboBoxModel<>(flightsBack.stream()
				.map(flight -> flight.getEntityForOptionReturn(endDate)).toArray(String[]::new)));
	}

	private void resetComboBoxes(JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
			JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
		flightBox.removeAllItems();
		taxiBox.removeAllItems();
		hotelBox.removeAllItems();
		returnTaxiBox.removeAllItems();
		returnFlightBox.removeAllItems();
	}

	private String calculateTotalCost(String[] options, int day, String departureCity, String arrivalCity) {
	    double cost = 0;
	    for (String option : options) {
	        if (option == null || option.isBlank()) {
	            return "0";
	        }
	    }

	    try {
	        ArrayList<Entity> entities = TravelPackage.returnEntityForm(options, departureCity, arrivalCity);
	        
	        cost = TravelPackage.totalCostOfEntities(entities, day);

	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return "0"; // Return 0 if there was an error in the cost calculation
	    }

	    return String.format("%.2f", cost); // Return the total calculated cost
	}


	private LocalDate getSelectedDate(JComboBox<Integer> dayBox, JComboBox<Integer> monthBox,
			JComboBox<Integer> yearBox) {
		try {
			int day = (int) dayBox.getSelectedItem();
			int month = (int) monthBox.getSelectedItem();
			int year = (int) yearBox.getSelectedItem();
			return LocalDate.of(year, month, day);
		} catch (Exception e) {
			System.err.println("Error while trying to cast non int value in getSelectedDate");
		}
		return null;
	}

	public Page createPage(CustomizedJPanel panel) {
		return new CustomReservationPage(panel, customer);
	}
}
