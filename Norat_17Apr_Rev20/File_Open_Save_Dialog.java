import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class File_Open_Save_Dialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3146218913623483933L;
	String fileName;
	String fileText;
	int returnVal;
	
	//constructor: openOrSave = 0 for open, 1 for save
	File_Open_Save_Dialog(int openOrSave) throws IOException {
		
		JFileChooser fileChooser = new JFileChooser("Main.class");
		fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
		FileFilter textFilter = new TextFileFilter();
		fileChooser.addChoosableFileFilter(textFilter);
		fileChooser.setFileFilter(textFilter);

		//If user selects open
		if (openOrSave == 0) {
			returnVal = fileChooser.showOpenDialog(File_Open_Save_Dialog.this);

			//if open button is selected
			if ( returnVal == JFileChooser.APPROVE_OPTION ) {		
				fileName = fileChooser.getSelectedFile().getAbsolutePath();	
				fileText = fileChooser.getSelectedFile().getName();
				System.out.println(fileName);
				
				//add .txt to file name if it does not already have it
				if (fileName.endsWith(".txt") == false) {
					fileName = fileName.concat(".txt");
				}
			}
		}	
		
		//If user selects save
		else if (openOrSave == 1) {
			returnVal = fileChooser.showSaveDialog(File_Open_Save_Dialog.this);
			
			//if save button is selected
			if ( returnVal == JFileChooser.APPROVE_OPTION ) {		
				fileName = fileChooser.getSelectedFile().getName();	
				
				//add .txt to file name if it does not already have it
				if (fileName.endsWith(".txt") == false) {
					fileName = fileName.concat(".txt");
				}
				saveFile(Main.airportsInfo, Main.routesInfo, Main.closuresInfo, fileName);
			}
		}
	}
	
	//method to save to a file
	static void saveFile(ArrayList<String> airportsInfo, ArrayList<String[]> routesInfo, ArrayList<String[]> closuresInfo, String fileName) throws IOException {
		
		//get date and time info
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		
		//create file and writer
		File output = new File(fileName);
		PrintWriter writer = new PrintWriter(output);
		
		//print current date and basic info
		writer.println("#comment Air Route Planner data");
		writer.println("#comment date and time: " + dateFormat.format(date));
		writer.println("");
		
		//print airports
		writer.println("");
		writer.println("#airports");
		for (int i = 0; i < airportsInfo.size(); i++) {
			writer.println(airportsInfo.get(i));
		}
		
		//print routes
		writer.println("");
		writer.println("#routes");
		for(int i = 0; i < routesInfo.size(); i++) {
			String[] tempString = routesInfo.get(i);
			for (int j = 0; j < tempString.length; j++) {
				writer.print(tempString[j]);
				if (j < tempString.length-1) {
					writer.print(", ");
				}
			}
			writer.println("");
		}
		
		//print closures
		writer.println("");
		writer.println("#closures");
		for(int i = 0; i < closuresInfo.size(); i++) {
			String[] tempString = closuresInfo.get(i);
			for (int j = 0; j < tempString.length; j++) {
				writer.print(tempString[j]);
				if (j < tempString.length-1) {
					writer.print(", ");
				}
			}
			writer.println("");
		}
		
		//print #end
		writer.println("");
		writer.println("#end");
		writer.close();
	}
	
}

//Class to create a .txt FileFilter
class TextFileFilter extends FileFilter {
	
	public boolean accept( File f ) {
		
		boolean retVal = false;
		
		String ext = f.getName().substring( f.getName().length() - 3 );
		
		if ( ext.equalsIgnoreCase( "txt" ) || f.isDirectory() )
			retVal = true;

		return retVal;
	}
	
	public String getDescription() {
		return ".txt Files";
	}
} 