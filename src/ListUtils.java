/**
 * @author Celeste Partan
 */

import java.util.ArrayList;

/**
 * *
 */
public class ListUtils{
	/**
	 * Determines if a given String is in an ArrayList
	 * @param arr ArrayList
	 * @param item String to check for
	 * @return True if String is in Array
	 */
	public static boolean isItemInArray(ArrayList<String> arr, String item){
		for (String i : arr){
			if (i.equals(item)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param f Faction to search
	 * @param s Name to search for
	 * @return Category with that name, or null if not found
	 */
	public static Category findCategory(Faction f, String s){
		for (Category c : f.getCategories()){
			if (c.getName().equals(s)){
				return c;
			}
		}
		return null;
	}
	
	/**
	 * @param arr ArrayList to search
	 * @param s Name to search for
	 * @return Faction with that name, or null if not found
	 */
	public static Faction findFaction(ArrayList<Faction> arr, String s){
		for (Faction f : arr){
			if (f.getName().equals(s)){
				return f;
			}
		}
		return null;
	}
}
