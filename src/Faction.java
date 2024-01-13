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
	
	public Faction(String name, int num){
		minNum = num;
		this.factionName = name;
		categories = new ArrayList<Category>();
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
}
