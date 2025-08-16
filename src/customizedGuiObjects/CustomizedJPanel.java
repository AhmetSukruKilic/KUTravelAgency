
package customizedGuiObjects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class CustomizedJPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private JLabel label; // Label to display text
    private Image backgroundImage; // Background image for the panel
    private BufferedImage blurredImage; // Blurred version of the background image

    // Constructor
    public CustomizedJPanel() {
        setLayout(null); // Use no layout manager
        setBackground(Color.LIGHT_GRAY); // Default background
        setBounds(0, 0, 800, 600); // Default size for the panel

        // Initialize the label with a modern style
        label = CustomizedJLabels.messageLabel("");
        label.setBounds(100, 10, 200, 30);
        label.setForeground(DEFAULT_TEXT_COLOR);

        add(label);
    }

    // Method to set the background image and blur it
    public void setBackgroundImage(String imagePath, double n) {
        backgroundImage = new ImageIcon(imagePath).getImage();
        blurredImage = blurImage(toBufferedImage(backgroundImage),n); // Blur the image
        repaint(); // Trigger repaint
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blurredImage != null) {
            g.drawImage(blurredImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Remove the label from the panel
    public void removeLabel() {
        if (label != null) {
            remove(label);
            label = null;
            revalidate();
            repaint();
        }
    }

    // Add a new label with modern styling
    public void addNewLabel(String text) {
        if (label == null) {
            label = CustomizedJLabels.messageLabel(text);
            add(label);
        }
        label.setText(text);

        // Position label at the top-center of the panel
        int labelWidth = 300;
        int labelHeight = 30;
        int panelWidth = getWidth();
        int x = (panelWidth - labelWidth) / 2;
        int y = 20;
        label.setForeground(DEFAULT_TEXT_COLOR);

        label.setBounds(x + 50, y, labelWidth, labelHeight);

        revalidate();
        repaint();
    }

    // Adjust the size of the panel dynamically
    public void adjustPanelSize(int x, int y) {
        setBounds(0, 0, x, y);
        if (label != null) {
            addNewLabel(label.getText()); // Reposition the label
        }
    }

    // Helper method: Convert Image to BufferedImage
    private BufferedImage toBufferedImage(Image img) {
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bimage.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return bimage;
    }

    // Helper method: Apply a blur filter to a BufferedImage
    private BufferedImage blurImage(BufferedImage image, double n) {
        // Define a 3x3 blur kernel
        float kernelValue = 1f / (float) n; 
        float[] blurKernel = {
                kernelValue, kernelValue, kernelValue, kernelValue, kernelValue,
                kernelValue, kernelValue, kernelValue, kernelValue, kernelValue,
                kernelValue, kernelValue, kernelValue, kernelValue, kernelValue,
                kernelValue, kernelValue, kernelValue, kernelValue, kernelValue,
                kernelValue, kernelValue, kernelValue, kernelValue, kernelValue
        };
        Kernel kernel = new Kernel(5, 5, blurKernel);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);

        // Create a new blurred image
        BufferedImage blurredImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        convolveOp.filter(image, blurredImage);

        return blurredImage;
    }
}
