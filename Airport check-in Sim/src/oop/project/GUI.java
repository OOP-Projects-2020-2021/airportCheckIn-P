package oop.project;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.Queue;


public class GUI extends Application{

    private Stage window;
    private Scene sceneAddPassenger;
    private Scene sceneFlightSchedule;
    private Scene sceneUpdatePassenger;
    private Scene sceneDeletePassenger;

    private Timeline timeline;
    private Button pausePlayButton;
    private TableView<Passenger> gatesTable;
    private final ObservableList<Passenger> gates = FXCollections.observableArrayList();
    private final ObservableList<Luggage> luggage = FXCollections.observableArrayList();

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
        topMenu.setAlignment(Pos.CENTER);
        topMenu.setPadding(new Insets(10, 10, 10, 10));

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
        /*buttonUpdatePassenger.setOnAction(e -> {
            boolean result;
            if (isFrozen == false) {
                result = ConfirmBox.display("Error", "Please, freeze everything first!", "Ok, Freeze", "No, thank you");
                isFrozen = true;    ///need to implement
            }
            window.setScene(sceneUpdatePassenger);
        });*/
        Button buttonDeletePassenger = new Button("Delete Passenger");
        buttonDeletePassenger.setOnAction(e -> {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                boolean result = ConfirmBox.display("Warning", "Would you like to stop the simulation first?", "Yes, please", "No, thank you!");
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

        topMenu.getChildren().addAll(buttonAddPassenger, buttonDeletePassenger, buttonFlightSchedule);

        BorderPane borderPane1 = new BorderPane();
        borderPane1.setTop(topMenu);


        GridPane gridTables = new GridPane();
        gridTables.setPadding( new Insets(20, 20, 20, 20)); //padding for the grid margins
        gridTables.setVgap(10);//set vertical spacing between cells
        gridTables.setVgap(10); //horizontal spacing


        //titles
        Label gatesTableTitle = new Label("Passengers status");
        gatesTableTitle.setAlignment(Pos.CENTER);
        gatesTableTitle.setFont(new Font("Arial", 20));
        GridPane.setConstraints(gatesTableTitle, 0, 0);

        Label luggageTableTitle = new Label("Luggage status");
        luggageTableTitle.setAlignment(Pos.CENTER);
        luggageTableTitle.setFont(new Font("Arial", 20));
        GridPane.setConstraints(luggageTableTitle, 1, 0);

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
        passengerStatus.setPrefWidth(160);
        passengerStatus.setCellValueFactory(new PropertyValueFactory<>("status"));



        gatesTable = new TableView<>();
        gatesTable.setItems(gates);
        gatesTable.getColumns().addAll(gateId, passengerId, passengerStatus);

        gatesTable.setOnMouseClicked(e -> {
            if (gatesTable.getSelectionModel().getSelectedItem()!=null){
                PassengerDetailsBox.display(gatesTable.getSelectionModel().getSelectedItem());
            }
        });


        GridPane.setConstraints(gatesTable, 0, 1);

        //id column
        TableColumn<Luggage, Integer> luggageId = new TableColumn<>("ID");
        luggageId.setMaxWidth(100);
        luggageId.setCellValueFactory(new PropertyValueFactory<>("luggageId"));

        //weight column
        TableColumn<Luggage, Integer> luggageWeight = new TableColumn<>("Weight");
        luggageWeight.setMaxWidth(100);
        luggageWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));

        //status
        TableColumn<Luggage, LuggageStatus> luggageStatus = new TableColumn<>("Status");
        luggageStatus.setMaxWidth(500);
        luggageStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


        TableView<Luggage> luggageTableView = new TableView<>();
        luggageTableView.setItems(luggage);
        luggageTableView.getColumns().addAll(luggageId, luggageWeight, luggageStatus);

        GridPane.setConstraints(luggageTableView, 1, 1);


        gridTables.getChildren().addAll(gatesTableTitle, luggageTableTitle, gatesTable, luggageTableView);

        borderPane1.setCenter(gridTables);

        HBox bottomMenu = new HBox();
        pausePlayButton = new Button("Play");
        pausePlayButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,  new CornerRadii(2), Insets.EMPTY)));
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> timeline.stop());
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> reset());

        reset();

        bottomMenu.getChildren().addAll(pausePlayButton, resetButton);
        bottomMenu.setAlignment(Pos.CENTER);
        bottomMenu.setPadding(new Insets(20, 20, 20, 20));
        bottomMenu.setSpacing(10);
        borderPane1.setBottom(bottomMenu);

        Scene scene1 = new Scene(borderPane1, 600, 600);


        sceneAddPassenger = InsertPassengerScene.display(window, scene1);
        sceneDeletePassenger = DeletePassengerScene.display(window, scene1);
        sceneFlightSchedule = FlightView.display(window, scene1);

        window.setScene(scene1);
        window.show();


    }

    private Passenger refreshQueue(Queue<Passenger> queue){
        if (queue.peek()!=null) {
            if (queue.peek().getStatus() == PassengerStatus.IN_QUEUE) {
                queue.peek().setStatus(PassengerStatus.AT_CHECK_IN);
                queue.peek().setStartGateTime((int)timeline.getCurrentTime().toSeconds());
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.AT_CHECK_IN && queue.peek().getStartGateTime()+queue.peek().getGateTime() >= (int)timeline.getCurrentTime().toSeconds()) {
                queue.peek().setStatus(PassengerStatus.CHECKING_DETAILS);
                queue.peek().setStartCheckDetailsTime((int)timeline.getCurrentTime().toSeconds());
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.CHECKING_DETAILS && queue.peek().getStartCheckDetailsTime()+queue.peek().getCheckDetailsTime() >= (int)timeline.getCurrentTime().toSeconds()) {
                if (queue.peek().getLuggage().getStatus() == LuggageStatus.NOT_WEIGHTED) {
                    queue.peek().setStatus(PassengerStatus.WEIGHTING_LUGGAGE);
                    queue.peek().setStartWeightLuggageTime((int) timeline.getCurrentTime().toSeconds());
                }else{
                    queue.peek().setStatus(PassengerStatus.MOVE_TROUGH_GATE);
                    queue.peek().setStartMoveThroughGateTime((int) timeline.getCurrentTime().toSeconds());
                }
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.WEIGHTING_LUGGAGE && queue.peek().getStartWeightLuggageTime()+queue.peek().getWeightLuggageTime() >= (int)timeline.getCurrentTime().toSeconds()) {
                queue.peek().setStatus(PassengerStatus.MOVE_TROUGH_GATE);
                queue.peek().setStartMoveThroughGateTime((int)timeline.getCurrentTime().toSeconds());
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.MOVE_TROUGH_GATE && queue.peek().getStartMoveThroughGateTime()+queue.peek().getMoveThroughGateTime() >= (int)timeline.getCurrentTime().toSeconds()) {
                queue.peek().setStatus(PassengerStatus.IN_WAITING_ROOM);
                queue.peek().setStartInWaitingRoomTime((int)timeline.getCurrentTime().toSeconds() );
                return queue.peek();
            } else if (queue.peek().getStatus() == PassengerStatus.IN_WAITING_ROOM && queue.peek().getStartInWaitingRoomTime()+queue.peek().getInWaitingRoomTime() >= (int)timeline.getCurrentTime().toSeconds()) {
                return queue.poll();
            }
        }
        return queue.poll();
    }

    private void refreshTable(){
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
    private Luggage refreshLuggage(Passenger passenger){
        if (passenger.getStatus() == PassengerStatus.WEIGHTING_LUGGAGE){
            passenger.getLuggage().setStatus(LuggageStatus.WEIGHTING);
        }
        if (passenger.getStatus() == PassengerStatus.MOVE_TROUGH_GATE){
            passenger.getLuggage().setStatus(LuggageStatus.WEIGHTED);
        }
        if (passenger.getStatus() == PassengerStatus.IN_WAITING_ROOM){
            passenger.getLuggage().setStatus(LuggageStatus.SENT_TO_PLANE);
        }
        return passenger.getLuggage();
    }
    private void refreshLuggageTable(){
        luggage.clear();
        if (Main.queue1.peek() != null){
            luggage.add(refreshLuggage(Main.queue1.peek()));
        }else luggage.add(null);

        if (Main.queue2.peek() != null){
            luggage.add(refreshLuggage(Main.queue2.peek()));
        }else luggage.add(null);

        if (Main.queue3.peek() != null){
            luggage.add(refreshLuggage(Main.queue3.peek()));
        }else luggage.add(null);

        if (Main.queue4.peek() != null){
            luggage.add(refreshLuggage(Main.queue4.peek()));
        }else luggage.add(null);

        if (Main.queue5.peek() != null){
            luggage.add(refreshLuggage(Main.queue5.peek()));
        }else luggage.add(null);

        if (Main.queue6.peek() != null){
            luggage.add(refreshLuggage(Main.queue6.peek()));
        }else luggage.add(null);
        if (Main.queue7.peek() != null){
            luggage.add(refreshLuggage(Main.queue7.peek()));
        }else luggage.add(null);
        if (Main.queue8.peek() != null){
            luggage.add(refreshLuggage(Main.queue8.peek()));
        }else luggage.add(null);
        if (Main.queue9.peek() != null){
            luggage.add(refreshLuggage(Main.queue9.peek()));
        }else luggage.add(null);
        if (Main.queue10.peek() != null){
            luggage.add(refreshLuggage(Main.queue10.peek()));
        }else luggage.add(null);

    }



    private void reset(){
        if (timeline != null){
            timeline.stop();
            pausePlayButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,  new CornerRadii(2), Insets.EMPTY)));
            pausePlayButton.setText("Play");
        }

        gates.clear();
        Main.initQueue();
        refreshTable();
        refreshLuggageTable();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10), e ->{
            if(moreStepsToDo()) {
                refreshTable();
                refreshLuggageTable();
            }else {
                timeline.stop();
                System.out.println("stopped");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        pausePlayButton.setOnAction(e -> {
            if (timeline.getStatus() == Animation.Status.RUNNING){
                timeline.pause();
                pausePlayButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,  new CornerRadii(2), Insets.EMPTY)));
                pausePlayButton.setText("Play");
                System.out.println("paused");
            }else{
                timeline.play();
                pausePlayButton.setBackground(new Background(new BackgroundFill(Color.ORANGE,  new CornerRadii(2), Insets.EMPTY)));
                pausePlayButton.setText("Pause");
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
