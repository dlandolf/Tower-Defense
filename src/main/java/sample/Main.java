package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class
 */
public class Main extends Application {

	/**
	 * Start function
	 * @param primaryStage Design of stage
	 * @throws Exception Throw Exception if resources could not be loaded
	 */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Tower Defence");
        primaryStage.setScene(new Scene(root, 600, 480));
        primaryStage.show();
        MyController appController = (MyController)loader.getController();
        appController.createArena();
    }

    /**
     * main function that launch
     * @param args Args of main
     */
    public static void main(String[] args) {
        launch(args);
    }
}
