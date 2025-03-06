package edu.uob.DB;

import edu.uob.Tools.KeyWordsOperations;
import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class DatabaseCreater extends Output {

    public static boolean createDatabase(String databaseName) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String directoryName = storageFolderPath + File.separator + databaseName;

        File directory = new File(directoryName);
        try {
            if (!directory.exists()) {
                if(!DatabaseProperties.initializeDatabaseProperties(databaseName)){
                    System.out.println("DatabaseProperties initialization failed");
                    /*return false;*/
                }
                return directory.mkdirs();
            } else {
                System.out.println("database " + databaseName + " is already existed");
                return false;
            }
        } catch (SecurityException e) {
            System.out.println("Encountered security permission issues when creating the database: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Encountered an unknown error: " + e.getMessage() +
                    "when creating the database: " + databaseName );
        }
        return false;
    }

    public static boolean createTable(String databaseName, String tableName, String tableHeads) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String tablePath = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        String directoryPath = storageFolderPath +
                File.separator + databaseName;
        if (!Total.fileExists(directoryPath)){
            System.out.println("The working database does not exist or has not been declared, unable to create a new table");
            return false;
        }
        if (Total.fileExists(tablePath)){
            System.out.println("Cannot create a new table with the currently used name.");
            return false;
        }
        try (PrintWriter newTable = new PrintWriter(new FileWriter(tablePath, false))) {
            if(KeyWordsOperations.isKeyWord(tableHeads)){
                System.out.println("The added columns' names contain the keywords.");
                return false;
            }
            String theHeads;
            if(!hasUniqueElements(tableHeads)){
                System.out.println("Cannot create two columns with the same name.");
                return false;
            }
            if(!tableHeads.isEmpty()) {
                theHeads ="id" + "\t" + tableHeads.replaceAll("\\s+", "\t");
            }else{
                theHeads ="id";
            }
            newTable.println(theHeads);
            if(!DatabaseProperties.initializeTableProperties(databaseName,tableName)){
                System.out.println("Id initialization failed");
                /*return false;*/
            }
            return true;
        }
        catch (IOException e) {
            System.out.println("Unable to create a new table " + e.getMessage());
        }
        return false;
    }

    public static boolean hasUniqueElements(String tableHeads) {
        if (tableHeads == null || tableHeads.isEmpty()) {
            return true;
        }
        String[] elements = tableHeads.split("\\s+");
        Set<String> uniqueElements = new HashSet<>();
        for (String element : elements) {
            if (!uniqueElements.add(element.toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}
