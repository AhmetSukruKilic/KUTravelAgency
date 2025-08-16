package customerPages;

import guiManagment.*;
import users.Customer;
import javax.swing.*;
import customizedGuiObjects.*;

public class MakeReservationsPage extends JFrame implements Page {
	private static final long serialVersionUID = 1L;
	private Customer customer;
	private static final String COLOR = "red";

	public MakeReservationsPage(CustomizedJPanel panel, Customer currentUser) {
		// Frame setup
		customer = currentUser;

		initializeLocation(); // Initialize frame properties
		initializeSize(panel, 900, 600); // Set size for frame and panel

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
		setTitle("Make Reservation Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null); // Using absolute positioning for simplicity
	}

	/**
	 * Initialize the size of the frame and panel.
	 */
	public void initializeSize(CustomizedJPanel panel, int x, int y) {
		panel.adjustPanelSize(x, y); // Adjust panel size
		setSize(x, y); // Set size of the frame
	}

	/**
	 * Add components (e.g., buttons) to the panel.
	 */
	private void addComponentsToPanel(CustomizedJPanel panel) {
		// Previous Page Button
		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

		// Next Page Button
		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		if (PageDataBase.isThereNextPage())
			panel.add(nextPageButton);

		JButton customReservationPageButton = CustomizedJButtons.customReservationPageButton(COLOR);
		customReservationPageButton.setBounds(350, 190, 150, 50);
		panel.add(customReservationPageButton);
		
		JButton specialDealsPagePageButton = CustomizedJButtons.specialDealsPagePageButton(COLOR);
		specialDealsPagePageButton.setBounds(360, 260, 130, 45);
		panel.add(specialDealsPagePageButton);
		// Menu Bar Button

		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logOutButton = CustomizedJButtons.logOutButton();
		JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
		JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, customerMainPageButton,
				shutDownButton, logOutButton);
		panel.add(menuBarButton);
	}

	public Page createPage(CustomizedJPanel panel) {
		return new MakeReservationsPage(panel, customer); // Create and return a new page instance
	}
}
