// The purpose of this code is to provide the mechanism by which a
// user can add a route to the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 March 2013


import java.awt.Dialog;
import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

public class Route_addroute_dialogue extends JDialog {

	Main mainClass = new Main();
	static Route_addroute_dialogue Test;
	// Initialize static labels
	static private JLabel Routenumlabel = new JLabel("Route Number", SwingConstants.CENTER);
	static private JLabel Carrierlabel = new JLabel("Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Departure Time", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrival Time", SwingConstants.CENTER);
	static private JLabel Pricelabel = new JLabel("Dollars", SwingConstants.CENTER);
	static private JLabel Pricecentslabel = new JLabel("Cents", SwingConstants.CENTER);

	// Initialize the add route and cancel buttons and panel
	JButton Addroutebutton = new JButton("Add Route");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels
	JPanel Choicepanel = new JPanel();
	JPanel Routenumpanel = new JPanel();
	JPanel Carrierpanel = new JPanel();
	JPanel DepAPpanel = new JPanel();
	JPanel Deptimepanel = new JPanel();
	JPanel ArrAPpanel = new JPanel();
	JPanel Arrtimepanel = new JPanel();
	JPanel Pricepanel = new JPanel();
	JPanel Pricecentspanel = new JPanel();

	// Get the screen size
	Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;

	JFormattedTextField Routenumtxt;
	JFormattedTextField Pricetxt;
	JFormattedTextField Pricecentstxt;

	JComboBox Carrierchoice;
	JComboBox DepAPchoice;
	JComboBox ArrAPchoice;

	String[] airports;
	String[] routeNums;

	TimeSelectBoxPanel Deptimechoice;
	TimeSelectBoxPanel Arrtimechoice;

	// Constructor for the dialogue box
	public Route_addroute_dialogue(String[] Airports, String[] RouteNums) {
		airports = Airports;
		routeNums = RouteNums;
		setModal(true);

		String[] Carriers = {"Delta", "United"};

		// Initialize all input boxes and drop-down menus
		// route text box
		Routenumtxt = new JFormattedTextField();
		MaskFormatter routeNumMask = null;
		try {
			routeNumMask = new MaskFormatter("###");
		} catch (Exception e) {

		}
		routeNumMask.install(Routenumtxt);
		// dollars text box
		Pricetxt = new JFormattedTextField("123");
		MaskFormatter Dollarstxt = null;
		try {
			Dollarstxt = new MaskFormatter("###");
		} catch (Exception e) {

		}
		Dollarstxt.install(Pricetxt);
		// cents text box
		Pricecentstxt = new JFormattedTextField("45");
		MaskFormatter Centstxt = null;
		try {
			Centstxt = new MaskFormatter("##");
		} catch (Exception e) {

		}
		Centstxt.install(Pricecentstxt);

		Carrierchoice = new JComboBox(Carriers);
		DepAPchoice = new JComboBox(Airports);
		ArrAPchoice = new JComboBox(Airports);

		Deptimechoice = new TimeSelectBoxPanel();
		Arrtimechoice = new TimeSelectBoxPanel();

		// Combine choice fields with their appropriate labels in panels
		Routenumpanel.add(Routenumlabel);
		Routenumpanel.add(Routenumtxt);
		Routenumpanel.setLayout(new BoxLayout(Routenumpanel, BoxLayout.Y_AXIS));
		Routenumpanel.setAlignmentY(CENTER_ALIGNMENT);

		Carrierpanel.add(Carrierlabel);
		Carrierpanel.add(Carrierchoice);
		Carrierpanel.setLayout(new BoxLayout(Carrierpanel, BoxLayout.Y_AXIS));
		Carrierpanel.setAlignmentY(CENTER_ALIGNMENT);

		DepAPpanel.add(DepAPlabel);
		DepAPpanel.add(DepAPchoice);
		DepAPpanel.setLayout(new BoxLayout(DepAPpanel, BoxLayout.Y_AXIS));
		DepAPpanel.setAlignmentY(CENTER_ALIGNMENT);

		Deptimepanel.add(Deptimelabel);
		Deptimepanel.add(Deptimechoice);
		Deptimepanel.setLayout(new BoxLayout(Deptimepanel, BoxLayout.Y_AXIS));
		Deptimepanel.setAlignmentY(CENTER_ALIGNMENT);

		ArrAPpanel.add(ArrAPlabel);
		ArrAPpanel.add(ArrAPchoice);
		ArrAPpanel.setLayout(new BoxLayout(ArrAPpanel, BoxLayout.Y_AXIS));
		ArrAPpanel.setAlignmentY(CENTER_ALIGNMENT);

		Arrtimepanel.add(Arrtimelabel);
		Arrtimepanel.add(Arrtimechoice);
		Arrtimepanel.setLayout(new BoxLayout(Arrtimepanel, BoxLayout.Y_AXIS));
		Arrtimepanel.setAlignmentY(CENTER_ALIGNMENT);

		Pricepanel.add(Pricelabel);
		Pricepanel.add(Pricetxt);
		Pricepanel.setLayout(new BoxLayout(Pricepanel, BoxLayout.Y_AXIS));
		Pricepanel.setAlignmentY(LEFT_ALIGNMENT);

		Pricecentspanel.add(Pricecentslabel);
		Pricecentspanel.add(Pricecentstxt);
		Pricecentspanel.setLayout(new BoxLayout(Pricecentspanel, BoxLayout.Y_AXIS));
		Pricecentspanel.setAlignmentY(LEFT_ALIGNMENT);


		// Add all choice panels to the overall choice panel
		Choicepanel.setLayout(new FlowLayout());
		Choicepanel.add(Routenumpanel);
		Choicepanel.add(Carrierpanel);
		Choicepanel.add(DepAPpanel);
		Choicepanel.add(Deptimepanel);
		Choicepanel.add(ArrAPpanel);
		Choicepanel.add(Arrtimepanel);
		Choicepanel.add(Pricepanel);
		Choicepanel.add(Pricecentspanel);

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Addroutebutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the cancel button is clicked
		Addroutebutton.addActionListener(new addRouteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);
	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports, String[] routeNums){
		Test = new Route_addroute_dialogue(airports, routeNums);
		Test.setTitle("Add Route");								// Rename the title
		Test.setSize((width * 6 / 10), (height * 2 / 10));		// Set the size
		//Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	

	}
	// action listener for when the add route button has been pressed
	class addRouteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String routeNumString = "R";
			int routeNumInt = 0;
			int routeNumLength = 0;
			String historyText = "";

			boolean routeError = false;
			boolean pathError = false;
			boolean priceError = false;
			boolean routeExists = false;
			boolean timeError = false;

			String routeNumTxt = Routenumtxt.getText();
			// start getting data from labels
			try {
				routeNumInt = Integer.parseInt(Routenumtxt.getText().trim().replace(" ", ""));
			} catch (Exception e) {
				routeError = true;
			}
			routeNumTxt = routeNumTxt.replace(" ", "");
			routeNumLength = routeNumTxt.length();
			routeNumString = routeNumCheck(routeNumInt, routeNumString, routeNumTxt, routeError, routeNumLength);
			if (routeNumString.equals("Error")) {
				routeError = true;
			}
			// carrier retrieval
			int carrierInt;
			String carrierString = "";
			carrierInt = Carrierchoice.getSelectedIndex();
			if (carrierInt == 0) {
				carrierString = "DELTA";
			}
			else if (carrierInt == 1) {
				carrierString = "UNITED";
			}

			// departure airport retrieval
			int depAirInt;
			String depAirStr = "";
			depAirInt = DepAPchoice.getSelectedIndex();
			depAirStr = airports[depAirInt];

			// arrival airport retrieval
			int arrAirInt;
			String arrAirStr = "";
			arrAirInt = ArrAPchoice.getSelectedIndex();
			arrAirStr = airports[arrAirInt];

			// checking if arrival and destination airports are the same
			if (airports[arrAirInt].equals(airports[depAirInt])) {
				pathError = true;
			}

			// get departure time
			//Deptimechoice;
			int depHourInt = 0;
			String depHourString = "";
			int depMinInt = 0;
			String depMinString = "";
			String depString = "";

			depHourInt = Deptimechoice.Hourchoice.getSelectedIndex();
			depMinInt = Deptimechoice.Minutechoice.getSelectedIndex();

			depHourString = Deptimechoice.Hourlist[depHourInt];
			depMinString = Deptimechoice.Minutelist[depMinInt];

			depString = depHourString + depMinString;

			//Arrtimechoice;
			int arrHourInt = 0;
			String arrHourString = "";
			int arrMinInt = 0;
			String arrMinString = "";
			String arrString = "";

			arrHourInt = Arrtimechoice.Hourchoice.getSelectedIndex();
			arrMinInt = Arrtimechoice.Minutechoice.getSelectedIndex();

			arrHourString = Deptimechoice.Hourlist[arrHourInt];
			arrMinString = Deptimechoice.Minutelist[arrMinInt];

			arrString = arrHourString + arrMinString;

			//Check that the arrival time is at least 30 min after the departure time			
			int depMin = Integer.parseInt((String) Deptimechoice.Minutechoice.getSelectedItem());
			int arrMin = Integer.parseInt((String) Arrtimechoice.Minutechoice.getSelectedItem());

			if (arrHourInt - depHourInt < 0) {
				timeError = true;	
			}
			else if ((arrMin - depMin < 30) && (arrHourInt - depHourInt <= 0)) {
				timeError = true;
			}

			// retrieving the price of the route
			// Pricetxt
			String priceString = "";
			String priceCentsString = "";
			double priceDouble = 0.0;
			double priceCentsDouble = 0.0;

			priceString = Pricetxt.getText().trim();
			try {
				priceDouble = Double.parseDouble(priceString);
			} catch (Exception e) {
				priceError = true;
			}
			priceCentsString = Pricecentstxt.getText().trim();
			try {
				priceCentsDouble = Double.parseDouble(priceCentsString);
			} catch (Exception e) {
				priceError = true;
			}
			if (priceCentsString.length() == 1) {
				priceCentsString = priceCentsString + "0";
			}
			else if (priceCentsString.length() == 0) {
				priceCentsString = priceCentsString + "00";
			}
			if (priceString == "0" || priceString == "00" || priceString == "000") {
				priceError = true;
			}
			String totalPrice = priceString + "." + priceCentsString;
			// only error is when a "d" is added to the text field. Not sure how to eliminate that :(

			// checking if previous route already exists
			for (int i = 0; i < routeNums.length; i++) {
				if (routeNums[i].equals(routeNumString)) {
					routeExists = true;
				}
			}

			//add the route to the array list and update the routes table
			if ((routeError == false) && (pathError == false) && (priceError == false) && (routeExists == false)
					&& (timeError == false)) {
				String[] dummyRoute = {routeNumString, carrierString, depAirStr, depString, arrAirStr, arrString, totalPrice, "true"};
				Main.routesInfo = Main.addRoute(Main.routesInfo, dummyRoute); //add route to routesInfo ArrayList
				Main.routesInfo = Main.sortRouteArray(Main.routesInfo); //resort the array in numerical order
				Main.routesInfo = Main.routeClosureStatus(Main.routesInfo, Main.closuresInfo); // check for route closures
				Object[][] routesArray = Main.convertArrayListTo2DArray(Main.routesInfo, 1); //turn routesInfo into a 2d array
				Main.updateRoutesTable(routesArray); //update the route table
				historyText = "Route " + routeNumString + " has now been successfully added to the list of routes.";
				mainClass.historyArea.append(historyText + "\n");
				Test.dispose();
			}
			else if (routeError == true) {
				historyText = "Please enter a valid route number.";
				mainClass.historyArea.append(historyText + "\n");
			}
			else if (pathError == true) {
				historyText = "Arrival and Destination airports are the same. Consider traveling to/from a different location!";
				mainClass.historyArea.append(historyText + "\n");
			}
			else if (priceError == true) {
				historyText = "You have not entered a valid price. Please use the following format for dollars: ### where " +
						"# is a number.\nPlease use the following format for cents: ## where # is a number.";
				mainClass.historyArea.append((historyText + "\n"));
			}
			else if (routeExists == true) {
				historyText = "Route " + routeNumString + " already exists! Enter a different route number and then " +
						"proceed to adding the route.";
				mainClass.historyArea.append((historyText + "\n"));
			}
			else if (timeError == true) {
				historyText = "The arrival time must be a minimum of 30 minutes later than the departure time";
				mainClass.historyArea.append((historyText + "\n"));
			}
		}
	}
	// action listener for when the cancel button has been pressed
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
	public static String routeNumCheck(int routeNumInt, String routeNumString, String routeNumTxt, boolean routeError, int length) {
		if (length == 3) {
			routeNumString = routeNumString + routeNumTxt;
		}
		else if (length == 2) {
			if ((routeNumInt < 10) && (routeNumInt > 0)) {
				routeNumString = routeNumString + "0" + routeNumTxt;
			}
			else if ((routeNumInt >= 10) && (routeNumInt < 100)) {
				routeNumString = routeNumString + "0" + routeNumTxt;
			}
			else if (routeNumInt > 99){
				routeNumString = routeNumString + "" + routeNumTxt;
			}
			else if (routeNumInt <= 0) {
				routeNumString = "Error";
			}
		}
		else if (length == 1) {
			if ((routeNumInt < 10) && (routeNumInt > 0)) {
				routeNumString = routeNumString + "00" + routeNumTxt;
			}
			else
				routeNumString = "Error";

		}
		return routeNumString;
	}
}