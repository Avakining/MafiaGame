
/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Driver class
 */
public class MafiaDriver{
	private static Mafia mafiaGame;
	private static Scanner scan;
	
	/**
	 * @param args Arguments
	 */
	public static void main(String[] args){
		mafiaGame = new Mafia();
		scan = new Scanner(System.in);
		System.out.println("Welcome to the Mafia GM Utility!\nYou will be asked to enter values. Each time this happens, type in the value requested then hit enter.\nWhen finished with that section, hit enter without typing anything.\nSome fields have defaults. They will be specified. Just hit enter as if you were finished entering information to use those defaults.");
		players();
		printGap();
		factions();
		printGap();
		categories();
		printGap();
		roles();
		printGap();
		assignRoles();
		printGap();
		getDrunk();
		printGap();
		showResults();
		scan.close();
	}
	
	private static void printGap(){
		System.out.println("------------------\n\n\n\n------------------");
	}
	
	private static void players(){
		// Get player list from user
		ArrayList<String> players = new ArrayList<>();
		System.out.println("Enter the player names:");
		boolean cont = true;
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
	}
	
	private static void factions(){
		// Get factions
		ArrayList<String> factionNames = new ArrayList<>();
		System.out.println("Enter the factions (Defaults: Town, Neutral Killer, Neutral Evil, Mafia): ");
		Boolean cont = true;
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
				anyFactions = new ArrayList<>();
				System.out.println("Which roles should \"Any\" be able to become? (Default: Town, Neutral Evil, Mafia)");
				boolean cont2 = true;
				while (cont2){
					in = scan.nextLine();
					if (in.strip().equals("")){
						cont2 = false;
						anyFactions.add("Town");
						anyFactions.add("Neutral Evil");
						anyFactions.add("Mafia");
					} else if (ListUtils.isItemInArray(factionNames, in)){
						anyFactions.add(in);
					} else{
						System.out.println("That is not an added faction. Please try again");
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
		
		ArrayList<Integer> numFaction = new ArrayList<>(factionNames.size());
		
		// for (String f : factionNames){
		// System.out.println(f);
		// }
		
		boolean addsUp = false;
		while (!addsUp){
			int total = 0;
			for (int i = 0; i < factionNames.size(); i++){
				System.out.println("How many members should there be of faction " + factionNames.get(i) + "?");
				int in = scan.nextInt();
				if (in >= 0){
					numFaction.add(i, in);
					total += in;
				} else{
					i--;
				}
			}
			if (total == mafiaGame.getPlayers().size()){
				addsUp = true;
			} else{
				System.out.println("Error: those values do not add up to the total number of players");
			}
		}
		
		for (int i = 0; i < factionNames.size(); i++){
			mafiaGame.addFaction(factionNames.get(i), numFaction.get(i));
		}
		
		printGap();
		
		System.out.println("Factions\t\tNumber of Players");
		for (Faction element : mafiaGame.getFactions()){
			System.out.print(element.getFactionName() + "\t");
			if (element.getFactionName().length() <= 8){
				System.out.print("\t");
			}
			System.out.println(element.getMinNum());
		}
	}
	
	private static void categories(){
		// Define categories
		System.out.println("Next, it's time to define the categories within each faction. Don't set \"Random\", as they will be any leftover after the number of each is set.");
		ArrayList<String> categoryNames;
		ArrayList<Integer> numCategory;
		for (int i = 0; i < mafiaGame.getFactions().size(); i++){
			categoryNames = new ArrayList<String>();
			scan.nextLine(); // idk why this is needed, but it is
			System.out.print("Enter the categories in the faction " + mafiaGame.getFactions().get(i).getFactionName());
			// Inform about defaults
			switch (mafiaGame.getFactions().get(i).getFactionName()){
				case "Town":
					System.out.println(" (Default: Investigative, Protective, Killing, Necrotic, Support)");
					break;
				case "Neutral Killer":
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
					break;
			}
			Boolean cont = true;
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
				switch (mafiaGame.getFactions().get(i).getFactionName()){
					case "Town":
						categoryNames.add("Investigative");
						categoryNames.add("Protective");
						categoryNames.add("Killing");
						categoryNames.add("Necrotic");
						categoryNames.add("Support");
						break;
					case "Neutral Killer":
					case "Neutral Evil":
					case "Mafia":
					case "Any":
					default:
						categoryNames.add("All");
						break;
				}
			}
			
			// Get numbers
			numCategory = new ArrayList<Integer>();
			
			Boolean addsUp = false;
			while (!addsUp){
				int total = 0;
				for (int j = 0; j < categoryNames.size(); j++){
					System.out.println("What is the minumum number of players that should be in the category " + categoryNames.get(j) + "?");
					int in = scan.nextInt();
					if (in >= 0){
						numCategory.add(in);
						total += in;
					} else{
						j--;
					}
				}
				if (total <= mafiaGame.getFactions().get(i).getMinNum()){
					addsUp = true;
				} else{
					System.out.println("Error: those values are greater than the number of players in this faction");
				}
			}
			
			for (int j = 0; j < categoryNames.size(); j++){
				mafiaGame.getFactions().get(i).addCategory(categoryNames.get(j), numCategory.get(j));
			}
			
			printGap();
			
			// System.out.println("Number of categories in the faction " + mafiaGame.getFactions().get(i).getFactionName() + ": " + mafiaGame.getFactions().get(i).getCategories().size());
			
			System.out.println("Category\t\tNumber of Players");
			for (Category element : mafiaGame.getFactions().get(i).getCategories()){
				System.out.print(element.getName() + "\t");
				if (element.getName().length() <= 8){
					System.out.print("\t");
				}
				System.out.println(element.getMinNum());
			}
		}
	}
	
	private static void roles(){
		// Define roles
		System.out.println("Next, it's time to define the roles within each category.");
		ArrayList<String> roleNames;
		ArrayList<Integer> roleMax;
		for (Faction faction : mafiaGame.getFactions()){
			for (Category category : faction.getCategories()){ // loop through each category
				roleNames = new ArrayList<>();
				System.out.println("Enter the roles in the category " + category.getName());
				switch (faction.getFactionName()){
					case "Town":
						switch (category.getName()){
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
						switch (category.getName()){
							case "All":
								System.out.println(" (Defaults: Serial Killer, Cannibal, Arsonist)");
								break;
						}
						break;
					case "Neutral Evil":
						switch (category.getName()){
							case "All":
								System.out.println(" (Defaults: Jester, Executioner)");
								break;
						}
						break;
					case "Mafia":
						switch (category.getName()){
							case "All":
								System.out.println(" (Defaults: Consort, Consigliere, Ambusher, Magnate, Framer)");
								break;
						}
						break;
				}
				Boolean cont = true;
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
					switch (faction.getFactionName()){
						case "Town":
							switch (category.getName()){
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
							switch (category.getName()){
								case "All":
									roleNames.add("Serial Killer");
									roleNames.add("Cannibal");
									roleNames.add("Arsonist");
									break;
							}
							break;
						case "Neutral Evil":
							switch (category.getName()){
								case "All":
									roleNames.add("Jester");
									roleNames.add("Executioner");
									break;
							}
							break;
						case "Mafia":
							switch (category.getName()){
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
				roleMax = new ArrayList<>();
				for (int i = 0; i < roleNames.size(); i++){
					System.out.println("What is the maximum number of players that should have the role " + roleNames.get(i) + "?");
					switch (faction.getFactionName()){
						case "Town":
							switch (category.getName()){
								case "Necrotic":
									System.out.println(" (Default: limit 1)");
									break;
								default:
									System.out.println(" (Default: no limit)");
									break;
							}
							break;
						default:
							System.out.println(" (Default: limit 1)");
							break;
					}
					String in = scan.nextLine();
					if (in.equals("")){
						switch (faction.getFactionName()){
							case "Town":
								switch (category.getName()){
									case "Necrotic":
										roleMax.add(i, 1);
										break;
									default:
										roleMax.add(i, Integer.MAX_VALUE);
										break;
								}
								break;
							default:
								roleMax.add(i, 1);
								break;
						}
					} else{
						int intIn = Integer.parseInt(in);
						roleMax.set(i, intIn);
					}
				}
				
				category.addRoles(roleNames, roleMax);
				
				printGap();
				
				System.out.println("Role\t\tMax Number");
				for (Role role : category.getRoles()){
					System.out.println(role.getName() + "\t");
					if (role.getName().length() <= 8){
						System.out.print("\t");
					}
					System.out.println(role.getMaxNum());
				}
			}
		}
	}
	
	private static void assignRoles(){
		// TODO
	}
	
	private static void getDrunk(){
		System.out.println("Do you want a Drunk? (Y/n)");
		String in = scan.nextLine();
		if (in.toUpperCase().equals("Y")){
			mafiaGame.selectDrunk().setIsDrunk(true);
		}
	}
	
	private static void showResults(){
		System.out.println("Name\t\tFaction\t\tCategory\t\tRole\t\tDrunk");
		for (Player p : mafiaGame.getPlayers()){
			System.out.print(p.getName() + "\t");
			if (p.getName().length() <= 8){
				System.out.print("\t");
			}
			System.out.print(p.getFaction().getFactionName() + "\t");
			if (p.getFaction().getFactionName().length() <= 8){
				System.out.print("\t");
			}
			System.out.print(p.getCategory().getName() + "\t");
			if (p.getCategory().getName().length() <= 8){
				System.out.print("\t");
			}
			System.out.print(p.getRole().getName());
			if (p.getIsDrunk()){
				if (p.getRole().getName().length() <= 8){
					System.out.print("\t");
				}
				System.out.println("\tDrunk");
			} else{
				System.out.println();
			}
		}
	}
}
