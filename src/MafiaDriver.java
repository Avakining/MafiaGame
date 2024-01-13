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
	 * @param args args
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
		
		Boolean addsUp = false;
		while (!addsUp){
			int total = 0;
			for (int i = 0; i < factionNames.size(); i++){
				System.out.println("How many members should there be of faction " + factionNames.get(i) + "?");
				int in = scan.nextInt();
				if (in >= 0){
					numFaction.set(i, in);
					total += in;
				} else{
					i--;
				}
			}
			if (total == players.size()){
				addsUp = true;
			} else{
				System.out.println("Error: those values do not add up to the total number of players");
			}
		}
		
		for (int i = 0; i < factionNames.size(); i++){
			mafiaGame.addFaction(factionNames.get(i), numFaction.get(i));
		}
		
		printGap();
		
		System.out.println("Factions\tNumber of Players");
		for (int i = 0; i < mafiaGame.getFactions().size(); i++){
			System.out.println(mafiaGame.getFactions().get(i).getFactionName() + "\t" + mafiaGame.getFactions().get(i).getMinNum());
		}
		
		// Define categories
		System.out.println("Next, it's time to define the categories within each faction. Don't set \"Random\", as they will be any leftover after the number of each is set.");
		ArrayList<String> categoryNames;
		ArrayList<Integer> numCategory;
		for (int i = 0; i < mafiaGame.getFactions().size(); i++){
			categoryNames = new ArrayList<String>();
			System.out.print("Enter the categories in the faction" + mafiaGame.getFactions().get(i));
			// Inform about defaults
			switch(mafiaGame.getFactions().get(i).getFactionName()){
				case "Town":
					System.out.println(" (Default: Investigative, Protective, Killing, Necrotic, Support)");
					break;
				case "Neutral Killing":
				case "Neutral Evil":
					System.out.println(" (Default: No categories)");
					break;
				case "Mafia":
					System.out.println(" (Default: No categories)");
					break;
				case "Any":
					System.out.println(" (Default: No categories)");
					break;
				default:
					System.out.println();
			}
			cont = true;
			while (cont){
				String in = scan.nextLine();
				if (in.strip().equals("")){
					cont = false;
				} else{
					categoryNames.add(in);
				}
			}
			
			// Set defaults
			if (categoryNames.size() == 0){
				switch(mafiaGame.getFactions().get(i).getFactionName()){
					case "Town":
						categoryNames.add("Investigative");
						categoryNames.add("Protective");
						categoryNames.add("Killing");
						categoryNames.add("Necrotic");
						categoryNames.add("Support");
						break;
					case "Neutral Killing":
					case "Neutral Evil":
					case "Mafia":
					case "Any":
						categoryNames.add("All");
						break;
				}
			}
			
			// Get numbers
			numCategory = new ArrayList<Integer>(categoryNames.size());
			
			addsUp = false;
			while (!addsUp){
				int total = 0;
				for (int j = 0; j < categoryNames.size(); j++){
					System.out.println("What is the minumum number of players that should be in the category " + categoryNames.get(j) + "?");
					int in = scan.nextInt();
					if (in >= 0){
						numCategory.set(j, in);
						total += in;
					} else{
						j--;
					}
				}
				if (total <= players.size()){
					addsUp = true;
				} else{
					System.out.println("Error: those values are greater than the number of players in this faction");
				}
			}
			
			for(int j = 0; j < factionNames.size(); j++) {
				mafiaGame.getFactions().get(j).addCategory(categoryNames.get(j), numCategory.get(j));
			}
			

			printGap();
			
			System.out.println("Factions\tNumber of Players");
			for (int j = 0; j < mafiaGame.getFactions().get(i).getCategories().size(); j++){
				System.out.println(mafiaGame.getFactions().get(i).getCategories().get(j).getName() + "\t" + mafiaGame.getFactions().get(i).getCategories().get(j).getMinNum());
			}
		}
		
		// Define roles
		System.out.println("Next, it's time to define the roles within each category.");
		ArrayList<String> roleNames;
		ArrayList<Integer> roleMax;
		for (int i = 0; i < mafiaGame.getFactions().size(); i++){
			for (int j = 0; j < mafiaGame.getFactions().get(i).getCategories().size(); j++){ // loop through each category
				roleNames = new ArrayList<String>();
				System.out.println("Enter the roles in the category " + mafiaGame.getFactions().get(i).getCategories().get(j).getName());
				switch (mafiaGame.getFactions().get(i).getFactionName()){
					case "Town":
						switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
							case "Investigative":
								System.out.println(" (Defaults: Sheriff, Psychic, Lookout, Tracker)");
								break;
							case "Protective":
								System.out.println(" (Defaults: Bodyguard, Doctor)");
								break;
							case "Killing":
								System.out.println(" (Defaults: Vigilante, Veteran)");
								break;
							case "Necrotic":
								System.out.println(" (Defaults: Medium, Necromancer)");
								break;
							case "Support":
								System.out.println(" (Defaults: Swapper, Escort, Admirer, Mayor)");
								break;
						}
						break;
					case "Neutral Killing":
						switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
							case "All":
								System.out.println(" (Defaults: Serial Killer, Cannibal, Arsonist)");
								break;
						}
						break;
					case "Neutral Evil":
						switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
							case "All":
								System.out.println(" (Defaults: Jester, Executioner)");
								break;
						}
						break;
					case "Mafia":
						switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
							case "All":
								System.out.println(" (Defaults: Consort, Consigliere, Ambusher, Magnate, Framer)");
								break;
						}
						break;
				}
				cont = true;
				while (cont){
					String in = scan.nextLine();
					if (in.strip().equals("")){
						cont = false;
					} else{
						roleNames.add(in);
					}
				}
				
				// Set defaults
				if (roleNames.size() == 0){
					switch (mafiaGame.getFactions().get(i).getFactionName()){
						case "Town":
							switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
								case "Investigative":
									roleNames.add("Sheriff");
									roleNames.add("Psychic");
									roleNames.add("Lookout");
									roleNames.add("Tracker");
									break;
								case "Protective":
									roleNames.add("Bodyguard");
									roleNames.add("Doctor");
									break;
								case "Killing":
									roleNames.add("Vigilante");
									roleNames.add("Veteran");
									break;
								case "Necrotic":
									roleNames.add("Medium");
									roleNames.add("Necromancer");
									break;
								case "Support":
									roleNames.add("Swapper");
									roleNames.add("Escort");
									roleNames.add("Admirer");
									roleNames.add("Mayor");
									break;
							}
							break;
						case "Neutral Killing":
							switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
								case "All":
									roleNames.add("Serial Killer");
									roleNames.add("Cannibal");
									roleNames.add("Arsonist");
									break;
							}
							break;
						case "Neutral Evil":
							switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
								case "All":
									roleNames.add("Jester");
									roleNames.add("Executioner");
									break;
							}
							break;
						case "Mafia":
							switch (mafiaGame.getFactions().get(i).getCategories().get(j).getName()){
								case "All":
									roleNames.add("Consort");
									roleNames.add("Consigliere");
									roleNames.add("Ambusher");
									roleNames.add("Magnate");
									roleNames.add("Framer");
									break;
							}
							break;
					}
				}
				
				// Define max number of each role
				roleMax = new ArrayList<Integer>();
				for (int k = 0; k < roleNames.size(); k++){
					System.out.println("What is the maximum number of players that should have the role " + roleNames.get(k) + "?");
					switch(mafiaGame.getFactions().get(i).getFactionName()) {
						case "Town":
							System.out.println(" (Default: no limit)");
							break;
						default:
							System.out.println(" (Default: limit 1)");
							break;
					}
					String in = scan.nextLine();
					if (in.equals("")){
						switch (mafiaGame.getFactions().get(i).getFactionName()){
							case "Town":
								roleMax.set(k, Integer.MAX_VALUE);
								break;
							default:
								roleMax.set(k, 1);
								break;
						}
					} else{
						int intIn = Integer.parseInt(in);
						roleMax.set(k, intIn);
					}
				}
			}
		}
		
		scan.close();
	}
	
	private static void printGap(){
		System.out.println("------------------\n\n\n\n------------------");
	}

}
