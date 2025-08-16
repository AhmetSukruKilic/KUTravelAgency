package customizedGuiObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;

public class CustomizedJLabels {

    private static final Font MODERN_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Color PLACEHOLDER_COLOR = new Color(160, 160, 160);
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final Border CUSTOM_BORDER = BorderFactory.createLineBorder(new Color(200, 200, 200), 1);

    public static JPasswordField textPassword() {
        JPasswordField passwordField = new JPasswordField("");
        goodlooking(passwordField);
        
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('*');
                    passwordField.setForeground(DEFAULT_TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText("");
                    passwordField.setEchoChar((char) 0);
                    passwordField.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
        return passwordField;
    }

    public static JTextField messagedTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        goodlooking(textField);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(DEFAULT_TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(PLACEHOLDER_COLOR);
                }
            }
        });
        return textField;
    }

    public static JLabel messageLabel(String message) {
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }
    
    private static void goodlooking(JTextComponent j) {
    	j.setFont(MODERN_FONT);
        j.setForeground(PLACEHOLDER_COLOR);
        j.setBorder(CUSTOM_BORDER);
    }

	public static JLabel messageLabelWhite(String message) {
        JLabel label = new JLabel(message);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.WHITE); // Set text color to white
        return label;
	}
}
