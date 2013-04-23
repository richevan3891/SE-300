import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


@SuppressWarnings("serial")
public class Airport_Open_Dialog extends JDialog {


	//Initialize stuff
	JButton cancelButton = new JButton("Cancel");
	JButton openButton = new JButton("Open Airport");
	JComboBox<?> closureSelection;
	JPanel selectionPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JLabel selectionLabel = new JLabel("Closures");

	// Get the screen size
	Dimension totalSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = totalSize.width;
	int height = totalSize.height;


	Airport_Open_Dialog(ArrayList<String[]> closuresInfo) {

		setModal(true);

		String[] closuresArray = new String[closuresInfo.size()];

		//turn arraylist into string array that can be used by JComboBox
		for (int i = 0; i < closuresInfo.size(); i++) {
			closuresArray[i] = closuresInfo.get(i)[0] + ": " + closuresInfo.get(i)[1] + " - " + closuresInfo.get(i)[2];
		}

		closureSelection = new JComboBox<Object>(closuresArray);

		//add stuff to the panels
		selectionPanel.add(selectionLabel);
		selectionPanel.add(closureSelection);
		selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
		selectionPanel.setAlignmentY(CENTER_ALIGNMENT);

		topPanel.setLayout(new FlowLayout());
		topPanel.add(selectionPanel);

		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(openButton);
		buttonPanel.add(cancelButton);

		//add panels to JDialog
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		add(topPanel);
		add(buttonPanel);

		//add actionlisteners
		openButton.addActionListener(new openListener());
		cancelButton.addActionListener(new cancelListener());

		setTitle("Open Airport");							// Rename the title
		setSize((width * 2 / 10), (height * 2 / 10));		// Set the size
		setResizable(false);								// No resizing
		setLocationRelativeTo(null);						// Center it
		setVisible(true);									// Make it visible	

	}
	// action listener for canceling the status box.
	class openListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			try {			
				//inform user of airport opening.
				String historyText = "The closure at " + Main.closuresInfo.get(closureSelection.getSelectedIndex())[0] + " from " +
						Main.closuresInfo.get(closureSelection.getSelectedIndex())[1] + " to " + 
						Main.closuresInfo.get(closureSelection.getSelectedIndex())[2] + " has now been opened.";
				Main.historyArea.append((historyText + "\n"));	

				removeClosure(Main.closuresInfo, closureSelection.getSelectedIndex()); //remove closures from closuresInfo arrayList
				closureSelection.removeItemAt(closureSelection.getSelectedIndex()); //remove closure from combobox

				//update the routes array list and table
				Main.routesInfo = Main.routeClosureStatus(Main.routesInfo, Main.closuresInfo);
				Main.updateRoutesTable(Main.convertArrayListTo2DArray(Main.routesInfo, 1));
			}
			catch (ArrayIndexOutOfBoundsException e) {
				String historyText = "There are no more airports to be opened";
				Main.historyArea.append((historyText + "\n"));
			}
		}
	}
	// action listener for canceling the status box.
	class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	//Method to remove a route from the routesInfo ArrayList
	static ArrayList<String[]> removeClosure(ArrayList<String[]> closuresInfo, int closureNumberIndex) {
		closuresInfo.remove(closureNumberIndex);
		return closuresInfo;
	}
}