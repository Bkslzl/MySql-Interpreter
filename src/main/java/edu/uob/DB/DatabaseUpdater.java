package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;
import edu.uob.Tools.UpdateStringOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUpdater extends Output {

    public static boolean setTable(String databaseName, String tableName, String newCommand, String printLines){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String theTable = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(theTable)){
            System.out.println("Can not find the table to set.");
            return false;
        }
        String[] lineNum = printLines.split("\\s+");
        String[] columnCondition = UpdateStringOperations.getColumnCondition(newCommand);//All in lowercase
        if(columnCondition == null){
            return false;
        }
        try {
            for (String cc : columnCondition) {
                String[] token = cc.split("\\s+");
                String newValue = token[2].toString();
                int columnNum = findColumnIndex(theTable, token[0]);
                if(printLines.equals("")){
                    break;
                }
                for (String ln : lineNum) {
                    int rowNum = Integer.parseInt(ln);
                    if (!modifyTable(theTable, rowNum, columnNum, newValue)) {
                        return false;
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Command error, the number of parameters is wrong.");
            return false;
        }
        return true;
    }

    public static int findColumnIndex(String filePath, String columnName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] columns = line.split("\t");
                for (int i = 0; i < columns.length; i++) {
                    if (columns[i].equalsIgnoreCase(columnName)) {
                        return i;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while findColumnIndex reading the table: " + e.getMessage());
        }
        return -1;
    }

    private static boolean modifyTable(String tablePath, int rowNum, int colNum, String newValue) {
        List<String> lines = new ArrayList<>();
        Path path = Paths.get(tablePath);
        try {
            lines = Files.readAllLines(path);
            if (rowNum < 0 || rowNum >= lines.size()) {
                System.out.println("Row number out of bounds.");
                return false;
            }
            if (colNum < 0) {
                System.out.println("Column number out of bounds.");
                return false;
            }
            String line = lines.get(rowNum);
            String[] columns = line.split("\t", -1);
            while (columns.length <= colNum) {
                line += "\t";
                columns = line.split("\t", -1);
            }
            columns[colNum] = newValue;
            lines.set(rowNum, String.join("\t", columns));
            Files.write(path, lines);
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while modifyTable reading the table: " + e.getMessage());
            return false;
        }
    }
}
