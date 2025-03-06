package edu.uob.DB;

import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class DatabasePrinter extends Output {

    public static boolean printSelectedColumns(String databaseName, String tableName, String neededColumns, String printLine) {
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String theTable = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(theTable)){
            System.out.println("Can not find the table to print.");
            return false;
        }
        if(printLine == null){
            System.out.println("Can not find the column to print.");
            return false;
        }
        Set<Integer> linesToPrint = new HashSet<>();
        boolean printAllLines = "*".equals(printLine);
        if (!printAllLines && printLine != null && !printLine.trim().isEmpty()) {
            for (String lineNumStr : printLine.split("\\s+")) {
                try {
                    linesToPrint.add(Integer.parseInt(lineNumStr));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid print line number: " + lineNumStr);
                    return false;
                }
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(theTable))) {
            String line = br.readLine();
            if (line == null || line.equals("id")) {
                System.out.println("The table is empty.");
                return false;
            }
            List<String> allColumns = Arrays.asList(line.split("\t"));
            Map<String, String> columnCaseMap = allColumns.stream()
                    .collect(Collectors.toMap(String::toLowerCase, column -> column, (first, second) -> first, LinkedHashMap::new));

            List<String> selectedColumns = "*".equals(neededColumns) ? new ArrayList<>(allColumns) :
                    Arrays.stream(neededColumns.split(",\\s*"))
                            .map(String::toLowerCase)
                            .map(columnCaseMap::get)
                            .collect(Collectors.toList());
            for (String column : selectedColumns) {
                if (column == null) {
                    System.out.println("One or more specified columns do not exist in the table.");
                    return false;
                }
            }
            System.out.println(String.join("\t", selectedColumns));
            int currentLine = 1;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                if (printAllLines || (linesToPrint.isEmpty() && currentLine == 0) || linesToPrint.contains(currentLine)) {
                    List<String> values = Arrays.asList(line.split("\t", -1));
                    for (String column : selectedColumns) {
                        int index = allColumns.indexOf(column);
                        if (index != -1 && index < values.size()) {
                            System.out.print(values.get(index));
                            if (index < selectedColumns.size() - 1) {
                                System.out.print("\t");
                            }
                        } else {
                            System.out.print("\t");
                        }
                    }
                    System.out.println();
                }
                currentLine++;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error occurred while printSelectedColumns reading the table: " + e.getMessage());
            return false;
        }
    }
}
