package sample;

/**
 * Created by s581467 on 10/11/2017.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
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

    //lists for data
    private ArrayList<String> studentList, datesList, headerList, studentFinal, headerFinal, datesFinal;

    //buttons and stuff from fxml
    @FXML Button loadStudentButton;
    @FXML Text displayStudentFile, displayHeaderFile, displayDatesFile, displayErrorMessage;

    public void loadStudents(ActionEvent actionEvent) {
        //file loader
        studentFinal = new ArrayList<>();

        loadFile(studentFile, studentList);

        //Dumb way to truncate data
        try {
            for (String student : studentList) {
                String[] clean = student.split("  ");
                String[] temp = clean[0].split(", ");
                studentFinal.add(temp[1] + " " + temp[0]);

                displayStudentFile.setFill(Color.BLACK);
                displayStudentFile.setText(studentFile.getName());

            }
        }
        catch(Exception e){
            displayStudentFile.setFill(Color.RED);
            displayStudentFile.setText("Incorrect File Format");

            studentFinal.clear();
        }
    }

    public void loadDates(ActionEvent actionEvent) {
        datesFinal = new ArrayList<>();

        loadFile(datesFile, datesList);
        for(String line: datesList){
            if(line.matches(".+"));
            datesFinal.add(line);
        }
        if(datesFinal.isEmpty()){
            System.out.println("error");
            displayDatesFile.setFill(Color.RED);
            displayDatesFile.setText("Incorrect File Format");
        }
        else{
            displayDatesFile.setFill(Color.BLACK);
            displayDatesFile.setText(datesFile.getName());
        }
    }

    public void loadHeader(ActionEvent actionEvent) {
        headerFinal = new ArrayList<>();

        loadFile(headerFile, headerList);
        

        headerFinal.add(headerList.get(0));

        if(headerFinal.isEmpty()){
            System.out.println("error");
            displayHeaderFile.setFill(Color.RED);
            displayHeaderFile.setText("Incorrect File Format");
        }
        else{
            System.out.println("haha");
            displayHeaderFile.setFill(Color.BLACK);
            displayHeaderFile.setText(headerFile.getName());
        }
    }

    private void loadFile(File dataFile, ArrayList<String> dataList){
        Scene stage = loadStudentButton.getScene();
        fileLoader.setTitle("Choose File");

        fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        try{
            dataFile = fileLoader.showOpenDialog(stage.getWindow());
            System.out.println(dataFile.getName());

            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            dataList = new ArrayList<String>();

            String temp;
            while((temp = reader.readLine()) != null){
                dataList.add(temp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void createChart(ActionEvent actionEvent) {
        if(!studentFinal.isEmpty()&&!headerFinal.isEmpty()&&!datesFinal.isEmpty()){

        }
        else{
            displayErrorMessage.setText("Missing Files");
        }
    }
}
