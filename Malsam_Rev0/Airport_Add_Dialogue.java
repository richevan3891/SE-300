
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import java.text.*;

public class Airport_Add_Dialogue extends JFrame {
	
	// Initializing variables
	
	static private JLabel Airportname = new JLabel("New Airport Name: ");
	
	// Initializing action buttons and JPanel
	
	JButton Addbutton = new JButton("Add");
	JButton Cancelbutton = new JButton("Cancel");
	
	JPanel Userchoicepanel = new JPanel();
	JPanel Buttonpanel = new JPanel();
	
	// Initializing text field
	
	JFormattedTextField Textfield = new JFormattedTextField();
	//Textfield.setColumns(3);


	
	// Dialogue box - use constructors
	
	public Airport_Add_Dialogue(String[] Airports) {
		
		// Add text field to panel
		Userchoicepanel.add(Airportname);
		Userchoicepanel.add(Textfield);
		Userchoicepanel.setLayout(new BoxLayout(Userchoicepanel, BoxLayout.Y_AXIS));
		
		// Add buttons to button panel
		
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Addbutton);
		Buttonpanel.add(Cancelbutton);
		
		// Combine panels into the dialogue box
		
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Userchoicepanel);
		add(Buttonpanel);
		
	}
	
	public static void main(String[] args) throws IOException {
		
		Airport_Add_Dialogue Test = new Airport_Add_Dialogue(args);
		
		Test.setSize(400,200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);
	}

}
