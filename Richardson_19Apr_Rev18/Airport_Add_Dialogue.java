import java.awt. *;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Airport_Add_Dialogue extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Main mainClass = new Main();
	// Initializing variables
	static Airport_Add_Dialogue Test;
	static private JLabel Airportname = new JLabel("New Airport Name: ");

	// Initializing action buttons and JPanel

	JButton Addbutton = new JButton("Add");
	JButton Cancelbutton = new JButton("Cancel");

	JPanel Userchoicepanel = new JPanel();
	JPanel Textpanel = new JPanel();
	JPanel Inputpanel = new JPanel();
	JPanel Buttonpanel = new JPanel();

	JFormattedTextField userInputField;

	// Class to set the limit for the text field to three

	// Initializing text field

	public Airport_Add_Dialogue(String[] Airports) {

		setModal(true);

		userInputField = new JFormattedTextField();
		MaskFormatter airportCodeMask = null;
		try {
			airportCodeMask = new MaskFormatter("UUU");
		} catch (Exception e) {

		}

		airportCodeMask.install(userInputField);
		// Only enable letters to be entered into the text box		

		// Add text field to panels

		Textpanel.add(Airportname);
		Textpanel.setLayout(new BoxLayout(Textpanel, BoxLayout.Y_AXIS));

		Inputpanel.add(userInputField);
		userInputField.setPreferredSize(new Dimension(60, 20));
		// Textfield.getText().toUpperCase(); // use when obtaining text from
		// the box
		//userInputField.setDocument(new JTextFieldLimit(3)); // sets limit to three
		// using above class
		Inputpanel.setLayout(new BoxLayout(Inputpanel, BoxLayout.Y_AXIS));

		// Add buttons to button panel

		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Addbutton);
		Buttonpanel.add(Cancelbutton);

		// add action listener for when the add button is clicked
		Addbutton.addActionListener(new addListener());

		// add action listener for when the cancel button is clicked
		Cancelbutton.addActionListener(new cancelListener());

		// Combine panels into the dialogue box

		Userchoicepanel.add(Textpanel);
		Userchoicepanel.add(Inputpanel);

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Userchoicepanel);
		add(Buttonpanel);

	}

	public static void addAirportDialogue(String[] airports) throws IOException {

		Test = new Airport_Add_Dialogue(airports);
		Test.setSize(400, 200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);
	}

	class cancelListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}

	class addListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {

			String airportCode = "";
			boolean airportCodeError = false;
			boolean airportExists = false;

			try {
				airportCode = userInputField.getText().toUpperCase().trim();
			} catch (Exception e) {

				airportCodeError = true;
			}
			if ((airportCode.contains(" ")) || (airportCode.length() != 3)) {
				airportCodeError = true;
			}
			for (int i = 0; i < Main.airportsInfo.size(); i++) {

				if (Main.airportsInfo.get(i).equals(airportCode)) {
					airportExists = true;
				}
			}
			// Add airport to airport array list
			if ((airportCodeError == false) && (airportExists == false)){
				String tempAirport = airportCode;
				Main.airportsInfo = Main.addAirport(Main.airportsInfo, tempAirport);
				@SuppressWarnings("unused")
				String[] airportsArray = Main.convertAirportArrayListToArray(Main.airportsInfo);
				String historyText = "Airport " + airportCode + " has been successfully added to the list of available airports.";
				Main.historyArea.append(historyText + "\n");
				Test.dispose();

			}

			else if (airportCodeError == true) {
				String historyText = "Airport entry was incorrect.  Please enter a three letter airport code.";
				Main.historyArea.append(historyText + "\n");
			}

			else if (airportExists = true) {
				String historyText = "Airport already exists!  Please enter a new airport.";
				Main.historyArea.append(historyText + "\n");
			}
		}
	}
}