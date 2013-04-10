
import java.awt.Component;
import java.awt.Dimension;  
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Airport_Route_Search extends JDialog {

	static Airport_Route_Search Test;
	static ArrayList<String[]> routesInfo;
	// Initialize static labels
	static private JLabel airPortLabel = new JLabel("Find all routes from the following airport: ", SwingConstants.CENTER);

	// Initialize the search route and cancel buttons and panel
	JButton Searchroutebutton = new JButton("Find Routes");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels
	JPanel Choicepanel = new JPanel();

	JPanel airPortPanel = new JPanel();

	// Get the screen size
	static Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;

	static String[] airports;
	static String[] routeNums;

	static JComboBox airPortChoice;

	static Component resultsDepAPTable;
	public Airport_Route_Search() {

	}
	// Constructor for the dialogue box
	public Airport_Route_Search(String[] Airports, String[] Routenums) {

		airports = Airports;
		routeNums = Routenums;

		airPortChoice = new JComboBox(airports);
		airPortPanel.setLayout(new FlowLayout());
		airPortPanel.add(airPortLabel);
		airPortPanel.add(airPortChoice);

		airPortPanel.add(Searchroutebutton);
		airPortPanel.add(Cancelbutton);

		add(airPortPanel);		

		// add action listener for when the findPath button is clicked
		Searchroutebutton.addActionListener(new searchRouteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports, String[] routes){
		Test = new Airport_Route_Search(airports, routes);
		Test.setTitle("Find Routes from Airport");				// Rename the title
		Test.setSize((width * 2 / 10), (height * 4 / 10));		// Set the size
		Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	
	}
	static class searchRouteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String historyText = "";
			routesInfo = Main.routesInfo;
			Object[][] data = new String[0][7];
			try { 
				data = Main.convertArrayListTo2DArray(routesInfo, 1);		
			}
			catch (NullPointerException e1) { //if there are any errors in input.txt
				historyText = "There are errors in the input.txt file.";
				Main.historyArea.append(historyText + "\n");
			}
			String[][] routes = (String[][])data;
			String depAPString = "";
			int depAPInt = 0;
			String[][] depAPPaths;
			depAPInt = airPortChoice.getSelectedIndex();
			depAPString = airports[depAPInt];
			Object[][] depAPObject = matchDepAP(depAPString, routes);
			Main.updateResultsTable(depAPObject);

		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
	// find routes based on departure airport, selected by user.
	public static Object[][] matchDepAP(String depAPString, String[][] routes) {
		int count = 0;
		Object[][] routesData = new Object[0][7];
		String historyText = "";

		for (int i = 0; i < routes.length; i++) {
			if ((routes[i][2].equals((depAPString)) || (routes[i][4].equals(depAPString))) && routes[i][7].equals("true")) {
				count = count + 1;
			}
		}
		String[][] depAPPaths = new String[count][7];
		int depCount = 0;
		for (int i = 0; i < routes.length; i++) {
			if ((routes[i][2].equals((depAPString)) || (routes[i][4].equals(depAPString))) && routes[i][7].equals("true")) {
				depAPPaths[depCount] = routes[i];
				depCount = depCount + 1;
			}
		}
		historyText = "There are " + count + " routes that are serviced at the " + depAPString + " airport that are open for business.";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])depAPPaths;
		return routesData;
	}
	// find routes based on arrival airport, selected by user.

}