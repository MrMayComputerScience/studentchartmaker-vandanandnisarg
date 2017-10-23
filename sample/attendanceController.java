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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


public class attendanceController {

    final FileChooser fileLoader = new FileChooser();

    //lists for data
    private ArrayList<String> studentList = new ArrayList<>(), datesList = new ArrayList<>(), headerList = new ArrayList<>(), studentFinal, headerFinal, datesFinal;

    //buttons and stuff from fxml
    @FXML Button loadStudentButton;
    @FXML Text displayStudentFile, displayHeaderFile, displayDatesFile, displayErrorMessage;

    public void loadStudents(ActionEvent actionEvent) {
        //file loader
        studentFinal = new ArrayList<>();

        studentList = loadFile();

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
                studentFinal.add(temp.get(1)+" "+temp.get(0));
            }
            displayStudentFile.setFill(Color.BLACK);
            displayStudentFile.setText(studentFileName);
        }
        catch(Exception e){
            e.printStackTrace();
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
    }

    public void loadDates(ActionEvent actionEvent) {
        datesFinal = new ArrayList<>();

        datesList = loadFile();

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
        headerFinal = new ArrayList<>();

        headerList = loadFile();

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
        if(!studentFinal.isEmpty()&&!headerFinal.isEmpty()&&!datesFinal.isEmpty()){

        }
        else{
            displayErrorMessage.setText("Missing Files");
        }
    }
}
