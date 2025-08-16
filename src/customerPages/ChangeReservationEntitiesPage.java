package customerPages;

import guiManagment.*;
import reservation.Reservation;
import travelPackage.CustomTravelPackage;
import travelPackage.TravelPackage;
import users.Customer;
import cities.*;
import javax.swing.*;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import customizedGuiObjects.*;
import entity.*;
import fileIOHandling.WriteUserLogs;

public class ChangeReservationEntitiesPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Customer customer;
	private String COLOR = "gold";

	public ChangeReservationEntitiesPage(CustomizedJPanel panel, Customer currentUser) {
		customer = currentUser;

		initializeLocation();
		initializeSize(panel, 900, 600);
		adjustPanelView(panel);
		getContentPane().add(panel);

		addComponentsToPanel(panel);
		setLocationRelativeTo(null);
	}

	@Override
	public void initializeLocation() {
		setTitle("Change Reservation Entities Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
	}

	@Override
	public void initializeSize(CustomizedJPanel panel, int x, int y) {
		panel.adjustPanelSize(x, y);
		setSize(x, y);
	}

	@Override
	public void adjustPanelView(CustomizedJPanel panel) {
		panel.removeAll();
		panel.setBackgroundImage("customReservationPage2.jpg", 50);
		panel.repaint();
	}

	private void addComponentsToPanel(CustomizedJPanel panel) {
	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(null);
	    mainPanel.setBounds(0, 50, 900, 600);
	    mainPanel.setOpaque(false);

	    JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
	    if (PageDataBase.isThereNextPage())
	        panel.add(nextPageButton);

	    JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
	    if (PageDataBase.isTherePreviousPage())
	        panel.add(previousPageButton);

	    JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
	    JButton makeReservation = CustomizedJButtons.makeReservationsPageButton(COLOR);
	    JButton shutDownButton = CustomizedJButtons.shutDownButton();
	    JButton logoutButton = CustomizedJButtons.logOutButton();

	    JButton menuBar = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, customerMainPageButton,
	            makeReservation, logoutButton, shutDownButton);
	    panel.add(menuBar);

	    JComboBox<Reservation> reservationBox = new JComboBox<>();
	    reservationBox.setBounds(50, 50, 800, 25);
	    mainPanel.add(createLabel("Select Reservation", 50, 25));
	    mainPanel.add(reservationBox);

	    JComboBox<String> flightBox = new JComboBox<>();
	    flightBox.setBounds(50, 100, 800, 25); // Adjusted index
	    mainPanel.add(createLabel("Flights", 50, 75));
	    mainPanel.add(flightBox);

	    JComboBox<String> returnFlightBox = new JComboBox<>();
	    returnFlightBox.setBounds(50, 150, 800, 25); // Adjusted index
	    mainPanel.add(createLabel("Return Flights", 50, 125));
	    mainPanel.add(returnFlightBox);

	    JComboBox<String> hotelBox = new JComboBox<>();
	    hotelBox.setBounds(50, 200, 800, 25); // Adjusted index
	    mainPanel.add(createLabel("Hotels", 50, 175));
	    mainPanel.add(hotelBox);

	    JComboBox<String> taxiBox = new JComboBox<>();
	    taxiBox.setBounds(50, 250, 800, 25); // Adjusted index
	    mainPanel.add(createLabel("Taxis", 50, 225));
	    mainPanel.add(taxiBox);

	    JComboBox<String> returnTaxiBox = new JComboBox<>();
	    returnTaxiBox.setBounds(50, 300, 800, 25); // Adjusted index
	    mainPanel.add(createLabel("Return Taxis", 50, 275));
	    mainPanel.add(returnTaxiBox);

	    DefaultComboBoxModel<Reservation> reservationModel = new DefaultComboBoxModel<>();
	    HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(customer);

	    if (activeReservations != null && !activeReservations.isEmpty()) {
	        for (Reservation res : activeReservations) {
	            if (res.getTravelPackage() instanceof CustomTravelPackage)
	                reservationModel.addElement(res);
	        }
	        reservationBox.setModel(reservationModel);
	        reservationBox.setRenderer(new DefaultListCellRenderer() {
	            private static final long serialVersionUID = 1L;

	            @Override
	            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
	                    boolean isSelected, boolean cellHasFocus) {
	                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
	                        cellHasFocus);
	                if (value instanceof Reservation) {
	                    Reservation reservation = (Reservation) value;
	                    label.setText(reservation.getName() + reservation.getReservationTimeInfo());
	                }
	                return label;
	            }
	        });

	        JButton updateButton = new JButton("Update Reservation");
	        updateButton.setBounds(350, 350, 200, 40); // Adjusted position
	        updateButton.addActionListener(e -> {
	            Reservation selectedReservation = (Reservation) reservationBox.getSelectedItem();

	            if (reservationBox.getSelectedItem() == null) {
	                return;
	            }

	            String[] selectedEntities = { (String) flightBox.getSelectedItem(), (String) taxiBox.getSelectedItem(),
	                    (String) hotelBox.getSelectedItem(), (String) returnTaxiBox.getSelectedItem(),
	                    (String) returnFlightBox.getSelectedItem() };

	            if (!validateSelections(flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox)) {
	                JOptionPane.showMessageDialog(panel, "Please make a valid selection for all fields.");
	                return;
	            }

	            LocalDate startDate = selectedReservation.getStartDate();
	            LocalDate endDate = selectedReservation.getEndDate();

	            String totalCost = calculateTotalCost(selectedEntities, selectedReservation.getTravelPackage().getDay(),
	                    selectedReservation.getFromCity(), selectedReservation.getToCity());
	            int confirmation = JOptionPane.showConfirmDialog(panel,
	                    "Total cost will be: $" + totalCost + "\nDo you want to proceed?", "Confirm Update",
	                    JOptionPane.YES_NO_OPTION);
	            if (confirmation == JOptionPane.YES_OPTION) {
	                confirmReservation(selectedReservation, panel, startDate, endDate, selectedEntities,
	                        selectedReservation.getFromCity(), selectedReservation.getToCity(),
	                        selectedReservation.getTravelPackage().getDay());
	            }
	        });

	        mainPanel.add(updateButton);

	        reservationBox.addActionListener(e -> {
	            Reservation selectedReservation = (Reservation) reservationBox.getSelectedItem();
	            if (selectedReservation != null) {
	                updateOptions(reservationBox, flightBox, taxiBox, hotelBox,
	                        returnTaxiBox, returnFlightBox);
	            }
	        });

	    } else {
	        JLabel noCancelledReservationsLabel = CustomizedJLabels.messageLabel("There is no active reservation for customer: " + customer.getName());
	        noCancelledReservationsLabel.setBounds(250, 70, 800, 30); // Adjusted position
	        panel.add(noCancelledReservationsLabel);
	    }
	    panel.add(mainPanel);
	}

	
	private void resetComboBoxes(JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
			JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
		flightBox.removeAllItems();
		taxiBox.removeAllItems();
		hotelBox.removeAllItems();
		returnTaxiBox.removeAllItems();
		returnFlightBox.removeAllItems();
	}

	private void updateOptions(JComboBox<Reservation> reservationBox,
			JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
			JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
		
		Reservation oldReservation = (Reservation)reservationBox.getSelectedItem();
		String departureCity = (String) oldReservation.getFromCity();
		String arrivalCity = (String) oldReservation.getToCity();

		if (departureCity == null || arrivalCity == null) {
			resetComboBoxes(flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox);
			return;
		}

		LocalDate startDate = oldReservation.getStartDate();
		LocalDate endDate = oldReservation.getEndDate();

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

	private void confirmReservation(Reservation selectedReservation, CustomizedJPanel panel, LocalDate startDate,
			LocalDate endDate, String[] entityStrings, String departureCity, String arrivalCity, int day) {
		try {

			ArrayList<Entity> entities = TravelPackage.returnEntityForm(entityStrings, departureCity, arrivalCity);
			String oldReservation = selectedReservation.reservationInfo();

			selectedReservation.newHotel(entities.get(2));
			selectedReservation.newFlight(entities.get(0));
			selectedReservation.newTaxi(entities.get(1));
			selectedReservation.newReturnTaxi(entities.get(3));
			selectedReservation.newReturnFlight(entities.get(4));

			if (selectedReservation.reservationInfo().equals(oldReservation)) {
				JOptionPane.showMessageDialog(panel, "Reservation Failed!\nEvery entity remains same.");
				return;
			}
			WriteUserLogs.edit(oldReservation, selectedReservation);

			JOptionPane.showMessageDialog(panel, "Reservation Update Confirmed!\nThank you for choosing us.");
		} catch (Exception e) {
			System.err.println("An error occurred while confirming reservation in CustomReservations");
		}
	}

	private JLabel createLabel(String text, int x, int y) {
		JLabel label = CustomizedJLabels.messageLabel(text);
		label.setBounds(x, y, 200, 30);
		return label;
	}

	private boolean validateSelections(JComboBox<String> flightBox, JComboBox<String> taxiBox,
			JComboBox<String> hotelBox, JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
		return flightBox.getSelectedItem() != null && taxiBox.getSelectedItem() != null
				&& hotelBox.getSelectedItem() != null && returnTaxiBox.getSelectedItem() != null && returnFlightBox.getSelectedItem() != null;
	}

	public Page createPage(CustomizedJPanel panel) {
		return new ChangeReservationEntitiesPage(panel, customer);
	}
}
