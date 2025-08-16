package guiManagment;

import customerPages.*;
import generalMessages.WarningMessage;
import users.Customer;

public class CustomerDataBase extends PageDataBase{
	
	public static void openCustomerMainPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}
		
		cleanPanel();
		handleCycle();
		
		// Create and show the End Page
		try {
			currentPage = new CustomerMainPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openMakeReservationsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new MakeReservationsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);
	}
	
	
	public static void openShowReservationsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		handleCycle();
		cleanPanel();

		// Create and show the End Page
		try {
			currentPage = new MakeReservationsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		handleCycle();
		currentPage.setVisible(true);
	}

	public static void openReservationHistoryPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new ReservationHistoryPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);
	}

	public static void openCustomReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new CustomReservationPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);		
	}

	public static void openSpecialDealsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new SpecialDealsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
		
	}

	public static void openCancelledReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new CancelledReservationsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
	}

	public static void openActiveReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new ActiveReservationsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
	}

	public static void openEditReservationsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new EditReservationsPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
		
	}

	public static void openCancelReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new CancelReservationPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
		
	}

	public static void openChangeReservationEntitiesPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		// Clear sharedPanel to avoid overlap
		cleanPanel();
		handleCycle();
		// Create and show the End Page
		try {
			currentPage = new ChangeReservationEntitiesPage(sharedPanel, (Customer)currentUser);
		}catch (ClassCastException e) {
			new WarningMessage("DownCasting Error","User is an admin cannot view the customer page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		}catch (Exception e) {
			System.err.println("An error occured in CustomerDataBase");
		}
		
		currentPage.setVisible(true);	
		
	}

	
	
}
