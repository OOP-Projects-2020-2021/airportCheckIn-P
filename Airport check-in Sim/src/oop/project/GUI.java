package oop.project;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class GUI extends Application{

    Stage window;
    Scene scene1, sceneAddPassenger, sceneFlightSchedule, sceneUpdatePassenger, sceneDeletePassenger;
    boolean isFrozen = false;
    private Timeline timeline;
    Button pausePlayButton;
    TableView<Passenger> gatesTable;
    final ObservableList<Passenger> gates = FXCollections.observableArrayList();

    public static void launchGUI() {
        launch();
    }




    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Airport Check-In Simulator");

        timeline = new Timeline(new KeyFrame(Duration.seconds(100)));
        timeline.setCycleCount(Animation.INDEFINITE);


        HBox topMenu = new HBox();
        Button buttonAddPassenger = new Button("Add Passenger");
        buttonAddPassenger.setOnAction(e -> window.setScene(sceneAddPassenger));
        Button buttonUpdatePassenger = new Button("Update Passenger");
        buttonUpdatePassenger.setOnAction(e -> {
            boolean result;
            if (isFrozen == false) {
                result = ConfirmBox.display("Error", "Please, freeze everything first!", "Ok, Freeze", "No, thank you");
                isFrozen = true;    ///need to implement
            }
            window.setScene(sceneUpdatePassenger);
        });
        Button buttonDeletePassenger = new Button("Delete Passenger");
        buttonDeletePassenger.setOnAction(e -> {
            boolean result;
            if (isFrozen == false) {
                result = ConfirmBox.display("Error", "Please, freeze everything first!", "Ok, Freeze", "No, thank you");
                isFrozen = true;    ///need to implement
            }
            window.setScene(sceneDeletePassenger);
        });
        Button buttonFlightSchedule = new Button("Flights Schedule");
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

        gatesTable.setOnMouseClicked(e -> {

            PassengerDetailsBox.display(gatesTable.getSelectionModel().getSelectedItem());
            //AlertBox.display("It works", "yey");
        });

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


        sceneAddPassenger = InsertPassengerScene.display(window, scene1);
        //sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);





        //scene delete passenger
       /* GridPane gridDeletePassenger = new GridPane();
        gridDeletePassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridDeletePassenger.setVgap(8);//set vertical spacing between cells
        gridDeletePassenger.setVgap(10); //horizontal spacing

        //First text
        Label text2 = new Label("You can only delete passengers which are in queue or in the waiting room!");
        GridPane.setConstraints(text2, 1, 0);

        //Find passenger
        Label textFindPassenger = new Label("Choose a passenger");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        //Passenger input
        ChoiceBox<String> choiceBoxPassengers = new ChoiceBox<>();
        ResultSet rsPassenger = MySqlCon.Query("select passenger.passenger_id, passenger.passenger_queue identity_card.id_first_name, identity_card.id_last_name, " +
                "flight.flight_destination, ticket.ticket_flight_seat FROM passenger JOIN identity_card ON passenger.passenger_identityCard = identity_card.id_id " +
                "JOIN ticket ON passenger.passenger_ticket_id = ticket.ticket_id JOIN flight ON ticket.ticket_flight_id = flight.flight_id");
        //set default value
        rsPassenger.next();
        choiceBoxPassengers.setValue(rsPassenger.getString(3) + "  " + rsPassenger.getString(4) + "  " + rsPassenger.getString(5)+ "  " + rsPassenger.getInt(6));
        choiceBoxPassengers.getItems().add(rsPassenger.getString(3) + "  " + rsPassenger.getString(4) + "  " + rsPassenger.getString(5)+ "  " + rsPassenger.getInt(6));
        while (rs.next())
            choiceBoxPassengers.getItems().add(rsPassenger.getString(3) + "  " + rsPassenger.getString(4) + "  " + rsPassenger.getString(5)+ "  " + rsPassenger.getInt(6));


        Button buttonDelete = new Button("Delete Passenger");
        buttonDelete.setOnAction(e -> {
            //delete
            String[] str = choiceBoxPassengers.getValue().split(" ");
            int passengerIndex = Integer.parseInt(str[0]);


            Main.addToQueue(passenger, queueNumber);    // add passenger to queue

            firstNameInput.clear();
            lastNameInput.clear();
            birthDatePicker.getEditor().clear();
            birthDatePicker.setValue(null);
            addressInput.clear();
            citizenshipInput.clear();
            luggageWeightInput.clear();
            choiceBoxFlights.getSelectionModel().clearSelection();
            choiceBoxSeats.getSelectionModel().clearSelection();
            choiceBoxQueue.getSelectionModel().clearSelection();

            window.setScene(scene1);
        });
        GridPane.setConstraints(savePassengerButton, 1, 10);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.setScene(scene1));
        GridPane.setConstraints(cancelButton, 2, 10);

        gridAddPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDatePicker, citizenshipLabel, citizenshipInput,
                luggageWeightLabel, luggageWeightInput, flightLabel, choiceBoxFlights, seatLabel, choiceBoxSeats, queueLabel, choiceBoxQueue,
                savePassengerButton, cancelButton);
        sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);
*/


        sceneFlightSchedule = FlightView.display(window, scene1);

        window.setScene(scene1);
        window.show();


    }
    private void closeProgram(){
        Boolean answer = ConfirmBox.display("Title", "Sure you want to exit?", "Yes", "No");
        if(answer)
        {
            window.close();
        }
        /*System.out.println("File is saved");
        window.close();*/
    }

    private boolean isInt(TextField input, String message){
        try{
            int var = Integer.parseInt(input.getText());
            System.out.println("user is: " + var);
            return true;
        }catch(NumberFormatException e){
            System.out.println("Error " + message + " is not a number");
            return false;
        }
    }


    private ObservableList<Passenger> getPassenger(){                                 ///for the table


        gates.add(Main.queue1.poll());
        gates.add(Main.queue2.poll());
        gates.add(Main.queue3.poll());
        gates.add(Main.queue4.poll());
        gates.add(Main.queue5.poll());
        gates.add(Main.queue6.poll());
        gates.add(Main.queue7.poll());
        gates.add(Main.queue8.poll());
        gates.add(Main.queue9.poll());
        gates.add(Main.queue10.poll());
        return gates;
    }


    public void refreshTable(){
        gates.clear();
        gates.add(Main.queue1.poll());
        gates.add(Main.queue2.poll());
        gates.add(Main.queue3.poll());
        gates.add(Main.queue4.poll());
        gates.add(Main.queue5.poll());
        gates.add(Main.queue6.poll());
        gates.add(Main.queue7.poll());
        gates.add(Main.queue8.poll());
        gates.add(Main.queue9.poll());
        gates.add(Main.queue10.poll());
        gatesTable.setItems(gates);
    }

    @FXML
    private void stopSimulation() {
        timeline.stop();
    }
    @FXML
    private void pauseSimulation() {
        timeline.pause();
    }

    @FXML
    private void playSimulation() {
        timeline.play();
    }

    @FXML
    private void resetSimulation() {
        timeline.jumpTo(Duration.ZERO);
    }

    private void reset(){
        if (timeline != null){
            timeline.stop();
        }

        gates.clear();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e ->{
            if(moreStepsToDo()) {
                refreshTable();
            }else {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        pausePlayButton.setOnAction(e -> {
            if (timeline.getStatus() == Animation.Status.RUNNING){
                timeline.pause();
                System.out.println("paused");
            }else{
                timeline.play();
                System.out.println("running");
            }
        });

    }

    private boolean moreStepsToDo(){
        if (!Main.queue1.isEmpty()) return true;
        if (!Main.queue2.isEmpty()) return true;
        if (!Main.queue3.isEmpty()) return true;
        if (!Main.queue4.isEmpty()) return true;
        if (!Main.queue5.isEmpty()) return true;
        if (!Main.queue6.isEmpty()) return true;
        if (!Main.queue7.isEmpty()) return true;
        if (!Main.queue8.isEmpty()) return true;
        if (!Main.queue9.isEmpty()) return true;
        if (!Main.queue10.isEmpty()) return true;
        return false;
    }

}
