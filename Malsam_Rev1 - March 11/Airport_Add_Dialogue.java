
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.text.*;

public class Airport_Add_Dialogue extends JFrame {
	
	// Initializing variables
	
	static private JLabel Airportname = new JLabel("New Airport Name: ");
	
	// Initializing action buttons and JPanel
	
	JButton Addbutton = new JButton("Add");
	JButton Cancelbutton = new JButton("Cancel");
	
	JPanel Userchoicepanel = new JPanel();
	JPanel Textpanel = new JPanel();
	JPanel Inputpanel = new JPanel();
	JPanel Buttonpanel = new JPanel();
	
	
	// Class to set the limit for the text field to three
	
	class JTextFieldLimit extends PlainDocument {
		  private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }

		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }

		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;

		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
		}
	
	
	// Initializing text field
	
	JTextField Textfield = new JTextField();


	
	// Dialogue box - use constructors
	
	public Airport_Add_Dialogue(String[] Airports) {
		
		// Add text field to panels
		
		Textpanel.add(Airportname);
		Textpanel.setLayout(new BoxLayout(Textpanel, BoxLayout.Y_AXIS));
		
		Inputpanel.add(Textfield);
		Textfield.setPreferredSize(new Dimension(60,20));
		//Textfield.getText().toUpperCase();               // use when obtaining text from the box
		Textfield.setDocument(new JTextFieldLimit(3));     // sets limit to three using above class
		Inputpanel.setLayout(new BoxLayout(Inputpanel, BoxLayout.Y_AXIS));
		
		// Add buttons to button panel
		
		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Addbutton);
		Buttonpanel.add(Cancelbutton);
		
		// Combine panels into the dialogue box
		
		Userchoicepanel.add(Textpanel);
		Userchoicepanel.add(Inputpanel);
		
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(Userchoicepanel);
		add(Buttonpanel);
		
	}
	
	public static void addAirportDialogue(String[] airports) throws IOException {
		
		Airport_Add_Dialogue Test = new Airport_Add_Dialogue(airports);
		
		Test.setSize(400,200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);
	}

}
