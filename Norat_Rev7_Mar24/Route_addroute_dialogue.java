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

import javax.swing.*;

public class Route_addroute_dialogue extends JDialog {
	
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



	// Constructor for the dialogue box
	public Route_addroute_dialogue(String[] Airports) {

		setModal(true);

		String[] Carriers = {"Delta", "United"};

		// Initialize all input boxes and drop-down menus
		JTextField Routenumtxt = new JTextField("0");
		JTextField Pricetxt = new JTextField("0.00");

		JComboBox Carrierchoice = new JComboBox(Carriers);
		JComboBox DepAPchoice = new JComboBox(Airports);
		JComboBox ArrAPchoice = new JComboBox(Airports);

		TimeSelectBoxPanel Deptimechoice = new TimeSelectBoxPanel();
		TimeSelectBoxPanel Arrtimechoice = new TimeSelectBoxPanel();

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
			System.out.println("Add Route button clicked");
			
			//add the route to the array list and update the routes table
			String[] dummyRoute = {"Derp", "a", "b", "c", "d", "e", "f"};	 //TEMPORARY DUMMY ROUTE INFO
			Main.routesInfo = Main.addRoute(Main.routesInfo, dummyRoute); //add route to routesInfo ArrayList
			Object[][] routesArray = Main.convertArrayListTo2DArray(Main.routesInfo, 1); //turn routesInfo into a 2d array
			Main.updateRoutesTable(routesArray); //update the route table
					
		}
	}
	// action listener for when the cancel button has been pressed
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
}