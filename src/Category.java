/**
 * @author Celeste Partan
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 */
public class Category implements Serializable{
	private String name;
	private int minNum;
	private HashSet<Role> roles;
	private HashSet<Role> rolesLeft;
	private ArrayList<Player> players;
	private int maxNum;
	
	/**
	 * Constructor
	 * @param name Name of category
	 * @param minNum Minimum number of assignments in this category
	 */
	public Category(String name, int minNum){
		this.name = name;
		this.minNum = minNum;
		this.roles = new HashSet<Role>();
		this.players = new ArrayList<Player>();
		this.rolesLeft = new HashSet<Role>(roles);
	}
	
	/**
	 * Add roles
	 * @param roles ArrayList of Roles
	 */
	public void addRoles(ArrayList<Role> roles){
		for (Role r : roles){
			this.addRole(r);
		}
	}
	
	/**
	 * Add roles
	 * @param roles ArrayList of Strings to be made into roles
	 * @param maxNums Maximum number of players in this Category before Any or Randoms
	 */
	public void addRoles(ArrayList<String> roles, ArrayList<Integer> maxNums){
		for (int i = 0; i < roles.size(); i++){
			this.addRole(roles.get(i), maxNums.get(i));
		}
	}
	
	/**
	 * @param r role to add
	 */
	public void addRole(Role r){
		this.roles.add(r);
	}
	
	/**
	 * @param name name of role
	 * @param maxNum max number for this role
	 */
	public void addRole(String name, int maxNum){
		this.roles.add(new Role(name, maxNum));
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return List of roles in this category
	 */
	public ArrayList<Role> getRoles(){
		ArrayList<Role> arr = new ArrayList<Role>(roles);
		return arr;
	}

	/**
	 * @return the minNum
	 */
	public int getMinNum(){
		return minNum;
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
	 * @return the maxNum
	 */
	public int getMaxNum(){
		return maxNum;
	}

	/**
	 * @param maxNum the maxNum to set
	 */
	public void setMaxNum(int maxNum){
		this.maxNum = maxNum;
	}

	/**
	 * @return the Roles in this Category that are not full
	 */
	public ArrayList<Role> getRolesLeft(){
		ArrayList<Role> arr = new ArrayList<Role>(rolesLeft);
		return arr;
	}

	/**
	 * @param r Role to remove
	 */
	public void removeRolesLeft(Role r){
		this.rolesLeft.remove(r);
	}
	
}
