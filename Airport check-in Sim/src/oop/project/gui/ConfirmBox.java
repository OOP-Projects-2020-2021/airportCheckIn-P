package oop.project.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private static boolean answer;

    public static boolean display(String title, String message, String button1Text, String button2Text){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block interactions with other window until this is closed
        window.setTitle(title);

        Label label = new Label();
        label.setText(message);


        //create 2 buttons
        Button yesButton = new Button(button1Text);
        Button noButton = new Button(button2Text);

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 280, 100);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
