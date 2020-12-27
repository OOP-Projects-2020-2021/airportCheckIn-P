package oop.project;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
        scene1 = new Scene(topMenu, 400, 400);

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

        //First Name
        Label firstNameLabel = new Label("First Name");
        GridPane.setConstraints(firstNameLabel, 0, 0);
        //.Name input
        TextField firstNameInput = new TextField();
        firstNameInput.setPromptText("first name");
        GridPane.setConstraints(firstNameInput, 1, 0);

        //Last Name
        Label lastNameLabel = new Label("Last Name");
        GridPane.setConstraints(lastNameLabel, 0, 1);
        //.Name input
        TextField lastNameInput = new TextField();
        lastNameInput.setPromptText("last name");
        GridPane.setConstraints(lastNameInput, 1, 1);

        //address
        Label addressLabel = new Label("Address");
        GridPane.setConstraints(addressLabel, 0, 2);
        //.Name input
        TextField addressInput = new TextField();
        addressInput.setPromptText("address");
        GridPane.setConstraints(addressInput, 1, 2);

        //address
        Label birthDateLabel = new Label("Birth date");
        GridPane.setConstraints(birthDateLabel, 0, 3);
        //.Name input
        TextField birthDateInput = new TextField();
        birthDateInput.setPromptText("birth date");
        GridPane.setConstraints(birthDateInput, 1, 3);

        //citizenship
        Label citizenshipLabel = new Label("Citizenship");
        GridPane.setConstraints(citizenshipLabel, 0, 4);
        //.Name input
        TextField citizenshipInput = new TextField();
        citizenshipInput.setPromptText("citizenship");
        GridPane.setConstraints(citizenshipInput, 1, 4);

        //luggage
        Label luggageWeightLabel = new Label("Luggage Weight");
        GridPane.setConstraints(luggageWeightLabel, 0, 5);
        //.Name input
        TextField luggageWeightInput = new TextField();
        luggageWeightInput.setPromptText("luggage weight");
        GridPane.setConstraints(luggageWeightInput, 1, 5);

        //flight
        Label flightLabel = new Label("Flight");
        GridPane.setConstraints(flightLabel, 0, 6);
        //.Name input
        TextField flightInput = new TextField();
        flightInput.setPromptText("flight");
        GridPane.setConstraints(flightInput, 1, 6);

        //seat
        Label seatLabel = new Label("Seat");
        GridPane.setConstraints(seatLabel, 0, 7);
        //.Name input
        TextField seatInput = new TextField();
        seatInput.setPromptText("seat");
        GridPane.setConstraints(seatInput, 1, 7);

        Button savePassengerButton = new Button("Save Passenger");
        savePassengerButton.setOnAction(e -> {
            //save everything in the database
            isInt(flightInput, flightInput.getText());
            isInt(seatInput, seatInput.getText());


            int luggageWeight=Integer.parseInt(luggageWeightInput.getText());
            int flight=Integer.parseInt(flightInput.getText());
            int seat=Integer.parseInt(seatInput.getText());
            Passenger passenger = new Passenger(firstNameInput.getText(), lastNameInput.getText(), addressInput.getText(), citizenshipInput.getText(), luggageWeight, flight, seat);
            Main.addToQueue(passenger);


            window.setScene(scene1);
        });
        GridPane.setConstraints(savePassengerButton, 1, 8);

        gridAddPassenger.getChildren().addAll(firstNameLabel, firstNameInput, lastNameLabel, lastNameInput, addressLabel, addressInput, birthDateLabel, birthDateInput, citizenshipLabel, citizenshipInput, luggageWeightLabel, luggageWeightInput, flightLabel, flightInput, seatLabel, seatInput, savePassengerButton);
        sceneAddPassenger = new Scene(gridAddPassenger, 400, 400);

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
