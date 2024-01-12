/**
 * @author Celeste Partan
 */

import java.util.ArrayList;
import java.util.Collections;

/**
 * @param <T> Type
 * 
 */
public abstract class ListUtils<T> {
	public ArrayList<T> randomizeList(ArrayList<T> inList){
		Collections.shuffle(inList);
		return inList;
	}
}
