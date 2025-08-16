package guiManagment;

import java.util.Stack;
import fileIOHandling.*;
import generalMessages.*;
import users.*;
import customizedGuiObjects.CustomizedJPanel;


public abstract class PageDataBase {
	protected static final CustomizedJPanel sharedPanel = new CustomizedJPanel(); // Shared panel instance
	protected static Page currentPage; // The currently active frame
	protected static Stack<Page> nexts = new Stack<Page>();
	protected static Stack<Page> previouses = new Stack<Page>();
	protected static User currentUser; 
	

	protected PageDataBase() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}

	public static void run() {
		readAllStartingFiles();
		LogSignDatabase.openLoginPage();
		
	}

	private static void readAllStartingFiles() {
		ReadUsers.read();
		ReadTaxis.read();
		ReadHotels.read();
		ReadFlights.read();
		ReadAdminTravelPackages.read();
		ReadUserLogs.read();
	}

	public static void shutDown() {
		clearWebHistory();
		cleanPanel();
		currentPage.dispose();
	}

	
	private static void clearWebHistory() {
		currentUser = null;
		previouses.clear();
		nexts.clear();
	}

	protected static void handleCycle() {
		try {
			nexts.clear();
		}
		catch (Throwable e) {
			new WarningMessage("nexts error", "an error occured in nexts stack" + e);
		}
	}
	
	protected static void cleanPanel() {
		sharedPanel.removeAll();
		sharedPanel.revalidate();
		sharedPanel.repaint();
	}
	
	
	public static void userLoggedIn(User user) {
		clearWebHistory();
		currentUser = user;
		if (currentUser.isAdmin()) {
			AdminDataBase.openAdminMainPage();
			return;
		}
		else if (currentUser.isCustomer()) {
			CustomerDataBase.openCustomerMainPage();
			return;
		}

		new WarningMessage("Error no such class", "No such class in PageDataBase.userLoggedIn");
	}
	

	/**
	 * @return the currentUser
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * @return the nexts
	 */
	public static Stack<Page> getNexts() {
		return nexts;
	}

	/**
	 * @return the previouses
	 */
	public static Stack<Page> getPreviouses() {
		return previouses;
	}
	
	
	public static boolean isTherePreviousPage() {
		return !previouses.isEmpty();
	}
	
	public static boolean isThereNextPage() {
		return !nexts.isEmpty();
	}

}
