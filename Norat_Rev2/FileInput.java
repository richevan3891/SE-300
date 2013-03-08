import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileInput {
	
	//Initialize string variables to hold the tokens created in the constructor
	public String airportsToken;
	public String routesToken;
	public String closuresToken;

	//Default Constructor to read input file and separate it into 3 tokens: for airports, routes, and closures
	public FileInput() throws IOException {
		
		//Turn the .txt file into a String object
			
		//Instantiate variables
		String currentLine;
		String text = ""; 
		//Open a BufferedReader to read the .txt file
		BufferedReader readFile = new BufferedReader(new FileReader("input.txt"));	 
		//Read through the file and add each line of the .txt file to the String object
		while ((currentLine = readFile.readLine()) != null) {
			text =  text + currentLine + "\n";	
		}
		readFile.close(); 
			 
		//Separate the input into airports, routes, closures sections
			
		//Tokenize the file with # as the deliminator
		StringTokenizer textTokenator = new StringTokenizer(text, "#");
		while (textTokenator.hasMoreTokens() == true) {
			String token = textTokenator.nextToken();
				
			//Read the first String in each token
			Scanner commentChecker = new Scanner (token);
			String firstString = commentChecker.next();
			
			//If the first string in the token is "airports", "routes", or "closures" save the token accordingly, 
			//ignore all others
			if (firstString.equals("airports")) {
				airportsToken = token; 
			}
			else if (firstString.equals("routes")) {
				routesToken = token;
			}
			else if (firstString.equals("closures")) {
				closuresToken = token;
			}
			commentChecker.close();
		}
	}
	
	//-------------------------------------------------------------------------------------------------------
	//Method to create an arrayList listing each airport
	ArrayList airportArrayList(String airportsToken) { // Must use airportsToken created in the constructor for the input of this method
		
		airportsToken = airportsToken.substring(9);  //Remove first line of string that says 'airlines'
		
		//create arryList of airports
		ArrayList airportsInfo = new ArrayList();
		
		//Read through the string and add each airport to the arrayList
		Scanner airportsScanner = new Scanner(airportsToken); 	
		while (airportsScanner.hasNext() == true) {
			airportsInfo.add(airportsScanner.next()); 
		}
		airportsScanner.close();

	return airportsInfo;
	}

	//-------------------------------------------------------------------------------------------------------
	//Method to turn each route into an array, then add all route arrays to an arrayList
	ArrayList routeArrayList(String routesToken) { // Must use routesToken created in the constructor for the input of this method
		routesToken = routesToken.substring(7); //Remove first line of string that says 'routes'
			
		//Determine the number of routes
		Scanner routesSizeScanner = new Scanner(routesToken);
		int numRoutes = 0;	 
		while (routesSizeScanner.hasNextLine() == true) {
			if (routesSizeScanner.nextLine().equals("") == false) { //Ignore any blank lines
				numRoutes++; //For each line in the string, add 1 to the number of routes
			}
		}
		routesSizeScanner.close();
		
		//create arryList of routes
		ArrayList routesInfo = new ArrayList();
		//For each line, tokenize with "," and newline as the deliminators
		StringTokenizer routesTokenator = new StringTokenizer(routesToken, ",\n");
		
		/* WORKING ON FIXING A DEFECT BUT NOT DONE YET PROBABLY HAVE TO CHANGE STUFF
		//For i < numRoutes
		for (int i = 0; i < numRoutes; i++) {
			String[] route = new String[7];
			//For 6 times tokenize with , as deliminator
			for (int j = 0; j < 6; j++) {
				StringTokenizer commaTokenator = newStringTokenizer(routesTaken, ",");
				
			}
			//then tokenize once with \n as token
			StringTokenizer newLineTokenator = newStringTokenizer(routesTaken, "\n");
		}
			*/
		
		
		
		//Create an array for each line in the string and add each token into the array
		for (int j = 0; j < numRoutes; j++) {
			String[] route = new String[7];
			for (int i = 0; i < route.length; i++) {	
				route[i] = routesTokenator.nextToken();
			}
			routesInfo.add(route); //Add each array to the arrayList 
		}
		return routesInfo;
	}	
	
	//-------------------------------------------------------------------------------------------------------
	//Method to turn each closure into an array, then add all closure arrays to an arrayList
	ArrayList closureArrayList(String closuresToken) { // Must use closuresToken created in the constructor for the input of this method
		
		closuresToken = closuresToken.substring(9); //Remove first line of string that says 'closures'
		
		//Determine the number of closures
		Scanner closuresSizeScanner = new Scanner(closuresToken);
		int numClosures = 0;	
		while (closuresSizeScanner.hasNextLine() == true) {
			if (closuresSizeScanner.nextLine().equals("") == false) { //Ignore any blank lines
				numClosures++; //For each line in the string, add 1 to the number of routes
			}
		}
		closuresSizeScanner.close();

		//Create arryList of closures
		ArrayList closuresInfo = new ArrayList(); 
		//For each line, tokenize with "," and newline as the deliminators
		StringTokenizer closuresTokenator = new StringTokenizer(closuresToken, ",\n");
		
		//Create an array for each line in the string and add each token into the array
		for (int j = 0; j < numClosures; j++) {
			String[] closure = new String[3];
			for (int i = 0; i < closure.length; i++) {
				closure[i] = closuresTokenator.nextToken();
			}
			closuresInfo.add(closure); //Add each array to the arrayList 
		}
		return closuresInfo;
	}
	
	//-------------------------------------------------------------------------------------------------------
	//Method to convert an arrayList to a 2D array, should only be used for the routes or closures arrayLists 
	//IMPORTANT: for routesOrClosures, enter 0 if using this method for closures, 1 for routes
	String[][] convertArrayListTo2DArray(ArrayList arrayList, int routesOrClosures) {	
		
		String[][] theArray = null;
		
		//Create 2d array with the same number of  the same size as the arrayList
		if (routesOrClosures > 0) {
			theArray = new String[arrayList.size()][7]; //For routes, 7 columns
		}
		else if (routesOrClosures < 1) {
			theArray = new String[arrayList.size()][3]; //For closures, 3 columns
		}
		
		for (int i = 0; i < arrayList.size(); i++) {
			String[] theString = (String[]) arrayList.get(i); //Create an array from each object in the arrayList
			for (int j = 0; j < theString.length; j++) {
				theArray[i][j] = theString[j];  //Add each value in the array created above to a line in the 2d array
			}
		}
		return theArray;
	}
	
	//-------------------------------------------------------------------------------------------------------
	//Method to convert an arrayList to an array, should only be used for the airports arrayList
	String[] convertArrayListToArray(ArrayList arrayList) {
		String[] theArray = new String[arrayList.size()]; //Create array the same size as the arrayList
		for (int i = 0; i < arrayList.size(); i++) {
			theArray[i] = (String) arrayList.get(i); //Add each component of the ArrayList to the array
		}
		return theArray;
	}
}