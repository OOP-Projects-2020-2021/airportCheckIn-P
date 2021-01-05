package oop.project;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainView {

    private static Scene mainScene;

    /*public static Scene display(Stage window, Scene sceneAddPassenger, Scene sceneDeletePassenger, Scene sceneFlightSchedule isFrozen){
        HBox topMenu = new HBox();
        Button buttonAddPassenger = new Button("Add Passenger");
        buttonAddPassenger.setOnAction(e -> window.setScene(sceneAddPassenger));
        Button buttonUpdatePassenger = new Button("Update Passenger");
        /*buttonUpdatePassenger.setOnAction(e -> {
            boolean result;
            if (isFrozen == false) {
                result = ConfirmBox.display("Error", "Please, freeze everything first!", "Ok, Freeze", "No, thank you");
                isFrozen = true;    ///need to implement
            }
            window.setScene(sceneUpdatePassenger);
        });*/
        Button buttonDeletePassenger = new Button("Delete Passenger");
        /*buttonDeletePassenger.setOnAction(e -> {
            boolean result;
            if (isFrozen == false) {
                result = ConfirmBox.display("Error", "Please, freeze everything first!", "Ok, Freeze", "No, thank you");
                isFrozen = true;    ///need to implement
            }
            window.setScene(sceneDeletePassenger);
        });*/
        /*Button buttonFlightSchedule = new Button("Flights Schedule");
        buttonFlightSchedule.setOnAction(e -> window.setScene(sceneFlightSchedule));

        topMenu.getChildren().addAll(buttonAddPassenger, buttonUpdatePassenger, buttonDeletePassenger, buttonFlightSchedule);

        BorderPane borderPane1 = new BorderPane();
        borderPane1.setTop(topMenu);



        //gate column
        TableColumn<Passenger, Integer> gateId = new TableColumn<>("Gate");
        gateId.setMaxWidth(100);
        gateId.setCellValueFactory(new PropertyValueFactory<>("queueNumber"));

        //id column
        TableColumn<Passenger, Integer> passengerId = new TableColumn<>("ID");
        passengerId.setMaxWidth(100);
        passengerId.setCellValueFactory(new PropertyValueFactory<>("id"));

        //status
        TableColumn<Passenger, PassengerStatus> passengerStatus = new TableColumn<>("Status");
        passengerStatus.setMaxWidth(500);
        passengerStatus.setCellValueFactory(new PropertyValueFactory<>("status"));



        gatesTable = new TableView<>();
        gatesTable.setItems(gates);
        gatesTable.getColumns().addAll(passengerId, passengerStatus);


        borderPane1.setCenter(gatesTable);

        HBox bottomMenu = new HBox();
        pausePlayButton = new Button("Pause/Play");
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> reset());

        reset();

        bottomMenu.getChildren().addAll(pausePlayButton, resetButton);
        bottomMenu.setAlignment(Pos.CENTER);
        borderPane1.setBottom(bottomMenu);

        scene1 = new Scene(borderPane1, 600, 600);
    }*/
}
