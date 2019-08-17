/*Designed and created by Pulkit Ghai
*
*Dated: 10/07/2019
*A university project for subject Advance Programming Practices
*
*/
package application;
	
import controller.BattleController;
import database.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage relaunch;
	static BattleController bc = null;
	@Override
	public void start(Stage primaryStage) {
		relaunch=primaryStage;
		startup(primaryStage);
	}

	public void startup(Stage primaryStage) {
		try {
			Database.fxmlLoader = new FXMLLoader(getClass().getResource("battleGround.fxml"));
			Parent root = (AnchorPane)Database.fxmlLoader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Database.BattleControllerReference = Database.fxmlLoader.getController();
			BattleController.loadShipsData();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
public BattleController getBattleControllerInstance() {
	return bc;
}

	public static void main(String[] args) {
		launch(args);
	}
}
