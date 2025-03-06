package edu.uob.DB;

import edu.uob.Tools.Output;

import java.io.File;
import java.nio.file.Paths;

public class DatabaseDeleter extends Output {

    public static boolean deleteDatabase(String databaseName) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String directoryName = storageFolderPath +
                File.separator + databaseName;
        File directory = new File(directoryName);
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File f : files) {
                    try {
                        if(!f.delete()) {
                            System.out.println("Error deleting the tables.");
                            return false;
                        }
                    } catch (SecurityException e) {
                        System.out.println("Security permission exception while deleting the tables: " + e.getMessage());
                        return false;
                    }
                }
            }
            try {
                if (directory.delete()) {
                    return true;
                } else {
                    System.out.println("Unable to delete database: " + databaseName +
                            " Please ensure that the database is not being used by another process.");
                }
            } catch (SecurityException e) {
                System.out.println("Security permission exception while deleting the database: " + e.getMessage());
            }
        } else {
            System.out.println("Database " + databaseName + " does not exist.");
        }
        return false;
    }

    public static boolean deleteTable(String databaseName, String tableName) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String filename = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        File file = new File(filename);
        try {
            if (file.delete()) {
                return true;
            } else {
                System.out.println("Unable to delete table: " + tableName +
                        " Please ensure that the table is not being used by another process " +
                        "or it is deleted yet.");
            }
        } catch (SecurityException e) {
            System.out.println("Security permission exception while deleting the table: " + e.getMessage());
        }
        return false;
    }
}
