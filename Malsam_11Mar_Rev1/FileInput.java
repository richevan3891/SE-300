import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileInput {
	
	public String airportsToken;
	public String routesToken;
	public String closuresToken;

	public FileInput() throws IOException {
		//Turn the .txt file into a String object
			//------------------------------------------------------------------------------------------------------
			 
			//Instantiate variables
			String currentLine;
			String text = ""; 
			 
			//Open a BufferedReader to read the .txt file
			FileReader theFile = new FileReader("input.txt");
			BufferedReader readFile = new BufferedReader(theFile);
			 
			//While there is more to be read, add each line of the .txt file to the String object
			while ((currentLine = readFile.readLine()) != null) {
				text =  text + currentLine + "\n";	
			}
			 
			readFile.close(); 
			 
			//Seperate the input into airports, routes, closures sections
			//-------------------------------------------------------------------------------------------------------
			
			//Tokenize the file with # as the deliminator
			StringTokenizer textTokenator = new StringTokenizer(text, "#");
			while (textTokenator.hasMoreTokens() == true) {
				String token = textTokenator.nextToken();
				
				//Read the first String in each token
				Scanner commentChecker = new Scanner (token);
				String firstString = commentChecker.next();
				
				//If the first string in the token is "airports", "routes", or "closures" save the token, ignore all others
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
	
	ArrayList airportArrayList(String airportsToken) {
		
		//airports section, turn into an arrayList of airports
		//------------------------------------------------------------------------------------------------------
		
		airportsToken = airportsToken.substring(9);  //edit string so that it doesn't include the first line 'airlines'
		
		//Determine the size of the array needed
		Scanner airportsSizeScanner = new Scanner(airportsToken);
		
		int numAirports = 0;	 // initialize variable for the size of the array
		
		while (airportsSizeScanner.hasNext() == true) {
			airportsSizeScanner.next();
			numAirports++;
		}
		
		airportsSizeScanner.close();
		
		//create arryList of routes
		ArrayList airportsInfo = new ArrayList();
		
		//add each airport to the array
		Scanner airportsScanner = new Scanner(airportsToken); 
		
		//continue reading after first line, add each line to the array
		for (int i  = 0; i < numAirports; i++) {
			airportsInfo.add(airportsScanner.next());
		}
		
		airportsScanner.close();
		
	return airportsInfo;
	}

	ArrayList routeArrayList(String routesToken) {

		//routes section, turn each route into an array, then add all route arrays to an arrayList
			//-----------------------------------------------------------------------------------------------------
			
			routesToken = routesToken.substring(7); //edit string so that it doesn't include the first line 'routes'
			
			//Determine the size of the array needed
			Scanner routesSizeScanner = new Scanner(routesToken);
			
			int numRoutes = 0;	 // initialize variable for the size of the array
			
			while (routesSizeScanner.hasNextLine() == true) {
				routesSizeScanner.nextLine();
				numRoutes++;
			}
			
			routesSizeScanner.close();
			
			numRoutes = numRoutes - 1;
			
			//create arryList of routes
			ArrayList routesInfo = new ArrayList();
			
			//For each line, tokenize with "," as the deliminator
			StringTokenizer routesTokenator = new StringTokenizer(routesToken, ",\n");
			
			for (int j = 0; j < numRoutes; j++) {
				String[] route = new String[7];
				for (int i = 0; i < route.length; i++) {
					
					route[i] = routesTokenator.nextToken();
				}
				routesInfo.add(route);
			}
			
			System.out.println("\n---------------");
			System.out.println("Routes info, Amount: " + numRoutes);
			System.out.println("---------------");
			for (int j = 0; j < numRoutes; j++) {
				String[] foo = (String[]) routesInfo.get(j);	
				for (int i = 0; i < foo.length; i++) {
					System.out.print(foo[i] + " " );
				}
				System.out.println("");
			}
		 
	return routesInfo;
	}	
	 
	
	ArrayList closureArrayList(String closureToken) {
		
		//closures section turn each closure into an array, then add all closure arrays to an arrayList
		//-----------------------------------------------------------------------------------------------------
		
		closuresToken = closuresToken.substring(9); //edit string so that it doesn't include the first line 'routes'
		
		//Determine the size of the array needed
		Scanner closuresSizeScanner = new Scanner(closuresToken);
		
		int numClosures = 0;	 // initialize variable for the size of the array
		
		while (closuresSizeScanner.hasNextLine() == true) {
			closuresSizeScanner.nextLine();
			numClosures++;
		}
		
		closuresSizeScanner.close();
		

		//create arryList of routes
		ArrayList closuresInfo = new ArrayList();
		
		//For each line, tokenize with "," as the deliminator
		StringTokenizer closuresTokenator = new StringTokenizer(closuresToken, ",\n");
		
		for (int j = 0; j < numClosures; j++) {
			String[] closure = new String[3];
			for (int i = 0; i < closure.length; i++) {
				closure[i] = closuresTokenator.nextToken();
			}
			closuresInfo.add(closure);
		}
		
		return closuresInfo;
	}
	
	
	//Method to convert an arrayList to a 2D array, only works with 
	 Object[][] convertArrayListTo2DArray(ArrayList arrayList) {		 
		
		Object[][] theArray = new Object[arrayList.size()][7];
		
		for (int i = 0; i < arrayList.size(); i++) {
			String[] foo = (String[]) arrayList.get(i);
			for (int j = 0; j < foo.length; j++) {
				theArray[i][j] = foo[j];
				System.out.println(theArray[i][j]);
			}
	 }
		 
		 return theArray;
	 }
	 
	//Method to convert an arrayList to an array, only works with 
	 String[] convertArrayListToArray(ArrayList arrayList) {
		 String[] theArray = new String[arrayList.size()];
		 for (int i = 0; i < arrayList.size(); i++) {
			 theArray[i] = (String) arrayList.get(i);
			 System.out.println(theArray[i]);
		 }
		 return theArray;
	 }
	 
}