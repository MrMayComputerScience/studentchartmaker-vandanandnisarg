package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
    import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class gradesController extends JFrame{

    @FXML Label SFName,HFName,Error;
    @FXML TextField ColumnNumber;
    String studentFileName = "";
    int colNumber;
    String headerFileName = "";
    public void loadStudents(ActionEvent actionEvent)
    {
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a File With the Name of the Students");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File file = chooser.showOpenDialog(new Stage());
            String studentFName = file.getAbsolutePath();
            System.out.println(file.getName());
            System.out.println("Student File: "+studentFName);
            SFName.setText(file.getName());
            Error.setText("");
            studentFileName = studentFName;
        }
        catch(Exception e)
        {
            System.out.println("student file not picked");
            Error.setText("Error Message: File Not Picked.");
        }

    }

    public void loadColumns(ActionEvent actionEvent)
    {
        try
        {
            String cnum = ColumnNumber.getText();
            int result = Integer.parseInt(cnum);
            System.out.println(result);
            Error.setText("");
            colNumber = result;
        }
        catch(Exception e)
        {
            System.out.println("Non-Number Value Entered");
            Error.setText("Error Message: Illegal Value Entered. Please Input an Integer Value.");
        }

    }

    public void loadHeader(ActionEvent actionEvent)
    {
        String finalHeader="";
        try
        {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Choose a File to be the Header");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
            File file = chooser.showOpenDialog(new Stage());
            String headerFName = file.getAbsolutePath();
            System.out.println("Header File: "+headerFName);
            HFName.setText(file.getName());
            try
            {
                List<String> lines = Files.readAllLines(Paths.get(headerFName)); //throws exception
                for(int i =0;i< lines.size();i++)
                {
                    if(lines.get(i).trim()!="")
                        finalHeader = finalHeader +"\n"+lines.get(i);
                }
                System.out.println("Line from the File: "+finalHeader);

            }
            catch (Exception w)
            {
                System.out.println("Not Getting Data From File");
                w.printStackTrace();
                Error.setText("Error Message: Not Getting Data From File.");
            }
            Error.setText("");
            headerFileName = headerFName;
        }
        catch(Exception e)
        {
            System.out.println("header file not picked");
            Error.setText("Error Message: File Not Picked.");
        }
    }

    public void createChart(ActionEvent actionEvent)
    {
        System.out.println("Student File Name: "+ studentFileName);
        System.out.println("Column Number "+ colNumber);
        System.out.println("Header File Name "+ headerFileName);

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("createGradesChart.fxml"));
            stage.setTitle("Add new entry");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
            // Hide this current window
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void printGui(ActionEvent actionEvent) {
    }

    public void saveGui(ActionEvent actionEvent) {
    }

}