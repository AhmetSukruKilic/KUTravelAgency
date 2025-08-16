package guiManagment;

import adminPages.*;
import generalMessages.WarningMessage;
import users.Admin;

public class AdminDataBase extends PageDataBase {
	public static void openAdminMainPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new AdminMainPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openViewUserStatsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new ViewUserStatsPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);

	}

	public static void openEditAdminReservationsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new EditAdminReservationsPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);

	}

	public static void openViewUserExpensesPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new ViewUserExpensesPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openViewUsersReservationsPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new ViewUsersReservationsPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openCancelReservationForCustomersPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new CancelReservationForCustomersPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
		
	}

	public static void openCreateTravelPackagePage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new CreateTravelPackagePage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openChangeEntitiesForCustomersPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new ChangeEntitiesForCustomersPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
		
	}

	public static void openMakeReservationForCustomerPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new MakeReservationForCustomerPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
		
	}

	public static void openMakeCustomReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new MakeCustomReservationPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
		
	}

	public static void openMakeDiscountedReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new MakeDiscountedReservationPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
	}

	public static void openMakeSingleEntityReservationPage() {
		if (currentPage != null) {
			previouses.push(currentPage);
			currentPage.dispose();
		}

		cleanPanel();
		handleCycle();

		// Create and show the End Page
		try {
			currentPage = new MakeSingleEntityReservationPage(sharedPanel, (Admin) currentUser);
		} catch (ClassCastException e) {
			new WarningMessage("DownCasting Error",
					"User is an customer cannot view the admin page, downcasting will not work!");
			LogSignDatabase.openLoginPage();
		} catch (Exception e) {
			System.err.println("An error occured in AdminDataBase");
		}

		currentPage.setVisible(true);
		
	}
	

}
