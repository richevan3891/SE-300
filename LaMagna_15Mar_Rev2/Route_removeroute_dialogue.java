// The purpose of this code is to provide the mechanism by which a
// user can remove a route to the ARP program. It occurs as a dialogue
// box with one option.

// Last edit: 8 March 2013


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.*;

public class Route_removeroute_dialogue extends JFrame {
	
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
	
	// Constructor
	public Route_removeroute_dialogue(String[] Routenums) {
		
		JComboBox Routenumchoice = new JComboBox(Routenums);
		
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
		Buttonpanel.add(Cancelbutton);
		Buttonpanel.add(Remroutebutton);
		
		// Add the choice panel and button to the dialogue box
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Choicepanel);
		add(Buttonpanel);
		
		
	}
	
	// Method for displaying the dialogue box
	public void showDialogue(){
		
		this.setTitle("Remove Route");							// Rename the title
		this.setSize((width * 2 / 10), (height * 2 / 10));		// Set the size
		this.setResizable(false);								// No resizing
		this.setLocationRelativeTo(null);						// Center it
		this.setVisible(true);									// Make it visible
		
	}
	
	
	
}