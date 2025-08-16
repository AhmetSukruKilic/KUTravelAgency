package customerPages;

import guiManagment.*;
import users.Customer;
import javax.swing.*;
import customizedGuiObjects.*;

public class EditReservationsPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Customer customer;
	private static final String COLOR = "gold";

	public EditReservationsPage(CustomizedJPanel panel, Customer currentUser) {
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
		panel.setBackgroundImage("reservationHistory2.jpg", 30);
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

		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

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

		JButton cancelReservationPageButton = CustomizedJButtons.cancelReservationPageButton(COLOR);
		cancelReservationPageButton.setBounds(180, 290, 200, 50);
		panel.add(cancelReservationPageButton);

		JButton changeReservationsEntitiesPageButton = CustomizedJButtons.changeReservationsEntitiesPageButton(COLOR);
		changeReservationsEntitiesPageButton.setBounds(420, 290, 200, 50);
		panel.add(changeReservationsEntitiesPageButton);

	}

	public Page createPage(CustomizedJPanel panel) {
		return new EditReservationsPage(panel, customer);
	}
}