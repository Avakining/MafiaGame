
/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.tomlj.*;
import java.nio.file.*;

/**
 * Driver class
 */
public class MafiaDriver{
	private static Mafia mafiaGame;
	private static Scanner scan;
	private static Random rand;
	private static boolean isDrunk;
	
	/**
	 * @param args Arguments
	 */
	public static void main(String[] args){
		isDrunk = false;
		rand = new Random();
		mafiaGame = new Mafia();
		scan = new Scanner(System.in);
		System.out.println("Welcome to the Mafia GM Utility!\nYou may be asked to enter values. Each time this happens, type in the value requested then hit enter.");
		playersFromFile();
		// printGap();
		rolesFromFile();
		assignFactions();
		assignCategories();
		assignRoles();
		// printGap();
		getDrunk();
		printGap();
		showResults();
		scan.close();
	}
	
	private static void printGap(){
		for (int i = 0; i < 25; i++){
			System.out.print("-");
		}
		System.out.println("\n\n\n\n\n");
		for (int i = 0; i < 25; i++){
			System.out.print("-");
		}
		System.out.println();
	}
	
	/*private static void players(){
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
		}*/
		
	/*private static void factions(){
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
			ArrayList<String> anyFactions = new ArrayList<>();
			System.out.println("Do you want an Any category? (Y/n)");
			cont = true;
			while (cont){
				String in = scan.nextLine();
				if (in.strip().toUpperCase().equals("Y")){
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
			
			ArrayList<Faction> anyFactionArr = new ArrayList<Faction>();
			for (String s : anyFactions){
				anyFactionArr.add(ListUtils.findFaction(mafiaGame.getFactions(), s));
			}
			mafiaGame.setAllowedAny(anyFactionArr);
			
			printGap();
			
			System.out.println("Factions\t\tNumber of Players");
			for (Faction element : mafiaGame.getFactions()){
				System.out.print(element.getName() + "\t");
				if (element.getName().length() <= 8){
					System.out.print("\t");
				}
				System.out.println(element.getMinNum());
			}
		}*/
		
	/*private static void categories(){
			// Define categories
			System.out.println("Next, it's time to define the categories within each faction. Don't set \"Random\", as they will be any leftover after the number of each is set.");
			ArrayList<String> categoryNames;
			ArrayList<Integer> numCategory;
			for (int i = 0; i < mafiaGame.getFactions().size(); i++){
				categoryNames = new ArrayList<String>();
				scan.nextLine(); // idk why this is needed, but it is
				System.out.print("Enter the categories in the faction " + mafiaGame.getFactions().get(i).getName());
				// Inform about defaults
				switch (mafiaGame.getFactions().get(i).getName()){
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
					switch (mafiaGame.getFactions().get(i).getName()){
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
				
				categoryNames.add("Random");
				numCategory.add(0);
				
				for (int j = 0; j < categoryNames.size(); j++){
					mafiaGame.getFactions().get(i).addCategory(categoryNames.get(j), numCategory.get(j));
				}
				
				printGap();
				
				// System.out.println("Number of categories in the faction " + mafiaGame.getFactions().get(i).getName() + ": " + mafiaGame.getFactions().get(i).getCategories().size());
				
				System.out.println("Category\t\tNumber of Players");
				for (Category element : mafiaGame.getFactions().get(i).getCategories()){
					System.out.print(element.getName() + "\t");
					if (element.getName().length() <= 8){
						System.out.print("\t");
					}
					System.out.println(element.getMinNum());
				}
			}
		}*/
		
	/*private static void roles(){
		// Define roles
		System.out.println("Next, it's time to define the roles within each category.");
		ArrayList<String> roleNames;
		ArrayList<Integer> roleMax;
		for (Faction faction : mafiaGame.getFactions()){
			if (!faction.getName().equals("Any")){
				for (Category category : faction.getCategories()){ // loop through each category
					roleNames = new ArrayList<>();
					if (!category.getName().equals("Random")){
						System.out.print("Enter the roles in the category " + category.getName() + " in faction " + faction.getName());
						switch (faction.getName()){
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
							case "Neutral Killer":
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
							default:
								System.out.println();
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
							switch (faction.getName()){
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
								case "Neutral Killer":
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
							System.out.print("What is the maximum number of players that should have the role " + roleNames.get(i) + "?");
							switch (faction.getName()){
								case "Town":
									switch (category.getName()){
										case "Necrotic":
											System.out.println(" (Default: limit 1)");
											break;
										case "Support":
											switch (roleNames.get(i)){
												case "Swapper":
													System.out.println(" (Default: limit 1)");
													break;
												default:
													System.out.println(" (Default: no limit)");
													break;
											}
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
								switch (faction.getName()){
									case "Town":
										switch (category.getName()){
											case "Necrotic":
												roleMax.add(i, 1);
												break;
											case "Support":
												switch (roleNames.get(i)){
													case "Swapper":
														roleMax.add(i, 1);
														break;
													default:
														roleMax.add(i, Integer.MAX_VALUE);
														break;
												}
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
								roleMax.add(i, intIn);
							}
						}
						
						category.addRoles(roleNames, roleMax);
						
						printGap();
						
						System.out.println("Role\t\tMax Number");
						for (Role role : category.getRoles()){
							System.out.print(role.getName() + "\t");
							if (role.getName().length() <= 8){
								System.out.print("\t");
							}
							System.out.println(role.getMaxNum());
						}
					}
				}
			}
		}
	}*/
	
	private static void assignFactions(){
		int playersAssigned = 0;
		for (Faction f : mafiaGame.getFactions()){
			for (int i = 0; i < f.getMinNum(); i++){
				mafiaGame.getPlayers().get(playersAssigned).setFaction(f);
				playersAssigned++;
			}
		}
		while (playersAssigned < mafiaGame.getPlayers().size()){
			double weightSum = 0;
			double randVal = rand.nextDouble();
			for(Faction f: mafiaGame.getFactions()) {
				if (randVal > weightSum){
					randVal += f.getWeight();
				} else{
					mafiaGame.getPlayers().get(playersAssigned).setFaction(f);
					playersAssigned++;
				}
			}
		}
		// printGap();
		// for (Player p : mafiaGame.getPlayers()){
		// System.out.println(p.getName() + "\t\t" + p.getFaction().getName());
		// }
	}
	
	private static void assignCategories(){
		int playersAssigned;
		for (Faction f : mafiaGame.getFactions()){
			playersAssigned = 0;
			for (Category c : f.getCategories()){
				for (int i = 0; i < c.getMinNum(); i++){
					f.getPlayers().get(playersAssigned).setCategory(c);
					playersAssigned++;
				}
			}
			while (playersAssigned < f.getPlayers().size()){
				// System.out.println("Faction: " + f.getName() + "\tCategories: " + f.getCategories().size());
				int randVal = rand.nextInt(f.getCategories().size());
				f.getPlayers().get(playersAssigned).setCategory(f.getCategories().get(randVal));
				playersAssigned++;
			}
		}
	}
	
	private static void assignRoles(){
		int loop;
		for(Player p : mafiaGame.getPlayers()) {
			loop = 0;
			while (p.getRole() == null){
				int randVal = rand.nextInt(p.getCategory().getRoles().size());
				if (p.getCategory().getRoles().get(randVal).getPlayers().size() < p.getCategory().getRoles().get(randVal).getMaxNum()){ // make sure there's room in the role
					p.setRole(p.getCategory().getRoles().get(randVal));
				}
				loop++;
				if (loop > 100){ // check the category is full
					System.err.println("Player " + p.getName() + " is in category " + p.getCategory().getName() + ". All the roles in that category are full. This should be rare.");
					randVal = rand.nextInt(p.getFaction().getCategories().size());
					p.setCategory(p.getFaction().getCategories().get(randVal)); // assign a new category
					loop = 0;
				}
			}
		}
	}
	
	private static void getDrunk(){
		// System.out.println("Do you want a Drunk? (Y/n)");
		// String in = scan.nextLine();
		if (isDrunk){
			mafiaGame.selectDrunk().setIsDrunk(true);
		}
	}
	
	private static void showResults(){
		mafiaGame.sortPlayers();
		System.out.println("Results\nName\t\t\tFaction\t\tCategory\t\t\tRole\t\t\t\tExtra");
		for (Player p : mafiaGame.getPlayers()){
			int len;
			System.out.print(p.getName());
			len = p.getName().length();
			while (len <= 12){
				System.out.print("\t");
				len += 4;
			}
			System.out.print(p.getFaction().getName());
			len = p.getFaction().getName().length();
			while (len <= 12){
				System.out.print("\t");
				len += 4;
			}
			System.out.print(p.getCategory().getName());
			len = p.getCategory().getName().length();
			while (len <= 16){
				System.out.print("\t");
				len += 4;
			}
			System.out.print(p.getRole().getName());
			len = p.getRole().getName().length();
			while (len <= 16){
				System.out.print("\t");
				len += 4;
			}
			len = 0;
			if (p.getIsDrunk()){
				System.out.print("Drunk\t");
			}
			if (p.getRole().getName().equals("Executioner")){ // set exe target
				boolean cont = true;
				while (cont){ // ensure exe target isn't exe
					int randVal = rand.nextInt(mafiaGame.getPlayers().size());
					if (!(mafiaGame.getPlayers().get(randVal).equals(p))){
						System.out.print("Target: " + mafiaGame.getPlayers().get(randVal).getName());
						cont = false;
					}
				}
			}
			System.out.println();
		}
		
		System.out.println("Alphabetical list of players to copy+paste into #player-list:");
		mafiaGame.sortPlayersByName();
		for (Player p : mafiaGame.getPlayers()){
			System.out.println(p.getName());
		}
	}
	
	private static void playersFromFile(){
		Scanner scanF;
		ArrayList<String> players = new ArrayList<>();
		File playersF = new File("src/players.txt");
		System.out.println("Looking at: " + playersF.getAbsolutePath());
		while (!playersF.exists() && !playersF.canRead()){
			System.err.println("File is not in expected location");
			System.out.println("Please enter the path to players.txt:");
			String in = scan.nextLine();
			playersF = new File(in);
		}
		try{
			scanF = new Scanner(playersF);
			while (scanF.hasNextLine()){
				players.add(scanF.nextLine());
			}
		} catch (FileNotFoundException e){ // this should never happen
			e.printStackTrace();
		}
		
		// Set player list
		mafiaGame.setPlayers(players); // this also shuffles the player list
		System.out.println("Players:");
		for (Player p : mafiaGame.getPlayers()){
			System.out.println(p.getName());
		}
	}
	
	private static void rolesFromFile(){
		Path source = Paths.get("src/factions_roles.toml");
		TomlParseResult result = null;
		while (!source.toFile().exists() && !source.toFile().canRead()){
			System.err.println("File is not in expected location");
			System.out.println("Please enter the path to factions_roles.toml:");
			String in = scan.nextLine();
			source = Paths.get(in);
		}
		try{
			result = Toml.parse(source);
		} catch (IOException e){
			e.printStackTrace();
		}
		result.errors().forEach(error -> System.err.println(error.toString()));
		Set<String> factionsString = result.keySet();
		if (factionsString.contains("Drunk")){
			isDrunk = result.getBoolean("Drunk.is_drunk");
			factionsString.remove("Drunk");
		}
		
		int factions = 0;
		for (String f : factionsString){
			// System.out.println(f);
			int categories = 0;
			// System.out.println(f);
			mafiaGame.addFaction(f, result.getDouble(f + ".min_num").intValue(), result.getDouble(f + ".weight"));
			Set<String> categoryString = result.getTable(f).keySet();
			if (categoryString.contains("min_num")){
				categoryString.remove("min_num");
			}
			if (categoryString.contains("weight")){
				categoryString.remove("weight");
			}
			if (categoryString.contains("isHoncho")){
				categoryString.remove("isHoncho");
			}
			for(String c : categoryString) {
				// System.out.println(c);
				mafiaGame.getFactions().get(factions).addCategory(c, result.getDouble(f + "." + c + ".min_num").intValue());
				
				Set<String> roleString = result.getTable(f + "." + c + ".roles").keySet();
				// System.out.println(roleString);
				if (roleString.contains("min_num")){
					roleString.remove("min_num");
				}
				// System.out.println(roleString);
				for(String r : roleString) {
					// System.out.println(r);
					mafiaGame.getFactions().get(factions).getCategories().get(categories).addRole(r, result.getDouble(f + "." + c + ".roles." + r + ".max_num").intValue());
				}
				categories++;
			}
			factions++;
		}
	}
}
