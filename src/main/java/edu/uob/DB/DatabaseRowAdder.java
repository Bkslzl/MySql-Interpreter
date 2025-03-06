package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.*;
import java.nio.file.Paths;

public class DatabaseRowAdder extends Output {

    public static boolean insertData(String databaseName, String tableName, String rowData) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String filename = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(filename)){
            System.out.println("Cannot find the table to insert.");
            return false;
        }
        int currentId = DatabaseProperties.getTableProperties(databaseName, tableName);
        /*if(currentId == -1){
            System.out.println("Id fetch failed");
            return false;
        }*/
        if(!insertSafetyDetect(filename, rowData)){
            System.out.println("Insert row column number is not matching the table heads.");
            return false;
        }
        String formattedData = currentId + "\t" + rowData.trim().replaceAll("\\s+", "\t");
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(formattedData);
            //id++
            if(!DatabaseProperties.addTableProperties(databaseName,tableName)){
                System.out.println("Id addition failed");
                /*return false;*/
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred while reading the file: " + e.getMessage());
        }
        return false;
    }

    //Check if the column number to insert is equal to the number of table heads.
    private static boolean insertSafetyDetect(String tablePath, String rowData){
        try (BufferedReader reader = new BufferedReader(new FileReader(tablePath))) {
            String headerLine = reader.readLine();
            int headColumnsNumber = headerLine.split("\\s+").length - 1;
            int rowDataColumnsNumber = rowData.split("\\s+").length;
            return headColumnsNumber == rowDataColumnsNumber;
        } catch (IOException e) {
            System.out.println("Error occurred while insertSafetyDetect reading the table: " + e.getMessage());
        }
        return false;
    }
}
