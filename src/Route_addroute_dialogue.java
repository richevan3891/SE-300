// The purpose of this code is to provide the mechanism by which a
// user can add a route to the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 4 March 2013


import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class Route_addroute_dialogue extends JFrame {
	
	// Initialize static labels
	static private JLabel Routenumlabel = new JLabel("Route\nNumber");
	static private JLabel Carrierlabel = new JLabel("Carrier");
	static private JLabel DepAPlabel = new JLabel("Departure\nAirport");
	static private JLabel Deptimelabel = new JLabel("Departure\nTime");
	static private JLabel ArrAPlabel = new JLabel("Arrival\nAirport");
	static private JLabel Arrtimelabel = new JLabel("Arrival\nTime");
	static private JLabel Pricelabel = new JLabel("Price");
	
	// Initialize static strings
	static private String[] Hourlist = {"0","1","2","3","4","5","6","7","8","9",
		"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	static private String[] Minutelist = {"00","05","10","15","20","25","30",
		"35","40","45","50","55"};
	
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
	
	
	// Constructor for the dialogue box
	public Route_addroute_dialogue(String[] Carriers, String[] Airports) {
		
		// Initialize all input boxes and drop-down menus
		JTextField Routenumtxt = new JTextField("0");
		JTextField Pricetxt = new JTextField("0.00");
		
		JComboBox Carrierchoice = new JComboBox(Carriers);
		JComboBox DepAPchoice = new JComboBox(Airports);
		JComboBox ArrAPchoice = new JComboBox(Airports);
		
		JComboBox DepHourchoice = new JComboBox(Hourlist);
		JComboBox DepMinutechoice = new JComboBox(Minutelist);
		
		JComboBox ArrHourchoice = new JComboBox(Hourlist);
		JComboBox ArrMinutechoice = new JComboBox(Minutelist);
		
		// Combine choice fields with their appropriate labels in panels
		Routenumpanel.add(Routenumlabel);
		Routenumpanel.add(Routenumtxt);
		Routenumpanel.setLayout(new BoxLayout(Routenumpanel, BoxLayout.Y_AXIS));
		
		Carrierpanel.add(Carrierlabel);
		Carrierpanel.add(Carrierchoice);
		Carrierpanel.setLayout(new BoxLayout(Carrierpanel, BoxLayout.Y_AXIS));
		
		DepAPpanel.add(DepAPlabel);
		DepAPpanel.add(DepAPchoice);
		DepAPpanel.setLayout(new BoxLayout(DepAPpanel, BoxLayout.Y_AXIS));
		
		Deptimepanel.add(Deptimelabel);
		Deptimepanel.add(DepHourchoice);
		Deptimepanel.add(DepMinutechoice);
		Deptimepanel.setLayout(new BoxLayout(Deptimepanel, BoxLayout.Y_AXIS));
		
		ArrAPpanel.add(ArrAPlabel);
		ArrAPpanel.add(ArrAPchoice);
		ArrAPpanel.setLayout(new BoxLayout(ArrAPpanel, BoxLayout.Y_AXIS));
		
		Arrtimepanel.add(Arrtimelabel);
		Arrtimepanel.add(ArrHourchoice);
		Arrtimepanel.add(ArrMinutechoice);
		Arrtimepanel.setLayout(new BoxLayout(Arrtimepanel, BoxLayout.Y_AXIS));
		
		Pricepanel.add(Pricelabel);
		Pricepanel.add(Pricetxt);
		Pricepanel.setLayout(new BoxLayout(Pricepanel, BoxLayout.Y_AXIS));
		
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
		Buttonpanel.add(Cancelbutton);
		Buttonpanel.add(Addroutebutton);
		
		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);
		
	}
	
	// Main method for testing the class
	public static void main(String[] args){
		String[] Carriers = {"Delta", "Southwest", "United"};
		String[] Airports = {"OMA", "DAB", "MCO"};
		
		Route_addroute_dialogue Test = new Route_addroute_dialogue(Carriers, Airports);
		
		//Test.setSize(500,650);									// Set the size
		Test.setLocationRelativeTo(null);						// Center it on the screen
		Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// If click the exit, close it
		Test.setVisible(true);									// Make it visible
		
	}
	
	
}