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
import java.util.Queue;
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
        buttonAddPassenger.setOnAction(e -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                boolean result = ConfirmBox.display("Warning", "Would you like to pause the simulation to proceed?", "Yes, please", "No, thank you!");
                if (result == true){
                    timeline.stop();
                    System.out.println("stopped");
                    window.setScene(sceneAddPassenger);
                }
            }
            else{
                window.setScene(sceneAddPassenger);
            }
        });
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
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                boolean result = ConfirmBox.display("Warning", "You have to stop the simulation first?", "Yes, please", "No, thank you!");
                if (result == true){
                    timeline.stop();
                    System.out.println("stopped");
                    window.setScene(sceneDeletePassenger);
                    reset();
                }
            }
            else{
                window.setScene(sceneDeletePassenger);
            }
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
        gatesTable.getColumns().addAll(gateId, passengerId, passengerStatus);

        gatesTable.setOnMouseClicked(e -> {

            PassengerDetailsBox.display(gatesTable.getSelectionModel().getSelectedItem());
            //AlertBox.display("It works", "yey");
        });

        borderPane1.setCenter(gatesTable);

        HBox bottomMenu = new HBox();
        pausePlayButton = new Button("Pause/Play");
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> timeline.stop());
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> reset());

        reset();

        bottomMenu.getChildren().addAll(pausePlayButton, resetButton);
        bottomMenu.setAlignment(Pos.CENTER);
        borderPane1.setBottom(bottomMenu);

        scene1 = new Scene(borderPane1, 600, 600);


        sceneAddPassenger = InsertPassengerScene.display(window, scene1);
        sceneDeletePassenger = DeletePassengerScene.display(window, scene1);
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


        gates.add(refreshQueue(Main.queue1));
        gates.add(refreshQueue(Main.queue2));
        gates.add(refreshQueue(Main.queue3));
        gates.add(refreshQueue(Main.queue4));
        gates.add(refreshQueue(Main.queue5));
        gates.add(refreshQueue(Main.queue6));
        gates.add(refreshQueue(Main.queue7));
        gates.add(refreshQueue(Main.queue8));
        gates.add(refreshQueue(Main.queue9));
        gates.add(refreshQueue(Main.queue10));
        return gates;
    }

    public Passenger refreshQueue(Queue<Passenger> queue){
        if (queue.peek()!=null) {
            if (queue.peek().getStatus() == PassengerStatus.IN_QUEUE) {
                queue.peek().setStatus(PassengerStatus.AT_CHECK_IN);
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.AT_CHECK_IN) {
                queue.peek().setStatus(PassengerStatus.CHECKING_DETAILS);
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.CHECKING_DETAILS) {
                queue.peek().setStatus(PassengerStatus.WEIGHTING_LUGGAGE);
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.WEIGHTING_LUGGAGE) {
                queue.peek().setStatus(PassengerStatus.MOVE_TROUGH_GATE);
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.MOVE_TROUGH_GATE) {
                queue.peek().setStatus(PassengerStatus.IN_WAITING_ROOM);
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.IN_WAITING_ROOM) {
                return queue.poll();
            }
        }
        return queue.poll();
    }

    public void refreshTable(){
        gates.clear();
        gates.add(refreshQueue(Main.queue1));
        gates.add(refreshQueue(Main.queue2));
        gates.add(refreshQueue(Main.queue3));
        gates.add(refreshQueue(Main.queue4));
        gates.add(refreshQueue(Main.queue5));
        gates.add(refreshQueue(Main.queue6));
        gates.add(refreshQueue(Main.queue7));
        gates.add(refreshQueue(Main.queue8));
        gates.add(refreshQueue(Main.queue9));
        gates.add(refreshQueue(Main.queue10));
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
        Main.initQueue();
        refreshTable();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e ->{
            if(moreStepsToDo()) {
                refreshTable();
            }else {
                timeline.stop();
                System.out.println("stopped");
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
