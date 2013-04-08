// The purpose of this code is to provide the mechanism by which a
// user can find a path in the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 April 2013

// CHECK doubtime2string FOR ACCURACY!!!!!!!!

import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Route_findpath_dialogue extends JDialog {
	
	// Initialize path search results variables
	ArrayList<String[]> resultsInfo = new ArrayList<String[]>();
	ArrayList<double[]> resultsProps = new ArrayList<double[]>();
	int number_of_results = 0;
	
	// Initialize static labels
	static private JLabel Carrierlabel = new JLabel("Use Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Depart After", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrive Before", SwingConstants.CENTER);
	static private JLabel Optionslabel = new JLabel("Options", SwingConstants.CENTER);
	static private JLabel Cheaplabel = new JLabel("Cheapest", SwingConstants.CENTER);
	static private JLabel Fastlabel = new JLabel("Fastest", SwingConstants.CENTER);

	// static ArrayList<String[]> routesInfo;

	// Initialize check boxes
	static JCheckBox Checkdeptime = new JCheckBox();
	static JCheckBox Checkarrtime = new JCheckBox();
	static JCheckBox Checkcarrier = new JCheckBox();
	
	ButtonGroup radioGroup = new ButtonGroup();
	static JRadioButton Checkcheap = new JRadioButton();
	static JRadioButton Checkfast = new JRadioButton();

	// Initialize the search route and cancel buttons and panel
	JButton Findpathbutton = new JButton("Find Path");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels
	JPanel Choicepanel = new JPanel();

	JPanel Selectpanel = new JPanel();
	JPanel DepAPpanel = new JPanel();
	JPanel ArrAPpanel = new JPanel();

	JPanel Optionpanel = new JPanel();
	JPanel Deptimepanel = new JPanel();
	JPanel Deptimecheckpanel = new JPanel();
	JPanel Arrtimepanel = new JPanel();
	JPanel Arrtimecheckpanel = new JPanel();
	JPanel Carrierpanel = new JPanel();
	JPanel Carriercheckpanel = new JPanel();
	JPanel Radiopanel = new JPanel();

	// Get the screen size
	Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;

	JComboBox Carrierchoice;
	JComboBox DepAPchoice;
	JComboBox ArrAPchoice;

	String[] airports;
	String[] Carriers = {"Delta", "United"};

	TimeSelectBoxPanel Deptimechoice;
	TimeSelectBoxPanel Arrtimechoice;

	// Constructor for the dialogue box
	public Route_findpath_dialogue(String[] Airports) {
		airports = Airports;
		setModal(true);

		// Initialize all drop-down menus
		Carrierchoice = new JComboBox(Carriers);
		DepAPchoice = new JComboBox(Airports);
		ArrAPchoice = new JComboBox(Airports);

		Deptimechoice = new TimeSelectBoxPanel();
		Arrtimechoice = new TimeSelectBoxPanel();
		
		// Initialize radiogroup
		radioGroup.add(Checkcheap);
		radioGroup.add(Checkfast);
		Checkcheap.setSelected(true);
		
		// Create departure and arrival airport panels
		DepAPpanel.add(DepAPlabel);
		DepAPpanel.add(DepAPchoice);
		DepAPpanel.setLayout(new BoxLayout(DepAPpanel, BoxLayout.Y_AXIS));
		DepAPpanel.setAlignmentY(CENTER_ALIGNMENT);

		ArrAPpanel.add(ArrAPlabel);
		ArrAPpanel.add(ArrAPchoice);
		ArrAPpanel.setLayout(new BoxLayout(ArrAPpanel, BoxLayout.Y_AXIS));
		ArrAPpanel.setAlignmentY(CENTER_ALIGNMENT);

		// Create departure time panel with check box panel
		Deptimepanel.add(Deptimelabel);
		Deptimepanel.add(Deptimechoice);
		Deptimepanel.setLayout(new BoxLayout(Deptimepanel, BoxLayout.Y_AXIS));
		Deptimepanel.setAlignmentY(CENTER_ALIGNMENT);
		Deptimecheckpanel.add(Deptimepanel);
		Deptimecheckpanel.add(Checkdeptime);
		Deptimecheckpanel.setLayout(new BoxLayout(Deptimecheckpanel, BoxLayout.X_AXIS));
		Deptimecheckpanel.setAlignmentX(CENTER_ALIGNMENT);

		// Create arrival time panel with check box panel
		Arrtimepanel.add(Arrtimelabel);
		Arrtimepanel.add(Arrtimechoice);
		Arrtimepanel.setLayout(new BoxLayout(Arrtimepanel, BoxLayout.Y_AXIS));
		Arrtimepanel.setAlignmentY(CENTER_ALIGNMENT);
		Arrtimecheckpanel.add(Arrtimepanel);
		Arrtimecheckpanel.add(Checkarrtime);
		Arrtimecheckpanel.setLayout(new BoxLayout(Arrtimecheckpanel, BoxLayout.X_AXIS));
		Arrtimecheckpanel.setAlignmentX(CENTER_ALIGNMENT);

		// Create carrier panel with check box panel
		Carrierpanel.add(Carrierlabel);
		Carrierpanel.add(Carrierchoice);
		Carrierpanel.setLayout(new BoxLayout(Carrierpanel, BoxLayout.Y_AXIS));
		Carrierpanel.setAlignmentY(CENTER_ALIGNMENT);
		Carriercheckpanel.add(Carrierpanel);
		Carriercheckpanel.add(Checkcarrier);
		Carriercheckpanel.setLayout(new BoxLayout(Carriercheckpanel, BoxLayout.X_AXIS));
		Carriercheckpanel.setAlignmentX(CENTER_ALIGNMENT);

		// Create radiopanel with radio buttons
		Radiopanel.add(Checkcheap);
		Radiopanel.add(Cheaplabel);
		Radiopanel.add(Checkfast);
		Radiopanel.add(Fastlabel);
		
		// Add all selection panels and option panels to the overall choice panel
		Selectpanel.setLayout(new BoxLayout(Selectpanel, BoxLayout.Y_AXIS));
		Selectpanel.setAlignmentY(CENTER_ALIGNMENT);
		Selectpanel.add(DepAPpanel);
		Selectpanel.add(ArrAPpanel);

		Optionpanel.setLayout(new BoxLayout(Optionpanel, BoxLayout.Y_AXIS));
		Optionpanel.setAlignmentY(CENTER_ALIGNMENT);
		Optionpanel.add(Optionslabel);
		Optionpanel.add(Box.createVerticalGlue());
		Optionpanel.add(Deptimecheckpanel);
		Optionpanel.add(Arrtimecheckpanel);
		Optionpanel.add(Carriercheckpanel);
		Optionpanel.add(Radiopanel);

		Choicepanel.setLayout(new BoxLayout(Choicepanel, BoxLayout.X_AXIS));
		Choicepanel.setAlignmentY(CENTER_ALIGNMENT);
		Choicepanel.add(Selectpanel);
		Choicepanel.add(Box.createHorizontalGlue());
		Choicepanel.add(Optionpanel);

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Findpathbutton);
		Buttonpanel.add(Cancelbutton);

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);
		
		Findpathbutton.addActionListener(new findPathListener());
		Cancelbutton.addActionListener((new cancelListener()));

	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports){
		setTitle("Find Path");								// Rename the title
		setSize((width * 4 / 10), (height * 4 / 10));		// Set the size
		setResizable(false);								// No resizing
		setLocationRelativeTo(null);						// Center it
		setVisible(true);									// Make it visible	
	}	
	
	
	// Method for finding a path with given routes, start and end data, and carrier opt
	public void Find_Path(String DepAP, String ArrAP, double Deptime, double Finaldesttime, String Carrier, String[] PrevAP, String[] PrevRoutes, int iteration) {
		
		// Create Arraylist containing all arrays to be evaluated by this instance of the method
		ArrayList<String[]> routes_to_evaluate = new ArrayList<String[]>();
		String[] thisRoute;
		String[] evalRoute;
		boolean rightstartpoint = true;
		boolean hasnotvisitedDest = true;
		boolean possibleConnection = true;
		boolean rightCarrier = true;
		boolean notCancelled = true;
		
		// Go through all routes from main, adding appropriate routes
		for (int i = 0; i < Main.routesInfo.size(); i++) {
			
			thisRoute = (String[]) Main.routesInfo.get(i); //Create an array from each object in the arrayList
			
			// Check to see if no route to evaluate
			if (thisRoute[0] == null) {
				break;
			}
			
			// Check to see if departing from this airport
			if (!(thisRoute[2].equalsIgnoreCase(DepAP))) {
				rightstartpoint = false;
			}
			
			
			// Check to see if the destination has already been visited
			for (int k = 0; k < PrevAP.length; k++ ) {
				if (PrevAP[k] == null) {
					break;
				}
				
				else if (PrevAP[k].equalsIgnoreCase(thisRoute[4])) {
					hasnotvisitedDest = false;
				}
			}
			
			// Check to see is the dep time is too soon
			if (routetime2doub(thisRoute[3]) <= (Deptime + 0.5)) {
				possibleConnection = false;
			}
			
			// Check to see if the carrier is right
			if ( !( thisRoute[1].equalsIgnoreCase(Carrier) || Carrier.equalsIgnoreCase("None") ) ) {
				rightCarrier = false;
			}
			
			// Check to see if the route is cancelled due to airport closures
			/*if (thisRoute[7].equalsIgnoreCase("false")){
				//notCancelled = false;
			}*/
			
			// If we haven't already visited the destination of the route, the departure
			//     time of the route is 30min or later past the current time, and the
			//     desired carrier is either matched or not specified
			if (hasnotvisitedDest && possibleConnection && rightCarrier && rightstartpoint/* && notCancelled*/){
				routes_to_evaluate.add(thisRoute);
			}
			
			// Reset booleans
			rightstartpoint = true;
			hasnotvisitedDest = true;
			possibleConnection = true;
			rightCarrier = true;
			notCancelled = true;
			
		}
		
		
		// Evaluate each route in the list of routes to evaluate
		for (int i = 0; i < routes_to_evaluate.size(); i++) {
			
			// load up the route
			evalRoute = (String[]) routes_to_evaluate.get(i);
			
			// Check to see if no routes to evaluate
			if (evalRoute[0] == null) {
				break;
			}
			
			// if it reaches destination before the required time
			if (evalRoute[4].equalsIgnoreCase(ArrAP) && routetime2doub(evalRoute[5]) <= Finaldesttime) {
				
				// Update the progression of routes to this solution
				PrevRoutes[iteration - 1] = evalRoute[0];
				
				// Grab the solution
				String [] result_to_store = new String[iteration];
				for( int l = 0; l < result_to_store.length; l++) {
					result_to_store[l] = PrevRoutes[l].toString();
				}
				
				// Store the solution and update the number of results
				resultsInfo.add(result_to_store);
				number_of_results = number_of_results + 1;
				
				// Reset the prevroutes
				PrevRoutes[iteration - 1] = null;
				
			}
			else {
				
				// Update the progression of routes to this solution
				PrevRoutes[iteration - 1] = evalRoute[0];
				
				PrevAP[iteration - 1] = DepAP;
				iteration++;
				
				Find_Path(evalRoute[4].toString(), ArrAP, routetime2doub(evalRoute[5]), Finaldesttime, Carrier, PrevAP, PrevRoutes, iteration);
				
				// Reset the prevroutes and iteration
				iteration--;
				PrevRoutes[iteration - 1] = null;
			}
			
		}
		
	}
	
	
	// Method for taking in the string of timedata from the route & converting it to double
	public double routetime2doub(String routetime) {
		
		int tempvalue = Integer.parseInt(routetime);
		
		int hourvalue = tempvalue/100;
		int minutevalue = tempvalue%100;
		double timevalue = (double) hourvalue + ((double) minutevalue)/60;
		
		return timevalue;
		
	}
	
	// Method for taking in a time in hrs and outputting the string xhrs xmin
	public String doubtime2string(double time) {
		
		int hours = (int) time;
		int minutes = (int) (Math.round((time*60) % 60));
		
		return hours + "hr " + minutes + "min";
		
	}
	
	
	// Method to populate the ArrayList<double[]> resultsProps with the path data
	// Array index for properties corresponds with same index for path in resultsInfo
	public int Path_Properties() {
		
		int necessary_array_size = 0;
		
		// For every path in resultsInfo
		for (int i = 0; i < resultsInfo.size(); i++) {
			
			double pathTime = 0;
			double pathCost = 0;
			
			// For every route in the path
			for (int j = 0; j < resultsInfo.get(i).length; j++) {
				necessary_array_size += 2;
				
				// Get the route number to grab properties for
				String routenum = resultsInfo.get(i)[j];
				
				// Go through the main table until route is found and grab its props
				for (int k = 0; k < Main.routesInfo.size(); k++) {
					if (Main.routesInfo.get(k)[0].equalsIgnoreCase(routenum)) {
						
						pathTime = pathTime + (routetime2doub(Main.routesInfo.get(k)[5]) - routetime2doub(Main.routesInfo.get(k)[3]));
						pathCost = pathCost + Double.parseDouble(Main.routesInfo.get(k)[6]);
						
						necessary_array_size += 1;
					}
				}
				
			} // End For every route in the path
			
			double[] path_props = {pathTime, pathCost};
			resultsProps.add(path_props);
			
		} // End for every path in the results
		
		return necessary_array_size;
		
	} // End method for populating the results properties
	
	
	// Method for sorting resultsInfo and resultsProps according to cheapest/fastest
	public void sortResults(boolean cheapest) {
		
		// Cheapest sort
		if (cheapest) {
			
			// For the number of results
			for (int i = 0; i < resultsInfo.size(); i++) {
				
				double[] tempProp = new double[2];
				tempProp[1] = Double.MAX_VALUE;
				String[] tempRoute = new String[7];
				
				// Rearrange the Arraylists
				for (int j = i; j < resultsProps.size(); j++) {
					if (resultsProps.get(j)[1] < tempProp[1]) {
						
						tempProp = resultsProps.get(j);
						resultsProps.set(j, resultsProps.get(i));
						resultsProps.set(i, tempProp);
						
						tempRoute = resultsInfo.get(j);
						resultsInfo.set(j, resultsInfo.get(i));
						resultsInfo.set(i, tempRoute);
						
					}
				}
				
			} // End for the number of Results
			
		} // End Cheapsort
		
		// Fastsort
		else {
			
			// For the number of results
			for (int i = 0; i < resultsInfo.size(); i++) {
				
				double[] tempProp = new double[2];
				tempProp[0] = Double.MAX_VALUE;
				String[] tempRoute = new String[7];
				
				// Rearrange the Arraylists
				for (int j = i; j < resultsProps.size(); j++) {
					if (resultsProps.get(j)[0] < tempProp[0]) {
						
						tempProp = resultsProps.get(j);
						resultsProps.set(j, resultsProps.get(i));
						resultsProps.set(i, tempProp);
						
						tempRoute = resultsInfo.get(j);
						resultsInfo.set(j, resultsInfo.get(i));
						resultsInfo.set(i, tempRoute);
						
					}
				}
				
			} // End for the number of Results
			
		} // End Fastsort
	}
	
	
	// Method for displaying the results on the table
	public void updatePathTable(int necessary_array_size) {
		
		// Create 2D array with all the organized pathdata
		String[][] PathTable = new String[necessary_array_size][7];
		int current_index = 0;
			
		for (int i = 0; i < resultsInfo.size(); i++) {
			
			// Header line with path number, routes, time, and cost
			PathTable[current_index][0] = "Path " + (i+1);
			PathTable[current_index][3] = doubtime2string(resultsProps.get(i)[0]);
			
			if (Double.toString(resultsProps.get(i)[1]).endsWith(".0")){
				PathTable[current_index][6] = "$" + Double.toString(resultsProps.get(i)[1]) + "0";
			}
			else{
				PathTable[current_index][6] = "$" + Double.toString(resultsProps.get(i)[1]);
			}
			
			current_index += 1;
			
			// Routes for the path
			for (int j = 0; j < resultsInfo.get(i).length; j++) {
				
				// Get the route number to grab properties for
				String routenum = resultsInfo.get(i)[j];
			
				for (int k = 0; k < Main.routesInfo.size(); k++) {
					if (Main.routesInfo.get(k)[0].equalsIgnoreCase(routenum)) {
						
						PathTable[current_index] = Main.routesInfo.get(k);
						current_index += 1;
						
					}
				}
				
			}
			
			// Add a blank line
			current_index += 1;
			
		}
		
		Object[][] pathObject = (Object[][]) PathTable;
		Main.updateResultsTable(pathObject);
		
	}
	
	// Listener for the find path button - activates the find path method
	class findPathListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {			
			
			// Declare option variables and set default options
			String Carrier_option = "None";
			double Deptime_option = -0.5;
			double Finaldesttime_option = 24.0;
			boolean cheapest = true;
			int iteration = 1;
			
			// If options are selected, set variables appropriately
			if (Checkcarrier.isSelected()) {
				Carrier_option = (String) Carrierchoice.getSelectedItem();
			}
			if (Checkdeptime.isSelected()) {
				Deptime_option = Deptimechoice.getTimeValue();
			}
			if (Checkarrtime.isSelected()) {
				Finaldesttime_option = Arrtimechoice.getTimeValue();
			}
			if (Checkfast.isSelected()) {
				cheapest = false;
			}
			
			// Get Departure and Arrival Airport
			String DepAP_option = (String) DepAPchoice.getSelectedItem();
			String ArrAP_option = (String) ArrAPchoice.getSelectedItem();
			
			// Get the number of airports from Main and create a blank array with that number of poss APs
			String[] PrevAP = new String[Main.airportsInfo.size()];
			String[] PrevRoutes = new String[Main.airportsInfo.size()];
			
			// Error Checking
			boolean APerror = false;
			boolean timeerror = false;
			
			if (DepAP_option.equalsIgnoreCase(ArrAP_option)) {
				APerror = true;
				Main.historyArea.append("Departure and Destination cannot be the same\n");
			}
			if (Deptime_option >= Finaldesttime_option) {
				timeerror = true;
				Main.historyArea.append("Arrival time cannot be before departure time\n");
			}
			
			if (!APerror && !timeerror) {
				
				// Find all possible paths and display the path results
				Find_Path(DepAP_option, ArrAP_option, Deptime_option, Finaldesttime_option, Carrier_option, PrevAP, PrevRoutes, iteration);
				int necessary_array_size = Path_Properties();
				
				sortResults(cheapest);
				updatePathTable(necessary_array_size);
				
				// Send a message to history
				Main.historyArea.append(number_of_results + " paths found from " + DepAP_option + " to " + ArrAP_option + "\n");
				
				// Close the window
				dispose();
			}
			
		}
	}
	
	
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}
	
	
	
	
}