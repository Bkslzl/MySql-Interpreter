package edu.uob.DB;

import edu.uob.Interpreter.InterpreterCondition;
import edu.uob.Tools.Output;
import edu.uob.Tools.Total;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DatabaseCondition extends Output {

    public static String findRows(String databaseName, String tableName, String condition) {//  这里的condition是一个条件
        condition = condition.toLowerCase();
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String tablePath = storageFolderPath +
                File.separator + databaseName +
                File.separator + tableName + ".tab";
        if(!Total.fileExists(tablePath)){
            return null;
        }
        List<Integer> linesToPrint = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(tablePath))) {
            String processedCondition = condition.replaceAll("([<>=!]+)", " $1 ");
            String line = br.readLine();
            List<String> columnNames = List.of(line.toLowerCase().split("\t"));
            int columnIndex = columnNames.indexOf(processedCondition.split("\\s+")[0]);
            int currentLine = 1;
            while ((line = br.readLine()) != null && !line.trim().isEmpty()) {
                String[] values = line.split("\t");
                if (columnIndex != -1 && values.length > columnIndex) {
                    if (InterpreterCondition.isMatchingCondition(values[columnIndex].toString().toLowerCase(), processedCondition)) {
                        linesToPrint.add(currentLine);
                    }
                }
                currentLine++;
            }
        } catch (IOException e) {
            System.out.println("Fail to read the file: " + tableName + e.getMessage());
        }
        return linesToPrint.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }
}
