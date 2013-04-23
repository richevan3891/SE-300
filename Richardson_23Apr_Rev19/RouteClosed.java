
import java.awt.Dimension;  
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.*;

public class RouteClosed {

	/**
	 * 
	 */
	static Airport_Route_Search Test;
	static ArrayList<String[]> routesInfo;
	// Initialize the search route and cancel buttons and panel
	JButton Searchroutebutton = new JButton("Find Routes");
	JButton Cancelbutton = new JButton("Close");
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
	public RouteClosed(String[][] routesList) {

	}
	// find routes based on departure airport, selected by user.
	public static Object[][] searchClosedRoute(String[][] routes) {
		int count = 0;
		Object[][] routesData = new Object[0][7];
		String historyText = "";
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][7].equals("false")) {
				count = count + 1;
			}
		}
		String[][] closedRoutes = new String[count][7];
		int routeCount = 0;
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][7].equals("false")) {
				closedRoutes[routeCount] = routes[i];
				routeCount = routeCount + 1;
			}
		}
		
		historyText = "There are " + count + " routes that are canceled due to airports being closed. They are listed in the results table abo" +
				"ve.";
		Main.historyArea.append(historyText + "\n");
		routesData = (Object[][])closedRoutes;
		return routesData;
	}
	// find routes based on arrival airport, selected by user.

}