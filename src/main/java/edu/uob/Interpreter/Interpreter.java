package edu.uob.Interpreter;

import edu.uob.Tools.Total;

import java.io.*;
import java.nio.file.Paths;

public class Interpreter extends Total {
    public static String currentDatabase = "-1";

    public static boolean isThereNotWorkingPath(){
        String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
        String currentDatabasePath = storageFolderPath +
                File.separator + currentDatabase;
        File theDatabase = new File(currentDatabasePath);
        if(!theDatabase.exists()) {
            System.out.println("No working database specified, or the database does not exist.");
            return true;
        }
        return false;
    }

    public static String extractColumns(String command) {
        if(command.contains("select")){
            String[] parts = command.split("from");
            return parts[0].split("select")[1].toLowerCase().trim();
        }
        return command;
    }

    public static String extractBracketsAndRemoveComma(String command) {
        int leftBracketPosition = command.lastIndexOf('(');
        int rightBracketPosition = command.lastIndexOf(')');
        if(leftBracketPosition == -1 || rightBracketPosition == -1){
            System.out.println("Do not find the brackets in the command.");
            return null;
        }
        String contentInsideBrackets = command.substring(leftBracketPosition + 1, rightBracketPosition);
        return contentInsideBrackets.replace(",", " ");
    }

    public static String removeLastSemicolon(String newCommand) {
        String command = newCommand.trim();
        if (command.isEmpty()) {
            System.out.println("No command detected.");
            return null;
        }
        if (!command.endsWith(";")) {
            System.out.println("Commands should end with semicolon.");
            return null;
        }
        return command.substring(0, command.length() - 1);
    }
}
