package adminPages;

import guiManagment.*;
import users.Admin;
import javax.swing.*;
import customizedGuiObjects.*;

public class AdminMainPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private Admin admin;
    private static final String COLOR = "gold";
    
    public AdminMainPage(CustomizedJPanel panel, Admin currentUser) {
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
        panel.setBackgroundImage("adminMainPage.jpg", 30);
        // Remove initial label and add a new welcome label
        panel.removeLabel();
        panel.addNewLabel("");
    }

    /**
     * Initialize the frame properties.
     */
    public void initializeLocation() {
        setTitle("Admin Main Page");
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
        
        
        JButton userStatsPageButton = CustomizedJButtons.userStatsPageButton(COLOR);
        userStatsPageButton.setBounds(220, 360, 170, 50);
        panel.add(userStatsPageButton);
        
        
        JButton editAdminReservationsButton = CustomizedJButtons.editAdminReservationsButton(COLOR);
        editAdminReservationsButton.setBounds(520, 360, 170, 50);
        panel.add(editAdminReservationsButton);
        
        JButton createTravelPackageButton = CustomizedJButtons.createTravelPackageButton(COLOR);
        createTravelPackageButton.setBounds(370, 460, 180, 50);
        panel.add(createTravelPackageButton);
        
    }

    
    
    public Page createPage(CustomizedJPanel panel) {
        return new AdminMainPage(panel, admin);
    }
}
