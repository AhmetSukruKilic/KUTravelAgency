package userEntryPages;

import javax.swing.*;

import customizedGuiObjects.*;
import guiManagment.Page;

public class RegisterPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private static final String COLOR = "gold";

    
    /**
     * Create the frame.
     */
    public RegisterPage(CustomizedJPanel panel) {
        // Frame setup
        initializeLocation();
        initializeSize(panel, 900, 600);
        // Use the passed JPanel as the contentPane
        adjustPanelView(panel);
        // Set panel properties
        getContentPane().add(panel);

        // Add components to the panel
        addComponentsToPanel(panel);

        // Center frame on screen
        setLocationRelativeTo(null);
    }

    
    public void adjustPanelView(CustomizedJPanel panel) {
    	// Set the background image on the panel
        panel.setBackgroundImage("logRegPagePhoto.jpg",30);

        // Remove initial label and add a new welcome label
        panel.removeLabel();
        panel.addNewLabel("Welcome to the Register Page!");
    }
    /**
     * Initialize the frame properties.
     */
    public void initializeLocation() {
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null); // Using absolute positioning for simplicity
    }
    
    public void initializeSize(CustomizedJPanel panel, int x, int y) {
    	panel.adjustPanelSize(x, y);
        setSize(x, y); // Reduced dimensions for a smaller screen
    }

    /**
     * Add components (e.g., text fields, button) to the panel.
     */
    private void addComponentsToPanel(CustomizedJPanel panel) {
        // Adjusted Y-coordinates to move elements higher

        // Username label
        JLabel usernameLabel = CustomizedJLabels.messageLabel(" New Username:");
        usernameLabel.setBounds(250, 140, 160, 20); // Positioned higher
        panel.add(usernameLabel);

        // Username text field
        JTextField textFieldName = CustomizedJLabels.messagedTextField("");
        textFieldName.setBounds(250, 165, 400, 30); // Positioned higher
        panel.add(textFieldName);

        // Password label
        JLabel passwordLabel1 = CustomizedJLabels.messageLabel(" New Password:");
        passwordLabel1.setBounds(250, 220, 160, 20); // Positioned higher
        panel.add(passwordLabel1);

        // Password text field
        JPasswordField passwordField1 = CustomizedJLabels.textPassword();
        passwordField1.setBounds(250, 240, 400, 30); // Positioned higher
        panel.add(passwordField1);
        
        JLabel passwordLabel2 = CustomizedJLabels.messageLabel(" Confirm Password:");
        passwordLabel2.setBounds(250, 270, 160, 20); // Positioned higher
        panel.add(passwordLabel2);
        
        JPasswordField passwordField2 = CustomizedJLabels.textPassword();
        passwordField2.setBounds(250, 290, 400, 30); // Positioned higher
        panel.add(passwordField2);

        // Login button 
        JButton signUpButton = CustomizedJButtons.signUpButton(textFieldName, passwordField1, passwordField2);
        signUpButton.setBounds(350, 340, 200, 50); // Button position slightly adjusted
        panel.add(signUpButton);
        
        JButton loginButton = CustomizedJButtons.loginPageButton();
        JButton shuwDownButton = CustomizedJButtons.shutDownButton();
        
        JButton popButton = CustomizedJButtons.createJPopupButtonsMenu("Menu",COLOR, loginButton, shuwDownButton);
        popButton.setBounds(800, 0, 100, 50); // Button position slightly adjusted
        panel.add(popButton);
    }
    
    
    public Page createPage(CustomizedJPanel panel) {
        return new RegisterPage(panel);
    }

}
