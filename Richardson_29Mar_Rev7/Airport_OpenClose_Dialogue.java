

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class Airport_OpenClose_Dialogue extends JDialog {

	// Initializing variables

	static Airport_OpenClose_Dialogue Test;
	static private JLabel Airportlabel = new JLabel("Airport");
	static private JLabel Fromlabel = new JLabel("From");
	static private JLabel Tolabel = new JLabel("To");

	// Initializing strings

	static private String[] hourList = {"0","1","2","3","4","5","6","7","8","9",
		"10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	static private String[] minuteList = {"00","05","10","15","20","25","30",
		"35","40","45","50","55"};

	// Initializing action buttons and JPanel

	JButton Cancelbutton = new JButton("Cancel");
	JButton Openbutton = new JButton("Open Airport");
	JButton Closebutton = new JButton("Close Airport");
	JPanel Buttonpanel = new JPanel();

	// Initializing the choice panels

	JPanel Userchoicepanel = new JPanel();
	JPanel Airportpanel = new JPanel();
	JPanel Frompanel = new JPanel();
	JPanel Topanel = new JPanel();

	// Dialogue box - Use constructors

	public Airport_OpenClose_Dialogue(String[] Airports) {
		
		setModal(true);

		JComboBox Airportchoice = new JComboBox(Airports);
		JComboBox Starttimehours = new JComboBox(hourList);
		JComboBox Starttimeminutes = new JComboBox(minuteList);
		JComboBox Endtimehours = new JComboBox(hourList);
		JComboBox Endtimeminutes = new JComboBox(minuteList);

		Airportpanel.add(Airportlabel);
		Airportpanel.add(Airportchoice);
		Airportpanel.setLayout(new BoxLayout(Airportpanel, BoxLayout.Y_AXIS));

		Frompanel.add(Fromlabel);
		Frompanel.add(Starttimehours);
		Frompanel.add(Starttimeminutes);
		Frompanel.setLayout(new BoxLayout(Frompanel, BoxLayout.Y_AXIS));

		Topanel.add(Tolabel);
		Topanel.add(Endtimehours);
		Topanel.add(Endtimeminutes);
		Topanel.setLayout(new BoxLayout(Topanel, BoxLayout.Y_AXIS));

		// Combine each individual panel into one overall JPanel

		Userchoicepanel.add(Airportpanel);
		Userchoicepanel.add(Frompanel);
		Userchoicepanel.add(Topanel);

		// Add buttons to overall "button" panel

		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Openbutton);
		Buttonpanel.add(Closebutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the cancel button is clicked
		Openbutton.addActionListener(new openListener());

		// add action listener for when the cancel button is clicked
		Closebutton.addActionListener(new closeListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Combine the user choice panel and the button panel into the dialogue box

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Userchoicepanel);
		add(Buttonpanel);

	}

	public static void status(String[] airports) throws IOException {

		Test = new Airport_OpenClose_Dialogue(airports);
		Test.setTitle("Set Airport Status");
		Test.setSize(600,200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);
	}
	// action listener for opening an airport
	class openListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Open button clicked");
		}
	}
	// action listener for closing an airport
	class closeListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Close button clicked");
		}
	}
	// action listener for canceling the status box.
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
}