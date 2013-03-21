// The purpose of this code is to provide a panel that contains two
// dropdown menus for selecting a time.

// Last edit: 6 Mar

import javax.swing.*;

public class TimeSelectBoxPanel extends JPanel {

	// Initialize static strings
	static private String[] Hourlist = {"00","01","02","03","04","05","06","07","08","09",
		"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	static private String[] Minutelist = {"00","05","10","15","20","25","30",
		"35","40","45","50","55"};

	// Initialize ComboBoxes
	JComboBox Hourchoice = new JComboBox(Hourlist);
	JComboBox Minutechoice = new JComboBox(Minutelist);

	// Constructor
	public TimeSelectBoxPanel() {

		// Set the layout and add the dropdowns
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(Hourchoice);
		add(Minutechoice);

	}

	// Main method for testing the class
	public static void main(String[] args){

		JFrame Test = new JFrame();
		TimeSelectBoxPanel TestPanel = new TimeSelectBoxPanel();
		Test.add(TestPanel);

		//Test.setSize(500,650);									// Set the size
		Test.setLocationRelativeTo(null);						// Center it on the screen
		Test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// If click the exit, close it
		//Test.setResizable(false);
		Test.setVisible(true);									// Make it visible	
	}
}