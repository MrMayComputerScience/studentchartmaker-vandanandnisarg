package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class gradesController extends JFrame{


    private final FileChooser fileLoader = new FileChooser();

    public ArrayList<String> studentFinal = new ArrayList<>();

    @FXML Label SFName,HFName,Error,SpacerText1,SpacerText3;
    @FXML Button loadStudentButtonGR;
    @FXML TextField ColumnNumber;
    String studentFileName = "";
    int colNumber;
    String headerFileName = "";
    String finalHeader="";
    private ArrayList<String> loadFile(){
        Scene stage = loadStudentButtonGR.getScene();
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
            //displayStudentFile.setFill(javafx.scene.paint.Color.BLACK);
            SpacerText1.setText(studentFileName);

            Collections.sort(studentFinal);
            for (int i = 0; i < studentFinal.size(); i++) {
                String[] reverse = studentFinal.get(i).split(" ");
                studentFinal.set(i, reverse[1]+" "+reverse[0]);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        for (String student: studentFinal){
            System.out.println(student);
        }
    }
    /*public void loadStudents(ActionEvent actionEvent)
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

    }*/

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

    /*
    ArrayList<String> headerList = loadFile();

        if(headerList == null){
            return;
        }

        String headerFileTitle = headerList.remove(headerList.size()-1);

        if(headerList.isEmpty()){
            SpacerText3.setText("Incorrect File Format");
        }
        else{
            String header = "";
            for (String line:headerList){
                header += " "+line;
            }
            headerFileName.equals(header);

            SpacerText3.setText(headerFileTitle);
        }
     */


    public void loadHeader(ActionEvent actionEvent)
    {

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

            // Hide this current window
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            createSheet();

            
        }
        catch(Exception e){
            //e.printStackTrace();
            SpacerText1.setText("Incorrect File Format");
        }
        if(studentFinal.isEmpty()){
            SpacerText1.setText("Incorrect File Format");
        }

        for (String student: studentFinal){
            System.out.println(student);
        }
    }
    public String createSheet() {


        int bordernum = 2;
        try {
            FileOutputStream fileOut = new FileOutputStream("Grades Sheet.xls");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Grades Sheet");
            worksheet.getPrintSetup().setLandscape(true);




            // row 1 for Prinitng attendance sheet in center
            HSSFRow row0 = worksheet.createRow((short) 0);//1
            HSSFCell cellmid = row0.createCell((short) 0);//2
            worksheet.addMergedRegion(new CellRangeAddress(0,0,0,colNumber));
            cellmid.setCellValue(finalHeader);//3
            HSSFCellStyle cellStylem = workbook.createCellStyle();//4
            cellStylem.setAlignment(HorizontalAlignment.CENTER);
            cellStylem.setFillForegroundColor(HSSFColor.GOLD.index);//5
            cellmid.setCellStyle(cellStylem);//6


            //.addMergedRegion(rowFrom,rowTo,colFrom,colTo);




            // row 2 with all the dates in the correct place
            HSSFRow row1 = worksheet.createRow((short) 1);//1
            HSSFCell cell1;
            for(int y = 0; y < colNumber; y++){

                cell1 = row1.createCell((short) y+1);//2
                createBorders(workbook, cell1, bordernum);


            }
            HSSFCellStyle cellStylei = workbook.createCellStyle();//4
            cellStylei.setFillForegroundColor(HSSFColor.GREEN.index);//5



            // row 3 and on until the studentList.size() create the box.
            int counter = 0;
            for(int stu = 2; stu <= (studentFinal.size()+1); stu++){
                HSSFRow Row = worksheet.createRow((short) stu);//1
                for(int gr = 0; gr <= colNumber; gr++){
                    if(gr == 0){
                        HSSFCell cell = Row.createCell((short) 0);//2
                        cell.setCellValue(studentFinal.get(counter));//3
                        worksheet.autoSizeColumn(gr);
                        HSSFCellStyle cellStyle2 = workbook.createCellStyle();//4


                        cell.setCellStyle(cellStyle2);//6
                        createBorders(workbook, cell, 2);
                    }else{
                        HSSFCell Cell = Row.createCell((short) gr);//
                        worksheet.autoSizeColumn(gr);
                        createBorders(workbook, Cell, 3);
                    }


                }
                counter++;
            }
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
            try {
                Desktop.getDesktop().open(new File("Grades Sheet.xls"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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


}