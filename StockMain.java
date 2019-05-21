
/*
 * Veniamin Lebich
 * Final Project CS13
 * 5/20/2019
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StockMain extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		// get the fxml file and create a scene with it
		Parent root = FXMLLoader.load(getClass().getResource("./StockView.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Final project");
		stage.show();

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
