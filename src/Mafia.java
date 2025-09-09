/**
 * @author Celeste Partan
 */

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class to control the Mafia game
 */
public class Mafia implements Serializable{
	private ArrayList<Player> players;
	private ArrayList<Faction> factions;
	private ArrayList<Faction> factionsLeft;
	private short day;
	
	/**
	 * Constructor
	 */
	public Mafia(){
		players = new ArrayList<Player>();
		factions = new ArrayList<Faction>();
		factionsLeft = new ArrayList<Faction>();
		day = 0;
	}
	
	/**
	 * Constructor
	 * @param mafiaGame Mafia object to copy
	 */
	public Mafia(Mafia mafiaGame){
		this.players = new ArrayList<Player>(mafiaGame.getPlayers());
		this.factions = new ArrayList<Faction>(mafiaGame.getFactions());
		this.factionsLeft = new ArrayList<Faction>(mafiaGame.getFactionsLeft());
	}
	
	/**
	 * Set players from File
	 * File should have one player name per line.
	 * @param f File with names
	 */
	public void setPlayers(File f){
		ArrayList<String> playersIn = new ArrayList<String>();
		try{
			Scanner scan = new Scanner(f);
			while (scan.hasNextLine()){
				playersIn.add(scan.nextLine());
			}
			scan.close();
		} catch (Exception ioException){
			System.err.println("File error");
		}
		this.setPlayers(playersIn);
	}
	
	/**
	 * Set players from ArrayList
	 * @param playersIn ArrayList of player names
	 */
	public void setPlayers(ArrayList<String> playersIn){
		Collections.sort(playersIn);
		while (playersIn.size() <= 1){
			System.err.println("Error: too few players, adding default");
			playersIn.add("1");
			playersIn.add("2");
			playersIn.add("3");
			playersIn.add("4");
			playersIn.add("5");
			playersIn.add("6");
			playersIn.add("7");
			playersIn.add("8");
			playersIn.add("9");
			playersIn.add("10");
			playersIn.add("11");
			playersIn.add("12");
			playersIn.add("13");
		}
		Boolean hasDuplicates = false;
		for(int i = 1; i < playersIn.size(); i++) {
			if (playersIn.get(i).equals(playersIn.get(i - 1))){
				hasDuplicates = true;
			}
		}
		if (hasDuplicates){
			System.err.println("Error: duplicate names in player list, continuing");
		}
		Collections.shuffle(playersIn);
		for (String s : playersIn){
			players.add(new Player(s));
		}
	}
	

	/**
	 * @return List of players
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	/**
	 * Check if the drunk will sober up tonight
	 * @return if the Drunk sobers
	 */
	public Boolean doesSober(){
		Random r = new Random();
		double third = 1.0 / 3.0;
		if (r.nextDouble() < third){
			return true;
		}
		return false;
	}
	
	/**
	 * @param faction Faction to add
	 */
	public void addFaction(Faction faction){
		factions.add(faction);
		factionsLeft.add(faction);
	}
	
	/**
	 * @param name Name of faction to add
	 * @param num Minimum number of players in that faction
	 * @param weight weight for "Any"
	 */
	public void addFaction(String name, int num, double weight){
		this.addFaction(new Faction(name, num, weight));
	}
	
	/**
	 * Select a Player to be the Drunk
	 * @return Drunk Player
	 */
	public Player selectDrunk(){
		Random r = new Random();
		return players.get(r.nextInt(players.size()));
	}
	
	/**
	 * @return List of Factions
	 */
	public ArrayList<Faction> getFactions(){
		return factions;
	}
	
	/**
	 * Sorts players in roles
	 */
	public void sortPlayers(){
		players.sort(null);
	}
	
	/**
	 * Sorts players alphabetically by name
	 */
	public void sortPlayersByName(){
		Boolean cont = true;
		while(cont) {
			cont = false;
			for(int i = 0; i < players.size() - 1; i++) {
				if (players.get(i).compareToName(players.get(i + 1)) > 0){
					cont = true;
					Collections.swap(players, i, i + 1);
				}
			}
		}
	}
	
	/**
	 * @return the factionsLeft
	 */
	public ArrayList<Faction> getFactionsLeft(){
		return factionsLeft;
	}
	
	/**
	 * @param f the Faction to remove
	 */
	public void removeFactionsLeft(Faction f){
		this.factionsLeft.remove(f);
	}

	/**
	 * @return the day
	 */
	public short getDay(){
		return day;
	}

	/**
	 * Add 1 to the current day
	 */
	public void incrementDay(){
		this.day += 1;
	}
}