package userEntryPages;

import javax.swing.*;
import customizedGuiObjects.*;
import guiManagment.Page;

public class LoginPage extends JFrame implements Page {

    private static final long serialVersionUID = 1L;
    private static final String COLOR = "gold";
    /**
     * Create the frame.
     */
    public LoginPage(CustomizedJPanel panel) {
        // Frame setup
        initializeLocation();
        
        initializeSize(panel, 900, 600);

        adjustPanelView(panel);

        // Add components to the panel
        addComponentsToPanel(panel);

        // Use the panel as the content pane
        getContentPane().add(panel);

        // Center frame on screen
        setLocationRelativeTo(null);
    }

    
    public void adjustPanelView(CustomizedJPanel panel) {
    	// Set the background image on the panel
        panel.setBackgroundImage("yeniPhoto.jpg",30);

        // Remove initial label and add a new welcome label
        panel.removeLabel();
        panel.addNewLabel("Welcome to the Login Page!");
    }
    /**
     * Initialize the frame properties.
     */
    public void initializeLocation() {
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null); // Using absolute positioning for simplicity
    }

    public void initializeSize(CustomizedJPanel panel, int x, int y) {
        panel.adjustPanelSize(x, y);
        setSize(x, y); // Adjust the size of the frame
    }

    /**
     * Add components (e.g., text fields, button) to the panel.
     */
    private void addComponentsToPanel(CustomizedJPanel panel) {
        // Username label
        JLabel usernameLabel = CustomizedJLabels.messageLabel(" Username:");
        usernameLabel.setBounds(250, 130, 100, 20);
        panel.add(usernameLabel);

        // Username text field
        JTextField textFieldName = CustomizedJLabels.messagedTextField("");
        textFieldName.setBounds(250, 160, 400, 40);
        panel.add(textFieldName);

        // Password label
        JLabel passwordLabel = CustomizedJLabels.messageLabel(" Password:");
        passwordLabel.setBounds(250, 220, 100, 20);
        panel.add(passwordLabel);

        // Password text field
        JPasswordField passwordField = CustomizedJLabels.textPassword();
        passwordField.setBounds(250, 250, 400, 40);
        panel.add(passwordField);

        // Login button
        JButton loginButton = CustomizedJButtons.loginButton(textFieldName, passwordField);
        loginButton.setBounds(350, 330, 200, 50);
        panel.add(loginButton);

        // Sign-in button
        JButton signInButton = CustomizedJButtons.signUpPageButton();
        signInButton.setBounds(350, 400, 200, 50);
        panel.add(signInButton);

        // Shutdown button inside popup menu
        JButton shutDownButton = CustomizedJButtons.shutDownButton();
        
        JButton popButton = CustomizedJButtons.createJPopupButtonsMenu("Menu",COLOR, shutDownButton);
        panel.add(popButton);
    }

    public Page createPage(CustomizedJPanel panel) {
        return new LoginPage(panel);
    }
}
