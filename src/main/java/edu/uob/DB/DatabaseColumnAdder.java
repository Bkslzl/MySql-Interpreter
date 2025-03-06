package edu.uob.DB;

import edu.uob.Tools.KeyWordsOperations;
import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.*;
import java.nio.file.Paths;

public class DatabaseColumnAdder extends Output {
    public static boolean addColumn(String databaseName, String tableName, String newColumnName) {
        if(KeyWordsOperations.isKeyWord(newColumnName)){
            System.out.println("The added column's name is one of the keywords.");
            return false;
        }
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String filename = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(filename)){
            System.out.println("Cannot find the table to add column.");
            return false;
        }
        File file = new File(filename);
        StringBuilder newContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isfirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isfirstLine) {
                    line = line + "\t" + newColumnName;
                    isfirstLine = false;
                }
                newContent.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Error occurred while column adder reading the table: " + e.getMessage());
            return false;
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(file, false))) {
            out.print(newContent.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred while column adder writing to the table: " + e.getMessage());
        }
        return false;
    }
}
