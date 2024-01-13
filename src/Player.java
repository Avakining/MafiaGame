/**
 * @author Celeste Partan
 */

import java.util.Random;

/**
 * 
 */
public class Player{
	private String name;
	private Role role;
	private Faction faction;
	private Category category;
	private Boolean isDrunk;
	
	/**
	 * Constructor
	 * @param n name of player
	 */
	public Player(String n){
		name = n;
		isDrunk = false;
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
	}
}
