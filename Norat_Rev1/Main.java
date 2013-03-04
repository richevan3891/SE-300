import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main implements ActionListener{
	JFrame frame;
	Dimension totalSize;
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
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		String[] columnNames = {"Route #",
                "Carrier",
                "Dep. Airport",
                "Dep. Time",
                "Arr. Airport",
                "Arr. Time",
                "Price"};
		FileInput input = new FileInput();
		ArrayList routesInfo = input.routeArrayList(input.routesToken);
		Object[][] data = input.convertArrayListTo2DArray(routesInfo);
		 
		JPanel routePanel = new JPanel();
		JScrollPane routeTable = new JScrollPane(new JTable(data, columnNames));
		routeTable.setPreferredSize(new Dimension((totalSize.width * 3 / 4) * 48 / 100, totalSize.height / 3));
		routePanel.add(routeTable);
		routePanel.setBorder(BorderFactory.createTitledBorder("Route Table"));
		//------------------------------------------------end of route table--------------------------------------------------------
		JPanel histPanel = new JPanel();
		histPanel.setPreferredSize(new Dimension(totalSize.width, totalSize.height / 5));
		histPanel.setBorder(BorderFactory.createTitledBorder("History"));
		//------------------------------------------------end of history table--------------------------------------------------------
		JPanel resultsPanel = new JPanel();
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

		menuItem = new JMenuItem("Cheapest");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteCheapListener());

		menuItem = new JMenuItem("Fastest");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteFastListener());

		menuItem = new JMenuItem("Find Route");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteFindListener());

		menuItem = new JMenuItem("All Routes");
		menu.add(menuItem);
		menuItem.addActionListener(new RouteAllListener());

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

		menuItem = new JMenuItem("Search");
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

		}
	}
	class FileSaveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

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

		}
	}
	class RouteRemoveListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class RouteCheapListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class RouteFastListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class RouteFindListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class RouteAllListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class AirOpenListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class AirCloseListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

		}
	}
	class AirAddListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

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
}