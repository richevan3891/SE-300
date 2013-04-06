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
	static JFrame frame;
	Dimension totalSize;

	//Initialize stuff
	static JPanel mainPanel = new JPanel(new BorderLayout());
	static JPanel routePanel = new JPanel();
	static JPanel histPanel = new JPanel();
	static JPanel resultsPanel = new JPanel();
	static int routePanelWidth;
	static int routePanelHeight;
	static int resultsPanelWidth;
	static int resultsPanelHeight;
	static JTable routeTable;
	static ArrayList<String[]> routesInfo;
	static ArrayList<String> airportsInfo;
	static ArrayList<String[]> closuresInfo;
	static boolean systemLocked = false;

	static TextArea historyArea;
	static TextArea resultsArea;
	
	static String[] airportsArray;

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
		frame.addWindowListener(new WindowAdapter() {
			   public void windowClosing(WindowEvent we) {
				   FileInput data = null;
					try {
						data = new FileInput("input.txt");
					} catch (Exception e) {

					}
					String[][] origRoutes = convertArrayListTo2DArray(sortRouteArray(data.routeArrayList(data.routesToken)), 1);
					String[][] routesList = Main.convertArrayListTo2DArray(routesInfo, 1);
					Main.changesCheck(origRoutes, routesList);
			   }
			});
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
			routesInfo = sortRouteArray(routesInfo);
			airportsInfo = input.airportArrayList(input.airportsToken);
			closuresInfo = input.closureArrayList(input.closuresToken);

			//If one section has an error (is null) set all sections to null and lock all routes and airprots menu items
			if (airportsInfo == null) {
				routesInfo = null;
				closuresInfo = null;
				systemLocked = true;
			}
			else if (routesInfo == null) {
				airportsInfo = null;
				closuresInfo = null;
				systemLocked = true;
			}
			else if (closuresInfo == null) {
				airportsInfo = null;
				routesInfo = null;
				systemLocked = true;
			}			

			data = convertArrayListTo2DArray(routesInfo, 1);			

		}
		catch (FileNotFoundException e1) { //If input.txt is missing
			System.out.println("The file input.txt is missing.");
		}
		catch (NullPointerException e1) { //If there are any other errors in the file
			System.out.println("There are errors in the input.txt file");
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
		Object[][] emptyData = new Object[0][7];
		resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));
		resultsPanel.add(CreateResultsTable(emptyData, totalSize));
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

		menuItem = new JMenuItem("Search Routes");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteFindListener());

		menuItem = new JMenuItem("Find Path");
		menu.add(menuItem);
		menuItem.addActionListener(new PathFindListener());

		menuBar.add(menu);

		// new menu option for Airport

		menu = new JMenu("Airport");

		menuItem = new JMenuItem("Open");
		menu.add(menuItem);
		menuItem.addActionListener(new AirOpenListener());
		
		menuItem = new JMenuItem("Close");
		menu.add(menuItem);
		menuItem.addActionListener(new AirCloseListener());

		menuItem = new JMenuItem("Add");
		menu.add(menuItem);
		menuItem.addActionListener(new AirAddListener());

		menuItem = new JMenuItem("Delete");
		menu.add(menuItem);
		menuItem.addActionListener(new AirDeleteListener());

		menuItem = new JMenuItem("Airport Route Information");
		menuItem.setToolTipText("Click to search all routes at a given airport.");
		menu.add(menuItem);
		menuItem.addActionListener(new AirSearchListener());

		menuItem = new JMenuItem("List Airports");
		menu.add(menuItem);
		menuItem.addActionListener(new AirAllListener());

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
							
				File_Open_Save_Dialog openDialog = new File_Open_Save_Dialog(0);	
				try{
					FileInput input = new FileInput(openDialog.fileName);
					
					routesInfo = input.routeArrayList(input.routesToken); 
					routesInfo = sortRouteArray(routesInfo);
					airportsInfo = input.airportArrayList(input.airportsToken);
					closuresInfo = input.closureArrayList(input.closuresToken);
					
					//If one section has an error (is null) set all sections to null and lock all routes and airprots menu items
					if (airportsInfo == null) {
						routesInfo = null;
						closuresInfo = null;
						systemLocked = true;
					}
					else if (routesInfo == null) {
						airportsInfo = null;
						closuresInfo = null;
						systemLocked = true;
					}
					else if (closuresInfo == null) {
						airportsInfo = null;
						routesInfo = null;
						systemLocked = true;
					}
					else {
						systemLocked = false;
					}
					
					String[][] data = convertArrayListTo2DArray(routesInfo, 1);
					updateRoutesTable(data);
					
					String historyText = ("The file " + openDialog.fileName + " has been opened.");
					historyArea.append(historyText + "\n");
					
					// remove all searches from route search table
					Object[][] emptyData = new Object[0][7];
					Main.updateResultsTable(emptyData);
					Main.historyArea.setText("");
				}
				catch(NullPointerException e) {
					if(openDialog.fileName != null) {
						String historyText = ("there are errors in " + openDialog.fileName);
						historyArea.append(historyText + "\n");
					}
				}
				catch(FileNotFoundException e) {
					String historyText = ("The file " + openDialog.fileName + " does not exist.");
					historyArea.append(historyText + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class FileSaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {	
			try {
				File_Open_Save_Dialog saveDialog = new File_Open_Save_Dialog(1);
				
				String historyText = ("The file " + saveDialog.fileName + " has been saved.");
				historyArea.append(historyText + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class FileExitListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			FileInput data = null;
			try {
				data = new FileInput("input.txt");
			} catch (Exception e) {

			}
			String[][] origRoutes = convertArrayListTo2DArray(data.routeArrayList(data.routesToken), 1);
			String[][] routesList = Main.convertArrayListTo2DArray(routesInfo, 1);
			changesCheck(origRoutes, routesList);
		}
	}
	class RouteAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			String historyText = "";
			if (systemLocked == false) {
				//addRouteCount = 1;
				String[] airportsArray;		// create empty array that will be used for the method that finds the airports list
				String[] routesArray;
				routesArray = routesArrayList(routesInfo);
				airportsArray = convertAirportArrayListToArray(airportsInfo);	// call airportsArrayList method to get list of airports
				
				Route_addroute_dialogue routeDialog = new Route_addroute_dialogue(airportsArray, routesArray);
				routeDialog.showDialogue(airportsArray, routesArray);	// show the add route dialog box.
			}
			else {
				historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class RouteRemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String[] routesArray;
				routesArray = routesArrayList(routesInfo);
				Route_removeroute_dialogue removeDialog = new Route_removeroute_dialogue(routesArray);
				removeDialog.showDialogue(routesArray);
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class RouteFindListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String[] airportsArray;		// create empty array that will be used for the method that finds the airports list
				airportsArray = convertAirportArrayListToArray(airportsInfo);	// call airportsArrayList method to get list of airports
				String[] routesArray;
				routesArray = routesArrayList(routesInfo);
				Route_searchroute_dialogue searchDialog = new Route_searchroute_dialogue(airportsArray, routesArray);
				searchDialog.showDialogue(airportsArray, routesArray);
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class PathFindListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				//String[] airportsArray;		// create empty array that will be used for the method that finds the airports list
				airportsArray = convertAirportArrayListToArray(airportsInfo);	// call airportsArrayList method to get list of airports
				Route_findpath_dialogue findDialog = new Route_findpath_dialogue(airportsArray);
				findDialog.showDialogue(airportsArray);
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirOpenListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				Airport_Open_Dialog airportOpen = new Airport_Open_Dialog(closuresInfo);
			//	airportOpen.showDialogue(closuresInfo);
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirCloseListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String[] airportsArray;
				airportsArray = convertAirportArrayListToArray(airportsInfo);
				Airport_Close_Dialogue airDialogue = new Airport_Close_Dialogue(airportsArray);
				try {
					airDialogue.status(airportsArray);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String[] airportsArray;
				airportsArray = convertAirportArrayListToArray(airportsInfo);
				Airport_Add_Dialogue airDialogue = new Airport_Add_Dialogue(airportsArray);
				try {
					airDialogue.addAirportDialogue(airportsArray);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirDeleteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String[] airportsArray;
				airportsArray = convertAirportArrayListToArray(airportsInfo);
				Airport_Delete_Dialogue airDialogue = new Airport_Delete_Dialogue(airportsArray);
				try {
					airDialogue.deleteAirportDialogue(airportsArray);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirSearchListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class AirAllListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			if (systemLocked == false) {
				String historyText = "All Airports: ";
				int count = airportsInfo.size();
				int airCount = 0;
				for (int i = 0; i < count; i++) {
					if (airCount == 0) {
						historyText = historyText + " " +  airportsInfo.get(i);
					}
					else if (airCount == count) {
						historyText = historyText + " " + airportsInfo.get(i) + "\n";
					}
					else
						historyText = historyText + ", " + airportsInfo.get(i);
					airCount = airCount + 1;
				}
				historyText = historyText + "\n";
				historyArea.append(historyText);
			}
			else {
				String historyText = "You must open a valid file to be able to perform this action.\n";
				historyArea.append(historyText);
			}
		}
	}
	class HelpManualListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	public void actionPerformed(ActionEvent arg0) {

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
	
	// Method to add a new airport to the airportsInfo ArrayList
	static ArrayList<String> addAirport(ArrayList<String> airportsInfo, String newAirport) {
		airportsInfo.add(newAirport);
		return airportsInfo;
	}

	//Method for updating the route table
	static public void updateRoutesTable(Object[][] data) {
		//remove old route table
		routePanel.removeAll();

		//create new route table
		String[] columnNames = {"Route #", "Carrier", "Dep. Airport", "Dep. Time", "Arr. Airport", "Arr. Time", "Price"};
		JScrollPane routeTable = new JScrollPane(new JTable(data, columnNames) { public
			boolean isCellEditable(int rowIndex, int vColIndex) { return false;};});		routeTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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
		routeTable.getTableHeader().setReorderingAllowed(false);
		routeTable.getTableHeader().setResizingAllowed(false);
		JScrollPane routeTableScroll = new JScrollPane(routeTable); //create the routes table in a scrollPane

		routeTableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		routePanelWidth = (totalSize.width * 3 / 4) * 48 / 100;
		routePanelHeight = totalSize.height * 45/100;
		routeTableScroll.setPreferredSize(new Dimension(routePanelWidth, routePanelHeight));
		return routeTableScroll;
	}
	static public Component CreateResultsTable (Object[][] data, Dimension totalSize) {
		String[] columnNames = {"Route #",  "Carrier", "Dep. Airport", "Dep. Time", "Arr. Airport", "Arr. Time", "Price"};

		// disable cell editing
		JTable routeTable = new JTable(data, columnNames){
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			};
		};

		JScrollPane routeTableScroll = new JScrollPane(routeTable); //create the routes table in a scrollPane

		routeTableScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultsPanelWidth = (totalSize.width * 3 / 4) * 48 / 100;
		resultsPanelHeight = totalSize.height * 45 / 100;
		routeTableScroll.setPreferredSize(new Dimension(resultsPanelWidth, resultsPanelHeight));
		return routeTableScroll;
	}
	static public void updateResultsTable(Object[][] data) {
		//remove old route table
		resultsPanel.removeAll();

		//create new route table
		String[] columnNames = {"Route #", "Carrier", "Dep. Airport", "Dep. Time", "Arr. Airport", "Arr. Time", "Price"};
		JScrollPane routeTable = new JScrollPane(new JTable(data, columnNames) { public
			boolean isCellEditable(int rowIndex, int vColIndex) { return false;};});
		//create the routes table in a scrollPane
		routeTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		routeTable.setPreferredSize(new Dimension(resultsPanelWidth, resultsPanelHeight));
		resultsPanel.add(routeTable); //add the table to the JPanel
		resultsPanel.setBorder(BorderFactory.createTitledBorder("Search Results"));

		//display new route table
		resultsPanel.revalidate();
		resultsPanel.repaint();
	}
	static public void changesCheck(String[][] origRoutes, String[][] routesList) {
		boolean routeSame = true;
		if (origRoutes.length == routesList.length) {
			for (int i = 0; i < origRoutes.length; i++) {
				for (int j = 0; j < origRoutes[i].length; j++) {
					if (!(routesList[i][j].equals(origRoutes[i][j]))) {
						routeSame = false;
						break;
					}
				}
			}
		}
		else {
			routeSame = false;
		}
		if (routeSame == true) {
			System.exit(0);
		}
		else if (routeSame == false) {
			Object[] options = {"Yes", "No"};
			int checkInt = JOptionPane.showOptionDialog(frame,
					"There are changes to the route table that have not been saved! Would you like to save them?",
					"Changes Exist", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.CLOSED_OPTION, null, options, options[0]);
			if (checkInt == 0) {
				try {
					File_Open_Save_Dialog saveDialog = new File_Open_Save_Dialog(1);
					// check if save is successful
					System.exit(0);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
				System.exit(0);
		}
	}
	
	//Method to resort the routesArray according to the route number
	static ArrayList<String[]> sortRouteArray(ArrayList<String[]> routesArrayList) {

		ArrayList<String[]> routesInfo = new ArrayList<String[]>();
		
		for(int i = 0; i < routesArrayList.size(); i++) {
		
			boolean smallest = false; //for use in adding the current largest value to the new array lsit
			int location = -1;
			
			//get the i route in the original array list
			String[] theRoute = routesArrayList.get(i);
			int theRouteNum = Integer.parseInt(theRoute[0].substring(1));
			
			//if first route, just add it to the new array list
			if (i == 0) {			
				routesInfo.add(theRoute);
			}

			//all others must be compared to what is already in the new array list
			else {

				for (int j = 0; j < routesInfo.size(); j++) {

					int existingRouteNum = Integer.parseInt(routesInfo.get(j)[0].substring(1));
					
					if(theRouteNum > existingRouteNum) {
						location = j+1;
					}
					else if (location == -1) {
						smallest = true;
					}
				}	
				//if smaller than something add it to that location 
				if (smallest == false) {
					routesInfo.add(location, theRoute);
				}
				//otherwise add to the beginning
				else {
					routesInfo.add(0, theRoute);
				}
			}	
		}
		return routesInfo;
	}
}