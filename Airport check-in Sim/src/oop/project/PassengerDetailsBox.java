package oop.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class PassengerDetailsBox {
    public static void display( Passenger passenger){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //block interactions with other window until this is closed
        window.setTitle("Passenger details");
        window.setMinWidth(400);
        window.setMinHeight(400);


        //Layout for viewing a passenger
        GridPane gridviewPassenger = new GridPane();
        gridviewPassenger.setPadding( new Insets(10, 10, 10, 10)); //padding for the grid margins
        gridviewPassenger.setVgap(8);//set vertical spacing between cells
        gridviewPassenger.setVgap(10); //horizontal spacing

        //First text
        Label text = new Label("Insert a new passenger");
        GridPane.setConstraints(text, 1, 0);

        //First Name
        Label firstNameLabel = new Label("First Name");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        //.Name input
        TextField firstNameInput = new TextField();
        firstNameInput.setText(passenger.getIdentityCard().getFirstName());
        GridPane.setConstraints(firstNameInput, 1, 1);

        //Last Name
        Label lastNameLabel = new Label("Last Name");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        //.Name input
        TextField lastNameInput = new TextField();
        lastNameInput.setText(passenger.getIdentityCard().getLastName());
        GridPane.setConstraints(lastNameInput, 1, 2);


        //address
        Label addressLabel = new Label("Address");
        GridPane.setConstraints(addressLabel, 0, 3);
        //.Name input
        TextField addressInput = new TextField();
        addressInput.setText(passenger.getIdentityCard().getAddress());
        GridPane.setConstraints(addressInput, 1, 3);

        //birth date
        Label birthDateLabel = new Label("Birth date");
        GridPane.setConstraints(birthDateLabel, 0, 4);
        //date input
        TextField birthDateInput = new TextField();
        birthDateInput.setText(passenger.getIdentityCard().getBirthDate().toString());
        GridPane.setConstraints(birthDateInput, 1, 4);



        //citizenship
        Label citizenshipLabel = new Label("Citizenship");
        GridPane.setConstraints(citizenshipLabel, 0, 5);
        //input
        TextField citizenshipInput = new TextField();
        citizenshipInput.setText(passenger.getIdentityCard().getCitizenship());
        GridPane.setConstraints(citizenshipInput, 1, 5);

        //luggage
        Label luggageWeightLabel = new Label("Luggage Weight");
        GridPane.setConstraints(luggageWeightLabel, 0, 6);
        //input
        TextField luggageWeightInput = new TextField();
        luggageWeightInput.setText(String.valueOf(passenger.getLuggage().getWeight()));
        GridPane.setConstraints(luggageWeightInput, 1, 6);



        //flight
        Label flightLabel = new Label("Flight");
        GridPane.setConstraints(flightLabel, 0, 7);
        //flight input
        TextField flightInput = new TextField();
        flightInput.setText(String.valueOf(passenger.getTicket().getFlightId()));
        GridPane.setConstraints(flightInput, 1, 7);


       //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 8);
        //seat input
        TextField seatInput = new TextField();
        seatInput.setText(String.valueOf(passenger.getTicket().getSeat()));
        GridPane.setConstraints(seatInput, 1, 8);


        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());
        GridPane.setConstraints(closeButton, 1, 10);

        gridviewPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, addressLabel, addressInput,
                birthDateLabel, birthDateInput, citizenshipLabel, citizenshipInput, luggageWeightLabel, luggageWeightInput,
                flightLabel, flightInput, seatLabel, seatInput, closeButton);
        /*VBox layout = new VBox(10);
        layout.getChildren().addAll( closeButton);
        layout.setAlignment(Pos.CENTER);*/

        Scene scene = new Scene( gridviewPassenger, 400, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
