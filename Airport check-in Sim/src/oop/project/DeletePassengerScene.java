package oop.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class DeletePassengerScene {

    private static Scene sceneDeletePassenger;


    public static Scene display(Stage window, Scene scene1){
        //Layout for adding more passengers
        GridPane gridAddPassenger = new GridPane();
        gridAddPassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridAddPassenger.setVgap(8);//set vertical spacing between cells
        gridAddPassenger.setVgap(10); //horizontal spacing

        //First text
        Label text = new Label("Delete a passenger");
        GridPane.setConstraints(text, 1, 0);











        //First Name
        Label firstNameLabel = new Label("First Name");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        //.Name input
        TextField firstNameInput = new TextField();
        GridPane.setConstraints(firstNameInput, 1, 1);

        //Last Name
        Label lastNameLabel = new Label("Last Name");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        //.Name input
        TextField lastNameInput = new TextField();
        GridPane.setConstraints(lastNameInput, 1, 2);


        //address
        Label addressLabel = new Label("Address");
        GridPane.setConstraints(addressLabel, 0, 3);
        //.Name input
        TextField addressInput = new TextField();
        GridPane.setConstraints(addressInput, 1, 3);

        //birth date
        Label birthDateLabel = new Label("Birth date");
        GridPane.setConstraints(birthDateLabel, 0, 4);
        //date input
        TextField birthDateInput = new TextField();
        GridPane.setConstraints(birthDateInput, 1, 4);



        //citizenship
        Label citizenshipLabel = new Label("Citizenship");
        GridPane.setConstraints(citizenshipLabel, 0, 5);
        //input
        TextField citizenshipInput = new TextField();
        GridPane.setConstraints(citizenshipInput, 1, 5);

        //luggage
        Label luggageWeightLabel = new Label("Luggage Weight");
        GridPane.setConstraints(luggageWeightLabel, 0, 6);
        //input
        TextField luggageWeightInput = new TextField();
        GridPane.setConstraints(luggageWeightInput, 1, 6);



        //flight
        Label flightLabel = new Label("Flight");
        GridPane.setConstraints(flightLabel, 0, 7);
        //flight input
        TextField flightInput = new TextField();
        GridPane.setConstraints(flightInput, 1, 7);


        //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 8);
        //flight input
        TextField seatInput = new TextField();
        GridPane.setConstraints(seatInput, 1, 8);


        //passenger
        Label passengerLabel = new Label("Choose a passenger");
        GridPane.setConstraints(passengerLabel, 0, 9);
        //flight input
        ChoiceBox<Integer> choiceBoxPassengers = new ChoiceBox<>();
        ResultSet rs = MySqlCon.Query("SELECT passenger_id FROM passenger");
        //set default value
        while (true) {
            try {
                if (!rs.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                choiceBoxPassengers.setValue(rs.getInt("passenger_id"));
                choiceBoxPassengers.getItems().add(rs.getInt("passenger_id"));
                /*choiceBoxPassengers.setValue("id:" + rs.getInt("passenger_id") + ", Name: " + rs.getString("id_first_name") + " " + rs.getString("id_last_name") +
                        ", Flight: " + rs.getInt("ticket_flight_id") + ", Seat: " + rs.getInt("ticket_flight_seat"));
                choiceBoxPassengers.getItems().add(("id:" + rs.getInt("passenger_id") + ", Name: " + rs.getString("id_first_name") + " " + rs.getString("id_last_name") +
                        ", Flight: " + rs.getInt("ticket_flight_id") + ", Seat: " + rs.getInt("ticket_flight_seat")));
                */while (rs.next())
                    choiceBoxPassengers.getItems().add(rs.getInt("passenger_id"));
                    /*choiceBoxPassengers.getItems().add(("id:" + rs.getInt("passenger_id") + ", Name: " + rs.getString("id_first_name") + " " + rs.getString("id_last_name") +
                            ", Flight: " + rs.getInt("ticket_flight_id") + ", Seat: " + rs.getInt("ticket_flight_seat")));*/

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        choiceBoxPassengers.setOnAction(e ->{
            ResultSet rs2 = MySqlCon.Query("SELECT * FROM passenger JOIN identity_card ON passenger.passenger_identityCard = identity_card.id_id JOIN ticket ON passenger.passenger_ticket_id = ticket.ticket_id JOIN luggage ON passenger.passenger_luggage_id = luggage.luggage_id WHERE passenger.passenger_id = " + choiceBoxPassengers.getSelectionModel().getSelectedItem());
            try {
                rs2.next();
                firstNameInput.setText(rs2.getString("identity_card.id_first_name"));
                lastNameInput.setText(rs2.getString("identity_card.id_last_name"));
                addressInput.setText(rs2.getString("identity_card.id_address"));
                birthDateInput.setText(rs2.getString("identity_card.id_birth_date"));
                citizenshipInput.setText(rs2.getString("identity_card.id_citizenship"));
                luggageWeightInput.setText(rs2.getString("luggage.luggage_weight"));
                flightInput.setText(rs2.getString("ticket.ticket_flight_id"));
                seatInput.setText(rs2.getString("ticket.ticket_flight_seat"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }


        });

        GridPane.setConstraints(choiceBoxPassengers, 1, 9);



        //delete passenger button
        Button savePassengerButton = new Button("Delete");
        savePassengerButton.setOnAction(e -> {
            //delete from database

            int id = choiceBoxPassengers.getSelectionModel().getSelectedItem();
            ResultSet rs3 = MySqlCon.Query("select * from passenger where passenger_id =" + choiceBoxPassengers.getSelectionModel().getSelectedItem());
            try {
                rs3.next();
                int luggageId = rs3.getInt("passenger_luggage_id");
                int ticketId = rs3.getInt("passenger_ticket_id");
                int idetityCardId = rs3.getInt("passenger_identityCard");
                MySqlCon.updateDB("DELETE FROM passenger WHERE passenger_id = " + id);
                MySqlCon.updateDB("DELETE FROM luggage WHERE luggage_id = " + rs3.getInt("passenger_luggage_id"));
                MySqlCon.updateDB("DELETE FROM ticket WHERE ticket_id = " + rs3.getInt("passenger_ticket_id"));
                MySqlCon.updateDB("DELETE FROM identity_card WHERE id_id = " + rs3.getInt("passenger_identityCard"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            firstNameInput.clear();
            lastNameInput.clear();
            birthDateInput.clear();
            addressInput.clear();
            citizenshipInput.clear();
            luggageWeightInput.clear();
            flightInput.clear();
            seatInput.clear();

            window.setScene(scene1);
        });
        GridPane.setConstraints(savePassengerButton, 1, 11);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            firstNameInput.clear();
            lastNameInput.clear();
            birthDateInput.clear();
            addressInput.clear();
            citizenshipInput.clear();
            luggageWeightInput.clear();
            flightInput.clear();
            seatInput.clear();
            window.setScene(scene1);
        });
        GridPane.setConstraints(cancelButton, 2, 11);

        gridAddPassenger.getChildren().addAll(text, passengerLabel, choiceBoxPassengers, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDateInput, citizenshipLabel, citizenshipInput,
                luggageWeightLabel, luggageWeightInput, flightLabel, flightInput, seatLabel, seatInput,
                savePassengerButton, cancelButton);

        sceneDeletePassenger = new Scene(gridAddPassenger, 600, 600);
        return sceneDeletePassenger;
    }
}
