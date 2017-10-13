package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;

import java.io.IOException;

public class welcomeController {
    @FXML Button createGrades, createAttendance;


    public void createAttendance(ActionEvent event) throws IOException {


        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("attendance.fxml"));
            stage.setTitle("Add new entry");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            // Hide this current window
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createGrades(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("grades.fxml"));
            stage.setTitle("Add new entry");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            // Hide this current window
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
