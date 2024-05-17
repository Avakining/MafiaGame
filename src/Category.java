/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.HashSet;

/**
 * 
 */
public class Category{
	private String name;
	private int minNum;
	private HashSet<Role> roles;
	private ArrayList<Player> players;
	
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
}
