package adminPages;

import guiManagment.*;
import travelPackage.AdminTravelPackage;
import travelPackage.TravelPackage;
import users.Admin;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashSet;
import entity.*;
import fileIOHandling.WriteAdminTravelPackages;
import cities.*;
import customizedGuiObjects.CustomizedJButtons;
import customizedGuiObjects.CustomizedJLabels;
import customizedGuiObjects.CustomizedJPanel;

import java.awt.event.ActionListener;

public class CreateTravelPackagePage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private String COLOR = "red";
    private Admin admin;

    public CreateTravelPackagePage(CustomizedJPanel panel, Admin currentUser) {
        admin = currentUser;
        initializeLocation();
        initializeSize(panel, 900, 600);
        adjustPanelView(panel);
        getContentPane().add(panel);

        // Add components and listeners
        addComponentsToPanel(panel);

        setLocationRelativeTo(null); // Center the frame
    }

    @Override
    public void initializeLocation() {
        setTitle("Create Travel Package Page");
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
        panel.setBackgroundImage("customReservationPage2.jpg", 50);
        panel.repaint();
    }

    private void addComponentsToPanel(CustomizedJPanel panel) {
        JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
        if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);

        JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
        if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

        JButton customerMainPageButton = CustomizedJButtons.customerMainPageButton(COLOR);
        JButton makeReservation = CustomizedJButtons.makeReservationsPageButton(COLOR);
        JButton shutDownButton = CustomizedJButtons.shutDownButton();
        JButton logoutButton = CustomizedJButtons.logOutButton();

        JButton menuBar = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, customerMainPageButton,
                makeReservation, logoutButton, shutDownButton);
        panel.add(menuBar);

        // Define ComboBoxes
        JComboBox<String> departureCityBox = new JComboBox<>(new HashSet<>(FromCity.getFromCities()).toArray(new String[0]));
        JComboBox<String> arrivalCityBox = new JComboBox<>(new HashSet<>(ToCity.getToCities()).toArray(new String[0]));
        JComboBox<String> flightBox = new JComboBox<>();
        JComboBox<String> taxiBox = new JComboBox<>();
        JComboBox<String> hotelBox = new JComboBox<>();
        JComboBox<String> returnTaxiBox = new JComboBox<>();
        JComboBox<String> returnFlightBox = new JComboBox<>();

        // Set bounds and labels
        departureCityBox.setBounds(250, 50, 300, 30);
        arrivalCityBox.setBounds(250, 100, 300, 30);
        flightBox.setBounds(250, 150, 300, 30);
        taxiBox.setBounds(250, 200, 300, 30);
        hotelBox.setBounds(250, 250, 300, 30);
        returnTaxiBox.setBounds(250, 300, 300, 30);
        returnFlightBox.setBounds(250, 350, 300, 30);

        panel.add(createLabel("Departure City:", 50, 50));
        panel.add(departureCityBox);
        panel.add(createLabel("Arrival City:", 50, 100));
        panel.add(arrivalCityBox);
        panel.add(createLabel("Flight:", 50, 150));
        panel.add(flightBox);
        panel.add(createLabel("Taxi:", 50, 200));
        panel.add(taxiBox);
        panel.add(createLabel("Hotel:", 50, 250));
        panel.add(hotelBox);
        panel.add(createLabel("Return Taxi:", 50, 300));
        panel.add(returnTaxiBox);
        panel.add(createLabel("Return Flight:", 50, 350));
        panel.add(returnFlightBox);

        // Add fields for name, description, and duration
        JTextField nameField = new JTextField();
        nameField.setBounds(250, 400, 300, 30);
        panel.add(createLabel("Package Name:", 50, 400));
        panel.add(nameField);

        JTextField descriptionField = new JTextField();
        descriptionField.setBounds(250, 450, 300, 30);
        panel.add(createLabel("Description:", 50, 450));
        panel.add(descriptionField);

        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        durationSpinner.setBounds(700, 200, 60, 30); // Aligned to center-right
        panel.add(createLabel("Duration (days):", 585, 200));
        panel.add(durationSpinner);

        JSpinner discountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        discountSpinner.setBounds(700, 300, 60, 30); // Aligned to center-right
        panel.add(createLabel("Discount (%):", 600, 300));
        panel.add(discountSpinner);

        JButton confirmButton = new JButton("Confirm Package");
        confirmButton.setBounds(360, 500, 150, 30); // Moved slightly up
        panel.add(confirmButton);

        // Add ActionListeners for dynamic updates
        addListeners(departureCityBox, arrivalCityBox, flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox);

        // Confirm button action
        confirmButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String description = descriptionField.getText().trim();
            int duration = (int) durationSpinner.getValue();
            int discount = (int) discountSpinner.getValue();
            
            
            String departureCity = (String) departureCityBox.getSelectedItem();
            String arrivalCity = (String) arrivalCityBox.getSelectedItem();

            String[] entityStrings = {
                (String) flightBox.getSelectedItem(),
                (String) taxiBox.getSelectedItem(),
                (String) hotelBox.getSelectedItem(),
                (String) returnTaxiBox.getSelectedItem(),
                (String) returnFlightBox.getSelectedItem()
            };

            if (departureCity == null || arrivalCity == null || name.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            

            confirmPackage(panel, entityStrings, description, name, departureCity, arrivalCity, duration, discount);
        });
    }

    private void confirmPackage(CustomizedJPanel panel, String[] entityStrings,
                                 String description, String name, String departureCity, String arrivalCity, int day, double discount) {
        try {

            if (!validateEntityOrder(entityStrings)) {
                return;
            }

            ArrayList<Entity> entities = TravelPackage.returnEntityForm(entityStrings, departureCity, arrivalCity);
            TravelPackage tPackage = AdminTravelPackage.isThereAdminTravelPackage(entities, day);
            
            if (AdminTravelPackage.isThereSameNameAdminTravelPackage(name)) {
            	JOptionPane.showMessageDialog(panel, "There is already a package with name");
                return;
            }
            if (tPackage != null) {
                JOptionPane.showMessageDialog(panel, "There is already a package with same entities and duration");
                return;
            } else {
                tPackage = new AdminTravelPackage(entities, day, description, name, discount);
            }

            WriteAdminTravelPackages.write(tPackage);

            JOptionPane.showMessageDialog(panel, "Package Confirmed!\nPackage saved successfully.");
        } catch (Exception e) {
            System.err.println("An error occured while confirming reservation in CustomReservations");
        }
    }

    private boolean validateEntityOrder(String[] entityStrings) {
        try {
            if (!(entityStrings[0].contains("class"))) {
                System.err.println("wrong order1 in customreservations");
                return false;
            }
            if (!(entityStrings[1].contains("rate"))) {
                System.err.println("wrong order2 in customreservations");
                return false;
            }
            if (!(entityStrings[2].contains("airport"))) {
                System.err.println("wrong order3 in customreservations");
                return false;
            }
            if (!(entityStrings[3].contains("rate"))) {
                System.err.println("wrong order4 in customreservations");
                return false;
            }
            if (!(entityStrings[4].contains("class"))) {
                System.err.println("wrong order5 in customreservations");
                return false;
            }
        } catch (Exception e) {
            System.err.println("out of index error in customreservations");
            return false;
        }
        return true;
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = CustomizedJLabels.messageLabel(text);
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private void addListeners(JComboBox<String> departureBox, JComboBox<String> arrivalBox,
                              JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
                              JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {

        ActionListener updateListener = e -> updateOptions(departureBox, arrivalBox, flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox);

        departureBox.addActionListener(updateListener);
        arrivalBox.addActionListener(updateListener);
    }

    private void updateOptions(JComboBox<String> departureBox, JComboBox<String> arrivalBox,
                                JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
                                JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {

        String departureCity = (String) departureBox.getSelectedItem();
        String arrivalCity = (String) arrivalBox.getSelectedItem();

        if (departureCity == null || arrivalCity == null || departureCity.isBlank() || arrivalCity.isBlank()) {
            resetComboBoxes(flightBox, taxiBox, hotelBox, returnTaxiBox, returnFlightBox);
            return;
        }

        // Fetch options dynamically using City methods
        HashSet<Flight> flightsGo = City.getDepartureCityFlightEntitiesForTravelPackage(departureCity, arrivalCity);
        HashSet<Flight> flightsBack = City.getArrivalCityFlightEntitiesForTravelPackage(departureCity, arrivalCity);
        HashSet<Taxi> taxis = City.getArrivalCityTaxiEntitiesForTravelPackage(arrivalCity);
        HashSet<Hotel> hotels = City.getArrivalCityHotelEntitiesForTravelPackage(arrivalCity);

        flightBox.setModel(new DefaultComboBoxModel<>(flightsGo.stream()
                .map(Flight::getEntityForOptionPrefix).toArray(String[]::new)));
        taxiBox.setModel(new DefaultComboBoxModel<>(taxis.stream()
                .map(Taxi::getEntityForOptionPrefix).toArray(String[]::new)));
        hotelBox.setModel(new DefaultComboBoxModel<>(hotels.stream()
                .map(Hotel::getEntityForOptionPrefix).toArray(String[]::new)));
        returnTaxiBox.setModel(new DefaultComboBoxModel<>(taxis.stream()
                .map(Taxi::getEntityForOptionPrefix).toArray(String[]::new)));
        returnFlightBox.setModel(new DefaultComboBoxModel<>(flightsBack.stream()
                .map(Flight::getEntityForOptionPrefix).toArray(String[]::new)));
    }

    private void resetComboBoxes(JComboBox<String> flightBox, JComboBox<String> taxiBox, JComboBox<String> hotelBox,
                                  JComboBox<String> returnTaxiBox, JComboBox<String> returnFlightBox) {
        flightBox.setModel(new DefaultComboBoxModel<>());
        taxiBox.setModel(new DefaultComboBoxModel<>());
        hotelBox.setModel(new DefaultComboBoxModel<>());
        returnTaxiBox.setModel(new DefaultComboBoxModel<>());
        returnFlightBox.setModel(new DefaultComboBoxModel<>());
    }

    @Override
    public Page createPage(CustomizedJPanel panel) {
        return new CreateTravelPackagePage(panel, admin);
    }
}