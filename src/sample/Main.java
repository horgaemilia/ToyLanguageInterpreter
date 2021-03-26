package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mainWindow.fxml"));
        GridPane root = loader.load();
        StartController start = loader.getController();
        start.SetParentStage(primaryStage);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
