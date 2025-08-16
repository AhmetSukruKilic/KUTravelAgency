package guiManagment;

import userEntryPages.LoginPage;
import userEntryPages.RegisterPage;

public class LogSignDatabase extends PageDataBase{
	
	private LogSignDatabase() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This class cannot be instantiated.");
	}
	
	public static void openLoginPage() {
		// Dispose the current frame if it exists
		if (currentPage != null) {
			currentPage.dispose();
		}
		// Clear sharedPanel to avoid overlap
		cleanPanel();
		
		// Create and show the Login Page
		currentPage = new LoginPage(sharedPanel);
		currentPage.setVisible(true);
	}
	
	
	// Open the Sign in Page
	public static void openRegisterPage(){
		if (currentPage != null) {
			currentPage.dispose();
		}
		
		// Clear sharedPanel to avoid overlap
		cleanPanel();

		// Create and show the End Page
		currentPage = new RegisterPage(sharedPanel);
		currentPage.setVisible(true);
	}
}
