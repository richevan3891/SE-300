

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

public class Airport_Close_Dialogue extends JDialog {

	// Initializing variables

	static Airport_Close_Dialogue Test;
	static private JLabel Airportlabel = new JLabel("Airport");
	static private JLabel Fromlabel = new JLabel("From");
	static private JLabel Tolabel = new JLabel("To");


	TimeSelectBoxPanel startTimeChoice = new TimeSelectBoxPanel();
	TimeSelectBoxPanel endTimeChoice = new TimeSelectBoxPanel();

	// Initializing action buttons and JPanel

	JButton Cancelbutton = new JButton("Cancel");
	JButton Closebutton = new JButton("Close Airport");
	JPanel Buttonpanel = new JPanel();

	// Initializing the choice panels

	JPanel Userchoicepanel = new JPanel();
	JPanel Airportpanel = new JPanel();
	JPanel Frompanel = new JPanel();
	JPanel Topanel = new JPanel();
	
	JComboBox Airportchoice;

	// Dialogue box - Use constructors

	public Airport_Close_Dialogue(String[] Airports) {
		
		setModal(true);

		Airportchoice = new JComboBox(Airports);


		Airportpanel.add(Airportlabel);
		Airportpanel.add(Airportchoice);
		Airportpanel.setLayout(new BoxLayout(Airportpanel, BoxLayout.Y_AXIS));

		Frompanel.add(Fromlabel);
		Frompanel.add(startTimeChoice);
		Frompanel.setLayout(new BoxLayout(Frompanel, BoxLayout.Y_AXIS));

		Topanel.add(Tolabel);
		Topanel.add(endTimeChoice);
		Topanel.setLayout(new BoxLayout(Topanel, BoxLayout.Y_AXIS));

		// Combine each individual panel into one overall JPanel

		Userchoicepanel.add(Airportpanel);
		Userchoicepanel.add(Frompanel);
		Userchoicepanel.add(Topanel);

		// Add buttons to overall "button" panel

		Buttonpanel.setLayout(new FlowLayout());
		Buttonpanel.add(Closebutton);
		Buttonpanel.add(Cancelbutton);

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

		Test = new Airport_Close_Dialogue(airports);
		Test.setTitle("Close Airport");
		Test.setSize(600,200);
		Test.setLocationRelativeTo(null);
		Test.setVisible(true);
		Test.setResizable(false);
	}

	// action listener for closing an airport
	class closeListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			
			String[] theClosure = new String[3];
			boolean timeError = false;
			boolean overlapError = false;
			
			//get airport index and times from the combo boxes
			int airportIndex = Airportchoice.getSelectedIndex();
			
			int startHour = startTimeChoice.Hourchoice.getSelectedIndex();
			int startMinute = startTimeChoice.Minutechoice.getSelectedIndex();	
			
			String startHourString = "" + startTimeChoice.Hourlist[startHour];
			String startMinString = "" + startTimeChoice.Minutelist[startMinute];
			
			int endHour = endTimeChoice.Hourchoice.getSelectedIndex();
			int endMinute = endTimeChoice.Minutechoice.getSelectedIndex();
			
			String endHourString = "" + endTimeChoice.Hourlist[endHour];
			String endMinString = "" + endTimeChoice.Minutelist[endMinute];
			
			//Check that the ending time is after the starting time		
			if ((endHour - startHour) < 1 && (endMinute - startMinute < 1)) {
				timeError = true;
			}
			
			//check that the airport is not already closed during this time
			for (int i  = 0; i < Main.closuresInfo.size(); i++) {
				
				String[] existingClosure = Main.closuresInfo.get(i);
				
				//check if the airport already has a closure
				if (Main.airportsInfo.get(airportIndex).equals(existingClosure[0])) {
					
					//check if the start time of the new closure is in between the start and end times of the existing closure
					if (Integer.parseInt(startHourString + startMinString) <= Integer.parseInt(existingClosure[2]) && 
							Integer.parseInt(startHourString + startMinString) >= Integer.parseInt(existingClosure[1])) {
						overlapError = true;
					}
					//check if the end time of the new closure is in between the start and end times of the existing closure
					else if (Integer.parseInt(endHourString + endMinString) <= Integer.parseInt(existingClosure[2]) &&
							Integer.parseInt(endHourString + endMinString) >= Integer.parseInt(existingClosure[1])) {
						overlapError = true;
					}
				}
			}
			
			//Add to the closuresInfo arrayList
			if (timeError == false && overlapError == false) {	
				theClosure[0] = Main.airportsInfo.get(airportIndex);
				theClosure[1] = startHourString + startMinString;
				theClosure[2] = endHourString + endMinString;

				Main.closuresInfo.add(theClosure);	
				
				String historyText = theClosure[0] + " will be closed from " + theClosure[1] + " to " + theClosure[2] + ".";
				Main.historyArea.append((historyText + "\n"));
				
				//update the routes array list and table
				Main.routesInfo = Main.routeClosureStatus(Main.routesInfo, Main.closuresInfo);
				Main.updateRoutesTable(Main.convertArrayListTo2DArray(Main.routesInfo, 1));
			}
			else if (timeError == true) {
				String historyText = "The ending time of the closure must be later than the starting time.";
				Main.historyArea.append((historyText + "\n"));
			}
			else if (overlapError == true) {
				String historyText = "The time of the closure cannot overlap with an existing closure at this airport.";
				Main.historyArea.append((historyText + "\n"));
			}
					
		}
	}
	// action listener for canceling the status box.
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			Test.dispose();
		}
	}
}