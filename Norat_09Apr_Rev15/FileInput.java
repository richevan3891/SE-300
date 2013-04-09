import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileInput {
	
	//Initialize string variables to hold the tokens created in the constructor
	public String airportsToken;
	public String routesToken;
	public String closuresToken;
	
	int airportsNull = 0;
	int routesNull = 0;
	int closuresNull = 0;
	
	static String errorText = null;

	//Default Constructor to read input file and separate it into 3 tokens: for airports, routes, and closures
	public FileInput(String fileName) throws IOException {
		
		//Turn the .txt file into a String object
			
		//Instantiate variables
		String currentLine;
		String text = ""; 
		//Open a BufferedReader to read the .txt file
		BufferedReader readFile = new BufferedReader(new FileReader(fileName));	 
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
			
			try {
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
			} //If input file is blank
			catch (NoSuchElementException e1) {
				System.out.println("the file input.txt is blank.");
			}
		}
	}
	
	//-------------------------------------------------------------------------------------------------------
	//Method to create an arrayList listing each airport
	ArrayList<String> airportArrayList(String airportsToken) { // Must use airportsToken created in the constructor for the input of this method
		
		airportsToken = airportsToken.substring(9);  //Remove first line of string that says 'airlines'
		
		//create arryList of airports
		ArrayList<String> airportsInfo = new ArrayList<String>();
		
		
		//Read through the string and add each airport to the arrayList
		Scanner airportsScanner = new Scanner(airportsToken); 
		
		while (airportsScanner.hasNext() == true) {
			String airport = airportsScanner.next();
			airportsInfo.add(airport); 
				
			//check to make sure airports token consists of letters
			for (int i = 0; i < airport.length(); i++) {
				if (Character.isLetter(airport.charAt(i)) == false) {
					airportsNull = 1;
					System.out.println("airportsNull = 1");
				}
			}
			
			//check that it has a length of three
			if (airport.length() > 3 || airport.length() < 3) {
				airportsNull = 1;
				System.out.println("airportsNull = 1");
			}
			
		}
		airportsScanner.close();
		
		
		if (airportsNull == 1) {
			airportsInfo = null;
		}
		return airportsInfo;
	}

	//-------------------------------------------------------------------------------------------------------
	//Method to turn each route into an array, then add all route arrays to an arrayList
	ArrayList<String[]> routeArrayList(String routesToken) { // Must use routesToken created in the constructor for the input of this method
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
		ArrayList<String[]> routesInfo = new ArrayList<String[]>();
		
		//For each line, tokenize with "," and newline as the deliminators
		StringTokenizer newLineTokenator = new StringTokenizer(routesToken, "\n");
		
		String[] routeNumArray = new String[numRoutes];
		
		//create array of airports to check that the airports in the routes are listed in the #airports section
		ArrayList<String> airportsCheck = new ArrayList<String>();
		
		Scanner airportsCheckScanner = new Scanner(airportsToken.substring(9));
		
		while (airportsCheckScanner.hasNext() == true) {
			String airport = airportsCheckScanner.next();
			airportsCheck.add(airport); 
			//check that the airports have a length of 3
			if ((airport.length() == 3) == false) {
				routesNull = 1;
			}
		}
		airportsCheckScanner.close();
		
		
		
		//create variable to count the number of correct airports the are in the routes
		int correctDepAirportCounter = 0;
		int correctArrAirportCounter = 0;
		
		try {
			//Create an array for each line in the string and add each token into the array
			for (int j = 0; j < numRoutes; j++) {
				
				String theRouteToken = newLineTokenator.nextToken();
				
				String[] route = new String[8];
				int depHour = 0;
				int depMinute = 0;
				int arrHour = 0;
				int arrMinute = 0;
				
				StringTokenizer commaTokenator = new StringTokenizer(theRouteToken, ",");
				
				for (int i = 0; i < 7; i++) {	
					route[i] = commaTokenator.nextToken();
					if (route[i].substring(0,1).equals(" ")) { //if there is a space at front of token, remove it
						route[i] = route[i].substring(1);
					}
				}
				//set the 8th value to true by default
				route[7] = "true";
				
				//check that route number is in the correct format (Rxyz) where xyz are numbers only
				Scanner routeNumScanner = new Scanner(route[0]);		
				
				String routeNum = routeNumScanner.next();
				
				String r = routeNum.substring(0,1);
				if (r.equals("R") == false) {
					routesNull = 1;
				}
				
				try {
					Integer.parseInt(routeNum.substring(1));
				}
				catch (NumberFormatException e1) {
					routesNull = 1;
				}
				routeNumScanner.close();
				
				//check that there are no duplicate route numbers
				for (int i = 0; i < routeNumArray.length; i++) {
					if (routeNum.equals(routeNumArray[i]) == true ) {
						routesNull = 1;
					}
				}
				routeNumArray[j] = routeNum; 
				
				//Check that the airline token says either "DELTA" or "UNITED"
				Scanner airlineScanner = new Scanner(route[1]);
				
				String airline = airlineScanner.next();
				if ((airline.equals("DELTA") || airline.equals("UNITED")) != true) {
					routesNull = 1;
				}
				airlineScanner.close();
				
				//Check that the departure airport token is in the #airports section
				Scanner depAirportScanner = new Scanner(route[2]);
				String depAirport = depAirportScanner.next();
				depAirportScanner.close();
				
				for (int i  = 0; i < airportsCheck.size(); i++) {
					if (depAirport.equals(airportsCheck.get(i)) == true) {
						correctDepAirportCounter++;
					}
				}
				
				//Check that the departure time token has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner depTimeScanner = new Scanner(route[3]);
				
				String depTime = depTimeScanner.next();
				try {
					depHour = Integer.parseInt(depTime.substring(0,2));
					depMinute = Integer.parseInt(depTime.substring(2));
					if ((depHour >= 0 && depHour < 24) == false) {
						routesNull = 1;
					}
					if ((depMinute >= 0 && depMinute < 60) == false) {
						routesNull = 1;
					}
					
					//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number
					for (int i = 0; i < depTime.length(); i++) {
						if (depTime.substring(i, i+1).equals("-") == true ) {
							routesNull = 1;
						}
					}
					
				}
				catch (NumberFormatException e1) {
					routesNull = 1;
				}
				catch (StringIndexOutOfBoundsException e) {
					routesNull = 1;
				}
				depTimeScanner.close();
				
				//Check that the arrival airport token is in the #airports section
				Scanner arrAirportScanner = new Scanner(route[4]);
				String arrAirport = arrAirportScanner.next();
				arrAirportScanner.close();
				
				for (int i  = 0; i < airportsCheck.size(); i++) {
					if (arrAirport.equals(airportsCheck.get(i)) == true) {
						correctArrAirportCounter++;
					}
				}
				//Check that the arrival airports isnt the same as the departure airport
				if (arrAirport.equals(depAirport)) {
					routesNull = 1;
				}
				
				
				//Check that the arrival time token has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner arrTimeScanner = new Scanner(route[5]);
				
				String arrTime = arrTimeScanner.next();
				try {
					arrHour = Integer.parseInt(arrTime.substring(0,2));
					arrMinute = Integer.parseInt(arrTime.substring(2));
					if ((arrHour >= 0 && arrHour < 24) == false) {
						routesNull = 1;
					}
					if ((arrMinute >= 0 && arrMinute < 60) == false) {
						routesNull = 1;
					}
					
					//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
					for (int i = 0; i < arrTime.length(); i++) {
						if (arrTime.substring(i, i+1).equals("-") == true ) {
							routesNull = 1;
						}
					}				
				}
				catch (NumberFormatException e1) {
					routesNull = 1;
				}
				catch (StringIndexOutOfBoundsException e) {
					routesNull = 1;
				}
				arrTimeScanner.close();
				
				//Check that the arrival time is at least 30 min after the departure time			
				if ((arrHour - depHour) < 2 && (arrHour - depHour) > 0) {
					if (arrMinute < depMinute && arrMinute + 60 - depMinute < 30) {
						routesNull = 1;
					}
				}
				else if ((arrHour - depHour) < 1) {
					if (arrMinute - depMinute < 30) {
						routesNull = 1;
					}
				}
				

				//Check that the price token is in the format (xxx.yy) where xxx is any number and yy is a number from 00 to 99 
				Scanner priceScanner = new Scanner(route[6]);
				
				String price = priceScanner.next();

				try {
					Integer.parseInt(price.substring(0,3));
					Integer.parseInt(price.substring(4));
				}
				catch (NumberFormatException e1) {
					routesNull = 1;
				}
				
				if ((price.substring(3,4).equals(".")) == false) { //check the . between the dollars and cents too
					routesNull = 1;
				}
				
				priceScanner.close();
				
				routesInfo.add(route); //Add each array to the arrayList 
			}
		}
		//Check if there are too few tokens
		catch (NoSuchElementException e1) {
			routesNull = 1;
		}
		
		//check that the number of correct airports in the routes is equal to the number of routes
		if (correctDepAirportCounter < numRoutes) {
			routesNull = 1;
		}
		if (correctArrAirportCounter < numRoutes) {
			routesNull = 1;
		}
		
		//Check for too many tokens 
	//	try {
			//routesTokenator.nextToken(); 
			//routesNull = 1;
		//}
		//catch (NoSuchElementException e1){
		//}
		

		//Any error will set routesInfo to null
		if (routesNull == 1){
			routesInfo = null;
		}
		return routesInfo;
	}	
	
	//-------------------------------------------------------------------------------------------------------
	//Method to turn each closure into an array, then add all closure arrays to an arrayList
	ArrayList<String[]> closureArrayList(String closuresToken) { // Must use closuresToken created in the constructor for the input of this method
		
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
		
		//create array of airports to check that the airports in the routes are listed in the #airports section
		ArrayList<String> airportsCheck = new ArrayList<String>();
		
		Scanner airportsCheckScanner = new Scanner(airportsToken.substring(9));
		
		while (airportsCheckScanner.hasNext() == true) {
			String airport = airportsCheckScanner.next();
			airportsCheck.add(airport); 
			//check that the airports have a length of 3
			if ((airport.length() == 3) == false) {
				routesNull = 1;
			}
		}
		airportsCheckScanner.close();
		int correctAirportCounter = 0;

		//Create arryList of closures
		ArrayList<String[]> closuresInfo = new ArrayList<String[]>(); 
		//For each line, tokenize with "," and newline as the deliminators
		StringTokenizer closuresTokenator = new StringTokenizer(closuresToken, ",\n");
		
		//Create an array for each line in the string and add each token into the array
		for (int j = 0; j < numClosures; j++) {
			String[] closure = new String[3];
			
			int startHour = 0;
			int startMinute = 0;
			int endHour = 0;
			int endMinute = 0;
			
			for (int i = 0; i < closure.length; i++) {
				closure[i] = closuresTokenator.nextToken();
				if (closure[i].substring(0,1).equals(" ")) { //if there is a space at front of token, remove it
					closure[i] = closure[i].substring(1);
				}
			}
			
			//Check that the airport is listed in the #airports section
			Scanner airportScanner = new Scanner(closure[0]);
			String depAirport = airportScanner.next();
			airportScanner.close();
			
			for (int i  = 0; i < airportsCheck.size(); i++) {
				if (depAirport.equals(airportsCheck.get(i)) == true) {
					correctAirportCounter++;
				}
			}
			
			airportScanner.close();
			
			//Check that the starting time has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
			Scanner startTimeScanner = new Scanner(closure[1]);
			
			String startTime = startTimeScanner.next();
			try {
				startHour = Integer.parseInt(startTime.substring(0,2));
				startMinute = Integer.parseInt(startTime.substring(2));
				if ((startHour >= 0 && startHour < 24) == false) {
					closuresNull = 1;
				}
				if ((startMinute >= 0 && startMinute < 60) == false) {
					closuresNull = 1;
				}
				
				//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
				for (int i = 0; i < startTime.length(); i++) {
					if (startTime.substring(i, i+1).equals("-") == true ) {
						closuresNull = 1;
					}
				}				
			}
			catch (NumberFormatException e1) {
				closuresNull = 1;
			}
			//if the length is too short
			catch (StringIndexOutOfBoundsException e) {
				closuresNull = 1;
			}
			startTimeScanner.close();
			
			//Check that the ending time has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
			Scanner endTimeScanner = new Scanner(closure[2]);
			
			String endTime = endTimeScanner.next();
			try {
				endHour = Integer.parseInt(endTime.substring(0,2));
				endMinute = Integer.parseInt(endTime.substring(2));
				if ((endHour >= 0 && endHour < 24) == false) {
					closuresNull = 1;
				}
				if ((endMinute >= 0 && endMinute < 60) == false) {
					closuresNull = 1;
				}
				
				//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
				for (int i = 0; i < endTime.length(); i++) {
					if (endTime.substring(i, i+1).equals("-") == true ) {
						closuresNull = 1;
					}
				}				
			}
			catch (NumberFormatException e1) {
				closuresNull = 1;
			}
			catch (StringIndexOutOfBoundsException e) {
				closuresNull = 1;
			}
			
			endTimeScanner.close();
			
			//Check that the ending time is later than the starting time
			try {
				if (Integer.parseInt(endTime) < Integer.parseInt(startTime)) {
					closuresNull = 1;
				}
			}
			catch (NumberFormatException e1) {
				closuresNull = 1;
			}

			
			
			closuresInfo.add(closure); //Add each array to the arrayList  
		}
		//check that the number of correct airports in the routes is equal to the number of routes
		if (correctAirportCounter < numClosures) {
			closuresNull = 1;
		}
		
		//set the arrayList to null if there is an error
		if (closuresNull == 1) {
			closuresInfo = null;
		}
		return closuresInfo;
	}	
}