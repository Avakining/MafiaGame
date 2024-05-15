/**
 * @author Celeste Partan
 */

import java.util.ArrayList;

/**
 * 
 */
public class Faction{
	private int minNum;
	private String factionName;
	private ArrayList<Category> categories;
	private double weight;
	
	public Faction(String name, int num, double weight){
		minNum = num;
		this.factionName = name;
		categories = new ArrayList<Category>();
		this.weight = weight;
	}
	
	public void addCategory(String name, int num){
		categories.add(new Category(name, num));
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
	
	public ArrayList<Category> getCategories(){
		return categories;
	}

	/**
	 * @return the weight
	 */
	public double getWeight(){
		return weight;
	}
}
