package adminPages;
import guiManagment.*;
import reservation.Reservation;
import users.Admin;
import users.Customer;

import java.awt.Component;
import java.util.HashSet;

import javax.swing.*;
import customizedGuiObjects.*;
import fileIOHandling.WriteUserLogs;

public class CancelReservationForCustomersPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "gold";
    
    public CancelReservationForCustomersPage(CustomizedJPanel panel, Admin currentUser) {
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
        setTitle("Cancel Reservations Page");
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

        JTextArea reservationDetailsArea = new JTextArea();
        JScrollPane reservationScrollPane = new JScrollPane(reservationDetailsArea);
        reservationScrollPane.setBounds(50, 130, 800, 300);
        reservationDetailsArea.setEditable(false);
        panel.add(reservationScrollPane);

        DefaultComboBoxModel<Reservation> reservationModel = new DefaultComboBoxModel<>();
        JComboBox<Reservation> reservationSelectionBox = new JComboBox<>(reservationModel);
        reservationSelectionBox.setBounds(50, 450, 800, 25);
        panel.add(reservationSelectionBox);

        JButton cancelReservationButton = new JButton("Cancel Selected Reservation");
        CustomizedJButtons.goodLookingButton(cancelReservationButton, COLOR);
        cancelReservationButton.setBounds(325, 490, 250, 30);
        cancelReservationButton.setEnabled(false);
        panel.add(cancelReservationButton);

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
                    setText("Customer: " + ((Customer) value).getName()); // Display the customer's name
                }
                return renderer;
            }
        });

        // Action listener for customer selection
        customerSelectionBox.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerSelectionBox.getSelectedItem();
            reservationModel.removeAllElements();
            reservationDetailsArea.setText("");

            if (selectedCustomer != null) {
                HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(selectedCustomer);
                if (activeReservations == null || activeReservations.isEmpty()) {
                    reservationDetailsArea.setText("No active reservations for customer: " + selectedCustomer.getName());
                } else {
                    for (Reservation reservation : activeReservations) {
                        reservationModel.addElement(reservation);
                    }
                }
            }
        });

        // Set custom renderer for reservation ComboBox
        reservationSelectionBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                	Reservation reservation = (Reservation) value;
                    setText( "Reservation: "+ reservation.getName() + reservation.getReservationTimeInfo()); // Display the reservation's name
                }
                return renderer;
            }
        });

        // Action listener for reservation selection
        reservationSelectionBox.addActionListener(e -> {
            Reservation selectedReservation = (Reservation) reservationSelectionBox.getSelectedItem();
            if (selectedReservation != null) {
                reservationDetailsArea.setText(selectedReservation.toString());
                double cancellationFee = selectedReservation.calculateCancellationFee();
                reservationDetailsArea.append("\nCancellation Fee: " + String.format("%.2f", cancellationFee));
                cancelReservationButton.setEnabled(true);
            } else {
                reservationDetailsArea.setText("No reservation selected.");
                cancelReservationButton.setEnabled(false);
            }
        });

        // Action listener for cancel button
        cancelReservationButton.addActionListener(e -> {
            Reservation selectedReservation = (Reservation) reservationSelectionBox.getSelectedItem();
            if (selectedReservation != null) {
                try {
                    double cancellationFee = selectedReservation.calculateCancellationFee();
                    int confirmation = JOptionPane.showConfirmDialog(null,
                            "The cancellation fee is: " + String.format("%.2f", cancellationFee) + ". Do you want to proceed?",
                            "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        String oldReservationInfo = selectedReservation.reservationInfo();
                        Reservation.cancelReservation(selectedReservation, (Customer) customerSelectionBox.getSelectedItem());
                        WriteUserLogs.edit(oldReservationInfo, selectedReservation);
                        JOptionPane.showMessageDialog(null, "Cancelled " + selectedReservation.getName(),
                                "Reservation Cancelled", JOptionPane.INFORMATION_MESSAGE);

                        // Remove the cancelled reservation from the ComboBox
                        reservationModel.removeElement(selectedReservation);
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancellation aborted.", "Action Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error during reservation cancellation.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No reservation selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


    
    
    public Page createPage(CustomizedJPanel panel) {
        return new CancelReservationForCustomersPage(panel, admin);
    }
}
