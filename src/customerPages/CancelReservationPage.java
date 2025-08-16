package customerPages;

import guiManagment.*;
import reservation.Reservation;
import users.Customer;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.*;
import customizedGuiObjects.*;
import fileIOHandling.WriteUserLogs;

public class CancelReservationPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Customer customer;
    private static final String COLOR = "gold";

    public CancelReservationPage(CustomizedJPanel panel, Customer currentUser) {
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
        setTitle("Cancel Reservation Page");
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

        JTextArea packageDetailsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(packageDetailsArea);
        scrollPane.setBounds(50, 130, 800, 300);
        packageDetailsArea.setEditable(false);
        panel.add(scrollPane);

        DefaultComboBoxModel<Reservation> model = new DefaultComboBoxModel<>();
        JComboBox<Reservation> packageSelectionBox = new JComboBox<>(model);
        packageSelectionBox.setBounds(50, 450, 800, 25);
        panel.add(packageSelectionBox);

        JButton reserveButton = new JButton("Cancel Selected Package");
        CustomizedJButtons.goodLookingButton(reserveButton, COLOR);
        reserveButton.setBounds(325, 490, 250, 30);
        reserveButton.setEnabled(false);
        panel.add(reserveButton);

        packageSelectionBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                    setText("Reservation: " + ((Reservation)value).getName() + " " + ((Reservation)value).getReservationTimeInfo());
                }
                return renderer;
            }
        });

        packageSelectionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation reservation = (Reservation) packageSelectionBox.getSelectedItem();
                if (reservation != null) {
                    try {
                        packageDetailsArea.setText(reservation.toString());
                        double cancellationFee = reservation.calculateCancellationFee();
                        packageDetailsArea.append("\nCancellation Fee: " + String.format("%.2f", cancellationFee));
                        reserveButton.setEnabled(true);
                    } catch (Exception ex) {
                        packageDetailsArea.setText("Error displaying package details.");
                    }
                } else {
                    packageDetailsArea.setText("No package selected.");
                    reserveButton.setEnabled(false);
                }
            }
        });

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reservation reservation = (Reservation) packageSelectionBox.getSelectedItem();
                if (reservation != null) {
                    try {
                        double cancellationFee = reservation.calculateCancellationFee();
                        int confirmation = JOptionPane.showConfirmDialog(null,
                                "The cancellation fee is: " + String.format("%.2f", cancellationFee) + ". Do you want to proceed?",
                                "Confirm Cancellation", JOptionPane.YES_NO_OPTION);

                        if (confirmation == JOptionPane.YES_OPTION) {
                            String oldReservationInfo = reservation.reservationInfo();
                            Reservation.cancelReservation(reservation, customer);
                            WriteUserLogs.edit(oldReservationInfo, reservation);
                            JOptionPane.showMessageDialog(null, "Cancelled " + reservation.getName() + reservation.getReservationTimeInfo(),
                                    "Reservation Cancelled", JOptionPane.INFORMATION_MESSAGE);

                            // Remove the cancelled reservation from the ComboBox
                            model.removeElement(reservation);
                        } else {
                            JOptionPane.showMessageDialog(null, "Cancellation aborted.", "Action Cancelled", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error during reservation cancellation.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No package selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Populate ComboBox with active reservations
        HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(customer);
        if (activeReservations == null || activeReservations.isEmpty()) {
            packageDetailsArea.setText("There is no active reservation for customer: " + customer.getName());
        } else {
            for (Reservation res : activeReservations) {
                model.addElement(res);
            }
        }
    }

    public Page createPage(CustomizedJPanel panel) {
        return new CancelReservationPage(panel, customer);
    }
}
