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
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
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
	static private JLabel Pricelabel = new JLabel("Price", SwingConstants.CENTER);

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

	// Get the screen size
	Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;

	JTextField Routenumtxt;
	JTextField Pricetxt;

	JComboBox Carrierchoice;
	JComboBox DepAPchoice;
	JComboBox ArrAPchoice;

	String[] airports;

	TimeSelectBoxPanel Deptimechoice;
	TimeSelectBoxPanel Arrtimechoice;

	class JTextFieldLimit extends PlainDocument {
		private int limit;
		JTextFieldLimit(int limit) {
			super();
			this.limit = limit;
		}

		JTextFieldLimit(int limit, boolean upper) {
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}

	// Constructor for the dialogue box
	public Route_addroute_dialogue(String[] Airports) {
		airports = Airports;
		setModal(true);

		String[] Carriers = {"Delta", "United"};

		// Initialize all input boxes and drop-down menus
		Routenumtxt = new JTextField("0");
		Pricetxt = new JTextField("0.00");

		Carrierchoice = new JComboBox(Carriers);
		DepAPchoice = new JComboBox(Airports);
		ArrAPchoice = new JComboBox(Airports);

		Deptimechoice = new TimeSelectBoxPanel();
		Arrtimechoice = new TimeSelectBoxPanel();

		// Combine choice fields with their appropriate labels in panels
		Routenumpanel.add(Routenumlabel);
		Routenumpanel.add(Routenumtxt);
		Routenumtxt.setDocument(new JTextFieldLimit(3));		// Code to set limit of chars in text field using above class
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
		Pricepanel.setAlignmentY(CENTER_ALIGNMENT);

		// Add all choice panels to the overall choice panel
		Choicepanel.setLayout(new FlowLayout());
		Choicepanel.add(Routenumpanel);
		Choicepanel.add(Carrierpanel);
		Choicepanel.add(DepAPpanel);
		Choicepanel.add(Deptimepanel);
		Choicepanel.add(ArrAPpanel);
		Choicepanel.add(Arrtimepanel);
		Choicepanel.add(Pricepanel);

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
	public void showDialogue(String[] airports){
		Test = new Route_addroute_dialogue(airports);
		Test.setTitle("Add Route");								// Rename the title
		Test.setSize((width * 6 / 10), (height * 2 / 10));		// Set the size
		Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	

	}
	// action listener for when the add route button has been pressed
	class addRouteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String routeNumString = "R";
			double routeNumInt = 0;
			String historyText = "";

			boolean routeError = false;
			boolean pathError = false;

			// start getting data from labels
			try {
				routeNumInt = Integer.parseInt(Routenumtxt.getText());
			} catch (Exception e) {
				routeError = true;
			}
			if ((routeNumInt < 10) && (routeNumInt > 0)) {
				routeNumString = routeNumString + "00" + Routenumtxt.getText();
			}
			else if ((routeNumInt >= 10) && (routeNumInt < 100)) {
				routeNumString = routeNumString + "0" + Routenumtxt.getText();
			}
			else if (routeNumInt >= 99){
				routeNumString = routeNumString + "" + Routenumtxt.getText();
			}
			else if (routeNumInt <= 0) {
				routeError = true;
			}

			// carrier retrieval
			int carrierInt;
			String carrierString = "";
			carrierInt = Carrierchoice.getSelectedIndex();
			if (carrierInt == 0) {
				carrierString = " DELTA";
			}
			else if (carrierInt == 1) {
				carrierString = " UNITED";
			}

			// departure airport retrieval
			int depAirInt;
			String depAirStr = "";
			depAirInt = DepAPchoice.getSelectedIndex();
			depAirStr = " " + airports[depAirInt];

			// arrival airport retrieval
			int arrAirInt;
			String arrAirStr = "";
			arrAirInt = ArrAPchoice.getSelectedIndex();
			arrAirStr = " " + airports[arrAirInt];

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

			depHourString = " " + Deptimechoice.Hourlist[depHourInt];
			depMinString = "" + Deptimechoice.Minutelist[depMinInt];

			depString = depHourString + depMinString;

			//Arrtimechoice;
			int arrHourInt = 0;
			String arrHourString = "";
			int arrMinInt = 0;
			String arrMinString = "";
			String arrString = "";

			arrHourInt = Arrtimechoice.Hourchoice.getSelectedIndex();
			arrMinInt = Arrtimechoice.Minutechoice.getSelectedIndex();

			arrHourString = " " + Deptimechoice.Hourlist[arrHourInt];
			arrMinString = "" + Deptimechoice.Minutelist[arrMinInt];

			arrString = arrHourString + arrMinString;

			// retrieving the price of the route
			// Pricetxt
			String priceString = "";
			double priceDouble = 0.0;
			boolean priceError = false;

			priceString = " " + Pricetxt.getText();
			String [] priceStringArray;
			try {
				priceDouble = Double.parseDouble(priceString);
			} catch (Exception e) {
				priceError = true;
			}
			// if there is no "." entered, enter 00
			if (!priceString.contains(".")) {
				priceString = priceString + ".00";
			}
			// if there is a "." and no numbers after, enter 00
			else if (priceString.endsWith(".")) {
				priceString = priceString + "00";
			}
			// if there is a "." and only one digit after, enter a 0 for the last digit
			else if ((priceString.endsWith(".0")) || (priceString.endsWith(".1")) || (priceString.endsWith(".2")) ||
					(priceString.endsWith(".3")) || (priceString.endsWith(".4")) || (priceString.endsWith(".5")) || 
					(priceString.endsWith(".6")) || (priceString.endsWith(".7")) || (priceString.endsWith(".8")) ||
					(priceString.endsWith(".9"))) {
				priceString = priceString + "0";
			}
			
			// only error is when a "d" is added to the text field. Not sure how to eliminate that :(

			//add the route to the array list and update the routes table
			if ((routeError == false) && (pathError == false) && (priceError == false)) {
				String[] dummyRoute = {routeNumString, carrierString, depAirStr, depString, arrAirStr, arrString, priceString};	 //TEMPORARY DUMMY ROUTE INFO
				Main.routesInfo = Main.addRoute(Main.routesInfo, dummyRoute); //add route to routesInfo ArrayList
				Object[][] routesArray = Main.convertArrayListTo2DArray(Main.routesInfo, 1); //turn routesInfo into a 2d array
				Main.updateRoutesTable(routesArray); //update the route table
				historyText = "Route " + routeNumString + " has now been successfully added to the list of routes.";
				mainClass.historyArea.append(historyText + "\n");
			}
			else if (routeError == true) {
				historyText = "Please enter a valid route number. (Do not enter characters or a 0).";
				mainClass.historyArea.append(historyText + "\n");
			}
			else if (pathError == true) {
				historyText = "Arrival and Destination airports are the same. Consider traveling to/from a different location!";
				mainClass.historyArea.append(historyText + "\n");
			}
			else if (priceError == true) {
				historyText = "You have not entered a valid price. Please use the following format: xx.xx where x is a number.\n" +
						"Please do not forget that characters and letters are not valid entries.";
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
}