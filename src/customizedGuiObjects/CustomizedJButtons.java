package customizedGuiObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import fileIOHandling.WriteNewUser;
import generalMessages.*;
import guiManagment.*;
import users.Customer;
import users.User;

public class CustomizedJButtons {

	public static JButton loginButton(JTextField name, JPasswordField password) {
		JButton loginButton = new JButton("Log In");
		goodLookingButtonGold(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String passString = new String(password.getPassword());
					User user = User.isValidUser(name.getText(), passString);
					if (user != null) {
						PageDataBase.userLoggedIn(user);
					}

				} catch (NullPointerException nullE) {
					new WarningMessage("Invalid Input", "Need to type a name and a password.");
				}
			}
		});

		return loginButton;
	}

	// signInButton
	public static JButton signUpButton(JTextField name, JPasswordField password1, JPasswordField password2) {
		JButton signInButton = new JButton("Sign Up");
		goodLookingButtonGold(signInButton);
		signInButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String passString1 = new String(password1.getPassword());
					String passString2 = new String(password2.getPassword());
					if (!passString1.equals(passString2)) {
						new WarningMessage("Invalid Input", "Passwords are different from each other.");
						return;
					}
					if (User.isValidNewUser(name.getText(), passString1)) {
						new Customer(name.getText(), passString1);
						WriteNewUser.write(name.getText(), passString1, "customer");
						LogSignDatabase.openLoginPage();
					}
				} catch (NullPointerException nullE) {
					new WarningMessage("Invalid Input", "Need to type a name and a password.");
				}
			}
		});

		return signInButton;
	}

	private static void goodLookingButtonBlue(JButton button) {
		button.setBorder(BorderFactory.createRaisedBevelBorder()); // Adds a raised bevel for styling
		button.setFocusPainted(false); // Removes the focus border for a cleaner look
		button.setBackground(new Color(70, 130, 180)); // Steel blue background
		button.setForeground(Color.WHITE); // White text for contrast
		button.setFont(new Font("Arial", Font.BOLD, 16));
	}

	private static void goodLookingButtonGold(JButton button) {
		button.setBorder(BorderFactory.createRaisedBevelBorder()); // Adds a raised bevel for styling
		button.setFocusPainted(false); // Removes the focus border for a cleaner look
		button.setBackground(new Color(255, 215, 0)); // Gold background
		button.setForeground(new Color(50, 50, 50)); // Dark gray text for better contrast
		button.setFont(new Font("Arial", Font.BOLD, 16)); // Bold Arial font
	}

	private static void goodLookingButtonBlack(JButton button) {
		System.out.println("wrong color");
		button.setBorder(BorderFactory.createRaisedBevelBorder()); // Adds a raised bevel for styling
		button.setFocusPainted(false); // Removes the focus border for a cleaner look
		button.setBackground(new Color(0, 0, 0)); // Gold background
		button.setForeground(new Color(50, 50, 50)); // Dark gray text for better contrast
		button.setFont(new Font("Arial", Font.BOLD, 16)); // Bold Arial font
	}

	private static void goodLookingButtonRed(JButton button) {
		button.setBorder(BorderFactory.createRaisedBevelBorder());
		button.setFocusPainted(false);
		button.setBackground(Color.RED);
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
	}

	public static void goodLookingButton(JButton button, String color) {
		if (color.equalsIgnoreCase("blue"))
			goodLookingButtonBlue(button);
		else if (color.equalsIgnoreCase("gold"))
			goodLookingButtonGold(button);
		else if (color.equalsIgnoreCase("red"))
			goodLookingButtonRed(button);
		else
			goodLookingButtonBlack(button);
	}

	// nextPageButton
	public static JButton nextPageButton(String color) {
		JButton nextPageButton = new JButton("->");
		nextPageButton.setBounds(100, 10, 80, 30);
		goodLookingButton(nextPageButton, color);

		nextPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreviousNextDataBase.nextPage();
			}
		});

		return nextPageButton;
	}

	// previousPageButton
	public static JButton previousPageButton(String color) {
		JButton previousPageButton = new JButton("<-");
		goodLookingButton(previousPageButton, color);
		previousPageButton.setBounds(10, 10, 80, 30);
		previousPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PreviousNextDataBase.previousPage();
			}
		});

		return previousPageButton;
	}

	public static JButton shutDownButton() {
		JButton shuwDownButton = new JButton("Shut Down");
		shuwDownButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PageDataBase.shutDown();
			}
		});
		return shuwDownButton;
	}

	public static JButton signUpPageButton() {
		JButton signInPageButton = new JButton("Sign Up");
		goodLookingButtonGold(signInPageButton);
		signInPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogSignDatabase.openRegisterPage();
			}
		});

		return signInPageButton;
	}

	public static JButton loginPageButton() {
		JButton loginPageButton = new JButton("Login Page");
		loginPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogSignDatabase.openLoginPage();
			}
		});

		return loginPageButton;
	}

	public static JButton logOutButton() {
		JButton logOutButton = new JButton("Log Out");
		logOutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LogSignDatabase.openLoginPage();
				;
			}
		});
		return logOutButton;
	}

	public static JButton createJPopupButtonsMenu(String message, String color, JButton... buttons) {
		JButton mainButton = new JButton(message);
		JPopupMenu popupMenu = new JPopupMenu();
		mainButton.setBounds(800, 0, 100, 50);
		goodLookingButton(mainButton, color);

		for (JButton button : buttons) {
			JMenuItem menuItem = new JMenuItem(button.getText());
			menuItem.addActionListener(button.getActionListeners()[0]); // Copy action listener from the button
			popupMenu.add(menuItem);
		}

		mainButton
				.addActionListener(e -> popupMenu.show(mainButton, mainButton.getWidth() / 5, mainButton.getHeight()));

		return mainButton;
	}

	public static JButton makeReservationsPageButton(String color) {
		JButton makeReservationsPageButton = new JButton("Make Reservation");
		goodLookingButton(makeReservationsPageButton, color);
		makeReservationsPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openMakeReservationsPage();
			}
		});

		return makeReservationsPageButton;
	}

	public static JButton customerMainPageButton(String color) {
		JButton customerMainPageButton = new JButton("Customer Main Page");
		customerMainPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openCustomerMainPage();
				;
			}
		});
		return customerMainPageButton;
	}

	public static JButton viewReservationsHistoryPageButton(String color) {
		JButton customerMainPageButton = new JButton("Reservation History");
		goodLookingButton(customerMainPageButton, color);
		customerMainPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openReservationHistoryPage();
				;
			}
		});
		return customerMainPageButton;
	}

	public static JButton customReservationPageButton(String color) {
		JButton customReservationPageButton = new JButton("Create Reservation");
		goodLookingButton(customReservationPageButton, color);
		customReservationPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openCustomReservationPage();
				;
			}
		});
		return customReservationPageButton;
	}

	public static JButton specialDealsPagePageButton(String color) {
		JButton customerMainPageButton = new JButton("Special Deals");
		goodLookingButton(customerMainPageButton, color);
		customerMainPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openSpecialDealsPage();
				;
			}
		});
		return customerMainPageButton;
	}

	public static JButton cancelledReservationPageButton(String color) {
		JButton cancelledReservationPageButton = new JButton("Cancelled Reservations");
		goodLookingButton(cancelledReservationPageButton, color);
		cancelledReservationPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openCancelledReservationPage();
				;
			}
		});
		return cancelledReservationPageButton;
	}

	public static JButton activeReservationPageButton(String color) {
		JButton activeReservationPageButton = new JButton("Active Reservations");
		goodLookingButton(activeReservationPageButton, color);
		activeReservationPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openActiveReservationPage();
				;
			}
		});
		return activeReservationPageButton;
	}

	public static JButton editReservationsPageButton(String color) {
		JButton editReservationsPageButton = new JButton("Edit Reservations");
		goodLookingButton(editReservationsPageButton, color);
		editReservationsPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openEditReservationsPage();
				;
			}
		});
		return editReservationsPageButton;
	}

	public static JButton cancelReservationPageButton(String color) {
		JButton cancelReservationPageButton = new JButton("Cancel Reservation");
		goodLookingButton(cancelReservationPageButton, color);
		cancelReservationPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openCancelReservationPage();
				;
			}
		});
		return cancelReservationPageButton;
	}

	public static JButton changeReservationsEntitiesPageButton(String color) {
		JButton changeReservationsEntitiesPageButton = new JButton("Edit Entities");
		goodLookingButton(changeReservationsEntitiesPageButton, color);
		changeReservationsEntitiesPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CustomerDataBase.openChangeReservationEntitiesPage();
				
			}
		});
		return changeReservationsEntitiesPageButton;
	}

	public static JButton userStatsPageButton(String color) {
		JButton userStatsPageButton = new JButton("View User Stats");
		goodLookingButton(userStatsPageButton, color);
		userStatsPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openViewUserStatsPage();
				
			}
		});
		return userStatsPageButton;
	}

	public static JButton editAdminReservationsButton(String color) {
		JButton userStatsPageButton = new JButton("Edit Reservations");
		goodLookingButton(userStatsPageButton, color);
		userStatsPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openEditAdminReservationsPage();
				
			}
		});
		return userStatsPageButton;
	}

	public static JButton viewUserExpensesButton(String color) {
		JButton userStatsPageButton = new JButton("View User Expenses");
		goodLookingButton(userStatsPageButton, color);
		userStatsPageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openViewUserExpensesPage();
				
			}
		});
		return userStatsPageButton;
	}

	public static JButton viewUsersReservationsButton(String color) {
		JButton viewUsersReservationsButton = new JButton("View Reservations");
		goodLookingButton(viewUsersReservationsButton, color);
		viewUsersReservationsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openViewUsersReservationsPage();
				
			}
		});
		return viewUsersReservationsButton;
	}

	public static JButton cancelReservationForCustomersButton(String color) {
		JButton cancelReservationForCustomersButton = new JButton("Cancel Reservations");
		goodLookingButton(cancelReservationForCustomersButton, color);
		cancelReservationForCustomersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openCancelReservationForCustomersPage();
				
			}
		});
		return cancelReservationForCustomersButton;
	}

	public static JButton createTravelPackageButton(String color) {
		JButton createTravelPackageButton = new JButton("Create Travel Package");
		goodLookingButton(createTravelPackageButton, color);
		createTravelPackageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openCreateTravelPackagePage();
				
			}
		});
		return createTravelPackageButton;
	}

	public static JButton changeEntitiesForCustomersButton(String color) {
		JButton changeEntitiesForCustomersButton = new JButton("Edit Entities Reservations");
		goodLookingButton(changeEntitiesForCustomersButton, color);
		changeEntitiesForCustomersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openChangeEntitiesForCustomersPage();
				
			}
		});
		return changeEntitiesForCustomersButton;
	}

	public static JButton makeReservationForCustomerButton(String color) {
		JButton makeReservationForCustomerButton = new JButton("Make Reservations");
		goodLookingButton(makeReservationForCustomerButton, color);
		makeReservationForCustomerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openMakeReservationForCustomerPage();
				
			}
		});
		return makeReservationForCustomerButton;
	}

	public static JButton makeCustomReservationButton(String color) {
		JButton makeCustomReservationButton = new JButton("Make Custom");
		goodLookingButton(makeCustomReservationButton, color);
		makeCustomReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openMakeCustomReservationPage();
				
			}
		});
		return makeCustomReservationButton;
	}
 
	public static JButton makeSingleEntityReservationButton(String color) {
		JButton makeSingleEntityReservationButton = new JButton("Make SingleEntity");
		goodLookingButton(makeSingleEntityReservationButton, color);
		makeSingleEntityReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openMakeSingleEntityReservationPage();
			}
		});
		return makeSingleEntityReservationButton;
	}

	public static JButton makeDiscountedReservationButton(String color) {
		JButton makeCustomReservationButton = new JButton("Make Discounted");
		goodLookingButton(makeCustomReservationButton, color);
		makeCustomReservationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminDataBase.openMakeDiscountedReservationPage();
			}
		});
		return makeCustomReservationButton;
	}
	
	

}
