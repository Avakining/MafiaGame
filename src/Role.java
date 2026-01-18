/**
 * @author Celeste Partan
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */
public class Role implements Serializable{
	private int maxNum;
	private String name;
	private int numCurr;
	private ArrayList<Player> players;
	private int abilityChargesLeft;
	
	/**
	 * Constructor
	 * @param name Role name
	 * @param maxNum Maximum number of this role
	 * @param abilityCharges Number of initial charges for class ability (if applicable)
	 */
	public Role(String name, int maxNum, int abilityCharges){
		this.name = name;
		this.maxNum = maxNum;
		numCurr = 0;
		this.players = new ArrayList<Player>();
	}
	
	/**
	 * Constructor
	 * @param name Role name
	 * @param maxNum Maximum number of this role
	 */
	public Role(String name, int maxNum){
		this(name, maxNum, 0);
	}
	
	/**
	 * Constructor
	 * @param name Role name
	 * @param isUnique Is this role unique?
	 */
	public Role(String name, Boolean isUnique){
		this(name, 1, 0);
	}

	/**
	 * @return the maxNum
	 */
	public int getMaxNum(){
		return maxNum;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return the numCurr
	 */
	public int getNumCurr(){
		return numCurr;
	}
	
	/**
	 */
	public void addNumCurr(){
		this.numCurr++;
	}
	
	/**
	 * @param p Player to be added
	 */
	public void addPlayer(Player p){
		players.add(p);
	}
	
	/**
	 * @return list of Players
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}

	/**
	 * @return the abilityChargesLeft
	 */
	public int getAbilityChargesLeft(){
		return abilityChargesLeft;
	}
}
