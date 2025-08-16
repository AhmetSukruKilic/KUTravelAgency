package adminPages;

import guiManagment.*;
import users.Admin;
import javax.swing.*;
import customizedGuiObjects.*;

public class MakeReservationForCustomerPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "gold";
    
    public MakeReservationForCustomerPage(CustomizedJPanel panel, Admin currentUser) {
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
        setTitle("Make Reservations Main Page");
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
        if (PageDataBase.isTherePreviousPage()) panel.add(previousPageButton);

        // Next Page Button
        JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR); 
        if (PageDataBase.isThereNextPage()) panel.add(nextPageButton);

        // Menu Bar Button
        JButton shutDownButton = CustomizedJButtons.shutDownButton();
        JButton logOutButton = CustomizedJButtons.logOutButton();
        JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, shutDownButton, logOutButton);
        panel.add(menuBarButton);
        
        JButton makeCustomReservationButton = CustomizedJButtons.makeCustomReservationButton(COLOR);
        makeCustomReservationButton.setBounds(500, 400, 170, 50);
        panel.add(makeCustomReservationButton);
        
        JButton makeSingleEntityReservationButton = CustomizedJButtons.makeSingleEntityReservationButton(COLOR);
        makeSingleEntityReservationButton.setBounds(200, 400, 210, 50);
        panel.add(makeSingleEntityReservationButton);
        
        JButton makeDiscountedReservationButton = CustomizedJButtons.makeDiscountedReservationButton(COLOR);
        makeDiscountedReservationButton.setBounds(380, 250, 170, 50);
        panel.add(makeDiscountedReservationButton);
        
        
    }

    
    
    public Page createPage(CustomizedJPanel panel) {
        return new MakeReservationForCustomerPage(panel, admin);
    }
}
