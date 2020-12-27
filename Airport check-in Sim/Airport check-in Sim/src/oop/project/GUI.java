package oop.project;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;


public class GUI extends Application{

    Stage window;
    Scene scene1, sceneAddPassenger;

    //Button button;
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
        Button buttonB = new Button("Edit");
        Button buttonC = new Button("View");
        topMenu.getChildren().addAll(buttonAddPassenger, buttonB, buttonC);
        scene1 = new Scene(topMenu, 600, 600);

        /*VBox leftMenu = new VBox();
        Button buttonD = new Button("D");
        Button buttonE = new Button("E");
        Button buttonF = new Button("F");
        leftMenu.getChildren().addAll(buttonD, buttonE, buttonF);*/

       /* BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);*/
        //borderPane.setLeft(leftMenu);
/*
        //window.setOnCloseRequest(e -> closeProgram());
        window.setOnCloseRequest(e -> {
                    e.consume();
                    closeProgram();
        });

        button = new Button("Click me");
        //button.setOnAction(e -> AlertBox.display("Title of window", "Taceti din gura :)"));
        /*button.setOnAction(e -> {
            boolean result = ConfirmBox.display("Title of Window", "Are you sure?");
            System.out.println(result);
        });*/

        //button.setOnAction(e -> closeProgram());

        /*StackPane layout = new StackPane();
        layout.getChildren().add(buttonAddPassenger);
        Scene scene = new Scene(layout, 300, 250);*/
       /* Scene scene = new Scene(borderPane, 300, 250);
        window.setScene(scene);
        window.show();*/

        /*window = primaryStage;

        Label label1 = new Label("welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        //Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20); //layout that stacks all the buttons on top of each other in a column with 20px space
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);

        //Button 2
        Button button2 = new Button("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);*/


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

        //address
        Label birthDateLabel = new Label("Birth date");
        GridPane.setConstraints(birthDateLabel, 0, 4);
        //.Name input
        TextField birthDateInput = new TextField();
        birthDateInput.setPromptText("birth date");
        GridPane.setConstraints(birthDateInput, 1, 4);

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
            seatInput.set(choiceBoxSeats.getValue());
            GridPane.setConstraints(choiceBoxSeats, 1, 8);
        });

        GridPane.setConstraints(choiceBoxFlights, 1, 7);




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

            int luggageWeight=Integer.parseInt(luggageWeightInput.getText());
            Passenger passenger = new Passenger(firstNameInput.getText(), lastNameInput.getText(), addressInput.getText(),
                    citizenshipInput.getText(), luggageWeight, flightInput.intValue(), seatInput.intValue());
            Main.addToQueue(passenger);


            window.setScene(scene1);
        });
        GridPane.setConstraints(savePassengerButton, 1, 10);

        //cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> window.setScene(scene1));
        GridPane.setConstraints(cancelButton, 2, 10);

        gridAddPassenger.getChildren().addAll(text, firstNameLabel, firstNameInput, lastNameLabel, lastNameInput,
                addressLabel, addressInput, birthDateLabel, birthDateInput, citizenshipLabel, citizenshipInput,
                luggageWeightLabel, luggageWeightInput, flightLabel, choiceBoxFlights, seatLabel, choiceBoxSeats, queueLabel, choiceBoxQueue,
                savePassengerButton, cancelButton);
        sceneAddPassenger = new Scene(gridAddPassenger, 600, 600);

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
        Boolean answer = ConfirmBox.display("Title", "Sure you want to exit?");
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

}
