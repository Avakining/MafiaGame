/**
 * @author Celeste Partan
 */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
// import java.util.Random;
import java.util.Scanner;

/**
 * 
 */
public class MafiaGameDriver{
	private static Mafia mafiaGame;
	private static Scanner scan;
	// private static Random rand;
	
	/**
	 * @param args Arguments*
	 */
	public static void main(String[] args){
		System.out.println("Welcome to the Mafia GM Utility day evaluation module!");
		loadSaveFile();
	}
	
	private static void loadSaveFile(){
		System.out.println("Loading game...");
		Path path = Paths.get("save.mafia");
		while (!(path.toFile().exists() && path.toFile().canRead())){
			System.err.println("File is not in expected location");
			System.out.println("Please enter the path to factions_roles.toml:");
			String in = scan.nextLine();
			path = Paths.get(in);
		}
		try {
			mafiaGame = SaveUtils.loadGame(path.toFile());
			System.out.println("Loaded");
			System.out.println(mafiaGame.getClass().getName());
		} catch (InvalidSaveFileException e){
			System.err.println("Error: could not read save file; it seems to be a corrupted.");
			e.printStackTrace();
		} catch (IOException e){
			System.err.println("Error: could not read file");
			e.printStackTrace();
			
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
