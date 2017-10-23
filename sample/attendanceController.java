package sample;

/**
 * Created by s581467 on 10/11/2017.
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class attendanceController {

    private final FileChooser fileLoader = new FileChooser();

    private ArrayList<String> studentFinal = new ArrayList<>();
    private ArrayList<String> headerFinal = new ArrayList<>();
    private ArrayList<String> datesFinal = new ArrayList<>();

    //buttons and stuff from fxml
    @FXML Button loadStudentButton;
    @FXML Text displayStudentFile, displayHeaderFile, displayDatesFile, displayErrorMessage;
    @FXML TableView finalChart;

    public void loadStudents(ActionEvent actionEvent) {
        //file loader

        ArrayList<String> studentList = loadFile();

        if(studentList == null){
            return;
        }

        String studentFileName = studentList.remove(studentList.size()-1);

        Pattern r = Pattern.compile("([A-Z][\\w'-]+)");


        //Dumb way to truncate data
        try {
            for (String student : studentList) {
                Matcher m = r.matcher(student);

                if(!student.matches("([A-Z][\\w'-]+)(, )([A-Z][\\w'-]+)(.*)")){
                    throw new Exception();
                }

                ArrayList<String> temp = new ArrayList<>();
                while(m.find()){
                    temp.add(m.group());
                }
                studentFinal.add(temp.get(0)+" "+temp.get(1));
            }
            displayStudentFile.setFill(Color.BLACK);
            displayStudentFile.setText(studentFileName);

            Collections.sort(studentFinal);
            for (int i = 0; i < studentFinal.size(); i++) {
                String[] reverse = studentFinal.get(i).split(" ");
                studentFinal.set(i, reverse[1]+" "+reverse[0]);
            }
        }
        catch(Exception e){
            //e.printStackTrace();
            displayStudentFile.setFill(Color.RED);
            displayStudentFile.setText("Incorrect File Format");
        }
        if(studentFinal.isEmpty()){
            displayStudentFile.setFill(Color.RED);
            displayStudentFile.setText("Incorrect File Format");
        }

        for (String student: studentFinal){
            System.out.println(student);
        }
        Collections.sort(studentFinal);
    }

    public void loadDates(ActionEvent actionEvent) {

        ArrayList<String> datesList = loadFile();

        if(datesList == null){
            return;
        }

        String datesFileName = datesList.remove(datesList.size()-1);

        for(String line: datesList){
            if(line.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{2,4}")){
                datesFinal.add(line);
            }
        }
        if(datesFinal.isEmpty()){
            System.out.println("error");
            displayDatesFile.setFill(Color.RED);
            displayDatesFile.setText("Incorrect File Format");
        }
        else{
            displayDatesFile.setFill(Color.BLACK);
            displayDatesFile.setText(datesFileName);
        }
        for (String date: datesFinal){
            System.out.println(date);
        }
    }

    public void loadHeader(ActionEvent actionEvent) {

        ArrayList<String> headerList = loadFile();

        if(headerList == null){
            return;
        }

        String headerFileTitle = headerList.remove(headerList.size()-1);

        if(headerList.isEmpty()){
            displayHeaderFile.setFill(Color.RED);
            displayHeaderFile.setText("Incorrect File Format");
        }
        else{
            headerFinal.add(headerList.get(0));

            displayHeaderFile.setFill(Color.BLACK);
            displayHeaderFile.setText(headerFileTitle);
        }
    }

    private ArrayList<String> loadFile(){
        Scene stage = loadStudentButton.getScene();
        fileLoader.setTitle("Choose File");

        ArrayList<String> dataList = new ArrayList<>();

        fileLoader.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        try{
            File dataFile = fileLoader.showOpenDialog(stage.getWindow());
            System.out.println(dataFile.getName());

            BufferedReader reader = new BufferedReader(new FileReader(dataFile));

            String temp;
            while((temp = reader.readLine()) != null){
                dataList.add(temp);
            }

            dataList.add(dataFile.getName());
        }
        catch(Exception e){
          return null;
        }
        return dataList;

    }

    public void createChart(ActionEvent actionEvent) {
        
    }


    public void printGui(ActionEvent actionEvent) {
    }

    public void saveGui(ActionEvent actionEvent) {
    }
}
