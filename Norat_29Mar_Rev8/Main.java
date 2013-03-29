import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Main implements ActionListener {
	JFrame frame;
	Dimension totalSize;

	//Initialize stuff
	static JPanel mainPanel = new JPanel(new BorderLayout());
	static JPanel routePanel = new JPanel();
	static JPanel histPanel = new JPanel();
	static JPanel resultsPanel = new JPanel();
	static int routePanelWidth;
	static int routePanelHeight;
	static JTable routeTable;
	static ArrayList<String[]> routesInfo;
	static ArrayList<String> airportsInfo;
	static ArrayList<String[]> closuresInfo;

	static TextArea historyArea;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Main gui = new Main();
		gui.go();		
	}
	public void go() throws IOException {
		//DefaultRouteTable route = new DefaultRouteTable();
		//System.out.println(route.routeInfo());
		//route.routeInfo();
		totalSize = Toolkit.getDefaultToolkit().getScreenSize();	// getting screen size
		int width = totalSize.width;
		int height = totalSize.height;
		frame = new JFrame();	// creating frame
		frame.setTitle("Air Route Planner");
		frame.add(Panels());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((width * 3 / 4), (height * 3 / 4));
		frame.setJMenuBar(menuBar());
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public JPanel Panels() throws IOException {

		Object[][] data = new Object[0][7];

		//Create the initial Routes Table
		try {
			FileInput input = new FileInput("input.txt"); //read the input file

			//get the data from input.txt 
			routesInfo = input.routeArrayList(input.routesToken); 
			airportsInfo = input.airportArrayList(input.airportsToken);
			closuresInfo = input.closureArrayList(input.closuresToken);

			try { 
				data = convertArrayListTo2DArray(routesInfo, 1);		
			}
			catch (NullPointerException e1) { //if there are any errors in input.txt
				System.out.println("There are errors in the input.txt file.");
			}
		}
		catch (FileNotFoundException e1) { //If input.txt is missing
			System.out.println("The file input.txt is missing.");
		}
		catch (NullPointerException e1) { //If input.txt is blank
		}

		routePanel.add(CreateRouteTable(data, totalSize)); //add the table to the JPanel
		routePanel.setBorder(BorderFactory.createTitledBorder("Route Table"));
		//------------------------------------------------end of route table--------------------------------------------------------

		histPanel.setPreferredSize(new Dimension(totalSize.width, totalSize.height / 5));
		histPanel.setBorder(BorderFactory.createTitledBorder("History"));
		historyArea = new TextArea();
		historyArea.setEditable(false);
		historyArea.setPreferredSize(new Dimension(totalSize.width * 7 / 10, totalSize.height / 7));
		histPanel.add(historyArea);
		//------------------------------------------------end of history table--------------------------------------------------------
		resultsPanel.setPreferredSize(new Dimension((totalSize.width * 3 / 4) * 49 / 100, totalSize.height / 3));
		resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
		resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.X_AXIS));
		//------------------------------------------------end of results table--------------------------------------------------------

		mainPanel.add(routePanel, BorderLayout.WEST);
		mainPanel.add(histPanel, BorderLayout.SOUTH);
		mainPanel.add(resultsPanel, BorderLayout.EAST);

		return mainPanel;
	}
	// code used to create the menu bar
	public JMenuBar menuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;

		menuBar = new JMenuBar();
		menu = new JMenu("File");

		// creates open option
		menuItem = new JMenuItem("Open", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Open a Previously Saved File");
		menu.add(menuItem);
		menuItem.addActionListener(new FileOpenListener());
		menu.addSeparator();

		// creates save option
		menuItem = new JMenuItem("Save", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Save File Data");
		menu.add(menuItem);
		menuItem.addActionListener(new FileSaveListener());
		menu.addSeparator();

		// creates close option
		menuItem = new JMenuItem("Exit", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription("Close Program");
		menu.add(menuItem);
		menuItem.addActionListener(new FileExitListener());

		menuBar.add(menu);

		// new menu option for Route
		menu = new JMenu("Route");

		menuItem = new JMenuItem("Add");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteAddListener());

		menuItem = new JMenuItem("Remove");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteRemoveListener());

		menuItem = new JMenuItem("Find Route");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteFindListener());

		menuItem = new JMenuItem("Find Path");
		menu.add(menuItem);
		menuItem.addActionListener(new PathFindListener());

		menuBar.add(menu);

		// new menu option for Airport

		menu = new JMenu("Airport");

		menuItem = new JMenuItem("Status");
		menu.add(menuItem);
		menuItem.addActionListener(new AirStatusListener());

		menuItem = new JMenuItem("Add");
		menu.add(menuItem);
		menuItem.addActionListener(new AirAddListener());

		menuItem = new JMenuItem("Delete");
		menu.add(menuItem);
		menuItem.addActionListener(new AirDeleteListener());

		menuItem = new JMenuItem("Airport Information");
		menuItem.setToolTipText("Click to search all routes at a given airport.");
		menu.add(menuItem);
		menuItem.addActionListener(new AirSearchListener());

		menuItem = new JMenuItem("All Airports");
		menu.add(menuItem);
		menuItem.addActionListener(new AirAllListener());

		menuItem = new JMenuItem("Add Carrier");
		menu.add(menuItem);
		menuItem.addActionListener(new AirCarrierListener());

		menuBar.add(menu);

		// new menu option for Help
		menu = new JMenu("Help");

		menuItem = new JMenuItem("User Manual");
		menu.add(menuItem);
		menuItem.addActionListener(new HelpManualListener());

		menuBar.add(menu);

		return menuBar;
	}
	class FileOpenListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {
				FileInput input = new FileInput("output.txt");
				routesInfo = input.routeArrayList(input.routesToken); 
				airportsInfo = input.airportArrayList(input.airportsToken);
				closuresInfo = input.closureArrayList(input.closuresToken);
				String[][] data = convertArrayListTo2DArray(routesInfo, 1);
				updateRoutesTable(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class FileSaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {
				FileInput.saveFile(airportsInfo, routesInfo, closuresInfo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class FileExitListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//need to check if there are changes that need to be saved before exiting
			//System.exit(0);		
		}
	}
	class RouteAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			//addRouteCount = 1;
			String[] airportsArray;		// create empty array that will be used for the method that finds the airports list
			airportsArray = airportsArrayList();	// call airportsArrayList method to get list of airports
			//frame.setEnabled(false);
			Route_addroute_dialogue routeDialog = new Route_addroute_dialogue(airportsArray);
			routeDialog.showDialogue(airportsArray);	// show the add route dialog box.
		}
	}
	class RouteRemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			int[] routesIntArray;		// create empty array that will be used for the method that finds the routes list
			String[] routesArray;
			routesArray = routesArrayList(routesInfo);
			Route_removeroute_dialogue removeDialog = new Route_removeroute_dialogue(routesArray);
			removeDialog.showDialogue(routesArray);
		}
	}
	class RouteFindListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class PathFindListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String[] airportsArray;		// create empty array that will be used for the method that finds the airports list
			airportsArray = airportsArrayList();	// call airportsArrayList method to get list of airports
			Route_findpath_dialogue findDialog = new Route_findpath_dialogue(airportsArray);
			findDialog.showDialogue(airportsArray);
		}
	}
	class AirStatusListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String[] airportsArray;
			airportsArray = airportsArrayList();
			Airport_OpenClose_Dialogue airDialogue = new Airport_OpenClose_Dialogue(airportsArray);
			try {
				airDialogue.status(airportsArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class AirAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String[] airportsArray;
			airportsArray = airportsArrayList();
			Airport_Add_Dialogue airDialogue = new Airport_Add_Dialogue(airportsArray);
			try {
				airDialogue.addAirportDialogue(airportsArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class AirDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String[] airportsArray;
			airportsArray = airportsArrayList();
			Airport_Delete_Dialogue airDialogue = new Airport_Delete_Dialogue(airportsArray);
			try {
				airDialogue.deleteAirportDialogue(airportsArray);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class AirSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class AirAllListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class AirCarrierListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class HelpManualListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	public void actionPerformed(ActionEvent arg0) {

	}
	// this method is used to determine the list of airports from the text file
	public String[] airportsArrayList () {
		ArrayList airports;
		String[] airportsArray;
		FileInput data = null;
		try {
			data = new FileInput("input.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		airports = data.airportArrayList(data.airportsToken);
		airportsArray = convertAirportArrayListToArray(airports);
		return airportsArray;
	}
	// this method is used to determine the list of routes from routesInfo
	public String[] routesArrayList(ArrayList<String[]> routesInfo) {
		String[] routesArray = null;
		routesArray = convertRoutesArrayListToArray(routesInfo);
		return routesArray;
	}

	//Method to convert an arrayList to a 2D array, should only be used for the routes or closures arrayLists 
	//IMPORTANT: for routesOrClosures, enter 0 if using this method for closures, 1 for routes
	static String[][] convertArrayListTo2DArray(ArrayList arrayList, int routesOrClosures) {	

		String[][] theArray = null;

		//Create 2d array with the same number of  the same size as the arrayList
		if (routesOrClosures > 0) {
			theArray = new String[arrayList.size()][7]; //For routes, 7 columns
		}
		else if (routesOrClosures < 1) {
			theArray = new String[arrayList.size()][3]; //For closures, 3 columns
		}

		for (int i = 0; i < arrayList.size(); i++) {
			String[] theString = (String[]) arrayList.get(i); //Create an array from each object in the arrayList
			for (int j = 0; j < theString.length; j++) {
				theArray[i][j] = theString[j];  //Add each value in the array created above to a line in the 2d array
			}
		}
		return theArray;
	}

	// This method converts the routes in such a way so that the route numbers can only be displayed in the drop down 
	// menu when attempting to remove a route.
	String[] convertRoutesArrayListToArray(ArrayList routesArrayList) {
		String[] theArray = new String[routesArrayList.size()];
		String[] tempArray = null;
		for (int i = 0; i < routesArrayList.size(); i++) {
			tempArray = (String[]) routesArrayList.get(i);
			for (int j = 0; j < tempArray.length; j++) {
				theArray[i] = tempArray[0];
			}
		}
		return theArray;
	}

	//Method to convert an arrayList to an array, should only be used for the airports arrayList
	static String[] convertAirportArrayListToArray(ArrayList airportArrayList) {
		String[] theArray = new String[airportArrayList.size()]; //Create array the same size as the arrayList
		for (int i = 0; i < airportArrayList.size(); i++) {
			theArray[i] = (String) airportArrayList.get(i); //Add each component of the ArrayList to the array
		}
		return theArray;
	}

	//method to add a new route to the routesInfo ArrayList
	static ArrayList<String[]> addRoute(ArrayList<String[]> routesInfo, String[] newRoute) {
		routesInfo.add(newRoute);
		return routesInfo;
	}

	//Method for updating the route table
	static public void updateRoutesTable(Object[][] data) {
		//remove old route table
		routePanel.removeAll();

		//create new route table
		String[] columnNames = {"Route #", "Carrier", "Dep. Airport", "Dep. Time", "Arr. Airport", "Arr. Time", "Price"};
		JScrollPane routeTable = new JScrollPane(new JTable(data, columnNames)); //create the routes table in a scrollPane
		routeTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		routeTable.setPreferredSize(new Dimension(routePanelWidth, routePanelHeight));
		routePanel.add(routeTable); //add the table to the JPanel
		routePanel.setBorder(BorderFactory.createTitledBorder("Route Table"));

		//display new route table
		routePanel.revalidate();
		routePanel.repaint();
	}
	static public Component CreateRouteTable (Object[][] data, Dimension totalSize) {
		String[] columnNames = {"Route #",  "Carrier", "Dep. Airport", "Dep. Time", "Arr. Airport", "Arr. Time", "Price"};

		// disable cell editing
		JTable routeTable = new JTable(data, columnNames){
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			};
		};

		JScrollPane routeTableScroll = new JScrollPane(routeTable); //create the routes table in a scrollPane

		routeTableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		routePanelWidth = (totalSize.width * 3 / 4) * 48 / 100;
		routePanelHeight = totalSize.height * 45/100;
		routeTableScroll.setPreferredSize(new Dimension(routePanelWidth, routePanelHeight));
		return routeTableScroll;
	}
}