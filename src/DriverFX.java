/**
 * @author Celeste Partan
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DriverFX extends Application{
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		// instantiate the FXMainPane, name it root
		FXMainPane root = new FXMainPane();
		// set the scene to hold root
		stage.setScene(new Scene(root, 600, 700));
		// set stage title
		stage.setTitle("Travelling Student");
		// display the stage
		stage.show();
		
	}
	
}
