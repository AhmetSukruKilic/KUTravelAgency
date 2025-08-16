package guiManagment;
import customizedGuiObjects.CustomizedJPanel;

public interface Page {
	void adjustPanelView(CustomizedJPanel panel);
	void initializeLocation();
	void initializeSize(CustomizedJPanel panel, int x, int y);
	Page createPage(CustomizedJPanel panel);
	void dispose();
	void setVisible(boolean b);
}

