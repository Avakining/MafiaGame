
/**
 * @author Celeste Partan
 */

import java.util.Scanner;

/**
 * 
 */
public class MafiaDriver{
	/**
	 * The menu of options
	 */
	private static String[][] menu = //@formatter:off
		{{"Select an option to continue:", ""},
			{"1. Start new Mafia game", "Start a new game of Mafia. "},
			{"2. Load an existing Mafia game", "Load an existing game of Mafia to compute day and night actions."},
			{"3. Help", ""}}; //@formatter:on
	
	/**
	 * @param args Arguments
	 */
	public static void main(String[] args){
		
		System.out.println("Welcome to the Mafia GM Utility");
		Boolean cont = true;
		Scanner scan = new Scanner(System.in);
		menu();
		while (cont){
			char input = scan.next().charAt(0);
			switch (input){
				case '1':
					MafiaSetupDriver.main(args);
					cont = false;
					break;
				case '2':
					MafiaGameDriver.main(args);
					cont = false;
					break;
				case '3':
					help();
					break;
				default:
					System.out.println("That input was not recognised. Please enter just the number of your selection.");
					menu();
			
			}
		}
		scan.close();
	}
	
	private static void menu(){
		for(String[] i : menu) {
			System.out.println(i[0]);
		}
	}
	
	private static void help(){
		for (String[] i : menu){
			System.out.println(i[0]);
			if (!i[1].equals("")){
				System.out.println("\tInfo:\t" + i[1]);
			}
		}
	}
}
