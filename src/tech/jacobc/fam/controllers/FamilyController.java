package tech.jacobc.fam.controllers;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
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
import java.util.Date;
import java.util.ResourceBundle;

import static tech.jacobc.fam.Main.chores;
import static tech.jacobc.fam.Main.family;

public class FamilyController implements Initializable {

    @FXML private ImageView exit, menu;
    @FXML private AnchorPane pane1;
    @FXML private AnchorPane pane2;
    @FXML private Label timeText;
    @FXML private TableView<FamilyMember> familyTable;
    @FXML private TableColumn nameColumn;
    @FXML private TableColumn pointsColumn;
    @FXML private JFXTextField nameTextField;

    private ObservableList<FamilyMember> data;

    private String name = "";
    private boolean isCapital = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SimpleDateFormat formatDate = new SimpleDateFormat("EEE, MMM d, ''yy hh:mm a");
        timeText.setText(formatDate.format(new Date()));

        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        // chore table stuff
        data = FXCollections.observableArrayList(family);
        nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory("totalPoints"));
        familyTable.setItems(data);
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

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(60), event -> {
            timeText.setText(formatDate.format(new Date()));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void addKey(String c) {
        if (isCapital) name += c.toUpperCase();
        else name += c.toLowerCase();
        nameTextField.setText(name);
    }

    @FXML private void addToFamily() {
        if (!name.trim().equals("")) {
            family.add(new FamilyMember(name, 0));
            name = "";
            nameTextField.setText(name);
        }
        update();
    }

    private void update() {
        data = FXCollections.observableArrayList(family);
        familyTable.setItems(data);
    }

    @FXML private void capitalPress() { isCapital = !(isCapital); }
    @FXML private void delPress() {
        name = name.substring(0, name.length() - 1);
        nameTextField.setText(name);
    }
    @FXML private void qPress() { addKey("q"); }
    @FXML private void wPress() { addKey("w"); }
    @FXML private void ePress() { addKey("e"); }
    @FXML private void rPress() { addKey("r"); }
    @FXML private void tPress() { addKey("t"); }
    @FXML private void yPress() { addKey("y"); }
    @FXML private void uPress() { addKey("u"); }
    @FXML private void iPress() { addKey("i"); }
    @FXML private void oPress() { addKey("o"); }
    @FXML private void pPress() { addKey("p"); }
    @FXML private void aPress() { addKey("a"); }
    @FXML private void sPress() { addKey("s"); }
    @FXML private void dPress() { addKey("d"); }
    @FXML private void fPress() { addKey("f"); }
    @FXML private void gPress() { addKey("g"); }
    @FXML private void hPress() { addKey("h"); }
    @FXML private void jPress() { addKey("j"); }
    @FXML private void kPress() { addKey("k"); }
    @FXML private void lPress() { addKey("l"); }
    @FXML private void zPress() { addKey("z"); }
    @FXML private void xPress() { addKey("x"); }
    @FXML private void cPress() { addKey("c"); }
    @FXML private void vPress() { addKey("v"); }
    @FXML private void bPress() { addKey("b"); }
    @FXML private void nPress() { addKey("n"); }
    @FXML private void mPress() { addKey("m"); }
    @FXML private void spacePress() { addKey(" "); }

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
