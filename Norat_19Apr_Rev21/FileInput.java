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

	int airportError = 0;
	int routeError = 0;
	int closureError = 0;


	static String errorText = null;

	//Default Constructor to read input file and separate it into 3 tokens: for airports, routes, and closures
	public FileInput(String fileName) throws IOException {

		//Turn the .txt file into a String object

		//Instantiate variables
		String currentLine;
		String text = ""; 
		boolean endExists = false; 
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
				else if (firstString.equals("end")) {
					endExists = true;
				}
				commentChecker.close();

			} //If input file is blank
			catch (NoSuchElementException e1) {
				Main.errorNum = 4;
			}
		}

		if (endExists == false) {
			airportsToken = null;
			routesToken = null;
			closuresToken = null;
		}
	}

	//-------------------------------------------------------------------------------------------------------
	//Method to create an arrayList listing each airport
	ArrayList<String> airportArrayList(String airportsToken) { // Must use airportsToken created in the constructor for the input of this method

		//create arryList of airports
		ArrayList<String> airportsInfo = new ArrayList<String>();

		if (airportsToken != null) {
			airportsToken = airportsToken.substring(9);  //Remove first line of string that says 'airlines'

			//Read through the string and add each airport to the arrayList
			Scanner airportsScanner = new Scanner(airportsToken); 

			while (airportsScanner.hasNext() == true) {
				String airport = airportsScanner.next();	

				airportError = 0;

				//check to make sure airports token consists of letters
				for (int i = 0; i < airport.length(); i++) {
					if (Character.isLetter(airport.charAt(i)) == false) {
						airportError = 1;
					}
				}

				//check that there are no duplicate airports
				for (int i = 0; i < airportsInfo.size(); i++) {
					if (airport.equals(airportsInfo.get(i))) {
						airportError = 1;
					}
				}
				
				//check that it has a length of three
				if (airport.length() > 3 || airport.length() < 3) {
					airportError = 1;
				}

				if (airportError == 0) {
					airportsInfo.add(airport.toUpperCase()); 
				}
				else if (airportError ==1) {
					Main.errorNum = 3;
				}
				
			}
			airportsScanner.close();
		}
		else {
			Main.errorNum = 5;
			airportsNull = 1;
		}

		if (airportsNull == 1) {
			airportsInfo = null;
		}
		return airportsInfo;
	}

	//-------------------------------------------------------------------------------------------------------
	//Method to turn each route into an array, then add all route arrays to an arrayList
	ArrayList<String[]> routeArrayList(String routesToken) { // Must use routesToken created in the constructor for the input of this method

		//create arryList of routes
		ArrayList<String[]> routesInfo = new ArrayList<String[]>();

		if (routesToken != null && airportsToken != null) {
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



			//For each line, tokenize with "," and newline as the deliminators
			StringTokenizer newLineTokenator = new StringTokenizer(routesToken, "\n");

			String[] routeNumArray = new String[numRoutes];

			//create array of airports to check that the airports in the routes are listed in the #airports section
			ArrayList<String> airportsCheck = airportArrayList(airportsToken);

			//Create an array for each line in the string and add each token into the array
			for (int j = 0; j < numRoutes; j++) {

				routeError = 0;
				String theRouteToken = newLineTokenator.nextToken();

				String[] route = new String[8];
				int depHour = 0;
				int depMinute = 0;
				int arrHour = 0;
				int arrMinute = 0;

				StringTokenizer commaTokenator = new StringTokenizer(theRouteToken, ",");

				for (int i = 0; i < 7; i++) {	
					try {
						route[i] = commaTokenator.nextToken();
						if (route[i].substring(0,1).equals(" ")) { //if there is a space at front of token, remove it
							route[i] = route[i].substring(1);
						}
					}
					catch (NoSuchElementException e) {
						route[i] = "";
					}

				}
				//set the 8th value to true by default
				route[7] = "On time";

				//check that route number is in the correct format (Rxyz) where xyz are numbers only
				Scanner routeNumScanner = new Scanner(route[0]);		

				String routeNum = "";
				try {
					routeNum = routeNumScanner.next();

					String r = routeNum.substring(0,1);
					if (r.equals("r") == true) {
						r = r.toUpperCase();
						route[0] = r + routeNum.substring(1);
					}
					else if (r.equals("R") == false) {
						routeError = 1;
					} 

					try {
						Integer.parseInt(routeNum.substring(1));
					}
					catch (NumberFormatException e1) {
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				routeNumScanner.close();

				//check that there are no duplicate route numbers
				for (int i = 0; i < routeNumArray.length; i++) {
					if (routeNum.equals(routeNumArray[i]) == true ) {
						routeError = 1;
					}
				}
				routeNumArray[j] = routeNum; 

				//Check that the airline token says either "DELTA" or "UNITED"
				Scanner airlineScanner = new Scanner(route[1]);
				try {
					String airline = airlineScanner.next();
					if ((airline.equals("DELTA") || airline.equals("UNITED")) != true) {
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				airlineScanner.close();

				//Check that the departure airport token is in the #airports section
				Scanner depAirportScanner = new Scanner(route[2]);

				String depAirport = "";

				try {
					depAirport = depAirportScanner.next();

					boolean depAirportMatch = false;

					for (int i  = 0; i < airportsCheck.size(); i++) {
						if (depAirport.equals(airportsCheck.get(i)) == true) {

							depAirportMatch = true;
						}
					}

					if (depAirportMatch == false) {
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				depAirportScanner.close();

				//Check that the departure time token has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner depTimeScanner = new Scanner(route[3]);
				try {
					String depTime = depTimeScanner.next();
					try {
						depHour = Integer.parseInt(depTime.substring(0,2));
						depMinute = Integer.parseInt(depTime.substring(2));
						if ((depHour >= 0 && depHour < 24) == false) {
							routeError = 1;
						}
						if ((depMinute >= 0 && depMinute < 60) == false) {
							routeError = 1;
						}

						//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number
						for (int i = 0; i < depTime.length(); i++) {
							if (depTime.substring(i, i+1).equals("-") == true ) {
								routeError = 1;
							}
						}

					}
					catch (NumberFormatException e1) {
						routeError = 1;
					}
					catch (StringIndexOutOfBoundsException e) {
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				depTimeScanner.close();

				//Check that the arrival airport token is in the #airports section
				Scanner arrAirportScanner = new Scanner(route[4]);
				try {
					String arrAirport = arrAirportScanner.next();

					boolean arrAirportMatch = false;

					for (int i  = 0; i < airportsCheck.size(); i++) {
						if (arrAirport.equals(airportsCheck.get(i)) == true) {

							arrAirportMatch = true;
						}
					}

					if (arrAirportMatch == false) {
						routeError = 1;
					}

					//Check that the arrival airports isnt the same as the departure airport
					if (arrAirport.equals(depAirport)) {
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				arrAirportScanner.close();

				//Check that the arrival time token has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner arrTimeScanner = new Scanner(route[5]);
				try {
					String arrTime = arrTimeScanner.next();
					try {
						arrHour = Integer.parseInt(arrTime.substring(0,2));
						arrMinute = Integer.parseInt(arrTime.substring(2));
						if ((arrHour >= 0 && arrHour < 24) == false) {
							routeError = 1;
						}
						if ((arrMinute >= 0 && arrMinute < 60) == false) {
							routeError = 1;
						}

						//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
						for (int i = 0; i < arrTime.length(); i++) {
							if (arrTime.substring(i, i+1).equals("-") == true ) {
								routeError = 1;
							}
						}				
					}
					catch (NumberFormatException e1) {
						routeError = 1;
					}
					catch (StringIndexOutOfBoundsException e) {
						routeError = 1;
					}

					//Check that the arrival time is at least 30 min after the departure time			
					if ((arrHour - depHour) < 2 && (arrHour - depHour) > 0) {
						if (arrMinute < depMinute && arrMinute + 60 - depMinute < 30) {
							routeError = 1;
						}
					}
					else if ((arrHour - depHour) < 1) {
						if (arrMinute - depMinute < 30) {
							routeError = 1;
						}
					}

				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				arrTimeScanner.close();

				//Check that the price token is in the format (xxx.yy) where xxx is any number and yy is a number from 00 to 99 
				Scanner priceScanner = new Scanner(route[6]);

				try {
					String price = priceScanner.next();

					try {
						Integer.parseInt(price.substring(0,3));
						Integer.parseInt(price.substring(4));
					}
					catch (NumberFormatException e1) {
						routeError = 1;
					}

					if ((price.substring(3,4).equals(".")) == false) { //check the . between the dollars and cents too
						routeError = 1;
					}
				}
				//check if the element exists
				catch (NoSuchElementException e) {
					routeError = 1;
				}

				priceScanner.close();

				//only add the route if it has no errors
				if (routeError == 0) {
					routesInfo.add(route); //Add each array to the arrayList
				}
				else if (routeError == 1) {
					route = null;
					Main.errorNum = 3;
				}
			}
		}
		else {
			Main.errorNum = 5;
			routesNull = 1;
		}


		//Any error will set routesInfo to null
		if (routesNull == 1){
			routesInfo = null;
		}

		return routesInfo;
	}	

	//-------------------------------------------------------------------------------------------------------
	//Method to turn each closure into an array, then add all closure arrays to an arrayList
	ArrayList<String[]> closureArrayList(String closuresToken) { // Must use closuresToken created in the constructor for the input of this method

		//Create arryList of closures
		ArrayList<String[]> closuresInfo = new ArrayList<String[]>(); 

		if (closuresToken != null && airportsToken != null) {
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

			//create array of airports to check that the airports in the closures are listed in the #airports section
			ArrayList<String> airportsCheck = airportArrayList(airportsToken);

			//For each line, tokenize with "," and newline as the deliminators
			StringTokenizer newLineCTokenator = new StringTokenizer(closuresToken, "\n");

			//Create an array for each line in the string and add each token into the array
			for (int j = 0; j < numClosures; j++) {

				String theClosureToken = newLineCTokenator.nextToken();

				int startHour = 0;
				int startMinute = 0;
				int endHour = 0;
				int endMinute = 0;
				closureError = 0;

				StringTokenizer commaTokenator = new StringTokenizer(theClosureToken, ",");

				String[] closure = new String[3];
				for (int i = 0; i < closure.length; i++) {
					try {
						closure[i] = commaTokenator.nextToken();
						if (closure[i].substring(0, 1).equals(" ")) { //if there is a space at front of token, remove it
							closure[i] = closure[i].substring(1);
						}
					} catch (NoSuchElementException e) {
						closure[i] = "";
					}
				}

				//Check that the airport is listed in the #airports section
				Scanner airportScanner = new Scanner(closure[0]);
				try {
					String depAirport = airportScanner.next();
					airportScanner.close();
					boolean closuresAirportMatch = false;
					for (int i = 0; i < airportsCheck.size(); i++) {
						if (depAirport.equals(airportsCheck.get(i)) == true) {
							closuresAirportMatch = true;
						}
					}
					if (closuresAirportMatch == false) {
						closureError = 1;
					}
				} catch (NoSuchElementException e) {
					closureError = 1;
				}
				airportScanner.close();

				//Check that the starting time has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner startTimeScanner = new Scanner(closure[1]);

				String startTime = null;
				try {
					startTime = startTimeScanner.next();
					try {
						startHour = Integer.parseInt(startTime.substring(0, 2));
						startMinute = Integer.parseInt(startTime.substring(2));
						if ((startHour >= 0 && startHour < 24) == false) {
							closureError = 1;
						}
						if ((startMinute >= 0 && startMinute < 60) == false) {
							closureError = 1;
						}

						//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
						for (int i = 0; i < startTime.length(); i++) {
							if (startTime.substring(i, i + 1).equals("-") == true) {
								closureError = 1;
							}
						}
					} catch (NumberFormatException e1) {
						closureError = 1;
					}
					//if the length is too short
					catch (StringIndexOutOfBoundsException e) {
						closureError = 1;
					}
				} catch (NoSuchElementException e) {
					closureError = 1;
				}
				startTimeScanner.close();

				//Check that the ending time has the format (xxyy) where xx is a number from 00 to 23 and yy is a number from 00 to 59
				Scanner endTimeScanner = new Scanner(closure[2]);

				String endTime;
				try {
					endTime = endTimeScanner.next();
					try {
						endHour = Integer.parseInt(endTime.substring(0, 2));
						endMinute = Integer.parseInt(endTime.substring(2));
						if ((endHour >= 0 && endHour < 24) == false) {
							closureError = 1;
						}
						if ((endMinute >= 0 && endMinute < 60) == false) {
							closureError = 1;
						}

						//Also have to check if there's any negative signs separately because java is retarded and says -0 is a number and is the same as 0 but the program must accept 0 but not -0 because -0 isn't a fucking number (ctrl-c ctrl-v version)
						for (int i = 0; i < endTime.length(); i++) {
							if (endTime.substring(i, i + 1).equals("-") == true) {
								closureError = 1;
							}
						}
					} catch (NumberFormatException e1) {
						closureError = 1;
					} catch (StringIndexOutOfBoundsException e) {
						closureError = 1;
					}

					//Check that the ending time is later than the starting time
					try {
						if (Integer.parseInt(endTime) < Integer.parseInt(startTime)) {
							closureError = 1;
						}
					}
					catch (NumberFormatException e1) {
						closureError = 1;
					}

				} catch (NoSuchElementException e) {
					closureError = 1;
				}
				endTimeScanner.close();

				if (closureError == 0) {
					closuresInfo.add(closure); //Add each array to the arrayList  
				} 
				else if (closureError == 1) {
					Main.errorNum = 3;
				}

			}
		}
		else {
			Main.errorNum = 5;
			closuresNull = 1;
		}

		//set the arrayList to null if there is an error
		if (closuresNull == 1) {
			closuresInfo = null;
		}
		return closuresInfo;
	}	
}