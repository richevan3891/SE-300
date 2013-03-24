// The purpose of this code is to provide the mechanism by which a
// user can search for a route in the ARP program. It occurs as a dialogue
// box with several options.

// Last edit: 8 March 2013


import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Route_searchroute_dialogue extends JDialog {

	static Route_searchroute_dialogue Test;
	// Initialize static labels
	static private JLabel Routenumlabel = new JLabel("Route Number", SwingConstants.CENTER);
	static private JLabel Carrierlabel = new JLabel("Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Departure Time", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrival Time", SwingConstants.CENTER);

	// Initialize check boxes
	JCheckBox Checkroutenum = new JCheckBox();
	JCheckBox CheckdepAP = new JCheckBox();
	JCheckBox CheckarrAP = new JCheckBox();
	JCheckBox Checkdeptime = new JCheckBox();
	JCheckBox Checkarrtime = new JCheckBox();
	JCheckBox Checkcarrier = new JCheckBox();

	// Initialize the search route and cancel buttons and panel
	JButton Searchroutebutton = new JButton("Search");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels
	JPanel Choicepanel = new JPanel();

	JPanel Routenumpanel = new JPanel();
	JPanel Routenumcheckpanel = new JPanel();

	JPanel DepAPpanel = new JPanel();
	JPanel DepAPcheckpanel = new JPanel();
	JPanel ArrAPpanel = new JPanel();
	JPanel ArrAPcheckpanel = new JPanel();

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



	// Constructor for the dialogue box
	public Route_searchroute_dialogue(String[] Airports, String[] Routenums) {
		
		setModal(true);

		String[] Carriers = {"Delta", "United"};
		JComboBox Routenumchoice = new JComboBox(Routenums);

		// Initialize all drop-down menus
		JComboBox Carrierchoice = new JComboBox(Carriers);
		JComboBox DepAPchoice = new JComboBox(Airports);
		JComboBox ArrAPchoice = new JComboBox(Airports);

		TimeSelectBoxPanel Deptimechoice = new TimeSelectBoxPanel();
		TimeSelectBoxPanel Arrtimechoice = new TimeSelectBoxPanel();


		// Try Overall Gridpanel
		Choicepanel.setLayout(new GridLayout(6,3));
		Choicepanel.add(Checkroutenum);
		Checkroutenum.setAlignmentX(RIGHT_ALIGNMENT);
		Choicepanel.add(Routenumlabel);
		Choicepanel.add(Routenumchoice);

		Choicepanel.add(CheckdepAP);
		Choicepanel.add(DepAPlabel);
		Choicepanel.add(DepAPchoice);

		Choicepanel.add(CheckarrAP);
		Choicepanel.add(ArrAPlabel);
		Choicepanel.add(ArrAPchoice);

		Choicepanel.add(Checkdeptime);
		Choicepanel.add(Deptimelabel);
		Choicepanel.add(Deptimechoice);

		Choicepanel.add(Checkarrtime);
		Choicepanel.add(Arrtimelabel);
		Choicepanel.add(Arrtimechoice);

		Choicepanel.add(Checkcarrier);
		Choicepanel.add(Carrierlabel);
		Choicepanel.add(Carrierchoice);

		/*
		// Create all choice panels and combine with checkpanels
		Routenumpanel.setLayout(new BoxLayout(Routenumpanel, BoxLayout.X_AXIS));
		Routenumpanel.setAlignmentY(CENTER_ALIGNMENT);
		Routenumpanel.add(Routenumlabel);
		//Routenumpanel.add(Box.createHorizontalBox());
		Routenumpanel.add(Routenumchoice);
		Routenumcheckpanel.add(Checkroutenum);
		//Routenumcheckpanel.add(Box.createHorizontalGlue());
		Routenumcheckpanel.add(Routenumpanel);

		DepAPpanel.add(DepAPlabel);
		DepAPpanel.add(DepAPchoice);
		DepAPpanel.setLayout(new BoxLayout(DepAPpanel, BoxLayout.X_AXIS));
		DepAPpanel.setAlignmentY(CENTER_ALIGNMENT);
		DepAPcheckpanel.add(CheckdepAP);
		DepAPcheckpanel.add(DepAPpanel);

		ArrAPpanel.add(ArrAPlabel);
		ArrAPpanel.add(ArrAPchoice);
		ArrAPpanel.setLayout(new BoxLayout(ArrAPpanel, BoxLayout.X_AXIS));
		ArrAPpanel.setAlignmentY(CENTER_ALIGNMENT);
		ArrAPcheckpanel.add(CheckarrAP);
		ArrAPcheckpanel.add(ArrAPpanel);

		Deptimepanel.add(Deptimelabel);
		Deptimepanel.add(Deptimechoice);
		Deptimepanel.setLayout(new BoxLayout(Deptimepanel, BoxLayout.X_AXIS));
		Deptimepanel.setAlignmentY(CENTER_ALIGNMENT);
		Deptimecheckpanel.add(Checkdeptime);
		Deptimecheckpanel.add(Deptimepanel);

		Arrtimepanel.add(Arrtimelabel);
		Arrtimepanel.add(Arrtimechoice);
		Arrtimepanel.setLayout(new BoxLayout(Arrtimepanel, BoxLayout.X_AXIS));
		Arrtimepanel.setAlignmentY(CENTER_ALIGNMENT);
		Arrtimecheckpanel.add(Checkarrtime);
		Arrtimecheckpanel.add(Arrtimepanel);

		Carrierpanel.add(Carrierlabel);
		Carrierpanel.add(Carrierchoice);
		Carrierpanel.setLayout(new BoxLayout(Carrierpanel, BoxLayout.X_AXIS));
		Carrierpanel.setAlignmentY(CENTER_ALIGNMENT);
		Carriercheckpanel.add(Checkcarrier);
		Carriercheckpanel.add(Carrierpanel);

		// Add all checkpanels to the overall choice panel
		Choicepanel.setLayout(new BoxLayout(Choicepanel, BoxLayout.Y_AXIS));
		Choicepanel.setAlignmentY(CENTER_ALIGNMENT);
		Choicepanel.add(Routenumcheckpanel);
		Choicepanel.add(DepAPcheckpanel);
		Choicepanel.add(ArrAPcheckpanel);
		Choicepanel.add(Deptimecheckpanel);
		Choicepanel.add(Arrtimecheckpanel);
		Choicepanel.add(Carriercheckpanel);
		 */

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Searchroutebutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the cancel button is clicked
		Searchroutebutton.addActionListener(new searchRouteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);

	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] airports, String[] routes){
		Test = new Route_searchroute_dialogue(airports, routes);
		Test.setTitle("Search Route");							// Rename the title
		Test.setSize((width * 2 / 10), (height * 4 / 10));		// Set the size
		Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	
	}
	class searchRouteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Search Route button has been pressed");
		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
}