import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class Airport_Delete_Dialogue extends JDialog {

	// Initialize variables
	static Airport_Delete_Dialogue Test;
	static private JLabel Airportlabel = new JLabel("Airport");

	// Initialize action buttons and button JPanel

	JButton Deletebutton = new JButton("Delete Airport");
	JButton Cancelbutton = new JButton("Cancel");
	JPanel Buttonpanel = new JPanel();

	// Initialize choice panels

	JPanel Userchoicepanel = new JPanel();
	JPanel Textpanel = new JPanel();
	JPanel Airportpanel = new JPanel();

	// String of airports for combo box

	static private String[] Airports = {"ATL", "ORL", "DCA", "JFK"};

	public Airport_Delete_Dialogue(String[] Airports) {
		
		setModal(true);

		JComboBox Airportchoice = new JComboBox(Airports);

		Textpanel.add(Airportlabel);
		Textpanel.setLayout(new BoxLayout(Textpanel, BoxLayout.Y_AXIS));

		Airportpanel.add(Airportchoice);
		Airportpanel.setLayout(new BoxLayout(Airportpanel, BoxLayout.Y_AXIS));

		// Combine into main JPanel

		Userchoicepanel.add(Textpanel);
		Userchoicepanel.add(Airportpanel);

		// Add the buttons to the panel

		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Deletebutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the delete button is clicked
		Deletebutton.addActionListener(new deleteListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Combine button panel and user choice panel to form dialogue box

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Userchoicepanel);
		add(Buttonpanel);

	}

	public static void deleteAirportDialogue(String[] airports) throws IOException {

		Test = new Airport_Delete_Dialogue(airports);

		Test.setSize(400,200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);	

	}
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
	class deleteListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			System.out.println("Delete Airport has been clicked");
		}
	}
}