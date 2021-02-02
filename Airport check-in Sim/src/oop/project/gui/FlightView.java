package oop.project.gui;

import oop.project.Flight;
import oop.project.database.MySqlCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FlightView {
    private static Scene sceneFlightSchedule;

    public static Scene display(Stage window, Scene scene1){
        //scene flights schedule
        BorderPane borderPaneFlightSchedule = new BorderPane();
        HBox topFlightsScene = new HBox();
        topFlightsScene.setAlignment(Pos.CENTER);
        topFlightsScene.setPadding(new Insets(10, 10, 10, 10));

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
        TableColumn<Flight, Timestamp> flightDeparture = new TableColumn<>("Departure Time");
        flightDeparture.setMaxWidth(500);
        flightDeparture.setCellValueFactory(new PropertyValueFactory<>("departureTime"));

        //arrival time column
        TableColumn<Flight, Timestamp> flightArrival = new TableColumn<>("Arrival Time");
        flightArrival.setMaxWidth(500);
        flightArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

        //arrival time column
        TableColumn<Flight, String> flightDestination = new TableColumn<>("Destination");
        flightDestination.setMaxWidth(400);
        flightDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));

        flightsTable = new TableView<>();
        flightsTable.setItems(getFlight());
        flightsTable.getColumns().addAll(flightID, flightDeparture, flightArrival, flightDestination);

        flightsTable.setOnMouseClicked(e -> {
            if (flightsTable.getSelectionModel().getSelectedItem()!=null) {
                SeatsViewBox.display(flightsTable.getSelectionModel().getSelectedItem());
            }
        });
        BorderPane.setAlignment(flightsTable, Pos.CENTER);
        BorderPane.setMargin(flightsTable, new Insets(30 ,30, 30, 30));
        borderPaneFlightSchedule.setCenter(flightsTable);

        sceneFlightSchedule = new Scene(borderPaneFlightSchedule, 600, 600);
        return sceneFlightSchedule;
    }

    private static ObservableList<Flight> getFlight(){                                 ///for the table
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

}
