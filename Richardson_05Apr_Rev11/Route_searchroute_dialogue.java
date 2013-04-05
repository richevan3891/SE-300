// The purpose of this code is to provide the mechanism by which a
// user can search for a route in the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 March 2013


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

public class Route_searchroute_dialogue extends JDialog {

	static Route_searchroute_dialogue Test;
	// Initialize static labels
	static private JLabel Routenumlabel = new JLabel("Route Number", SwingConstants.CENTER);
	static private JLabel Carrierlabel = new JLabel("Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Departure Time", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrival Time", SwingConstants.CENTER);

	// Initialize check boxes
	static JRadioButton Checkroutenum = new JRadioButton();
	static JRadioButton CheckdepAP = new JRadioButton();
	static JRadioButton CheckarrAP = new JRadioButton();
	static JRadioButton Checkdeptime = new JRadioButton();
	static JRadioButton Checkarrtime = new JRadioButton();
	static JRadioButton Checkcarrier = new JRadioButton();

	ButtonGroup radioGroup = new ButtonGroup();
	static ArrayList<String[]> routesInfo;

	// Initialize the search route and cancel buttons and panel
	JButton Searchroutebutton = new JButton("Search");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels
	JPanel Choicepanel = new JPanel();

	JPanel Routenumpanel = new JPanel();
	JPanel Routenumcheckpanel = new JPanel();

	JPanel DepAPpanel = new JPanel();
	JPanel DepAPcheckpanel = new JPanel();
	JPanel ArrAPpanel = new JPanel();
	JPanel ArrAPcheckpanel = new JPanel();

	JPanel Deptimepanel = new JPanel();
	JPanel Deptimecheckpanel = new JPanel();
	JPanel Arrtimepanel = new JPanel();
	JPanel Arrtimecheckpanel = new JPanel();

	JPanel Carrierpanel = new JPanel();
	JPanel Carriercheckpanel = new JPanel();

	// Get the screen size
	static Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;

	static String[] airports;
	static String[] routeNums;

	static JComboBox Routenumchoice;
	static JComboBox Carrierchoice;
	static JComboBox DepAPchoice;
	static JComboBox ArrAPchoice;

	static TimeSelectBoxPanel Deptimechoice;
	static TimeSelectBoxPanel Arrtimechoice;

	static String[] Carriers = {"DELTA", "UNITED"};

	static Component resultsDepAPTable;
	public Route_searchroute_dialogue() {

	}
	// Constructor for the dialogue box
	public Route_searchroute_dialogue(String[] Airports, String[] Routenums) {

		airports = Airports;
		routeNums = Routenums;

		radioGroup.add(Checkroutenum);
		radioGroup.add(CheckdepAP);
		radioGroup.add(CheckarrAP);
		radioGroup.add(Checkdeptime);
		radioGroup.add(Checkarrtime);
		radioGroup.add(Checkcarrier);

		Checkroutenum.setSelected(true);

		setModal(true);

		Routenumchoice = new JComboBox(Routenums);

		// Initialize all drop-down menus
		Carrierchoice = new JComboBox(Carriers);
		DepAPchoice = new JComboBox(Airports);
		ArrAPchoice = new JComboBox(Airports);

		Deptimechoice = new TimeSelectBoxPanel();
		Arrtimechoice = new TimeSelectBoxPanel();


		// Try Overall Gridpanel
		Choicepanel.setLayout(new GridLayout(6,3));
		Choicepanel.add(Checkroutenum);
		Checkroutenum.setAlignmentX(RIGHT_ALIGNMENT);
		Choicepanel.add(Routenumlabel);
		Choicepanel.add(Routenumchoice);

		Choicepanel.add(CheckdepAP);
		Choicepanel.add(DepAPlabel);
		Choicepanel.add(DepAPchoice);

		Choicepanel.add(CheckarrAP);
		Choicepanel.add(ArrAPlabel);
		Choicepanel.add(ArrAPchoice);

		Choicepanel.add(Checkdeptime);
		Choicepanel.add(Deptimelabel);
		Choicepanel.add(Deptimechoice);

		Choicepanel.add(Checkarrtime);
		Choicepanel.add(Arrtimelabel);
		Choicepanel.add(Arrtimechoice);

		Choicepanel.add(Checkcarrier);
		Choicepanel.add(Carrierlabel);
		Choicepanel.add(Carrierchoice);

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Searchroutebutton);
		Buttonpanel.add(Cancelbutton);

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);

		// add action listener for when the findPath button is clicked
		Searchroutebutton.addActionListener(new searchRouteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// add item listener for when departure time check clicked
		Checkroutenum.addItemListener(new FindRouteNum());

		// add item listener for when arrival time check clicked
		CheckdepAP.addItemListener(new FindDepAP());

		// add item listener for when arrival time check clicked
		CheckarrAP.addItemListener(new FindArrAP());

		// add item listener for when arrival time check clicked
		Checkdeptime.addItemListener(new FindDepTime());

		// add item listener for when arrival time check clicked
		Checkarrtime.addItemListener(new FindArrTime());

		// add item listener for when carrier clicked
		Checkcarrier.addItemListener(new FindCarrier());

	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports, String[] routes){
		Test = new Route_searchroute_dialogue(airports, routes);
		Test.setTitle("Search Route");							// Rename the title
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
			if (FindRouteNum.routeNumSelected == true) {
				String routeNumString = "";
				int routeNumInt = 0;
				routeNumInt = Routenumchoice.getSelectedIndex();
				routeNumString = routeNums[routeNumInt];
				Object[][] routeNumObject = matchRouteNum(routeNumInt, routeNumString, routeNums, routes);
				Main.updateResultsTable(routeNumObject);
			}
			else if (FindDepAP.depAPSelected == true) {
				String depAPString = "";
				int depAPInt = 0;
				String[][] depAPPaths;
				depAPInt = DepAPchoice.getSelectedIndex();
				depAPString = airports[depAPInt];
				Object[][] depAPObject = matchDepAP(depAPString, routes);
				Main.updateResultsTable(depAPObject);
			}
			else if (FindArrAP.arrAPSelected == true) {
				String arrAPString = "";
				int arrAPInt = 0;
				arrAPInt = ArrAPchoice.getSelectedIndex();
				arrAPString = airports[arrAPInt];
				Object[][] arrAPObject = matchArrAP(arrAPString, routes);
				Main.updateResultsTable(arrAPObject);
			}
			else if (FindDepTime.depTimeSelected == true) {
				String depTimeHrString = "";
				String depTimeMinString = "";
				int depTimeHrInt = 0;
				int depTimeMinInt = 0;

				depTimeHrInt = Deptimechoice.Hourchoice.getSelectedIndex();
				depTimeHrString = Deptimechoice.Hourlist[depTimeHrInt];

				depTimeMinInt = Deptimechoice.Minutechoice.getSelectedIndex();
				depTimeMinString = Deptimechoice.Minutelist[depTimeMinInt];

				Object[][] depTimeObject = depTimeMethod(depTimeHrString, depTimeMinString, routes);
				Main.updateResultsTable(depTimeObject);

			}
			else if (FindArrTime.arrTimeSelected == true) {
				String arrTimeHrString = "";
				String arrTimeMinString = "";
				int arrTimeHrInt = 0;
				int arrTimeMinInt = 0;

				arrTimeHrInt = Arrtimechoice.Hourchoice.getSelectedIndex();
				arrTimeHrString = Arrtimechoice.Hourlist[arrTimeHrInt];

				arrTimeMinInt = Arrtimechoice.Minutechoice.getSelectedIndex();
				arrTimeMinString = Arrtimechoice.Minutelist[arrTimeMinInt];
				
				Object[][] arrTimeObject = arrTimeMethod(arrTimeHrString, arrTimeMinString, routes);
				Main.updateResultsTable(arrTimeObject);
			}
			else if (FindCarrier.carrierSelected == true) {
				int carrierInt = 0;
				String carrierString = "";
				carrierInt = Carrierchoice.getSelectedIndex();
				carrierString = Carriers[carrierInt];
				
				Object[][] carrierObject = carrierMethod(carrierString, routes);
				Main.updateResultsTable(carrierObject);
			}
		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
	// following methods are determining which radio button is selected
	static class FindRouteNum implements ItemListener{
		static boolean routeNumSelected = true;
		public void itemStateChanged(ItemEvent event) {
			if (Checkroutenum.isSelected()) {
				routeNumSelected = true;
			}
			else 
				routeNumSelected = false;
		}
	}
	static class FindDepAP implements ItemListener{
		static boolean depAPSelected = false;
		public void itemStateChanged(ItemEvent event) {
			if (CheckdepAP.isSelected()) {
				depAPSelected = true;
			}
			else
				depAPSelected = false;
		}
	}
	static class FindArrAP implements ItemListener{
		static boolean arrAPSelected = false;
		public void itemStateChanged(ItemEvent event) {
			if (CheckarrAP.isSelected()) {
				arrAPSelected = true;
			}
			else
				arrAPSelected = false;
		}
	}
	static class FindDepTime implements ItemListener{
		static boolean depTimeSelected = false;
		public void itemStateChanged(ItemEvent event) {
			if (Checkdeptime.isSelected()) {
				depTimeSelected = true;
			}
			else
				depTimeSelected = false;
		}
	}
	static class FindArrTime implements ItemListener{
		static boolean arrTimeSelected = false;
		public void itemStateChanged(ItemEvent event) {
			if (Checkarrtime.isSelected()) {
				arrTimeSelected = true;
			}
			else
				arrTimeSelected = false;
		}
	}
	static class FindCarrier implements ItemListener{
		static boolean carrierSelected = false;
		public void itemStateChanged(ItemEvent event) {
			if (Checkcarrier.isSelected()) {
				carrierSelected = true;
			}
			else
				carrierSelected = false;
		}
	}
	// find routes based on route number, selected by user.
	public static Object[][] matchRouteNum(int routeNumInt, String routeNumString, String[] routeNums, String[][] routes) {
		String[] pathFound = null;
		Object[][] routesData = new Object[1][7];
		String[][] routesDataStr = new String[1][7];
		String historyText = "";
		for (int i = 0; i < routes.length; i++) {
			if (routeNumString.equals(routes[i][0])) {
				pathFound = routes[i];
			}
		}
		for (int i = 0; i < pathFound.length; i++) {
			routesDataStr[0][i] = pathFound[i];
		}
		historyText = "Your search returned one result, which is displayed in the search results table.";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])routesDataStr;
		return routesData;
	}
	// find routes based on departure airport, selected by user.
	public static Object[][] matchDepAP(String depAPString, String[][] routes) {
		int count = 0;
		Object[][] routesData = new Object[0][7];
		String historyText = "";

		for (int i = 0; i < routes.length; i++) {
			if (routes[i][2].equals((depAPString))) {
				count = count + 1;
			}
		}
		String[][] depAPPaths = new String[count][7];
		int depCount = 0;
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][2].equals((depAPString))) {
				depAPPaths[depCount] = routes[i];
				depCount = depCount + 1;
			}
		}
		historyText = "There are " + count + " routes that leave from the " + depAPString + " airport.";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])depAPPaths;
		return routesData;
	}
	// find routes based on arrival airport, selected by user.
	public static Object[][] matchArrAP(String arrAPString, String[][] routes) {
		int count = 0;
		Object[][] routesData = new Object[0][7];
		String historyText = "";

		for (int i = 0; i < routes.length; i++) {
			if (routes[i][4].equals((arrAPString))) {
				count = count + 1;
			}
		}
		String[][] arrAPPaths = new String[count][7];
		int arrCount = 0;
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][4].equals((arrAPString))) {
				arrAPPaths[arrCount] = routes[i];
				arrCount = arrCount + 1;
			}
		}
		historyText = "There are " + count + " routes that arrive at the " + arrAPString + " airport.";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])arrAPPaths;
		return routesData;
	}
	// find routes based on departure time selected by user
	static Object[][] depTimeMethod(String depTimeHrString, String depTimeMinString, String[][] routes) {
		int count = 0;
		int timeCount = 0;
		String historyText = "";
		Object[][] routesData = new Object[0][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][3].equals(depTimeHrString + depTimeMinString)) {
				count = count + 1;
			}
		}
		String[][] depTimeString = new String[count][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][3].equals(depTimeHrString + depTimeMinString)) {
				depTimeString[timeCount] = routes[i];
				timeCount = timeCount + 1;
			}
		}
		historyText = "There are " + count + " routes listed that depart at " + depTimeHrString + ":" + depTimeMinString + ".";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])depTimeString;
		return routesData;
	}
	// find routes based on arrival time selected by user
	public static Object[][] arrTimeMethod(String arrTimeHrString, String arrTimeMinString, String[][] routes) {
		int count = 0;
		int timeCount = 0;
		String historyText = "";
		Object[][] routesData = new Object[0][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][5].equals(arrTimeHrString + arrTimeMinString)) {
				count = count + 1;
			}
		}
		String[][] arrTimeString = new String[count][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][5].equals(arrTimeHrString + arrTimeMinString)) {
				arrTimeString[timeCount] = routes[i];
				timeCount = timeCount + 1;
			}
		}
		historyText = "There are " + count + " routes listed that arrive at " + arrTimeHrString + ":" + arrTimeMinString + ".";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])arrTimeString;
		return routesData;
	}
	// finding the routes from a given carrier, selected by the user
	public static Object[][] carrierMethod(String carrierString, String[][] routes) {
		int count = 0;
		int carrierCount = 0;
		String historyText = "";
		Object[][] routesData = new Object[0][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][1].equals(carrierString)) {
				count = count + 1;
			}
		}
		String[][] carrierResults = new String[count][7];
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][1].equals(carrierString)) {
				carrierResults[carrierCount] = routes[i];
				carrierCount = carrierCount + 1;
			}
		}
		historyText = "There are " + count + " routes listed that are flying with " + carrierString + ".";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])carrierResults;
		return routesData;
	}
}