package edu.uob.Tools;

import edu.uob.Interpreter.*;

import java.io.File;
import java.util.Scanner;

public class Total {

    public static boolean handleCommandTotal(String theCommand) {
        String rawCommand = KeyWordsOperations.convertKeywordsToLowercase(theCommand);
        String newCommand = Interpreter.removeLastSemicolon(rawCommand);
        if(newCommand == null){
            System.out.println("Semicolon removing failed.");
            return false;
        }
        String[] tokens = newCommand.split("\\s+");
        if ("use".equals(tokens[0])) {
            return InterpreterUse.useKeyWord(newCommand);
        } else if ("create".equals(tokens[0])) {
            return InterpreterCreate.createKeyWord(newCommand);
        } else if ("drop".equals(tokens[0])) {
            return InterpreterDrop.dropKeyWord(newCommand);
        } else if ("alter".equals(tokens[0])) {
            return InterpreterAlter.alterKeyWord(newCommand);
        } else if ("insert".equals(tokens[0])) {
            return InterpreterInsert.insertKeyWord(newCommand);
        } else if ("select".equals(tokens[0])) {
            return InterpreterSelect.selectKeyWord(newCommand);
        } else if ("update".equals(tokens[0])) {
            return InterpreterUpdate.updateKeyWord(newCommand);
        } else if ("delete".equals(tokens[0])) {
            return InterpreterDelete.deleteKeyWord(newCommand);
        } else if ("join".equals(tokens[0])) {
            return InterpreterJoin.joinKeyWord(newCommand);
        } else {
            System.out.println("The first keyword is not detected.");
            return false;
        }
    }

    public static void changeOutputStream(){
        System.setOut(Output.captureStream);
        //System.setErr(Output.captureErrStream);
    }

    public static boolean fileExists(String filePath){
        File theFile = new File(filePath);
        return theFile.exists();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //handleCommandTotal("CREATE DATABASE markbook;");
        /*handleCommandTotal("USE markbook;");*/
        while (true) {
            System.setOut(Output.originalOut);
            System.out.print("Please enter command：");
            String inputString = scanner.nextLine();
            if (inputString.equals("stop")) {
                break;
            }
            System.out.println("Your command is: " + inputString);
            handleCommandTotal(inputString);
            System.out.println(Output.data);
            Output.data.reset();
        }
        scanner.close();
    }
}
