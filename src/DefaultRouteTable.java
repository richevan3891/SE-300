import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// route table
public class DefaultRouteTable {

	// get route information from file
	@SuppressWarnings("null")
	public void routeInfo () {
		ArrayList<String[]> allRoutes = new ArrayList<String[]>();
		//String[][] allRoutes = null;
		String[] rows = null;
		//String[] row = null;
		String routeID = null;
		String depAirLine = null;
		String arrAirLine = null;
		String dest = null;
		int depTime, arrTime;
		int i = 0, j = 0;
		double cost;
		Scanner input = null;
		File routeData = new File("../Routes.txt");
		try {
			input = new Scanner(routeData);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.useDelimiter(", ");
		boolean found = false;
		String str = null;
		//System.out.println("Here");
		while (input.hasNextLine()) {
			rows = input.nextLine().split(", ");
			allRoutes.add(rows);
			//for (String row: rows) {
				
			//}
			/*allRoutes[i][j] = input.next();
			if (input.next().equals("\n")) {
				i = i + 1;
				j = 0;
			}
			else {
				j = j + 1;
			}
			if (row.equals('\n')) {
				allRoutes.add(routeRow);
				System.out.println("Here");
			}*/
			i++;
			
		}
		for (int k = 0; k < allRoutes.size(); k++) {
			
		}
		//System.out.println(rows);
		//System.out.println(row);
		//System.out.println(allRoutes);
		//return row;
	}
}

