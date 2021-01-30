package tech.jacobc.fam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    public static HashMap<String, Integer> family = new HashMap<>();
    public static HashMap<String, Integer> chores = new HashMap<>();
    public static ArrayList<String> reminders = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../../sample.fxml"));
        primaryStage.setTitle("Fam");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        Chore chore = new Chore("Garbage", "Daily", 10);
        System.out.println(chore.getID());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
