package oop.project.gui;

import oop.project.*;
import oop.project.database.MySqlCon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class InsertPassengerScene extends Parent {

    private static Scene sceneAddPassenger;
    private static GridPane gridAddPassenger;
    private static Label[] warning;

    public static Scene display(Stage window, Scene scene1){
        //Layout for adding more passengers
        gridAddPassenger = new GridPane();
        gridAddPassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridAddPassenger.setVgap(8);//set vertical spacing between cells
        gridAddPassenger.setVgap(10); //horizontal spacing

        String message = new String("Please insert a valid Input!");
        warning = new Label[10];
        for (int i=0; i<warning.length; i++){
            warning[i] = new Label(message);
            warning[i].setTextFill(Color.RED);
        }

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



        //nationality
        Label citizenshipLabel = new Label("Nationality");
        GridPane.setConstraints(citizenshipLabel, 0, 5);
        //.Name input
        ChoiceBox<String> choiceBoxNationality = new ChoiceBox<>();
        ResultSet rs0 = MySqlCon.Query("select nationality from countries");
        //set default value
        while (true) {
            try {
                if (!rs0.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {

                choiceBoxNationality.setValue(rs0.getString("nationality"));
                choiceBoxNationality.getItems().add(rs0.getString("nationality"));
                while (rs0.next())
                    choiceBoxNationality.getItems().add(rs0.getString("nationality"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        GridPane.setConstraints(choiceBoxNationality, 1, 5);

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



        //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 11);
        ChoiceBox<Integer> choiceBoxSeats = new ChoiceBox<>();

        choiceBoxFlights.setOnAction(e ->{
            if (choiceBoxFlights.getSelectionModel().getSelectedItem()!=null) {
                ResultSet rs2 = MySqlCon.Query("SELECT * FROM flight where flight_id = " + choiceBoxFlights.getSelectionModel().getSelectedItem());
                try {
                    rs2.next();
                    departureInput.setText(rs2.getString("flight_departure"));
                    arrivalInput.setText(rs2.getString("flight_arrival"));
                    destinationInput.setText(rs2.getString("flight_destination"));

                    choiceBoxSeats.getSelectionModel().clearSelection();
                    choiceBoxSeats.getItems().clear();

                    ResultSet rs3 = MySqlCon.Query("select ticket_flight_seat from ticket where ticket_flight_id = " + choiceBoxFlights.getSelectionModel().getSelectedItem());
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
                    for (int i = 1; i < 41; i++) {
                        if (visitedSeats[i] == Boolean.FALSE) {
                            choiceBoxSeats.getItems().add(i);
                            if (defaultValue == -1)
                                defaultValue = i;
                        }
                    }
                    //set default value
                    choiceBoxSeats.setValue(defaultValue);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });


        GridPane.setConstraints(choiceBoxFlights, 1, 7);
        GridPane.setConstraints(choiceBoxSeats, 1, 11);





        //save passenger button
        Button savePassengerButton = new Button("Save Passenger");
        savePassengerButton.setOnAction(e -> {
            //save everything in the database
            if(isNotEmpty(luggageWeightInput, firstNameInput, lastNameInput, birthDatePicker, addressInput, choiceBoxNationality.getSelectionModel().getSelectedItem(),
                    choiceBoxFlights.getSelectionModel().getSelectedItem(), choiceBoxSeats.getSelectionModel().getSelectedItem())){

                LocalDate value = birthDatePicker.getValue();
                Date birthDateInput = java.sql.Date.valueOf( value );

                int queueNumber = choiceBoxFlights.getSelectionModel().getSelectedItem();                    //get the queue number
                int luggageWeight=Integer.parseInt(luggageWeightInput.getText());   //get the luggage weight

                Passenger passenger = new Passenger(firstNameInput.getText(), lastNameInput.getText(), birthDateInput, addressInput.getText(),
                        choiceBoxNationality.getSelectionModel().getSelectedItem(), luggageWeight, choiceBoxFlights.getSelectionModel().getSelectedItem(),
                        choiceBoxSeats.getSelectionModel().getSelectedItem(), queueNumber);

                Main.addToQueue(passenger, queueNumber);    // add passenger to queue

                firstNameInput.clear();
                lastNameInput.clear();
                birthDatePicker.getEditor().clear();
                birthDatePicker.setValue(null);
                addressInput.clear();
                choiceBoxNationality.getSelectionModel().clearSelection();
                luggageWeightInput.clear();
                choiceBoxFlights.getSelectionModel().clearSelection();
                choiceBoxSeats.getSelectionModel().clearSelection();

                removeWarnings();
                window.setScene(scene1);
            }
        });
        GridPane.setConstraints(savePassengerButton, 1, 12);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {

            firstNameInput.clear();
            lastNameInput.clear();
            birthDatePicker.getEditor().clear();
            birthDatePicker.setValue(null);
            addressInput.clear();
            choiceBoxNationality.getSelectionModel().clearSelection();
            luggageWeightInput.clear();
            choiceBoxFlights.getSelectionModel().clearSelection();
            choiceBoxSeats.getSelectionModel().clearSelection();

            removeWarnings();
            window.setScene(scene1);
        });
        GridPane.setConstraints(cancelButton, 2, 12);

        gridAddPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDatePicker, citizenshipLabel, choiceBoxNationality,
                luggageWeightLabel, luggageWeightInput, flightLabel, choiceBoxFlights, departureLabel, departureInput,
                arrivalLabel, arrivalInput, destinationLabel, destinationInput, seatLabel, choiceBoxSeats,
                savePassengerButton, cancelButton);



        sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);
        return sceneAddPassenger;
    }




    private static boolean isNotEmpty(TextField luggage, TextField firstNameInput, TextField lastNameInput, DatePicker birthDatePicker, TextField addressInput, String nationality, Integer selectedItem, Integer selectedItem1) {
        boolean empty = false;
        if (!isInt(luggage, luggage.getText())){
            GridPane.setConstraints(warning[5], 2, 6);
            gridAddPassenger.getChildren().add(warning[5]);
            empty = true;
        }
        if (firstNameInput.getText().isEmpty()){
            GridPane.setConstraints(warning[0], 2, 1);
            gridAddPassenger.getChildren().add(warning[0]);
            empty = true;
        }
        if (lastNameInput.getText().isEmpty()){
            GridPane.setConstraints(warning[1], 2, 2);
            gridAddPassenger.getChildren().add(warning[1]);
            empty = true;
        }
        if (birthDatePicker.getValue() == null){
            GridPane.setConstraints(warning[3], 2, 4);
            gridAddPassenger.getChildren().add(warning[3]);
            empty = true;;
        }
        if (addressInput.getText().isEmpty()){
            GridPane.setConstraints(warning[2], 2, 3);
            gridAddPassenger.getChildren().add(warning[2]);
            empty = true;
        }
        if (nationality == null){
            GridPane.setConstraints(warning[4], 2, 5);
            gridAddPassenger.getChildren().add(warning[4]);
            empty = true;
        }
        if (selectedItem == null){
            GridPane.setConstraints(warning[6], 2, 7);
            gridAddPassenger.getChildren().add(warning[6]);
            empty = true;
        }
        if (selectedItem1 == null){
            GridPane.setConstraints(warning[7], 2, 11);
            gridAddPassenger.getChildren().add(warning[7]);
        }
        return !empty;
    }

    private static boolean isInt(TextField input, String message){
        try{
            int number = Integer.parseInt(message);
            if(number<=0){
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    private static void removeWarnings() {
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 3 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 4 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 5 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 6 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 7 && GridPane.getColumnIndex(node) == 2);
        gridAddPassenger.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 11 && GridPane.getColumnIndex(node) == 2);
    }


    @Override
    public Node getStyleableNode() {
        return null;
    }
}
