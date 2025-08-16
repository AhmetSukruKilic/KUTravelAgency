package adminPages;

import java.util.*;
import java.util.Map.Entry;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import customizedGuiObjects.CustomizedJButtons;
import customizedGuiObjects.CustomizedJPanel;
import guiManagment.Page;
import guiManagment.PageDataBase;
import reservation.Reservation;
import users.Admin;
import users.Customer;

public class ViewUserExpensesPage extends JFrame implements Page {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private static final String COLOR = "red";
	private JTable expensesTable; // Tabloyu global olarak tanımlıyoruz
	private DefaultTableModel tableModel; // Tablo modelini güncellenebilir hale getiriyoruz

	public ViewUserExpensesPage(CustomizedJPanel panel, Admin currentUser) {
		// Frame setup
		admin = currentUser;

		initializeLocation();
		initializeSize(panel, 900, 600);

		// Modify the shared panel
		adjustPanelView(panel);

		// Set panel properties
		getContentPane().add(panel);

		// Add components
		addComponentsToPanel(panel);

		// Center frame on screen
		setLocationRelativeTo(null); // Center the frame
	}

	public void adjustPanelView(CustomizedJPanel panel) {
		// Set the background image on the panel
		panel.setBackgroundImage("viewUserStatsPhoto.jpg", 30);
		// Remove initial label and add a new welcome label
		panel.removeLabel();
		panel.addNewLabel("");
	}

	public void initializeLocation() {
		setTitle("View User Expenses Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null); // Using absolute positioning for simplicity
	}

	public void initializeSize(CustomizedJPanel panel, int x, int y) {
		panel.adjustPanelSize(x, y);
		setSize(x, y); // Set size of frame and panel
	}

	private void addComponentsToPanel(CustomizedJPanel panel) {
		// Previous Page Button
		JButton previousPageButton = CustomizedJButtons.previousPageButton(COLOR);
		previousPageButton.setBounds(10, 10, 80, 30);
		if (PageDataBase.isTherePreviousPage())
			panel.add(previousPageButton);

		// Next Page Button
		JButton nextPageButton = CustomizedJButtons.nextPageButton(COLOR);
		nextPageButton.setBounds(100, 10, 80, 30);
		if (PageDataBase.isThereNextPage())
			panel.add(nextPageButton);

		// Menu Bar Button
		JButton viewUserStatsButton = CustomizedJButtons.userStatsPageButton(COLOR);
		JButton shutDownButton = CustomizedJButtons.shutDownButton();
		JButton logOutButton = CustomizedJButtons.logOutButton();
		JButton menuBarButton = CustomizedJButtons.createJPopupButtonsMenu("Menu", COLOR, viewUserStatsButton,
				shutDownButton, logOutButton);
		panel.add(menuBarButton);

		// Sorted Customer Expenses
		LinkedHashMap<Customer, Double> rstSorted = Reservation.sortedExpensesForCustomers();
		populateTable(panel, rstSorted);

		// Add ascending and descending buttons
		JButton ascendingButton = new JButton("Ascending");
		ascendingButton.setBounds(50, 500, 120, 30);
		ascendingButton.addActionListener(e -> {
			LinkedHashMap<Customer, Double> ascendingMap = sortExpenses(rstSorted, true);
			updateTableData(ascendingMap);
		});
		panel.add(ascendingButton);

		JButton descendingButton = new JButton("Descending");
		descendingButton.setBounds(200, 500, 120, 30);
		descendingButton.addActionListener(e -> {
			LinkedHashMap<Customer, Double> descendingMap = sortExpenses(rstSorted, false);
			updateTableData(descendingMap);
		});
		panel.add(descendingButton);
	}

	private void populateTable(CustomizedJPanel panel, LinkedHashMap<Customer, Double> rstSorted) {
		// Convert LinkedHashMap to table data
		String[] columnNames = { "Customer Name", "Total Expense" };
		Object[][] tableData = new Object[rstSorted.size()][2];
		int row = 0;
		for (Entry<Customer, Double> entry : rstSorted.entrySet()) {
			tableData[row][0] = entry.getKey().getName(); // Assuming Customer has getName()
			tableData[row][1] = entry.getValue();
			row++;
		}

		// Create JTable
		tableModel = new DefaultTableModel(tableData, columnNames);
		expensesTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(expensesTable);
		scrollPane.setBounds(50, 70, 800, 400);
		panel.add(scrollPane);
	}

	private LinkedHashMap<Customer, Double> sortExpenses(LinkedHashMap<Customer, Double> expenses, boolean ascending) {
		LinkedHashMap<Customer, Double> rst = Reservation.sortedExpensesForCustomers();

		if (ascending) {
			return rst;
		} else {
			return reverseMap(rst);
		}
	}

	private LinkedHashMap<Customer, Double> reverseMap(LinkedHashMap<Customer, Double> map) {
		LinkedHashMap<Customer, Double> reversedMap = new LinkedHashMap<>();
		List<Entry<Customer, Double>> entryList = new ArrayList<>(map.entrySet());
		Collections.reverse(entryList); // Listeyi ters çevir

		for (Entry<Customer, Double> entry : entryList) {
			reversedMap.put(entry.getKey(), entry.getValue());
		}
		return reversedMap;
	}

	private void updateTableData(LinkedHashMap<Customer, Double> sortedData) {
		tableModel.setRowCount(0); // Clear the table
		for (Entry<Customer, Double> entry : sortedData.entrySet()) {
			tableModel.addRow(new Object[] { entry.getKey().getName(), entry.getValue() });
		}
	}

	public Page createPage(CustomizedJPanel panel) {
		return new ViewUserExpensesPage(panel, admin);
	}
}
