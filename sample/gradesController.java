package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
    import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class gradesController {

    @FXML Label SFName,HFName;
    @FXML TextField ColumnNumber;
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
            SFName.setText(studentFName);

        }
        catch(Exception e)
        {
            System.out.println("student file not picked");
        }

    }

    public void loadColumns(ActionEvent actionEvent)
    {
        String cnum = ColumnNumber.getText();
        int result = Integer.parseInt(cnum);
        System.out.println(result);
    }

    public void loadHeader(ActionEvent actionEvent)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a File to be the Header");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File file = chooser.showOpenDialog(new Stage());
            String headerFName = file.getName();
            System.out.println("Header File: "+headerFName);
            HFName.setText(headerFName);
            try
            {
                List<String> lines = Files.readAllLines(Paths.get(headerFName)); //throws exception
                System.out.println("Line from the File: "+lines.get(0));
            }
            catch (Exception w)
            {
                System.out.println("Not Getting Data From File");
            }

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