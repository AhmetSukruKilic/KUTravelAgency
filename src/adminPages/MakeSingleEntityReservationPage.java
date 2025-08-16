package adminPages;

import guiManagment.*;
import reservation.Reservation;
import reservation.TravelPackageReservation;
import travelPackage.SingleEntityTravelPackage;
import travelPackage.TravelPackage;
import users.Admin;
import users.Customer;
import javax.swing.*;
import cities.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import customizedGuiObjects.*;
import entity.*;
import fileIOHandling.WriteUserLogs;

public class MakeSingleEntityReservationPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private String COLOR = "red";

	public MakeSingleEntityReservationPage(CustomizedJPanel panel, Admin currentUser) {
		admin = currentUser;

		initializeLocation();
		initializeSize(panel, 900, 600);
		adjustPanelView(panel);
		getContentPane().add(panel);

		addAllStepComponents(panel);

		setLocationRelativeTo(null);
	}

	@Override
	public void initializeLocation() {
		setTitle("SingleEntity Reservations Page");
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
		panel.setBackgroundImage("viewUserStatsPhoto.jpg", 50);
		panel.repaint();
	}

	private void addAllStepComponents(CustomizedJPanel panel) {
		JPanel stepsPanel = new JPanel();
		stepsPanel.setLayout(null);
		stepsPanel.setBounds(0, 50, 900, 600);
		stepsPanel.setOpaque(false);

		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		if (PageDataBase.isThereNextPage())
			panel.add(nextPageButton);

		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logoutButton = CustomizedJButtons.logOutButton();

		JButton menuBar = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, logoutButton, shutDownButton);
		panel.add(menuBar);

		// Customer Selection ComboBox
		// Customer Selection ComboBox
		DefaultComboBoxModel<Customer> customerModel = new DefaultComboBoxModel<>();
		JComboBox<Customer> customerBox = new JComboBox<>(customerModel);
		customerBox.setBounds(250, 25, 400, 30);
		customerBox.setRenderer(new DefaultListCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
						cellHasFocus);
				if (value instanceof Customer) {
					Customer customer = (Customer) value;
					label.setText(customer.getName()); // Müşteri ismini göster
				}
				return label;
			}
		});
		stepsPanel.add(createLabel("Select Customer", 50, 25));
		stepsPanel.add(customerBox);

		// Populate customers
		HashSet<Customer> customers = Customer.getValidCustomers();
		for (Customer customer : customers) {
			customerModel.addElement(customer);
		}

		// Departure and Arrival City ComboBoxes
		JComboBox<String> departureCityBox = new JComboBox<>(
				new HashSet<>(FromCity.getFromCities()).toArray(new String[0]));
		departureCityBox.setBounds(250, 75, 200, 30);
		stepsPanel.add(createLabel("Departure City", 50, 75));
		stepsPanel.add(departureCityBox);

		JComboBox<String> arrivalCityBox = new JComboBox<>(new HashSet<>(ToCity.getToCities()).toArray(new String[0]));
		arrivalCityBox.setBounds(250, 125, 200, 30);
		stepsPanel.add(createLabel("Arrival City", 50, 125));
		stepsPanel.add(arrivalCityBox);

		JComboBox<String> singleEntityBox = new JComboBox<>();
		singleEntityBox.setBounds(230, 325, 650, 30);
		stepsPanel.add(createLabel("Single Entity", 50, 325));
		stepsPanel.add(singleEntityBox);

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

		startDayBox.setBounds(250, 175, 60, 30);
		startMonthBox.setBounds(320, 175, 60, 30);
		startYearBox.setBounds(390, 175, 80, 30);

		stepsPanel.add(createLabel("Start Date", 50, 175));
		stepsPanel.add(startDayBox);
		stepsPanel.add(startMonthBox);
		stepsPanel.add(startYearBox);
		int day = 1;
		// Complete Button
		JButton completeButton = new JButton("Complete Reservation");
		completeButton.setBounds(600, 375, 200, 30);
		completeButton.addActionListener(e -> {
			Customer selectedCustomer = (Customer) customerBox.getSelectedItem();
			if (selectedCustomer == null) {
				JOptionPane.showMessageDialog(panel, "Please select a customer.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (!validateSelections(departureCityBox, arrivalCityBox, singleEntityBox)) {
				JOptionPane.showMessageDialog(panel, "Please make a valid selection for all fields.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			LocalDate startDate = getSelectedDate(startDayBox, startMonthBox, startYearBox);
			LocalDate endDate = startDate.plusDays(day);

			String selectedEntity = (String) singleEntityBox.getSelectedItem() ;

			confirmReservation(panel, selectedCustomer, startDate, endDate, selectedEntity,
					(String) departureCityBox.getSelectedItem(), (String) arrivalCityBox.getSelectedItem(), day);
		});
		stepsPanel.add(completeButton);

		// Date Change Listener
		ActionListener dateChangeListener = e -> updateOptions(departureCityBox, arrivalCityBox, singleEntityBox,
				startDayBox, startMonthBox, startYearBox, day);

		startDayBox.addActionListener(dateChangeListener);
		startMonthBox.addActionListener(dateChangeListener);
		startYearBox.addActionListener(dateChangeListener);

		departureCityBox.addActionListener(dateChangeListener);
		arrivalCityBox.addActionListener(dateChangeListener);

		panel.add(stepsPanel);
	}

	private void confirmReservation(CustomizedJPanel panel, Customer customer, LocalDate startDate, LocalDate endDate,
			String selectedEntity, String departureCity, String arrivalCity, int day) {
		try {
			if (customer.isBusy(startDate, endDate)) {
				return;
			}

			ArrayList<Entity> entities = TravelPackage.returnEntityForm(selectedEntity, departureCity, arrivalCity);
			Reservation reservation;
			SingleEntityTravelPackage sPackage = new SingleEntityTravelPackage(entities, day, "SingleEntity",
					"SingleEntity", day);

			reservation = new TravelPackageReservation(sPackage, startDate.toString(), endDate.toString(), customer);
			Reservation.makeActiveReservation(reservation, customer);
			WriteUserLogs.write(reservation);

			JOptionPane.showMessageDialog(panel, "Reservation Confirmed for " + customer.getName() + "!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.err.println("An error occurred while confirming reservation for Admin");
		}
	}

	private boolean validateSelections(JComboBox<String> departureCityBox, JComboBox<String> arrivalCityBox,
			JComboBox<String> singleEntityBox) {
		return departureCityBox.getSelectedItem() != null && arrivalCityBox.getSelectedItem() != null
				&& !departureCityBox.getSelectedItem().toString().isEmpty()
				&& !arrivalCityBox.getSelectedItem().toString().isEmpty()
				&& !singleEntityBox.getSelectedItem().toString().isEmpty() && singleEntityBox.getSelectedItem() != null;

	}

	private JLabel createLabel(String text, int x, int y) {
		JLabel label = CustomizedJLabels.messageLabel(text);
		label.setBounds(x, y, 200, 30);
		return label;
	}

	private void updateOptions(JComboBox<String> departureBox, JComboBox<String> arrivalBox,
			JComboBox<String> singleEntityBox, JComboBox<Integer> startDayBox, JComboBox<Integer> startMonthBox,
			JComboBox<Integer> startYearBox, int day) {

		String departureCity = (String) departureBox.getSelectedItem();
		String arrivalCity = (String) arrivalBox.getSelectedItem();

		if (departureCity == null || arrivalCity == null) {
			resetComboBoxes(singleEntityBox);
			return;
		}

		LocalDate startDate = getSelectedDate(startDayBox, startMonthBox, startYearBox);
		LocalDate endDate = startDate.plusDays(day);

		HashSet<Entity> singleEntityGoBack = City.getSingleReservationEntities(departureCity, arrivalCity, startDate,
				endDate);

		singleEntityBox.setModel(new DefaultComboBoxModel<>(
				singleEntityGoBack.stream().map(Entity -> Entity.getEntityForOptionGoBack(startDate, endDate)).toArray(String[]::new)));

	}

	private void resetComboBoxes(JComboBox<String> singleEntityBox) {
		singleEntityBox.removeAllItems();
	}

	private LocalDate getSelectedDate(JComboBox<Integer> dayBox, JComboBox<Integer> monthBox,
			JComboBox<Integer> yearBox) {
		try {
			int day = (int) dayBox.getSelectedItem();
			int month = (int) monthBox.getSelectedItem();
			int year = (int) yearBox.getSelectedItem();
			return LocalDate.of(year, month, day);
		} catch (Exception e) {
			System.err.println("Error while trying to cast non-integer value in getSelectedDate");
		}
		return null;
	}

	@Override
	public Page createPage(CustomizedJPanel panel) {
		return new MakeSingleEntityReservationPage(panel, admin);
	}
}
