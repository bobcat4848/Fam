package tech.jacobc.fam.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class Controller implements Initializable {

    @FXML
    private ImageView exit, menu;

    @FXML
    private AnchorPane pane1;

    @FXML
    private AnchorPane pane2;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart barChart;

    private Firestore db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        db = FirestoreClient.getFirestore();
        pieChart.setData(getPieChartData());
        barChart.getData().add(getBarChartData());
        pane1.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), pane1);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), pane2);
        translateTransition.setByX(-600);
        translateTransition.play();

        menu.setOnMouseClicked(event -> {

            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(8);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        pane1.setOnMouseClicked(event -> {
            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane1);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                pane1.setVisible(false);
            });


            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane2);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

    private ObservableList<PieChart.Data> getPieChartData() {
        // some database stuff, get people in the family
        return FXCollections.observableArrayList(
                new PieChart.Data("Mom", 13),
                new PieChart.Data("Dad", 25),
                new PieChart.Data("Brother", 10),
                new PieChart.Data("Sister", 22),
                new PieChart.Data("Me", 30));
    }

    private XYChart.Series getBarChartData() {
        // some database stuff, get people in the family
        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection("familyMember").get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = null;
        try {
            querySnapshot = query.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println("Name: " + document.getString("memberName"));
            System.out.println("Points: " + document.getLong("points"));
        }

        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("Mom", 25601.34));
        series1.getData().add(new XYChart.Data("Dad", 20148.82));
        series1.getData().add(new XYChart.Data("Brother", 10000));
        series1.getData().add(new XYChart.Data("Sister", 35407.15));
        series1.getData().add(new XYChart.Data("Me", 12000));
        return series1;
    }
}
