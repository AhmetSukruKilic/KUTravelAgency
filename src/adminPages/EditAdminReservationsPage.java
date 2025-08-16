package adminPages;

import guiManagment.*;
import users.Admin;
import javax.swing.*;
import customizedGuiObjects.*;

public class EditAdminReservationsPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private static final String COLOR = "gold";

	public EditAdminReservationsPage(CustomizedJPanel panel, Admin currentUser) {
		// Frame setup
		admin = currentUser;

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
		panel.setBackgroundImage("viewUserStatsPhoto.jpg", 30);
		// Remove initial label and add a new welcome label
		panel.removeLabel();
		panel.addNewLabel("");
	}

	/**
	 * Initialize the frame properties.
	 */
	public void initializeLocation() {
		setTitle("Edit Reservations Page");
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
	 * Add components (e.g., buttons) to the panel.
	 */
	private void addComponentsToPanel(CustomizedJPanel panel) {
		// Previous Page Button
		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		previousPageButton.setBounds(10, 10, 80, 30);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

		// Next Page Button
		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		nextPageButton.setBounds(100, 10, 80, 30);
		if (PageDataBase.isThereNextPage())
			panel.add(nextPageButton);

		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logOutButton = CustomizedJButtons.logOutButton();
		JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, shutDownButton, logOutButton);
		panel.add(menuBarButton);

		JButton cancelReservationForCustomersButton = CustomizedJButtons.cancelReservationForCustomersButton(COLOR);
		cancelReservationForCustomersButton.setBounds(500, 400, 170, 50);
		panel.add(cancelReservationForCustomersButton);

		JButton changeEntitiesForCustomersButton = CustomizedJButtons.changeEntitiesForCustomersButton(COLOR);
		changeEntitiesForCustomersButton.setBounds(200, 400, 210, 50);
		panel.add(changeEntitiesForCustomersButton);

		JButton makeReservationForCustomerButton = CustomizedJButtons.makeReservationForCustomerButton(COLOR);
		makeReservationForCustomerButton.setBounds(380, 250, 170, 50);
		panel.add(makeReservationForCustomerButton);

	}

	public Page createPage(CustomizedJPanel panel) {
		return new EditAdminReservationsPage(panel, admin);
	}
}
