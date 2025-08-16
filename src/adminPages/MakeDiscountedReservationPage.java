package adminPages;

import customizedGuiObjects.CustomizedJButtons;
import customizedGuiObjects.CustomizedJLabels;
import customizedGuiObjects.CustomizedJPanel;
import fileIOHandling.WriteUserLogs;
import guiManagment.Page;
import guiManagment.PageDataBase;
import reservation.Reservation;
import reservation.TravelPackageReservation;
import travelPackage.AdminTravelPackage;
import users.Admin;
import users.Customer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashSet;

public class MakeDiscountedReservationPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "red";

    public MakeDiscountedReservationPage(CustomizedJPanel panel, Admin currentUser) {
        admin = currentUser;

        initializeLocation();
        initializeSize(panel, 900, 600);
        adjustPanelView(panel);
        getContentPane().add(panel);
        addComponentsToPanel(panel);
        setLocationRelativeTo(null);
    }

    @Override
    public void adjustPanelView(CustomizedJPanel panel) {
        panel.setBackgroundImage("viewUserStatsPhoto.jpg", 50);
        panel.repaint();
    }

    @Override
    public void initializeLocation() {
        setTitle("Make Discounted Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);
    }

    @Override
    public void initializeSize(CustomizedJPanel panel, int x, int y) {
        panel.setBounds(0, 0, x, y);
        setSize(x, y);
    }

    private void addComponentsToPanel(CustomizedJPanel panel) {
    	 JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
         if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

         // Next Page Button
         JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR); 
         if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);

         // Menu Bar Button
         JButton shutDownButton = CustomizedJButtons.shutDownButton();
         JButton logOutButton = CustomizedJButtons.logOutButton();
         JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, shutDownButton, logOutButton);
         panel.add(menuBarButton);
         
        JPanel stepsPanel = new JPanel();
        stepsPanel.setLayout(null);
        stepsPanel.setBounds(0, 50, 900, 600);
        stepsPanel.setOpaque(false);

        // Customer Selection ComboBox
        DefaultComboBoxModel<Customer> customerModel = new DefaultComboBoxModel<>();
        JComboBox<Customer> customerBox = new JComboBox<>(customerModel);
        customerBox.setBounds(50, 25, 800, 30);
        stepsPanel.add(createLabel("Select Customer", 50, 0));
        stepsPanel.add(customerBox);

        customerBox.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Customer) {
                    Customer customer = (Customer) value;
                    label.setText(customer.getName());
                }
                return label;
            }
        });

        HashSet<Customer> customers = Customer.getValidCustomers();
        for (Customer customer : customers) {
            customerModel.addElement(customer);
        }

        JLabel startDateLabel = new JLabel("Select Start Date:");
        startDateLabel.setBounds(50, 75, 150, 25);
        stepsPanel.add(startDateLabel);

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

        startDayBox.setBounds(200, 75, 50, 25);
        startMonthBox.setBounds(260, 75, 50, 25);
        startYearBox.setBounds(320, 75, 80, 25);

        stepsPanel.add(startDayBox);
        stepsPanel.add(startMonthBox);
        stepsPanel.add(startYearBox);

        JTextArea packageDetailsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(packageDetailsArea);
        scrollPane.setBounds(50, 130, 800, 300);
        packageDetailsArea.setEditable(false);
        stepsPanel.add(scrollPane);

        DefaultComboBoxModel<AdminTravelPackage> model = new DefaultComboBoxModel<>();
        JComboBox<AdminTravelPackage> packageSelectionBox = new JComboBox<>(model);
        packageSelectionBox.setBounds(50, 450, 800, 25);
        stepsPanel.add(packageSelectionBox);

        JButton reserveButton = new JButton("Reserve Selected Package");
        reserveButton.setBounds(325, 490, 250, 30);
        reserveButton.setEnabled(false);
        stepsPanel.add(reserveButton);

        packageSelectionBox.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof AdminTravelPackage) {
                    AdminTravelPackage pkg = (AdminTravelPackage) value;
                    label.setText(pkg.getName());
                }
                return label;
            }
        });

        ActionListener updatePackagesListener = e -> {
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
        };

        startDayBox.addActionListener(updatePackagesListener);
        startMonthBox.addActionListener(updatePackagesListener);
        startYearBox.addActionListener(updatePackagesListener);

        packageSelectionBox.addActionListener(e -> {
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
        });

        reserveButton.addActionListener(e -> {
            AdminTravelPackage selectedPackage = (AdminTravelPackage) packageSelectionBox.getSelectedItem();
            Customer selectedCustomer = (Customer) customerBox.getSelectedItem();

            if (selectedPackage != null && selectedCustomer != null) {
                try {
                    LocalDate startDate = LocalDate.of((Integer) startYearBox.getSelectedItem(),
                            (Integer) startMonthBox.getSelectedItem(), (Integer) startDayBox.getSelectedItem());

                    int day = selectedPackage.getDay();

                    LocalDate endDate = startDate.plusDays(day);

                    if (selectedCustomer.isBusy(startDate, endDate)) {
                        JOptionPane.showMessageDialog(null, "Selected customer is busy during this period.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    Reservation reservation = new TravelPackageReservation(selectedPackage, startDate.toString(),
                            endDate.toString(), selectedCustomer);
                    Reservation.makeActiveReservation(reservation, selectedCustomer);
                    WriteUserLogs.write(reservation);

                    JOptionPane.showMessageDialog(null, "Successfully reserved: " + selectedPackage.getName() +
                            " for " + selectedCustomer.getName(), "Reservation Complete", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error during reservation.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No package or customer selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(stepsPanel);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = CustomizedJLabels.messageLabel(text);
        label.setBounds(x, y, 200, 25);
        return label;
    }

    @Override
    public Page createPage(CustomizedJPanel panel) {
        return new MakeDiscountedReservationPage(panel, admin);
    }
}
