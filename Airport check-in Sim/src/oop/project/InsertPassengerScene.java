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
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                rs.next();
                choiceBoxFlights.setValue(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));
                choiceBoxFlights.getItems().add(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));
                while (rs.next())
                    choiceBoxFlights.getItems().add(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3)+ "  " + rs.getString(4));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }


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
            seatInput.set(choiceBoxSeats.getValue());
            GridPane.setConstraints(choiceBoxSeats, 1, 8);
        });

        GridPane.setConstraints(choiceBoxFlights, 1, 7);
        GridPane.setConstraints(choiceBoxSeats, 1, 8);





        //save passenger button
        Button savePassengerButton = new Button("Save Passenger");
        savePassengerButton.setOnAction(e -> {
            //save everything in the database

            LocalDate value = birthDatePicker.getValue();
            Date birthDateInput = java.sql.Date.valueOf( value );
            seatInput.set(choiceBoxSeats.getValue());                           //get the seat number
            int queueNumber = flightInput.intValue();                        //get the queue number
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

            window.setScene(scene1);
        });
        GridPane.setConstraints(savePassengerButton, 1, 10);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.setScene(scene1));
        GridPane.setConstraints(cancelButton, 2, 10);

        gridAddPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDatePicker, citizenshipLabel, citizenshipInput,
                luggageWeightLabel, luggageWeightInput, flightLabel, choiceBoxFlights, seatLabel, choiceBoxSeats,
                savePassengerButton, cancelButton);

        sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);
        return sceneAddPassenger;
    }

    @Override
    public Node getStyleableNode() {
        return null;
    }
}
