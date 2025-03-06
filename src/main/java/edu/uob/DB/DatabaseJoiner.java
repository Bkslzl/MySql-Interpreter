package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DatabaseJoiner extends Output {

    public static boolean joinTables(String currentDatabase,
                                     String tableName1, String tableName2,
                                     String column1, String column2) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        try {
            String tablePath1 = storageFolderPath +
                    File.separator + currentDatabase +
                    File.separator + tableName1 + ".tab";
            String tablePath2 = storageFolderPath +
                    File.separator + currentDatabase +
                    File.separator + tableName2 + ".tab";

            if(!Total.fileExists(tablePath1) || !Total.fileExists(tablePath2)){
                System.out.println("Can not find the table to join.");
                return false;
            }
            List<String> tableLines1 = Files.readAllLines(Path.of(tablePath1));
            List<String> tableLines2 = Files.readAllLines(Path.of(tablePath2));

            /*List<String> table1Header = Arrays.asList(tableLines1.get(0).split("\t"));
            List<String> table2Header = Arrays.asList(tableLines2.get(0).split("\t"));*/

            List<String> table1Header = Arrays.stream(tableLines1.get(0).split("\t"))
                    .map(String::toLowerCase)
                    .toList();

            List<String> table2Header = Arrays.stream(tableLines2.get(0).split("\t"))
                    .map(String::toLowerCase)
                    .toList();
            int index1 = table1Header.indexOf(column1.toLowerCase());
            int index2 = table2Header.indexOf(column2.toLowerCase());
            String newTableHeader = "id";
            for (int i = 1; i < table1Header.size(); i++) {
                if (!table1Header.get(i).equalsIgnoreCase(column1)) {
                    newTableHeader += "\t" + tableName1 + "." + table1Header.get(i);
                }
            }
            for (int i = 1; i < table2Header.size(); i++) {
                if (!table2Header.get(i).equalsIgnoreCase(column2)) {
                    newTableHeader += "\t" + tableName2 + "." + table2Header.get(i);
                }
            }
            System.out.println(newTableHeader);
            int newId = 1;
            for (int i = 1; i < tableLines1.size(); i++) {
                String[] row1 = tableLines1.get(i).split("\t");
                for (int j = 1; j < tableLines2.size(); j++) {
                    String[] row2 = tableLines2.get(j).split("\t");
                    if (row1[index1].equals(row2[index2])) {
                        String newRow = Integer.toString(newId++);
                        for (int k = 1; k < row1.length; k++) {
                            if (k != index1) {
                                newRow += "\t" + row1[k];
                            }
                        }
                        for (int k = 1; k < row2.length; k++) {
                            if (k != index2) {
                                newRow += "\t" + row2[k];
                            }
                        }
                        System.out.println(newRow);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred while join reading the tables: " + e.getMessage());
            return false;
        }
    }
}
