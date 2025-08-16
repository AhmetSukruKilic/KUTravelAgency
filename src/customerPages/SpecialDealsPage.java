package customerPages;

import guiManagment.*;
import reservation.Reservation;
import reservation.TravelPackageReservation;
import users.Customer;
import travelPackage.AdminTravelPackage;
import javax.swing.*;
import customizedGuiObjects.*;
import fileIOHandling.WriteUserLogs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashSet;

public class SpecialDealsPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Customer customer;
    private static final String COLOR = "red";

    public SpecialDealsPage(CustomizedJPanel panel, Customer currentUser) {
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
        panel.setBackgroundImage("discountPage2.jpg", 40);
        // Remove initial label and add a new welcome label
        panel.removeLabel();
        panel.addNewLabel("");
    }
    /**
     * Initialize the frame properties.
     */
    public void initializeLocation() {
        setTitle("Special Deals Page");
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
	    // Discounted Packages Section
        JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
        if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

        // Next Page Button
        JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR); 
        if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);
        
	    // Menu Bar Button
	    JButton shutDownButton = CustomizedJButtons.shutDownButton();
	    JButton logOutButton = CustomizedJButtons.logOutButton();
	    JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
	    JButton makeReservationsPageButton = CustomizedJButtons.makeReservationsPageButton(COLOR);
	    JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, customerMainPageButton, makeReservationsPageButton, shutDownButton, logOutButton);
	    panel.add(menuBarButton);
	    
	    
        JLabel startDateLabel = new JLabel("Select Start Date:");
        startDateLabel.setBounds(50, 65, 150, 25);
        panel.add(startDateLabel);

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

        startDayBox.setBounds(200, 65, 50, 25);
        startMonthBox.setBounds(260, 65, 50, 25);
        startYearBox.setBounds(320, 65, 80, 25);

        panel.add(startDayBox);
        panel.add(startMonthBox);
        panel.add(startYearBox);

        JTextArea packageDetailsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(packageDetailsArea);
        scrollPane.setBounds(50, 130, 800, 300);
        packageDetailsArea.setEditable(false);
        panel.add(scrollPane);

        DefaultComboBoxModel<AdminTravelPackage> model = new DefaultComboBoxModel<>();
        JComboBox<AdminTravelPackage> packageSelectionBox = new JComboBox<>(model);
        packageSelectionBox.setBounds(50, 450, 800, 25);
        panel.add(packageSelectionBox);

        JButton reserveButton = new JButton("Reserve Selected Package");
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
                if (value instanceof AdminTravelPackage) {
                    setText("Package: " + ((AdminTravelPackage) value).getName());
                }
                return renderer;
            }
        });

        ActionListener updatePackagesListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashSet<AdminTravelPackage> adminPackages = AdminTravelPackage.getAdminTravelPackages();
                    model.removeAllElements();

                    for (AdminTravelPackage pkg : adminPackages) {
                        model.addElement(pkg);
                    }

                    packageDetailsArea.setText("Date has been updated. Select package to see details.");
                    reserveButton.setEnabled(false);

                } catch (Exception ex) {
                    packageDetailsArea.setText("Error updating packages.");
                }
            }
        };

        startDayBox.addActionListener(updatePackagesListener);
        startMonthBox.addActionListener(updatePackagesListener);
        startYearBox.addActionListener(updatePackagesListener);

        packageSelectionBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminTravelPackage selectedPackage = (AdminTravelPackage) packageSelectionBox.getSelectedItem();
                if (selectedPackage != null) {
                    try {
                        LocalDate startDate = LocalDate.of((Integer) startYearBox.getSelectedItem(),
                                (Integer) startMonthBox.getSelectedItem(), (Integer) startDayBox.getSelectedItem());

                        int day = selectedPackage.getDay();
                        LocalDate endDate = startDate.plusDays(day);

                        packageDetailsArea.setText(selectedPackage.adminTravelPackageInfo(startDate, endDate));
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
                AdminTravelPackage selectedPackage = (AdminTravelPackage) packageSelectionBox.getSelectedItem();
                if (selectedPackage != null) {
                    try {
                        LocalDate startDate = LocalDate.of((Integer) startYearBox.getSelectedItem(),
                                (Integer) startMonthBox.getSelectedItem(), (Integer) startDayBox.getSelectedItem());

                        int day = selectedPackage.getDay();

                        LocalDate endDate = startDate.plusDays(day);

                        if (customer.isBusy(startDate, endDate)) {
                            return;
                        }

                        Reservation reservation = new TravelPackageReservation(selectedPackage, startDate.toString(),
                                endDate.toString(), customer);
                        Reservation.makeActiveReservation(reservation, customer);
                        WriteUserLogs.write(reservation);

                        JOptionPane.showMessageDialog(null, "Successfully reserved: " + selectedPackage.getName(),
                                "Reservation Complete", JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error during reservation.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No package selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Page createPage(CustomizedJPanel panel) {
        return new SpecialDealsPage(panel, customer); // Create and return a new page instance
    }
}
