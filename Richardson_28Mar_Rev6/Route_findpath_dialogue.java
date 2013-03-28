// The purpose of this code is to provide the mechanism by which a
// user can find a path in the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 March 2013


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

	//Main mainClass = new Main();
	static Route_findpath_dialogue Test;
	// Initialize static labels
	static private JLabel Carrierlabel = new JLabel("Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Departure Time", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrival Time", SwingConstants.CENTER);
	static private JLabel Optionslabel = new JLabel("Options", SwingConstants.CENTER);

	static ArrayList<String[]> routesInfo;

	// Initialize check boxes
	static JCheckBox Checkdeptime = new JCheckBox();
	static JCheckBox Checkarrtime = new JCheckBox();
	static JCheckBox Checkcarrier = new JCheckBox();

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

		Choicepanel.setLayout(new BoxLayout(Choicepanel, BoxLayout.X_AXIS));
		Choicepanel.setAlignmentY(CENTER_ALIGNMENT);
		Choicepanel.add(Selectpanel);
		Choicepanel.add(Box.createHorizontalGlue());
		Choicepanel.add(Optionpanel);

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Findpathbutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the findPath button is clicked
		Findpathbutton.addActionListener(new findPathListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// add item listener for when departure time check clicked
		Checkdeptime.addItemListener(new checkListener());

		// add item listener for when arrival time check clicked
		Checkarrtime.addItemListener(new checkListener());

		// add item listener for when carrier clicked
		Checkcarrier.addItemListener(new checkListener());

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);

	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports){
		Test = new Route_findpath_dialogue(airports);
		Test.setTitle("Find Path");								// Rename the title
		Test.setSize((width * 4 / 10), (height * 4 / 10));		// Set the size
		Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	
	}	
	class findPathListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {

			String historyText = "";
			

			// retrieve arrival airport
			int arrAirportInt = 0;
			String arrAirportString = "";
			arrAirportInt = ArrAPchoice.getSelectedIndex();
			arrAirportString = airports[arrAirportInt];

			// retrieve departure airport
			int depAirportInt = 0;
			String depAirportString = "";
			depAirportInt = DepAPchoice.getSelectedIndex();
			depAirportString = airports[depAirportInt];

			// retrieve carrier
			int carrierInt = 0;
			String carrierString = "";
			carrierInt = Carrierchoice.getSelectedIndex();
			carrierString = Carriers[carrierInt];

			// Deptimechoice;
			String depTimeHrString = "";
			String depTimeMinString = "";
			int depTimeHrInt = 0;
			int depTimeMinInt = 0;

			depTimeHrInt = Deptimechoice.Hourchoice.getSelectedIndex();
			depTimeHrString = Deptimechoice.Hourlist[depTimeHrInt];

			depTimeMinInt = Deptimechoice.Minutechoice.getSelectedIndex();
			depTimeMinString = Deptimechoice.Minutelist[depTimeMinInt];
			// Arrtimechoice;

			String arrTimeHrString = "";
			String arrTimeMinString = "";
			int arrTimeHrInt = 0;
			int arrTimeMinInt = 0;

			arrTimeHrInt = Arrtimechoice.Hourchoice.getSelectedIndex();
			arrTimeHrString = Arrtimechoice.Hourlist[arrTimeHrInt];

			arrTimeMinInt = Arrtimechoice.Minutechoice.getSelectedIndex();
			arrTimeMinString = Arrtimechoice.Minutelist[arrTimeMinInt];

			// start doing stuff to search the routes!
			int depTime = checkListener.index[0];
			int arrTime = checkListener.index[1];
			int carrierCheck = checkListener.index[2];

			FileInput input = null;
			try {
				input = new FileInput();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			routesInfo = input.routeArrayList(input.routesToken);
			Object[][] data = new String[0][7];
			try { 
				data = Main.convertArrayListTo2DArray(routesInfo, 1);		
			}
			catch (NullPointerException e1) { //if there are any errors in input.txt
				System.out.println("There are errors in the input.txt file.");
			}

			//System.out.println("First route number: " + route);

			// only worrying about one individual check box checked
			// need to look at the departure times in the routes table and compare them with the times that are input into
			// the find route dialog.
			// dealing first with the departure time
			String[] routeNums;
			if (depTime == 1) {
				routeNums = matchDepTime(depTimeHrString, depTimeMinString, data);
			}
			else if (arrTime == 1) {

			}
			else if (carrierCheck == 1) {

			}

		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
	static class checkListener implements ItemListener{
		static int[] index = new int[]{0, 0, 0};
		public void itemStateChanged(ItemEvent event) {
			Object check = event.getSource();
			if (check == Checkdeptime) {
				if (Checkdeptime.isSelected()) {
					index[0] = 1;
				}
				else
					index[0] = 0;
			}
			else if (check == Checkarrtime) {
				if (Checkarrtime.isSelected()) {
					index[1] = 1;
				}
				else 
					index[1] = 0;
			}
			else if (check == Checkcarrier) {
				if (Checkcarrier.isSelected()) {
					index[2] = 1;
				}
				else
					index[2] = 0;
			}
			// 
		}
	}
	public static String[] matchDepTime (String depTimeHrString, String depTimeMinString, Object[][] data) {
		Main mainClass = new Main();
		String[] routeNums = null;
		String[][] routes = (String[][])data;
		String route = "";

		int count = 0;
		int routeCount = 0;
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][3].equals((" " + depTimeHrString + depTimeMinString))) {
				count = count + 1;
			}
		}
		routeNums = new String[count];
		String historyText = "Your departure time of " + depTimeHrString + depTimeMinString + " has matched " +
				count + " routes.";
		mainClass.historyArea.append(historyText + "\n");
		for (int i = 0; i < routes.length; i++) {
			if (routes[i][3].equals((" " + depTimeHrString + depTimeMinString))) {
				route = routes[i][0];
				routeNums[routeCount] = route;
				routeCount = routeCount + 1;
				historyText = "Route: " + route + " matches.";
				mainClass.historyArea.append(historyText + "\n");
			}
		}
		// routeNums holds the route numbers that are found that match the departure times!
		return routeNums;
	}
}