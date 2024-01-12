/**
 * @author Celeste Partan
 */

/**
 * 
 */
public class Role{
	private int maxNum;
	private String name;
	
	/**
	 * Constructor
	 * @param name Role name
	 * @param maxNum Maximum number of this role
	 */
	public Role(String name, int maxNum) {
		this.name = name;
		this.maxNum = maxNum;
	}
	
	/**
	 * Constructor
	 * @param name Role name
	 * @param isUnique Is this role unique?
	 */
	public Role(String name, Boolean isUnique){
		this(name, 1);
	}

	/**
	 * @return the maxNum
	 */
	public int getMaxNum(){
		return maxNum;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}
}
