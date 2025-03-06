package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class DatabaseRowDeleter extends Output {

    public static boolean deleteLine(String databaseName, String tableName, String printLines) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String theTable = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(theTable)){
            System.out.println("Can not find the table to deleteLine.");
            return false;
        }
        String[] linesToBeDeleted = printLines.split("\\s+");
        List<String> idsToBeDeleted = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(theTable))) {
            String line;
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                for (String lineToBeDeleted : linesToBeDeleted) {
                    if (Integer.parseInt(lineToBeDeleted) == lineNum) {
                        String[] parts = line.split("\t");
                        if (parts.length > 0) {
                            idsToBeDeleted.add(parts[0]);
                        }
                        break;
                    }
                }
                lineNum++;
            }
        } catch (IOException e) {
            System.out.println("DeleteLine failed to read the table: " + e.getMessage());
            return false;
        }
        for (String id : idsToBeDeleted) {
            boolean found = false;
            int currentLineNum = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(theTable))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length > 0 && parts[0].equals(id)) {
                        found = true;
                        if (!removeLine(theTable, currentLineNum)) {
                            return false;
                        }
                        break;
                    }
                    currentLineNum++;
                }
            } catch (IOException e) {
                System.out.println("DeleteLine failed to read the table: " + e.getMessage());
                return false;
            }
            if (!found) {
                System.out.println("ID not found for deletion: " + id);
                return false;
            }
        }
        return true;
    }

    private static boolean removeLine(String tablePath, int rowIndex) {
        Path path = Path.of(tablePath);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(path);
            if (rowIndex < 0 || rowIndex >= lines.size()) {
                System.out.println("Row deleting index out of bounds.");
                return false;
            }
            lines.remove(rowIndex);
            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            System.out.println("RemoveLine failed to read the table: " + e.getMessage());
        }
        return false;
    }
}
