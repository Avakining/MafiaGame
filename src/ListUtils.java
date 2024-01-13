/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * *
 */
public class ListUtils{
	public static boolean isItemInArray(ArrayList<String> arr, String item){
		for (String i : arr){
			if (i.equals(item)){
				return true;
			}
		}
		return false;
	}
}
