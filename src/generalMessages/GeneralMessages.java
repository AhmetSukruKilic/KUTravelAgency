package generalMessages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public abstract class GeneralMessages {
	protected GeneralMessages(String infoLabel, String infoMessage, String path, int a, int b) {
        JFrame frame = new JFrame(infoLabel);
        frame.setSize(330, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null); // Use manual positioning

        // Set frame icon
        if (path != null && !path.isEmpty()) {
            ImageIcon icon = new ImageIcon(path);
            frame.setIconImage(icon.getImage());
        }

        // Create and position the message label
        JLabel messageLabel = new JLabel(infoMessage, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        messageLabel.setForeground(new Color(50, 50, 50));
        messageLabel.setBounds(20, 30, 290, 30); // Set position and size
        frame.add(messageLabel);

        // Create and position the "OK" button
        JButton okButton = new JButton("OK");
        okButton.setFocusPainted(false);
        okButton.setBounds(125, 120, 80, 30); // Set position and size
        okButton.addActionListener(e -> frame.dispose());
        frame.add(okButton);

        // Place frame slightly to the right of the center
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = Math.max(0, (int) (screenSize.getWidth() / 2) + a - frame.getWidth() / 2);
        int y = Math.max(0, (int) (screenSize.getHeight() / 2) + b - frame.getHeight() / 2);
        frame.setLocation(x, y);

        // Make the frame visible
        frame.setVisible(true);

        // Timer to close the frame after 3 seconds
        Timer timer = new Timer(3000, e -> frame.dispose());
        timer.setRepeats(false);
        timer.start();
	}
}
