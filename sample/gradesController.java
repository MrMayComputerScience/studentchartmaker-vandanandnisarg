package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
    import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class gradesController {
    public void loadStudents(ActionEvent actionEvent)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a File With the Name of the Students");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File file = chooser.showOpenDialog(new Stage());
            String studentFName = file.getName();
            System.out.println("Student File: "+studentFName);
        }
        catch(Exception e)
        {
            System.out.println("student file not picked");
        }
    }

    public void loadColumns(ActionEvent actionEvent)
    {
        
    }

    public void loadHeader(ActionEvent actionEvent)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a File to be the Header");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File file = chooser.showOpenDialog(new Stage());
            String studentFName = file.getName();
            System.out.println("Header File: "+studentFName);
        }
        catch(Exception e)
        {
            System.out.println("header file not picked");
        }
    }

    public void createChart(ActionEvent actionEvent)
    {

    }
}