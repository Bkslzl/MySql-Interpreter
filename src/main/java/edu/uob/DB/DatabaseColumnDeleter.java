package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.*;
import java.nio.file.Paths;

public class DatabaseColumnDeleter extends Output {
    public static boolean deleteColumn(String databaseName, String tableName, String columnName) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String filename = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(filename)){
            System.out.println("Cannot find the table to delete column.");
            return false;
        }
        StringBuilder newContent = new StringBuilder();
        int columnIndex = -1;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null) {
                String[] columns = line.split("\t");
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].equalsIgnoreCase(columnName)) {
                        columnIndex = i;
                        if(columnIndex == 0){
                            System.out.println("Cannot delete the first column (id).");
                            return false;
                        }
                        continue;
                    }
                    newContent.append(i == 0 ? "" : "\t").append(columns[i]);
                }
                newContent.append(System.lineSeparator());
            }
            if (columnIndex != -1) {
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split("\t");
                    for (int i = 0; i < data.length; i++) {
                        if (i == columnIndex) continue;
                        newContent.append(i == 0 ? "" : "\t").append(data[i]);
                    }
                    newContent.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while column deleter reading the table: " + e.getMessage());
            return false;
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            out.print(newContent.toString());
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred while column deleter writing to the table: " + e.getMessage());
        }
        return false;
    }
}
