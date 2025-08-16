package adminPages;
import guiManagment.*;
import reservation.Reservation;
import users.Admin;
import users.Customer;
import java.awt.Component;
import java.util.HashSet;
import javax.swing.*;
import customizedGuiObjects.*;

public class ViewUsersReservationsPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "red";
    
    public ViewUsersReservationsPage(CustomizedJPanel panel, Admin currentUser) {
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
        setTitle("View User Reservations Page");
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
        if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

        // Next Page Button
        JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
        nextPageButton.setBounds(100, 10, 80, 30);
        if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);

        // Menu Bar Button
        JButton shutDownButton = CustomizedJButtons.shutDownButton();
        JButton logOutButton = CustomizedJButtons.logOutButton();
        JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, shutDownButton, logOutButton);
        panel.add(menuBarButton);

        // Components for selecting a customer
        DefaultComboBoxModel<Customer> customerModel = new DefaultComboBoxModel<>();
        JComboBox<Customer> customerSelectionBox = new JComboBox<>(customerModel);
        customerSelectionBox.setBounds(50, 50, 800, 25);
        panel.add(customerSelectionBox);

        JTextArea packageDetailsArea = new JTextArea();
        JScrollPane packageScrollPane = new JScrollPane(packageDetailsArea);
        packageScrollPane.setBounds(50, 100, 800, 400);
        packageDetailsArea.setEditable(false);
        panel.add(packageScrollPane);

        // Populate customers in the ComboBox
        HashSet<Customer> allCustomers = Customer.getValidCustomers();
        for (Customer customer : allCustomers) {
            customerModel.addElement(customer);
        }

        // Set custom renderer for customer ComboBox
        customerSelectionBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Customer) {
                    setText(((Customer) value).getName()); // Display the customer's name
                }
                return renderer;
            }
        });

        // Action listener for customer selection
        customerSelectionBox.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerSelectionBox.getSelectedItem();
            packageDetailsArea.setText(""); // Clear the text area

            if (selectedCustomer != null) {
                // Get active reservations
                HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(selectedCustomer);
                packageDetailsArea.append("Active Reservations:\n");
                if (activeReservations == null || activeReservations.isEmpty()) {
                    packageDetailsArea.append("No active reservations found.\n");
                } else {
                    for (Reservation reservation : activeReservations) {
                        packageDetailsArea.append(reservation.toString() + "\n");
                        packageDetailsArea.append("---------------------------------------------------------------------------------------\n"); // Add separator
                    }
                }

                // Get canceled reservations
                HashSet<Reservation> cancelledReservations = Reservation.getCanceledCustomerReservations(selectedCustomer);
                packageDetailsArea.append("\nCancelled Reservations:\n");
                if (cancelledReservations == null || cancelledReservations.isEmpty()) {
                    packageDetailsArea.append("No cancelled reservations found.\n");
                } else {
                    for (Reservation reservation : cancelledReservations) {
                        packageDetailsArea.append(reservation.toString() + "\n");
                        packageDetailsArea.append("---------------------------------------------------------------------------------------\n"); // Add separator
                    }
                }
            } else {
                packageDetailsArea.setText("No customer selected.");
            }
        });

    }

    
    public Page createPage(CustomizedJPanel panel) {
        return new ViewUsersReservationsPage(panel, admin);
    }
}
