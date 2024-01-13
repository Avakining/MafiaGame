/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver class
 */
public class MafiaDriver{
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		Mafia mafiaGame = new Mafia();
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Welcome to the Mafia GM Utility!\nYou will be asked to enter values. Each time this happens, type in the value requested then hit enter.\nWhen finished with that section, hit enter without typing anything.\nSome fields have defaults. They will be specified. Just hit enter as if you were finished entering information to use those defaults.");
		
		// Get player list from user
		ArrayList<String> players = new ArrayList<String>();
		System.out.print("Enter the player names:");
		Boolean cont = true;
		while (cont){
			String in = scan.nextLine();
			if (in.strip().equals("")){
				cont = false;
			} else{
				players.add(in);
			}
		}
		
		printGap();
		
		// Set player list
		mafiaGame.setPlayers(players);
		System.out.println("Players:");
		for (Player p : mafiaGame.getPlayers()){
			System.out.println(p.getName());
		}
		
		
		// Get factions
		ArrayList<String> factionNames = new ArrayList<String>();
		System.out.println("Enter the factions (Defaults: Town, Neutral Killer, Neutral Evil, Mafia): ");
		cont = true;
		while (cont){
			String in = scan.nextLine();
			if (in.strip().equals("")){
				cont = false;
			} else{
				factionNames.add(in);
			}
		}
		
		if (factionNames.size() == 0){
			factionNames.add("Town");
			factionNames.add("Neutral Killer");
			factionNames.add("Neutral Evil");
			factionNames.add("Mafia");
		}
		
		printGap();
		
		System.out.println("Added factions:");
		for (String s : factionNames){
			System.out.println(s);
		}
		ArrayList<String> anyFactions;
		System.out.println("Do you want an Any category? (Y/n)");
		cont = true;
		while (cont){
			String in = scan.nextLine();
			if (in.strip().toUpperCase().equals("Y")){
				anyFactions = new ArrayList<String>();
				System.out.println("Which roles should \"Any\" be able to become? (Default: Town, Neutral Evil, Mafia)");
				Boolean cont2 = true;
				while (cont2){
					in = scan.nextLine();
					if (in.strip().equals("")){
						cont = false;
					} else{
						if (ListUtils.isItemInArray(factionNames, in)){
							anyFactions.add(in);
						} else{
							System.out.println("That is not an added faction. Please try again");
						}
						
					}
				}
				
				factionNames.add("Any");
				cont = false;
			} else if (in.strip().toUpperCase().equals("N")){
				cont = false;
			} else{
				System.out.println("Please enter Y/n");
			}
		}
		
		ArrayList<Integer> numFaction = new ArrayList<Integer>(factionNames.size());
		
		for (int i = 0; i < factionNames.size(); i++){
			System.out.println("How many members should there be of faction " + factionNames.get(i) + "?");
			int in = scan.nextInt();
			numFaction.set(i, in);
		}
	}
	
	private static void printGap(){
		System.out.println("------------------\n\n\n\n------------------");
	}

}
