package customerPages;

import guiManagment.*;
import reservation.Reservation;
import users.Customer;

import java.util.HashSet;

import javax.swing.*;
import customizedGuiObjects.*;

public class ActiveReservationsPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Customer customer;
	private static final String COLOR = "red";

	public ActiveReservationsPage(CustomizedJPanel panel, Customer currentUser) {
		// Frame setup
		customer = currentUser;

		initializeLocation();
		initializeSize(panel, 900, 600);

		// Modify the shared panel
		adjustPanelView(panel);

		// Set panel properties
		getContentPane().add(panel);

		// Add components
		addComponentsToPanel(panel);

		// Center frame on screen
		setLocationRelativeTo(null); // Center the frame
	}

	public void adjustPanelView(CustomizedJPanel panel) {
		// Set the background image on the panel
		panel.setBackgroundImage("makeReservationPagePhoto1.jpg", 30);
		// Remove initial label and add a new welcome label
		panel.removeLabel();
		panel.addNewLabel("");
	}

	/**
	 * Initialize the frame properties.
	 */
	public void initializeLocation() {
		setTitle("Cancelled Reservations Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null); // Using absolute positioning for simplicity
	}

	/**
	 * Initialize the size of the frame and panel.
	 */
	public void initializeSize(CustomizedJPanel panel, int x, int y) {
		panel.adjustPanelSize(x, y);
		setSize(x, y); // Set size of frame and panel
	}

	/**
	 * Add components (e.g., buttons and reservation list) to the panel.
	 */
	private void addComponentsToPanel(CustomizedJPanel panel) {

		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

		// Add a "Next Page" button next to the previous button
		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		if (PageDataBase.isThereNextPage())
			panel.add(nextPageButton);

		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logOutButton = CustomizedJButtons.logOutButton();
		JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
		JButton reservationHistoryButton = CustomizedJButtons.viewReservationsHistoryPageButton(COLOR);
		JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, reservationHistoryButton,
				customerMainPageButton, shutDownButton, logOutButton);
		panel.add(menuBarButton);

		// Create a JTextArea to display reservations
		JTextArea reservationsTextArea = new JTextArea();
		reservationsTextArea.setEditable(false); // Make it read-only

		// Fetch cancelled reservations
		HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(customer);
		// Append reservations to the text area
		if (activeReservations == null) {
			reservationsTextArea.append("There is no active reservation for customer: " + customer.getName());
		} else {
			for (Reservation reservation : activeReservations) {
				reservationsTextArea.append(reservation.reservationInfo()
						+ "\n---------------------------------------------------------------------------\n");
			}
		}
		// Wrap the text area in a scroll pane
		JScrollPane scrollPane = new JScrollPane(reservationsTextArea);
		scrollPane.setBounds(50, 130, 800, 400); // Set bounds for the scroll pane
		panel.add(scrollPane);
	}

	public Page createPage(CustomizedJPanel panel) {
		return new ActiveReservationsPage(panel, customer);
	}
}
