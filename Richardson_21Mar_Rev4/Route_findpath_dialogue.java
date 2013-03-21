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
import javax.swing.*;

public class Route_findpath_dialogue extends JFrame {

	static Route_findpath_dialogue Test;
	// Initialize static labels
	static private JLabel Carrierlabel = new JLabel("Carrier", SwingConstants.CENTER);
	static private JLabel DepAPlabel = new JLabel("Departure Airport", SwingConstants.CENTER);
	static private JLabel Deptimelabel = new JLabel("Departure Time", SwingConstants.CENTER);
	static private JLabel ArrAPlabel = new JLabel("Arrival Airport", SwingConstants.CENTER);
	static private JLabel Arrtimelabel = new JLabel("Arrival Time", SwingConstants.CENTER);
	static private JLabel Optionslabel = new JLabel("Options", SwingConstants.CENTER);

	// Initialize check boxes
	JCheckBox Checkdeptime = new JCheckBox();
	JCheckBox Checkarrtime = new JCheckBox();
	JCheckBox Checkcarrier = new JCheckBox();

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



	// Constructor for the dialogue box
	public Route_findpath_dialogue(String[] Airports) {

		String[] Carriers = {"Delta", "United"};

		// Initialize all drop-down menus
		JComboBox Carrierchoice = new JComboBox(Carriers);
		JComboBox DepAPchoice = new JComboBox(Airports);
		JComboBox ArrAPchoice = new JComboBox(Airports);

		TimeSelectBoxPanel Deptimechoice = new TimeSelectBoxPanel();
		TimeSelectBoxPanel Arrtimechoice = new TimeSelectBoxPanel();

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
			System.out.println("Find Path button pressed");
		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
}