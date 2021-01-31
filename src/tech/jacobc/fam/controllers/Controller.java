package tech.jacobc.fam.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.ObjectUtils;
import tech.jacobc.fam.FamilyMember;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import static tech.jacobc.fam.Main.family;

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

    @FXML
    private Label timeText;


    private Firestore db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat formatDate = new SimpleDateFormat("EEE, MMM d, ''yy hh:mm a");
        timeText.setText(formatDate.format(new Date()));

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

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
            menu.setDisable(true);
            pane1.setVisible(true);

            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),pane1);
            fadeTransition1.setFromValue(8);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),pane2);
            translateTransition1.setByX(+600);
            translateTransition1.play();

            new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> menu.setDisable(false)))
                    .play();
        });

        pane1.setOnMouseClicked(event -> {
            pane1.setDisable(true);
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

            new Timeline(new KeyFrame(
                    Duration.millis(2500),
                    ae -> pane1.setDisable(false)))
                    .play();
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), event -> {
            timeText.setText(formatDate.format(new Date()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private ObservableList<PieChart.Data> getPieChartData() {
        List<PieChart.Data> familyMembers = new ArrayList<>();
        for (FamilyMember member : family) {
            familyMembers.add(new PieChart.Data(member.getName(), member.getTotalPoints()));
        }
        return FXCollections.observableArrayList(familyMembers);
    }

    private XYChart.Series getBarChartData() {
        // some database stuff, get people in the family
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Total Points");
        for (FamilyMember member : family) {
            series1.getData().add(new XYChart.Data(member.getName(), member.getTotalPoints()));
        }

        return series1;
    }

    @FXML
    private void onChoreListButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../chores.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void onFamilyButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../family.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void onNotificationButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../reminders.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void onRewardButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../rewards.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void onGroceryButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../grocery.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void onCalendarButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../calendar.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }
}
