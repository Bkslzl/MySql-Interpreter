package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;

public class DatabaseProperties extends Output {

    public static boolean initializeDatabaseProperties(String databaseName){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String databaseProperties = storageFolderPath +
                File.separator + databaseName + "Properties";
        File directory = new File(databaseProperties);
        try {
            if (!directory.exists()) {
                return directory.mkdirs();
            } else {
                System.out.println("databaseProperties " + databaseName + " is already existed");
                return false;
            }
        } catch (SecurityException e) {
            System.out.println("Encountered security permission issues when creating the databaseProperties: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Encountered an unknown error:\n " + e.getMessage() +
                    "when creating the databaseProperties: " + databaseName );
        }
        return false;
    }

    public static boolean initializeTableProperties(String databaseName, String tableName){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String databaseProperties = storageFolderPath +
                File.separator + databaseName + "Properties";
        String tableProperties = databaseProperties +
                File.separator + tableName + "Properties" + ".tab";
        File directory = new File(databaseProperties);
        if (!directory.exists()){
            System.out.println("The working databaseProperties does not exist.");
            return false;
        }
        try (PrintWriter newTable = new PrintWriter(new FileWriter(tableProperties, false))) {
            String theHeads = "id" + " " + ":" + " " + 1;
            newTable.println(theHeads);
            return true;
        }
        catch (IOException e) {
            System.out.println("Unable to create a new table " + e.getMessage());
        }
        return false;
    }

    public static int getTableProperties(String databaseName, String tableName){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String databaseProperties = storageFolderPath +
                File.separator + databaseName + "Properties";
        String tableProperties = databaseProperties +
                File.separator + tableName + "Properties" + ".tab";
        File directory = new File(databaseProperties);
        if (!directory.exists()){
            System.out.println("The working databaseProperties does not exist.");
            return -1;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(tableProperties))) {
            String firstLine = reader.readLine();
            return Integer.parseInt(firstLine.split("\\s+")[2]);
        }
        catch (IOException e) {
            System.out.println("Unable to find the property " + e.getMessage());
        }
        return -1;
    }

    public static boolean addTableProperties(String databaseName, String tableName){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String databaseProperties = storageFolderPath +
                File.separator + databaseName + "Properties";
        String tableProperties = databaseProperties +
                File.separator + tableName + "Properties" + ".tab";
        if (!Total.fileExists(databaseProperties)){
            System.out.println("The working databaseProperties does not exist.");
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(tableProperties))) {
            String firstLine = reader.readLine();
            int currentId = Integer.parseInt(firstLine.split("\\s+")[2]);
            currentId++;
            try (PrintWriter newTable = new PrintWriter(new FileWriter(tableProperties, false))) {
                String newId = "id" + " " + ":" + " " + currentId;
                newTable.println(newId);
            }
            return true;
        }
        catch (IOException e) {
            System.out.println("Unable to find the property " + e.getMessage());
        }
        return false;
    }

    public static void deleteDatabaseProperties(String databaseName) {
        DatabaseDeleter.deleteDatabase(databaseName + "Properties");
    }

    public static void deleteTableProperties(String databaseName, String tableName) {
        DatabaseDeleter.deleteTable(databaseName + "Properties", tableName + "Properties");
    }
}
