package oop.project;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    Timeline timeline;
    Button pausePlayButton;
    TableView<Passenger> gatesTable;

    public static void launchGUI() {
        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Airport Check-In Simulator");

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




        borderPane1.setCenter(gatesTable);

        HBox bottomMenu = new HBox();
        pausePlayButton = new Button("Pause/Play");
        Button resetButton = new Button("Reset");
         //resetButton.setOnAction(e -> reset());

        //reset();
        bottomMenu.getChildren().addAll(pausePlayButton, resetButton);
        bottomMenu.setAlignment(Pos.CENTER);
        borderPane1.setBottom(bottomMenu);

        scene1 = new Scene(borderPane1, 600, 600);




        //Layout for adding more passengers
        GridPane gridAddPassenger = new GridPane();
        gridAddPassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridAddPassenger.setVgap(8);//set vertical spacing between cells
        gridAddPassenger.setVgap(10); //horizontal spacing

        //First text
        Label text = new Label("Insert a new passenger");
        GridPane.setConstraints(text, 1, 0);

        //First Name
        Label firstNameLabel = new Label("First Name");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        //.Name input
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("first name");
        GridPane.setConstraints(firstNameInput, 1, 1);

        //Last Name
        Label lastNameLabel = new Label("Last Name");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        //.Name input
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("last name");
        GridPane.setConstraints(lastNameInput, 1, 2);


        //address
        Label addressLabel = new Label("Address");
        GridPane.setConstraints(addressLabel, 0, 3);
        //.Name input
        TextField addressInput = new TextField();
        addressInput.setPromptText("address");
        GridPane.setConstraints(addressInput, 1, 3);

        //birth date
        Label birthDateLabel = new Label("Birth date");
        GridPane.setConstraints(birthDateLabel, 0, 4);
        //date input
        DatePicker birthDatePicker = new DatePicker();

        GridPane.setConstraints(birthDatePicker, 1, 4);



        //citizenship
        Label citizenshipLabel = new Label("Citizenship");
        GridPane.setConstraints(citizenshipLabel, 0, 5);
        //.Name input
        TextField citizenshipInput = new TextField();
        citizenshipInput.setPromptText("citizenship");
        GridPane.setConstraints(citizenshipInput, 1, 5);

        //luggage
        Label luggageWeightLabel = new Label("Luggage Weight");
        GridPane.setConstraints(luggageWeightLabel, 0, 6);
        //.Name input
        TextField luggageWeightInput = new TextField();
        luggageWeightInput.setPromptText("luggage weight");
        GridPane.setConstraints(luggageWeightInput, 1, 6);



        //flight
        Label flightLabel = new Label("Flight");
        GridPane.setConstraints(flightLabel, 0, 7);
        //flight input
        ChoiceBox<String> choiceBoxFlights = new ChoiceBox<>();
        ResultSet rs = MySqlCon.Query("select * from flight");
        //set default value
        rs.next();
        choiceBoxFlights.setValue(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));
        choiceBoxFlights.getItems().add(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));
        while (rs.next())
            choiceBoxFlights.getItems().add(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));


        AtomicInteger flightInput = new AtomicInteger();
        AtomicInteger seatInput = new AtomicInteger();
        //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 8);
        ChoiceBox<Integer> choiceBoxSeats = new ChoiceBox<>();
        choiceBoxFlights.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            String[] str = choiceBoxFlights.getValue().split(" ");
            flightInput.set(Integer.parseInt(str[0]));


            //Seat input
            choiceBoxSeats.getSelectionModel().clearSelection();
            choiceBoxSeats.getItems().clear();

            ResultSet rs2 = MySqlCon.Query("select ticket_flight_seat from ticket where ticket_flight_id = "+flightInput);
            Boolean[] visitedSeats = new Boolean[41];
            Arrays.fill(visitedSeats, Boolean.FALSE);
            while (true) {
                try {
                    if (!rs2.next()) break;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    visitedSeats[rs2.getInt("ticket_flight_seat")] = Boolean.TRUE;
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            int defaultValue = -1;
            for (int i=1; i<41; i++)
            {
                if (visitedSeats[i] == Boolean.FALSE){
                    choiceBoxSeats.getItems().add(i);
                    if (defaultValue == -1)
                        defaultValue = i;
                }
            }
            //set default value
            choiceBoxSeats.setValue(defaultValue);


        });

        GridPane.setConstraints(choiceBoxFlights, 1, 7);
        GridPane.setConstraints(choiceBoxSeats, 1, 8);



        //check in gate   -> to do
        Label queueLabel = new Label("Check-In Gate");
        GridPane.setConstraints(queueLabel, 0, 9);
        //flight input
        ChoiceBox<Integer> choiceBoxQueue = new ChoiceBox<>();
        GridPane.setConstraints(choiceBoxQueue, 0, 9);
        choiceBoxQueue.setValue(1);
        choiceBoxQueue.getItems().addAll(1, 2, 3, 4, 5, 6,7 , 8, 9, 10);
        GridPane.setConstraints(choiceBoxQueue, 1, 9);


        //save passenger button
        Button savePassengerButton = new Button("Save Passenger");
        savePassengerButton.setOnAction(e -> {
            //save everything in the database

            LocalDate value = birthDatePicker.getValue();
            Date birthDateInput = java.sql.Date.valueOf( value );
            seatInput.set(choiceBoxSeats.getValue());                           //get the seat number
            int queueNumber = choiceBoxQueue.getValue();                        //get the queue number
            int luggageWeight=Integer.parseInt(luggageWeightInput.getText());   //get the luggage weight

            Passenger passenger = new Passenger(firstNameInput.getText(), lastNameInput.getText(), birthDateInput, addressInput.getText(),
                    citizenshipInput.getText(), luggageWeight, flightInput.intValue(), seatInput.intValue(), queueNumber);

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


        //scene flights schedule
        BorderPane borderPaneFlightSchedule = new BorderPane();
        HBox topFlightsScene = new HBox();
        Button buttonGoBackToScene1 = new Button("Go back");
        buttonGoBackToScene1.setOnAction(e -> window.setScene(scene1));
        topFlightsScene.getChildren().add(buttonGoBackToScene1);
        borderPaneFlightSchedule.setTop(topFlightsScene);


        TableView<Flight> flightsTable;

        //id column
        TableColumn<Flight, Integer> flightID = new TableColumn<>("Id");
        flightID.setMaxWidth(100);
        flightID.setCellValueFactory(new PropertyValueFactory<>("id"));

        //departure time column
        TableColumn<Flight, Date> flightDeparture = new TableColumn<>("Departure Time");
        flightDeparture.setMaxWidth(500);
        flightDeparture.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        //arrival time column
        TableColumn<Flight, Date> flightArrival = new TableColumn<>("Arrival Time");
        flightArrival.setMaxWidth(500);
        flightArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        //arrival time column
        TableColumn<Flight, String> flightDestination = new TableColumn<>("Destination");
        flightDestination.setMaxWidth(400);
        flightDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        flightsTable = new TableView<>();
        flightsTable.setItems(getFlight());
        flightsTable.getColumns().addAll(flightID, flightDeparture, flightArrival, flightDestination);


        borderPaneFlightSchedule.setCenter(flightsTable);
        sceneFlightSchedule = new Scene(borderPaneFlightSchedule, 600, 600);

        window.setScene(scene1);
        window.show();

        /*window.setScene(scene1);
        window.setTitle("Title here");
        window.show();*/

        /*primaryStage.setTitle("Airport Check-In Simulator");
        button = new Button();
        button.setText("Click me");
        button.setOnAction(e -> {
            System.out.println("hey now brown cow");
            System.out.println("hey");

        }); // when click the code to handle this is in "this" class

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();*/
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

    private ObservableList<Flight> getFlight(){                                 ///for the table
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        ResultSet rs = MySqlCon.Query("select * from flight");
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                flights.add(new Flight(rs.getInt(1), rs.getTimestamp(2) ,rs.getTimestamp(3), rs.getString(4) ));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return flights;
    }
    private ObservableList<Passenger> getPassenger(){                                 ///for the table
        ObservableList<Passenger> gates = FXCollections.observableArrayList();

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

    /*private void reset(){
        if (timeline != null){
            timeline.stop();
        }

        //simulation table
        //id column
        TableColumn<Passenger, Integer> passengerIdColumn = new TableColumn<>("ID");
        passengerIdColumn.setMinWidth(100);
        passengerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        //status column
        TableColumn<Passenger, PassengerStatus> passengerStatusColumn = new TableColumn<>("Status");
        passengerStatusColumn.setMinWidth(400);
        passengerStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));



        gatesTable = new TableView<>();
        gatesTable.setItems(getPassenger());
        gatesTable.getColumns().addAll(passengerIdColumn, passengerStatusColumn);

        timeline = new Timeline(new KeyFrame(Duration.seconds(2), e ->{
            if(moreStepsToDo()) {
                doNextStep();
            }else {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        pausePlayButton.disabledProperty().bind(Bindings.createBooleanBinding())

    }
    }*/
}
