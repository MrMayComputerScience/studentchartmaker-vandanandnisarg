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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;



import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.GREEN;
import org.apache.poi.ss.usermodel.*;


public class attendanceController {

    private final FileChooser fileLoader = new FileChooser();

    public ArrayList<String> studentFinal = new ArrayList<>();
    public ArrayList<String> headerFinal = new ArrayList<>();
    public ArrayList<String> datesFinal = new ArrayList<>();

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
        //[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2}
        String datesFileName = datesList.remove(datesList.size()-1);
        for(String line: datesList){
            if(line.matches("[0-9]{1,2}/[0-9]{1,2}/[0-9]{1,2}")){
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
        if(!studentFinal.isEmpty()&&!headerFinal.isEmpty()&&!datesFinal.isEmpty()){
            try {
                /* Hide this current window
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("createAttendanceChart.fxml"));
                stage.setTitle("Add new entry");
                stage.setScene(new Scene(root, 800, 600));
                stage.show();*/

                // Hide this current window
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

                createSheet();


            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            displayErrorMessage.setFill(Color.RED);
            displayErrorMessage.setText("Missing Files");
        }
    }
    public String createSheet() {


        int bordernum = 2;
        try {
            FileOutputStream fileOut = new FileOutputStream("Attendance Sheet.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Attendance sheet");



            // row 1 for Prinitng attendance sheet in center
            HSSFRow row0 = worksheet.createRow((short) 0);//1
            HSSFCell cellmid = row0.createCell((short) (datesFinal.size()/2-1));//2
            cellmid.setCellValue(headerFinal.get(0));//3
            HSSFCellStyle cellStylem = workbook.createCellStyle();//4
            cellStylem.setFillForegroundColor(HSSFColor.GOLD.index);//5
            cellmid.setCellStyle(cellStylem);//6
            createBorders(workbook, cellmid, 1);
            HSSFCell cellmid2 = row0.createCell((short) (datesFinal.size()/2));//2
            createBorders(workbook, cellmid2, 1);



            // row 2 with all the dates in the correct place
            HSSFRow row1 = worksheet.createRow((short) 1);//1
            HSSFCell cell1;
            for(int y = 0; y < datesFinal.size(); y++){

                cell1 = row1.createCell((short) y+1);//2
                cell1.setCellValue(datesFinal.get(y));//3
                createBorders(workbook, cell1, bordernum);

            }
            HSSFCellStyle cellStylei = workbook.createCellStyle();//4
            cellStylei.setFillForegroundColor(HSSFColor.GREEN.index);//5



            // row 3 and on until the studentList.size() create the box.
            int counter = 0;
            for(int stu = 2; stu <= (studentFinal.size()+1); stu++){
                HSSFRow Row = worksheet.createRow((short) stu);//1
                for(int gr = 0; gr <= datesFinal.size(); gr++){
                    if(gr == 0){
                        HSSFCell cell = Row.createCell((short) 0);//2
                        cell.setCellValue(studentFinal.get(counter));//3
                        worksheet.autoSizeColumn(gr);
                        HSSFCellStyle cellStyle2 = workbook.createCellStyle();//4
                        //cellStyle2.setAlignment(HSSFCellStyle.);

                        //cellStyle2.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
                        //cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        //cellStyle2.setFillForegroundColor(HSSFColor.GOLD.index);//5

                        cell.setCellStyle(cellStyle2);//6
                        createBorders(workbook, cell, 2);
                    }else{
                        HSSFCell Cell = Row.createCell((short) gr);//2
                        createBorders(workbook, Cell, 3);
                    }


                }
                counter++;
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "It Works";

    }
    public static void createBorders(HSSFWorkbook workbook,HSSFCell cell, int x) {
        if (x == 1) {
            HSSFCellStyle style = workbook.createCellStyle();
            //style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.THICK);
            style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderLeft(BorderStyle.THICK);
            style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderRight(BorderStyle.THICK);
            style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderTop(BorderStyle.THICK);
            style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cell.setCellStyle(style);
        } else if (x == 2) {
            HSSFCellStyle style = workbook.createCellStyle();
            //style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_BLUE.getIndex());
            //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderLeft(BorderStyle.MEDIUM);
            style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderRight(BorderStyle.MEDIUM);
            style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderTop(BorderStyle.MEDIUM);
            style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cell.setCellStyle(style);
        } else {
            HSSFCellStyle style = workbook.createCellStyle();
            //style.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.AQUA.getIndex());
            //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            cell.setCellStyle(style);
        }


    }
    public void printGui(ActionEvent actionEvent) {
    }

    public void saveGui(ActionEvent actionEvent) {
    }
}
