// The purpose of this code is to provide the mechanism by which a
// user can remove a route to the ARP program. It occurs as a dialogue
// box with one option.

// Last edit: 8 March 2013


import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Route_removeroute_dialogue extends JDialog {

	static Route_removeroute_dialogue Test;
	// Initialize label and panels
	static private JLabel Routenumlabel = new JLabel("Route Number", SwingConstants.CENTER);
	JPanel Routenumpanel = new JPanel();
	JPanel Choicepanel = new JPanel();

	// Initialize the remove route and cancel buttons and panel
	JButton Remroutebutton = new JButton("Remove Route");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Get the screen size
	Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;
	
	JComboBox Routenumchoice;

	// Constructor
	public Route_removeroute_dialogue(String[] Routenums) {
		
		setModal(true);

		Routenumchoice = new JComboBox(Routenums);

		// Add the label and menu to the Route Number Panel
		Routenumpanel.add(Routenumlabel);
		Routenumpanel.add(Routenumchoice);
		Routenumpanel.setLayout(new BoxLayout(Routenumpanel, BoxLayout.Y_AXIS));
		Routenumpanel.setAlignmentY(CENTER_ALIGNMENT);

		// Add all choice panels to the overall choice panel
		Choicepanel.setLayout(new FlowLayout());
		Choicepanel.add(Routenumpanel);

		// Add the buttons to the overall button panel
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Remroutebutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the cancel button is clicked
		Remroutebutton.addActionListener(new remRouteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);


	}

	// Method for displaying the dialogue box
	public void showDialogue(String[] routes){
		Test = new Route_removeroute_dialogue(routes);
		Test.setTitle("Remove Route");							// Rename the title
		Test.setSize((width * 2 / 10), (height * 2 / 10));		// Set the size
		Test.setResizable(false);								// No resizing
		Test.setLocationRelativeTo(null);						// Center it
		Test.setVisible(true);									// Make it visible	
	}
	class remRouteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Remove route button pressed");

			removeRoute(Main.routesInfo, Routenumchoice.getSelectedIndex()); //remove route from routesInfo
			Routenumchoice.removeItemAt(Routenumchoice.getSelectedIndex()); //remove route number from combobox
			Object[][] routesArray = Main.convertArrayListTo2DArray(Main.routesInfo, 1);
			Main.updateRoutesTable(routesArray); //update routes table
		}
	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}

	//Method to remove a route from the routesInfo ArrayList
	static ArrayList<String[]> removeRoute(ArrayList<String[]> routesInfo, int routeNumberIndex) {
		routesInfo.remove(routeNumberIndex);
		return routesInfo;
	}
}