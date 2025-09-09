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
 * Using a players.txt and factions_roles.toml file, automatically assigns factions, roles, and role-specific details to all players in a game of Mafia.
 */
public class MafiaSetupDriver{
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
		System.out.println("Welcome to the Mafia GM Utility setup module!"/* + "\nYou may be asked to enter values. Each time this happens, type in the value requested then hit enter."*/);
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
		writeSaveFile();
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
	
	private static void playersFromFile(){
		Scanner scanF;
		ArrayList<String> players = new ArrayList<>();
		File playersF = new File("players.txt"); // look for players.txt in default location
		System.out.println("Looking at: " + playersF.getAbsolutePath());
		while (!playersF.exists() && !playersF.canRead()){ // if not there or not readable, ask for path
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
		} catch (FileNotFoundException e){ // this should only happen if the players.txt file moves while the program is running
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
		Path source = Paths.get("factions_roles.toml");
		TomlParseResult result = null;
		while (!(source.toFile().exists() && source.toFile().canRead())){
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
			for (String c : categoryString){
				// System.out.println(c);
				mafiaGame.getFactions().get(factions).addCategory(c, result.getDouble(f + "." + c + ".min_num").intValue());
				
				Set<String> roleString = result.getTable(f + "." + c + ".roles").keySet();
				// System.out.println(roleString);
				if (roleString.contains("min_num")){
					roleString.remove("min_num");
				}
				// System.out.println(roleString);
				for (String r : roleString){
					// System.out.println(r);
					mafiaGame.getFactions().get(factions).getCategories().get(categories).addRole(r, result.getDouble(f + "." + c + ".roles." + r + ".max_num").intValue());
				}
				categories++;
			}
			factions++;
		}
		
		for (Faction f : mafiaGame.getFactions()){
			int maxFNum = 0;
			for (Category c : f.getCategories()){
				int maxCNum = 0;
				for (Role r : c.getRoles()){
					if (r.getMaxNum() >= 99){
						maxCNum = 99;
						break;
					}
					maxCNum += r.getMaxNum();
				}
				if (maxCNum > 99){
					maxCNum = 99;
				}
				c.setMaxNum(maxCNum);
				if (c.getMaxNum() < c.getMinNum()){
					System.err.println("Category " + c.getName() + " has a maximum number less than its minimum number");
				}
				maxFNum += maxCNum;
			}
			if (maxFNum > 99){
				maxFNum = 99;
			}
			f.setMaxNum(maxFNum);
			if (f.getMaxNum() < f.getMinNum()){
				System.err.println("Faction " + f.getName() + " has a maximum number less than its minimum number");
			}
		}
	}
	
	/**
	 * Using the already shuffled list of Players, assigns factions to them.
	 * 
	 * This is done by assigning the minNum of each faction, in order, then assigning "Any"s randomly by weight
	 */
	private static void assignFactions(){
		int playersAssigned = 0;
		for (Faction f : mafiaGame.getFactions()){ // Loop through all factions
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
					weightSum += f.getWeight();
				} else{
					if (f.getWeight() != 0.0){
						if (f.getPlayers().size() >= f.getMaxNum()){
							System.err.println("Tried to assign faction " + f.getName() + " more than max number of players, trying again...");
							mafiaGame.removeFactionsLeft(f);
						} else{
							mafiaGame.getPlayers().get(playersAssigned).setFaction(f);
							playersAssigned++;
						}
						break;
					}
				}
			}
		}
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
				int randVal = rand.nextInt(f.getCategoriesLeft().size());
				if (f.getCategoriesLeft().get(randVal).getPlayers().size() < f.getCategoriesLeft().get(randVal).getMaxNum()){
					f.getPlayers().get(playersAssigned).setCategory(f.getCategoriesLeft().get(randVal));
					playersAssigned++;
				} else{
					System.err.println("Tried to assign category " + f.getCategoriesLeft().get(randVal).getName() + " of faction " + f.getName() + " more than max number of players, trying again...");
					f.removeCategoriesLeft(f.getCategoriesLeft().get(randVal));
				}
			}
		}
	}
	
	private static void assignRoles(){
		for(Player p : mafiaGame.getPlayers()) {
			while (p.getRole() == null){
				int randVal = rand.nextInt(p.getCategory().getRoles().size());
				if (p.getCategory().getRoles().get(randVal).getPlayers().size() < p.getCategory().getRoles().get(randVal).getMaxNum()){ // make sure there's room in the role
					p.setRole(p.getCategory().getRoles().get(randVal));
					if (p.getRole().getName().equals("Executioner")){ // set exe target
						boolean cont = true;
						while (cont){ // ensure exe target isn't exe
							randVal = rand.nextInt(mafiaGame.getPlayers().size());
							if (!(mafiaGame.getPlayers().get(randVal).equals(p))){
								p.setTarget(mafiaGame.getPlayers().get(randVal));
								cont = false;
							}
						}
					}
				} // else{
				// p.getCategory().removeRolesLeft(p.getCategory().getRolesLeft().get(randVal));
				// }
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
		System.out.println("Results\nName        Faction     Category        Role            Extra");
		for (Player p : mafiaGame.getPlayers()){
			int len;
			System.out.print(p.getName());
			len = p.getName().length();
			while (len < 12){
				System.out.print(" ");
				len += 1;
			}
			System.out.print(p.getFaction().getName());
			len = p.getFaction().getName().length();
			while (len < 12){
				System.out.print(" ");
				len += 1;
			}
			System.out.print(p.getCategory().getName());
			len = p.getCategory().getName().length();
			while (len < 16){
				System.out.print(" ");
				len += 1;
			}
			System.out.print(p.getRole().getName());
			len = p.getRole().getName().length();
			while (len < 16){
				System.out.print(" ");
				len += 1;
			}
			len = 0;
			if (p.getIsDrunk()){
				System.out.print("Drunk    ");
			}
			if (p.getRole().getName().equals("Executioner")){
				System.out.print("Target: " + p.getTarget().getName());
			}
			System.out.println();
		}
		
		System.out.println("Alphabetical list of players to copy+paste into #player-list:");
		mafiaGame.sortPlayersByName();
		for (Player p : mafiaGame.getPlayers()){
			System.out.println(p.getName());
		}
	}
	
	private static void writeSaveFile(){
		System.out.println("Saving game...");
		try{
			SaveUtils.saveGame(mafiaGame, "save.mafia");
			System.out.println("Saved.");
		} catch (IOException e){
			System.err.println("Error saving file");
			e.printStackTrace();
		}
	}
}