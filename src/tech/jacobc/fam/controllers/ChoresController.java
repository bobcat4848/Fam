package tech.jacobc.fam.controllers;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tech.jacobc.fam.Chore;
import tech.jacobc.fam.FamilyMember;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static tech.jacobc.fam.Main.chores;
import static tech.jacobc.fam.Main.family;

public class ChoresController implements Initializable {

    @FXML private ImageView exit, menu;
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;
    @FXML private Label timeText;
    @FXML private JFXComboBox personComboBox;
    @FXML private JFXComboBox frequencyComboBox;
    @FXML private TableView<Chore> choreListNotDone;
    @FXML private ListView choreListDone;
    @FXML private TableColumn choreColumn;
    @FXML private TableColumn frequencyColumn;
    @FXML private TableColumn pointsColumn;
    @FXML private JFXSlider pointBar;

    private ObservableList<Chore> data;

    private String selectedToDoChore = "";
    private long selectedToDoChorePoints = 0;

    private Chore currentlySelectedChore;

    private Firestore db;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat formatDate = new SimpleDateFormat("EEE, MMM d, ''yy hh:mm a");
        timeText.setText(formatDate.format(new Date()));

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // chore table stuff
        data = FXCollections.observableArrayList(chores);
        choreColumn.setCellValueFactory(new PropertyValueFactory("name"));
        frequencyColumn.setCellValueFactory(new PropertyValueFactory("frequency"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory("points"));
        choreListNotDone.setItems(data);
        // end of chore table stuff

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

        personComboBox.setItems(populatePersonComboBox());
        frequencyComboBox.setItems(populateFrequencyComboBox());

        choreListDone.setItems(populateChoreListDone());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), event -> {
            timeText.setText(formatDate.format(new Date()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
    @FXML
    private void choreStore() {
        currentlySelectedChore = choreListNotDone.getSelectionModel().selectedItemProperty().get();
    }

    @FXML
    private void storeChore() {
        selectedToDoChore = (String) choreListDone.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void storePoints() {
        selectedToDoChorePoints = (long) pointBar.getValue();
        System.out.println(selectedToDoChorePoints);
    }

    // This method should only be called if a new website has been added and the table must be updated
    private void update() {
        data = FXCollections.observableArrayList(chores);
        choreListNotDone.setItems(data);
    }

    private ObservableList<String> populatePersonComboBox() {
        List<String> people = new ArrayList<>();
        for (FamilyMember member : family) {
            people.add(member.getName());
        }
        return FXCollections.observableArrayList(people);
    }

    private ObservableList<String> populateFrequencyComboBox() {
        return FXCollections.observableArrayList(
                "Daily",
                "Weekly",
                "Bi-Weekly",
                "Monthly"
        );
    }

    private ObservableList<String> populateChoreListDone() {
        return FXCollections.observableArrayList(
                "Do the Garbage",
                "Do the Dishes",
                "Mow the lawn",
                "Dust the house",
                "Clean room",
                "Wash car",
                "Do the laundry",
                "Mop",
                "Wash Dogs",
                "Clean cat litter",
                "Make bed",
                "Clean tub",
                "Clean toilet",
                "Weed the garden"
        );
    }

    @FXML
    private void createChoreButtonPressed(ActionEvent event) throws IOException {
        chores.add(new Chore(selectedToDoChore, frequencyComboBox.getValue().toString(), selectedToDoChorePoints));
        update();
    }

    @FXML
    private void markChoreDoneButton(ActionEvent event) {
        System.out.println("Before: " + chores);
        chores.remove(currentlySelectedChore);
        System.out.println("After: " + chores);
        String familyMemberName = personComboBox.getValue().toString();
        for (int i = 0; i < family.size(); i++) {
            if (family.get(i).getName().equalsIgnoreCase(familyMemberName)) {
                family.get(i).setTotalPoints(family.get(i).getTotalPoints() + currentlySelectedChore.getPoints());
            }
        }
    }

    @FXML
    private void onHomeButtonPressed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("../../../../track.fxml"));
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
