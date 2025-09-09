/**
 * @author Celeste Partan
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
		if (path.exists()){
			System.out.println(path.toString() + " already exists, overwriting...");
		} else{
			path.createNewFile();
		}
		if (path.canWrite()){
			FileOutputStream file = new FileOutputStream(path);
			ObjectOutputStream saveObject = new ObjectOutputStream(file);
			saveObject.writeObject(mafiaGame);
			saveObject.flush();
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
	
	/**
	 * @param path Path of mafia save file
	 * @return Mafia object
	 * @throws IOException cannot find save file at provided path
	 * @throws ClassNotFoundException class not found
	 */
	public static Mafia loadGame(File path) throws IOException, ClassNotFoundException{
		Mafia mafiaGame = null;
		if (path.canRead()){
			FileInputStream file = new FileInputStream(path);
			ObjectInputStream saveObject = new ObjectInputStream(file);
			try{
				mafiaGame = (Mafia) saveObject.readObject();
				saveObject.close();
			} catch (Exception e){
				throw new InvalidSaveFileException("Error: The provided save file could not be read correctly");
			}
		} else{
			throw new IOException("Error: Could not find save file at the path");
		}
		return mafiaGame;
	}
	
	/**
	 * @return Mafia object
	 * @throws IOException cannot find save file at default location, "save.mafia"
	 * @throws ClassNotFoundException class not found
	 */
	public static Mafia loadGame() throws IOException, ClassNotFoundException{
		File path = new File("save.mafia");
		return loadGame(path);
	}
}
