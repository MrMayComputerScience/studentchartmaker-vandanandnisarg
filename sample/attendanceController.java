package sample;

/**
 * Created by s581467 on 10/11/2017.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;


public class attendanceController {

    final FileChooser fileLoader = new FileChooser();
    File studentFile, datesFile, headerFile;

    private ArrayList<String> studentList, datesList, headerList, studentFinal;

    @FXML Button loadStudentButton;
    @FXML Text displayStudentFile, displayHeaderFile, displayDatesFile;

    public void loadStudents(ActionEvent actionEvent) {
        Scene stage = loadStudentButton.getScene();
        fileLoader.setTitle("Choose Student File");

        fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        studentFile = fileLoader.showOpenDialog(stage.getWindow());
        System.out.println(studentFile.getName());

        try{
            BufferedReader reader = new BufferedReader(new FileReader(studentFile));
            studentList = new ArrayList<String>();
            studentFinal = new ArrayList<>();

            String student;
            while((student = reader.readLine()) != null){
                studentList.add(student);
            }
    }
        catch(Exception e){
            e.printStackTrace();
        }

        //Dumb way to truncate data
        try {
            for (String student : studentList) {
                String[] clean = student.split("  ");
                String[] temp = clean[0].split(", ");
                studentFinal.add(temp[1] + " " + temp[0]);
            }
        }
        catch(Exception e){

        }

        displayStudentFile.setText(studentFile.getName());
    }

    public void loadDates(ActionEvent actionEvent) {
        Scene stage = loadStudentButton.getScene();
        fileLoader.setTitle("Choose Dates File");

        fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        datesFile = fileLoader.showOpenDialog(stage.getWindow());
        System.out.println(datesFile.getName());

        try{
            BufferedReader reader = new BufferedReader(new FileReader(datesFile));
            datesList = new ArrayList<String>();

            String date;
            while((date = reader.readLine()) != null){
                datesList.add(date);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        displayDatesFile.setText(datesFile.getName());
    }

    public void loadHeader(ActionEvent actionEvent) {
        Scene stage = loadStudentButton.getScene();
        fileLoader.setTitle("Choose Header File");

        fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        headerFile = fileLoader.showOpenDialog(stage.getWindow());
        System.out.println(headerFile.getName());

        try{
            BufferedReader reader = new BufferedReader(new FileReader(headerFile));
            headerList = new ArrayList<String>();

            String header;
            while((header = reader.readLine()) != null){
                headerList.add(header);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        displayHeaderFile.setText(headerFile.getName());
    }

    public void createChart(ActionEvent actionEvent) {
    }
}
