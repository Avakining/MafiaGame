/**
 * @author Celeste Partan
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 * 
 */
public class SaveUtils{
	/**
	 * Saves a given mafiaGame
	 * @param mafiaGame mafia game to be saved
	 * @param path path to store game file
	 * @throws IOException cannot write save file to provided path
	 */
	public static void saveGame(Mafia mafiaGame, File path) throws IOException{
		if (path.createNewFile()){
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream saveObject = new ObjectOutputStream(file);
			saveObject.writeObject(mafiaGame);
			saveObject.close();
		} else{
			throw new IOException("Failed to create save file at " + path.getAbsolutePath());
		}
	}
	
	/**
	 * Saves a given mafiaGame at the default location
	 * @param mafiaGame mafia game to be saved
	 * @throws IOException cannot write save file to default path
	 */
	public static void saveGame(Mafia mafiaGame) throws IOException{
		File path = new File("save_" + System.currentTimeMillis() + ".mafia");
		System.out.println("Saving to " + path.getAbsolutePath());
		saveGame(mafiaGame, path);
	}
	
	/**
	 * Saves a given mafiaGame
	 * @param mafiaGame mafia game to be saved
	 * @param path path to store game file
	 * @throws IOException cannot write save file to provided path
	 */
	public static void saveGame(Mafia mafiaGame, String path) throws IOException{
		File filePath = new File(path);
		saveGame(mafiaGame, filePath);
	}
}
