/**
 * @author Celeste Partan
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class to control the Mafia game
 */
public class Mafia{
	private ArrayList<Player> players;
	private ArrayList<Faction> factions;
	
	/**
	 * Constructor
	 */
	public Mafia(){
		players = new ArrayList<Player>();
		factions = new ArrayList<Faction>();
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
		if (playersIn.size() <= 1){
			System.err.println("Error: too few players");
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
		return new ArrayList<Player>(players);
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
	}
	
	/**
	 * @param name Name of faction to add
	 * @param num Minimum number of players in that faction
	 */
	public void addFaction(String name, int num){
		factions.add(new Faction(name, num));
	}
	
	/**
	 * Select a Player to be the Drunk
	 * @return Drunk Player
	 */
	public Player selectDrunk(){
		Random r = new Random();
		return players.get(r.nextInt(players.size()));
	}
}
