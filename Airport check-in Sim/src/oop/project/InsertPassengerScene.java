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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class InsertPassengerScene extends Parent {

    private static Scene sceneAddPassenger;

    public static Scene display(Stage window, Scene scene1){
        //Layout for adding more passengers
        GridPane gridAddPassenger = new GridPane();
        gridAddPassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridAddPassenger.setVgap(8);//set vertical spacing between cells
        gridAddPassenger.setVgap(10); //horizontal spacing

        //First text
        Label text = new Label("Insert a new passenger");
        text.setAlignment(Pos.CENTER);
        text.setFont(new Font("Arial", 20));
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
        ChoiceBox<Integer> choiceBoxFlights = new ChoiceBox<>();
        ResultSet rs = MySqlCon.Query("select flight_id from flight");
        //set default value
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {

                choiceBoxFlights.setValue(rs.getInt("flight_id"));
                choiceBoxFlights.getItems().add(rs.getInt("flight_id"));
                while (rs.next())
                    choiceBoxFlights.getItems().add(rs.getInt("flight_id"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        //departure time
        Label departureLabel = new Label("Departure Time");
        GridPane.setConstraints(departureLabel, 0, 8);
        //input
        TextField departureInput = new TextField();
        GridPane.setConstraints(departureInput, 1, 8);

        //arrival time
        Label arrivalLabel = new Label("Arrival Time");
        GridPane.setConstraints(arrivalLabel, 0, 9);
        //input
        TextField arrivalInput = new TextField();
        GridPane.setConstraints(arrivalInput, 1, 9);

        //destination
        Label destinationLabel = new Label("Destination");
        GridPane.setConstraints(destinationLabel, 0, 10);
        //input
        TextField destinationInput = new TextField();
        GridPane.setConstraints(destinationInput, 1, 10);



        //AtomicInteger flightInput = new AtomicInteger();
        //AtomicInteger seatInput = new AtomicInteger();
        //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 11);
        ChoiceBox<Integer> choiceBoxSeats = new ChoiceBox<>();

        choiceBoxFlights.setOnAction(e ->{
            ResultSet rs2 = MySqlCon.Query("SELECT * FROM flight where flight_id = " + choiceBoxFlights.getSelectionModel().getSelectedItem());
            try {
                rs2.next();
                departureInput.setText(rs2.getString("flight_departure"));
                arrivalInput.setText(rs2.getString("flight_arrival"));
                destinationInput.setText(rs2.getString("flight_destination"));

                choiceBoxSeats.getSelectionModel().clearSelection();
                choiceBoxSeats.getItems().clear();

                ResultSet rs3 = MySqlCon.Query("select ticket_flight_seat from ticket where ticket_flight_id = "+choiceBoxFlights.getSelectionModel().getSelectedItem());
                Boolean[] visitedSeats = new Boolean[41];
                Arrays.fill(visitedSeats, Boolean.FALSE);
                while (true) {
                    try {
                        if (!rs3.next()) break;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    try {
                        visitedSeats[rs3.getInt("ticket_flight_seat")] = Boolean.TRUE;
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
                //seatInput.set(choiceBoxSeats.getValue());
                //GridPane.setConstraints(choiceBoxSeats, 1, 11);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });


        GridPane.setConstraints(choiceBoxFlights, 1, 7);
        GridPane.setConstraints(choiceBoxSeats, 1, 11);





        //save passenger button
        Button savePassengerButton = new Button("Save Passenger");
        savePassengerButton.setOnAction(e -> {
            //save everything in the database
            if(isNotEmpty(luggageWeightInput, firstNameInput, lastNameInput, birthDatePicker, addressInput, citizenshipInput)){

                LocalDate value = birthDatePicker.getValue();
                Date birthDateInput = java.sql.Date.valueOf( value );

                int queueNumber = choiceBoxFlights.getSelectionModel().getSelectedItem();                    //get the queue number
                int luggageWeight=Integer.parseInt(luggageWeightInput.getText());   //get the luggage weight

                Passenger passenger = new Passenger(firstNameInput.getText(), lastNameInput.getText(), birthDateInput, addressInput.getText(),
                        citizenshipInput.getText(), luggageWeight, choiceBoxFlights.getSelectionModel().getSelectedItem(),
                        choiceBoxSeats.getSelectionModel().getSelectedItem(), queueNumber);

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

                window.setScene(scene1);
            }
        });
        GridPane.setConstraints(savePassengerButton, 1, 12);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            luggageWeightInput.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY )));
            firstNameInput.clear();
            lastNameInput.clear();
            birthDatePicker.getEditor().clear();
            birthDatePicker.setValue(null);
            addressInput.clear();
            citizenshipInput.clear();
            luggageWeightInput.clear();
            choiceBoxFlights.getSelectionModel().clearSelection();
            choiceBoxSeats.getSelectionModel().clearSelection();
            window.setScene(scene1);
        });
        GridPane.setConstraints(cancelButton, 2, 12);

        gridAddPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDatePicker, citizenshipLabel, citizenshipInput,
                luggageWeightLabel, luggageWeightInput, flightLabel, choiceBoxFlights, departureLabel, departureInput,
                arrivalLabel, arrivalInput, destinationLabel, destinationInput, seatLabel, choiceBoxSeats,
                savePassengerButton, cancelButton);



        sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);
        return sceneAddPassenger;
    }

    private static boolean isNotEmpty(TextField luggage, TextField firstNameInput, TextField lastNameInput, DatePicker birthDatePicker, TextField addressInput, TextField citizenshipInput) {
        boolean empty = false;
        if (!isInt(luggage, luggage.getText())){
            empty = true;
            luggage.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (firstNameInput.getText().isEmpty()){
            empty = true;
            firstNameInput.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (lastNameInput.getText().isEmpty()){
            empty = true;
            lastNameInput.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (birthDatePicker.getValue() == null){
            empty = true;
            birthDatePicker.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (addressInput.getText().isEmpty()){
            empty = true;
            addressInput.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (citizenshipInput.getText().isEmpty()){
            empty = true;
            citizenshipInput.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
        }
        if (empty) return false;
        return true;
    }

    private static boolean isInt(TextField input, String message){
        try{
            int number = Integer.parseInt(message);
            if(number<=0){
                input.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            input.setBackground(new Background(new BackgroundFill(Paint.valueOf("#eb5505"), CornerRadii.EMPTY, Insets.EMPTY )));
            return false;
        }
    }


    @Override
    public Node getStyleableNode() {
        return null;
    }
}
