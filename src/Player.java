/**
 * @author Celeste Partan
 */

import java.io.Serializable;

/**
 * 
 */
public class Player implements Comparable<Player>, Serializable{
	private String name;
	private Role role;
	private Faction faction;
	private Category category;
	private Boolean isDrunk;
	private Player target;
	private Boolean isAlive;
	
	/**
	 * Constructor
	 * @param n name of player
	 */
	public Player(String n){
		name = n;
		isDrunk = false;
		target = null;
		isAlive = true;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}

	/**
	 * @return the role
	 */
	public Role getRole(){
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role){
		this.role = role;
		role.addPlayer(this);
	}

	/**
	 * @return the isDrunk
	 */
	public Boolean getIsDrunk(){
		return isDrunk;
	}

	/**
	 * @param isDrunk the isDrunk to set
	 */
	public void setIsDrunk(Boolean isDrunk){
		this.isDrunk = isDrunk;
	}
	
	/**
	 * @return the category
	 */
	public Category getCategory(){
		return category;
	}
	
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category){
		this.category = category;
		category.addPlayer(this);
	}

	/**
	 * @return the faction
	 */
	public Faction getFaction(){
		return faction;
	}

	/**
	 * @param faction the faction to set
	 */
	public void setFaction(Faction faction){
		this.faction = faction;
		faction.addPlayer(this);
	}
	
	@Override
	public int compareTo(Player o){
		if (this.faction.getName().compareTo(o.getFaction().getName()) == 0){
			if (this.category.getName().compareTo(o.getCategory().getName()) == 0){
				return this.name.compareTo(o.getName());
			} else{
				return this.category.getName().compareTo(o.getCategory().getName());
			}
		} else{
			return this.faction.getName().compareTo(o.getFaction().getName());
		}
	}
	
	/**
	 * @param o the Player to be compared
	 * @return the value 0 if the argument Player's name is equal to this string; a value less than 0 if this Player's name is lexicographically less than the string argument; and a value greater than 0 if this Player's name is lexicographically greater than the string argument.
	 */
	public int compareToName(Player o){
		return this.name.compareTo(o.getName());
	}
	
	/**
	 * @return True if and only if this Player has a target
	 */
	public boolean hasTarget(){
		return !target.equals(null);
	}
	
	/**
	 * @param p Player to set as this Player's target
	 */
	public void setTarget(Player p){
		this.target = p;
	}
	
	/**
	 * @return this Player's target
	 */
	public Player getTarget(){
		return this.target;
	}

	/**
	 * @return the isAlive
	 */
	public Boolean getIsAlive(){
		return isAlive;
	}

	/**
	 * Marks this Player as dead
	 */
	public void killPlayer(){
		this.isAlive = false;
	}
	
	/**
	 * Marks this Player as alive
	 */
	public void ressurectPlayer(){
		this.isAlive = true;
	}
}
