package tech.jacobc.fam;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.ObjectUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Main extends Application {

    public static ArrayList<Chore> chores = new ArrayList<>();
    public static ArrayList<FamilyMember> family = new ArrayList<>();
    public static ArrayList<Reminder> reminders = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../../../track.fxml"));
        primaryStage.setTitle("Fam");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
        Chore chore = new Chore("Garbage", "Daily", 10);
        System.out.println(chore.getID());
    }

    public static void main(String[] args) {
        FileInputStream serviceAccount = null;
        try {
            serviceAccount = new FileInputStream("./ServiceAccountKey.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Firestore db = FirestoreClient.getFirestore();;
        ApiFuture<QuerySnapshot> query = db.collection("familyMember").get();
        ApiFuture<QuerySnapshot> choreQuery = db.collection("chore").get();
        ApiFuture<QuerySnapshot> reminderQuery = db.collection("reminder").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = null;
        QuerySnapshot choreQuerySnapshot = null;
        QuerySnapshot reminderQuerySnapshot = null;
        try {
            querySnapshot = query.get();
            choreQuerySnapshot = choreQuery.get();
            reminderQuerySnapshot = reminderQuery.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        List<QueryDocumentSnapshot> choreDocuments = choreQuerySnapshot.getDocuments();
        List<QueryDocumentSnapshot> reminderDocuments = reminderQuerySnapshot.getDocuments();

        // Load Family Members into list
        for (QueryDocumentSnapshot document : documents) {
            String memberName = document.getString("memberName");
            long totalPoints = ObjectUtils.defaultIfNull(document.getLong("points"), 0L);
            family.add(new FamilyMember(document.getString("memberName"), totalPoints));
        }

        // Load chores into list
        for (QueryDocumentSnapshot document : choreDocuments) {
            String choreName = document.getString("choreName");
            String frequency = document.getString("frequency");
            long totalPoints = ObjectUtils.defaultIfNull(document.getLong("points"), 0L);
            chores.add(new Chore(choreName, frequency, totalPoints));
        }

        launch(args);
    }
}
