package oop.project;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SeatsViewBox {
    public static void display(Flight flight){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL); //block interactions with other window until this is closed
        window.setTitle("Plane seats");
        //window.setMinWidth(400);

        Label label = new Label();
        label.setText("Seats in the plane flight id " + flight.getId());

        //Create TilePane
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10));
        //Add nodes to the tilePane
        ResultSet rs2 = MySqlCon.Query(" select ticket_flight_seat from ticket where ticket_flight_id = "+flight.getId());
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

        for (int i=1; i<41; i++){
            Button button = new Button(Integer.toString(i));
            button.setPrefWidth(50);
            button.setPrefHeight(50);
            if (visitedSeats[i] == Boolean.FALSE)
                button.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
            else{
                button.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                ResultSet rs = MySqlCon.Query("SELECT * FROM passenger JOIN identity_card on passenger.passenger_identityCard = identity_card.id_id JOIN ticket ON passenger.passenger_ticket_id = ticket.ticket_id JOIN luggage ON passenger.passenger_luggage_id = luggage.luggage_id JOIN flight ON ticket.ticket_flight_id = flight.flight_id WHERE flight_id = "
                + flight.getId()+ " AND ticket_flight_seat = " +i);

                button.setOnAction(e -> {
                    try {
                        rs.next();
                        PassengerDetailsBox.display(new Passenger(rs.getInt("passenger_id"), rs.getInt("identity_card.id_id"), rs.getInt("ticket.ticket_id"),
                                rs.getInt("luggage.luggage_id"), rs.getString("id_first_name"), rs.getString("id_last_name"),
                                rs.getDate("id_birth_date"), rs.getString("id_address"),
                                rs.getString("id_citizenship"), rs.getInt("luggage_weight"), rs.getInt("flight_id"),
                                rs.getInt("ticket_flight_seat"), rs.getInt("ticket_flight_id")));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
            }
            tilePane.getChildren().add(button);
        }
        //Set orientation
        tilePane.setOrientation(Orientation.HORIZONTAL);
        //Set gap
        tilePane.setHgap(5);
        tilePane.setVgap(5);
        //Set nb of rows and columns
        tilePane.setPrefColumns(8);
        tilePane.setPrefRows(5);

        Button legendButtonTaken = new Button("Taken");
        legendButtonTaken.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        legendButtonTaken.setPrefWidth(50);
        Button legendButtonFree = new Button("Free");
        legendButtonFree.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        legendButtonFree.setPrefWidth(50);
        tilePane.getChildren().addAll(legendButtonFree, legendButtonTaken);


        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, tilePane, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 475, 500);
        window.setScene(scene);
        window.showAndWait();
    }
}
