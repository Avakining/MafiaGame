/**
 * @author Celeste Partan
 */

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 */
public class Faction implements Serializable{
	private int minNum;
	private String factionName;
	private ArrayList<Category> categories;
	private double weight;
	private ArrayList<Player> players;
	private int maxNum;
	private ArrayList<Category> categoriesLeft;
	
	/**
	 * @param name Faction name
	 * @param num minimum number
	 * @param weight weight for Any
	 */
	public Faction(String name, int num, double weight){
		minNum = num;
		this.factionName = name;
		categories = new ArrayList<Category>();
		this.weight = weight;
		this.players = new ArrayList<Player>();
		this.categoriesLeft = new ArrayList<Category>();
	}
	
	/**
	 * @param c Category to add
	 */
	public void addCategory(Category c){
		categories.add(c);
		categoriesLeft.add(c);
	}
	/**
	 * @param name Category name
	 * @param num minimum number
	 */
	public void addCategory(String name, int num){
		this.addCategory(new Category(name, num));
	}

	/**
	 * @return the minNum
	 */
	public int getMinNum(){
		return minNum;
	}

	/**
	 * @return the factionName
	 */
	public String getName(){
		return factionName;
	}
	
	/**
	 * @return categories in this faction
	 */
	public ArrayList<Category> getCategories(){
		return categories;
	}

	/**
	 * @return the weight
	 */
	public double getWeight(){
		return weight;
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
	 * @return the Categories in this Faction that are not full
	 */
	public ArrayList<Category> getCategoriesLeft(){
		return categoriesLeft;
	}

	/**
	 * @param c the Category to remove
	 */
	public void removeCategoriesLeft(Category c){
		this.categoriesLeft.remove(c);
	}
}
