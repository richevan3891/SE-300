// The purpose of this code is to provide the mechanism by which a
// user can add a route to the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 March 2013


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

public class Route_addroute_dialogue extends JFrame {
	
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
		Buttonpanel.add(Cancelbutton);
		Buttonpanel.add(Addroutebutton);
		
		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);
		
	}
	
	
	// Method for displaying the dialogue box
	public void showDialogue(){
		
		this.setTitle("Add Route");								// Rename the title
		this.setSize((width * 6 / 10), (height * 2 / 10));		// Set the size
		this.setResizable(false);								// No resizing
		this.setLocationRelativeTo(null);						// Center it
		this.setVisible(true);									// Make it visible
		
	}
	
	
}