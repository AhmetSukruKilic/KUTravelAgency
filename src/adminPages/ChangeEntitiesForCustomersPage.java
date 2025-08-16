package adminPages;

import guiManagment.*;
import reservation.Reservation;
import travelPackage.TravelPackage;
import users.Admin;
import users.Customer;
import cities.*;
import javax.swing.*;
import java.awt.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import customizedGuiObjects.*;
import entity.*;
import fileIOHandling.WriteUserLogs;

public class ChangeEntitiesForCustomersPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "red";

    public ChangeEntitiesForCustomersPage(CustomizedJPanel panel, Admin currentUser) {
        admin = currentUser;

        initializeLocation();
        initializeSize(panel, 900, 600);
        adjustPanelView(panel);
        getContentPane().add(panel);

        addComponentsToPanel(panel);
        setLocationRelativeTo(null); // Center the frame
    }

    @Override
    public void initializeLocation() {
        setTitle("Change Entities for Customers Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);
    }

    @Override
    public void initializeSize(CustomizedJPanel panel, int x, int y) {
        panel.adjustPanelSize(x, y);
        setSize(x, y);
    }

    @Override
    public void adjustPanelView(CustomizedJPanel panel) {
        panel.removeAll();
        panel.setBackgroundImage("viewUserStatsPhoto.jpg", 30);
        panel.repaint();
    }

    private void addComponentsToPanel(CustomizedJPanel panel) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 50, 900, 600);
        mainPanel.setOpaque(false);

        JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
        if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

        JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
        if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);

        JButton shutDownButton = CustomizedJButtons.shutDownButton();
        JButton logOutButton = CustomizedJButtons.logOutButton();
        JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, shutDownButton, logOutButton);
        panel.add(menuBarButton);

        // Reservation Selection
        JComboBox<Reservation> reservationBox = new JComboBox<>();
        reservationBox.setBounds(50, 100, 800, 25);

        // Renderer ekleyerek rezervasyon ismini gösterecek şekilde yapılandırıyoruz
        reservationBox.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Reservation) {
                    Reservation reservation = (Reservation) value;
                    label.setText(reservation.getName() + reservation.getReservationTimeInfo()); // Rezervasyon ismini göster
                }
                return label;
            }
        });

        mainPanel.add(createLabel("Select Reservation", 50, 75));
        mainPanel.add(reservationBox);

        // Entities
        JComboBox<String> flightBox = new JComboBox<>();
        flightBox.setBounds(50, 150, 800, 25);
        mainPanel.add(createLabel("Flights", 50, 125));
        mainPanel.add(flightBox);

        JComboBox<String> returnFlightBox = new JComboBox<>();
        returnFlightBox.setBounds(50, 200, 800, 25);
        mainPanel.add(createLabel("Return Flights", 50, 175));
        mainPanel.add(returnFlightBox);

        JComboBox<String> hotelBox = new JComboBox<>();
        hotelBox.setBounds(50, 250, 800, 25);
        mainPanel.add(createLabel("Hotels", 50, 225));
        mainPanel.add(hotelBox);

        JComboBox<String> taxiBox = new JComboBox<>();
        taxiBox.setBounds(50, 300, 800, 25);
        mainPanel.add(createLabel("Taxis", 50, 275));
        mainPanel.add(taxiBox);

        JComboBox<String> returnTaxiBox = new JComboBox<>();
        returnTaxiBox.setBounds(50, 350, 800, 25);
        mainPanel.add(createLabel("Return Taxis", 50, 325));
        mainPanel.add(returnTaxiBox);

        JButton updateButton = new JButton("Update Reservation");
        updateButton.setBounds(350, 400, 200, 40);
        mainPanel.add(updateButton);

        DefaultComboBoxModel<Customer> customerModel = new DefaultComboBoxModel<>();
        JComboBox<Customer> customerBox = new JComboBox<>(customerModel);
        customerBox.setBounds(50, 50, 800, 25);

        // Renderer ekleyerek müşteri ismini gösterecek şekilde yapılandırıyoruz
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
                    label.setText(customer.getName()); // Müşteri ismini göster
                }
                return label;
            }
        });

        mainPanel.add(createLabel("Select Customer", 50, 25));
        mainPanel.add(customerBox);

        // Populate customers
        HashSet<Customer> customers = Customer.getValidCustomers();
        for (Customer customer : customers) {
            customerModel.addElement(customer);
        }


        // Listeners
        customerBox.addActionListener(e -> {
            Customer selectedCustomer = (Customer) customerBox.getSelectedItem();
            if (selectedCustomer != null) {
                populateReservations(reservationBox, selectedCustomer);
            }
        });

        reservationBox.addActionListener(e -> {
            Reservation selectedReservation = (Reservation) reservationBox.getSelectedItem();
            if (selectedReservation != null) {
                updateOptions(selectedReservation, flightBox, returnFlightBox, hotelBox, taxiBox, returnTaxiBox);
            }
        });

        updateButton.addActionListener(e -> {
            Reservation selectedReservation = (Reservation) reservationBox.getSelectedItem();
            if (selectedReservation == null) {
                JOptionPane.showMessageDialog(panel, "No reservation selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] selectedEntities = {
                    (String) flightBox.getSelectedItem(),
                    (String) taxiBox.getSelectedItem(),
                    (String) hotelBox.getSelectedItem(),
                    (String) returnTaxiBox.getSelectedItem(),
                    (String) returnFlightBox.getSelectedItem()
            };

            if (!validateSelections(flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox)) {
                JOptionPane.showMessageDialog(panel, "Please make a valid selection for all fields.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate startDate = selectedReservation.getStartDate();
            LocalDate endDate = selectedReservation.getEndDate();
            String totalCost = calculateTotalCost(selectedEntities, selectedReservation.getTravelPackage().getDay(),
                    selectedReservation.getFromCity(), selectedReservation.getToCity());

            int confirmation = JOptionPane.showConfirmDialog(panel,
                    "Total cost will be: $" + totalCost + "\nDo you want to proceed?", "Confirm Update",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                confirmReservation(selectedReservation, panel, startDate, endDate, selectedEntities,
                        selectedReservation.getFromCity(), selectedReservation.getToCity(),
                        selectedReservation.getTravelPackage().getDay());
            }
        });

        panel.add(mainPanel);
    }

    private void populateReservations(JComboBox<Reservation> reservationBox, Customer customer) {
        DefaultComboBoxModel<Reservation> reservationModel = new DefaultComboBoxModel<>();
        HashSet<Reservation> activeReservations = Reservation.getActiveCustomerReservations(customer);
        if (activeReservations != null && !activeReservations.isEmpty()) {
            for (Reservation reservation : activeReservations) {
                reservationModel.addElement(reservation);
            }
        }
        reservationBox.setModel(reservationModel);
    }

    private void updateOptions(Reservation reservation, JComboBox<String> flightBox, JComboBox<String> returnFlightBox,
                               JComboBox<String> hotelBox, JComboBox<String> taxiBox, JComboBox<String> returnTaxiBox) {
        String fromCity = reservation.getFromCity();
        String toCity = reservation.getToCity();
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        HashSet<Flight> flights = City.getDepartureCityFlightEntities(fromCity, toCity, startDate, endDate);
        HashSet<Flight> returnFlights = City.getArrivalCityFlightEntities(fromCity, toCity, startDate, endDate);
        HashSet<Hotel> hotels = City.getArrivalCityHotelEntities(toCity, startDate, endDate);
        HashSet<Taxi> taxis = City.getArrivalCityTaxiEntities(toCity, startDate, endDate);
        HashSet<Taxi> returnTaxis = City.getArrivalCityReturnTaxiEntities(toCity, startDate, endDate);

        flightBox.setModel(new DefaultComboBoxModel<>(flights.stream()
                .map(flight -> flight.getEntityForOptionGo(startDate)).toArray(String[]::new)));
        returnFlightBox.setModel(new DefaultComboBoxModel<>(returnFlights.stream()
                .map(flight -> flight.getEntityForOptionReturn(endDate)).toArray(String[]::new)));
        hotelBox.setModel(new DefaultComboBoxModel<>(hotels.stream()
                .map(hotel -> hotel.getEntityForOption(startDate, endDate)).toArray(String[]::new)));
        taxiBox.setModel(new DefaultComboBoxModel<>(taxis.stream()
                .map(taxi -> taxi.getEntityForOptionGo(startDate)).toArray(String[]::new)));
        returnTaxiBox.setModel(new DefaultComboBoxModel<>(returnTaxis.stream()
                .map(taxi -> taxi.getEntityForOptionReturn(endDate)).toArray(String[]::new)));
    }

    private String calculateTotalCost(String[] options, int day, String departureCity, String arrivalCity) {
        double cost = 0;
        for (String option : options) {
            if (option == null || option.isBlank()) {
                return "0";
            }
        }

        try {
            ArrayList<Entity> entities = TravelPackage.returnEntityForm(options, departureCity, arrivalCity);
            cost = TravelPackage.totalCostOfEntities(entities, day);
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

        return String.format("%.2f", cost);
    }

    private void confirmReservation(Reservation selectedReservation, CustomizedJPanel panel, LocalDate startDate,
                                    LocalDate endDate, String[] entityStrings, String departureCity, String arrivalCity, int day) {
        try {
            ArrayList<Entity> entities = TravelPackage.returnEntityForm(entityStrings, departureCity, arrivalCity);
            String oldReservation = selectedReservation.reservationInfo();

            selectedReservation.newHotel(entities.get(2));
            selectedReservation.newFlight(entities.get(0));
            selectedReservation.newTaxi(entities.get(1));
            selectedReservation.newReturnTaxi(entities.get(3));
            selectedReservation.newReturnFlight(entities.get(4));

            if (selectedReservation.reservationInfo().equals(oldReservation)) {
                JOptionPane.showMessageDialog(panel, "Reservation Failed! No changes made.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            WriteUserLogs.edit(oldReservation, selectedReservation);
            JOptionPane.showMessageDialog(panel, "Reservation Updated Successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error occurred while updating the reservation.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = CustomizedJLabels.messageLabel(text);
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private boolean validateSelections(JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
                                       JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
        return flightBox.getSelectedItem() != null && taxiBox.getSelectedItem() != null
                && hotelBox.getSelectedItem() != null && returnTaxiBox.getSelectedItem() != null
                && returnFlightBox.getSelectedItem() != null;
    }

    @Override
    public Page createPage(CustomizedJPanel panel) {
        return new ChangeEntitiesForCustomersPage(panel, admin);
    }
}
