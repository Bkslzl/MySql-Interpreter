package edu.uob.Interpreter;

import edu.uob.Tools.ConditionStringOperations;
import edu.uob.DB.DatabaseCondition;
import edu.uob.DB.DatabasePrinter;
import edu.uob.DB.DatabaseRowDeleter;
import edu.uob.DB.DatabaseUpdater;

public class InterpreterCondition extends Interpreter{
    public static boolean conditionKeyWord(String newCommand, String keyWord){
        try {
            String[] command = newCommand.split("where");
            String condition = command[1].trim();
            String[] separatedCommand;
            String tableName;
            if(keyWord.equals("select")){
                separatedCommand = command[0].split("from");
                tableName = separatedCommand[1].toLowerCase().trim();
            }else if (keyWord.equals("update")){
                separatedCommand = command[0].split("\\s+");
                tableName = separatedCommand[1].toLowerCase().trim();
            }else {
                separatedCommand = command[0].split("from");
                tableName = separatedCommand[1].toLowerCase().trim();
            }
            String readyCommand = newCommand.toLowerCase().split("where")[1].trim();
            String neededColumns = ConditionStringOperations.extractColumns(newCommand);//Only for select
            String printLines = evaluateConditions(tableName, "(" + readyCommand + ")");
            if(printLines == null){
                System.out.println("Cannot find the table.");
                return false;
            }
            if (condition.contains("and")) {
                return chooseCondition(newCommand, keyWord, tableName, neededColumns, printLines);
            } else if (condition.contains("or")) {
                return chooseCondition(newCommand, keyWord, tableName, neededColumns, printLines);
            } else {
                return chooseCondition(newCommand, keyWord, tableName, neededColumns, printLines);
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(keyWord + " command error, the number of parameters is wrong.");
            return false;
        }
    }

    private static String evaluateConditions(String tableName, String condition) {
        condition = condition.trim();
        try {
            if (condition.startsWith("(") && condition.endsWith(")") &&
                    ConditionStringOperations.bracketsMatch(condition)) {
                condition = condition.substring(1, condition.length() - 1).trim();
            }
            String[] parts = ConditionStringOperations.splitCondition(condition);
            if (parts == null) {
                return DatabaseCondition.findRows(currentDatabase, tableName, condition);
            }
            String result1 = evaluateConditions(tableName, parts[0].trim());
            String result2 = evaluateConditions(tableName, parts[2].trim());
            boolean isAnd = parts[1].trim().equals("and");
            return isAnd ? ConditionStringOperations.mergeStrings(result1, result2) :
                    ConditionStringOperations.addStrings(result1, result2);
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("CONDITION command error, the number of parameters is wrong.");
            return "";
        }
    }

    private static boolean chooseCondition(String newCommand, String keyWord, String tableName, String neededColumns, String printLines) {
        if(printLines == null){
            return false;
        }
        if(keyWord.equals("select")) {
            return DatabasePrinter.printSelectedColumns(currentDatabase, tableName, neededColumns, printLines);
        }else if(keyWord.equals("update")){
            return DatabaseUpdater.setTable(currentDatabase, tableName, newCommand, printLines);
        }else{//"delete"
            return DatabaseRowDeleter.deleteLine(currentDatabase, tableName, printLines);
        }
    }

    public static boolean isMatchingCondition(String specificColumn, String condition) {
        String[] tokens = condition.split("\\s+");
        String operator = tokens[1]; // 操作符
        String parameter = tokens[2].replace("'", "");
        final double precision = 1E-3;
        if (parameter.matches("-?\\d+(\\.\\d+)?") && specificColumn.matches("-?\\d+(\\.\\d+)?")) {
            double dblSpecificColumn = Double.parseDouble(specificColumn);
            double dblParameter = Double.parseDouble(parameter);
            switch (operator) {
                case ">=" -> {
                    return dblSpecificColumn >= dblParameter;
                }
                case "<=" -> {
                    return dblSpecificColumn <= dblParameter;
                }
                case "<" -> {
                    return dblSpecificColumn < dblParameter;
                }
                case ">" -> {
                    return dblSpecificColumn > dblParameter;
                }
                case "==" -> {
                    return Math.abs(dblSpecificColumn - dblParameter) < precision;
                }
                case "!=" -> {
                    return Math.abs(dblSpecificColumn - dblParameter) >= precision;
                }
                case "like" -> {
                    return specificColumn.contains(parameter);
                }
                default -> {
                    System.out.println("This operator makes no sense between numbers.");
                    return false;
                }
            }
        } else {
            if ("like".equals(operator)) {
                return specificColumn.contains(parameter);
            } else if ("==".equals(operator)) {
                return specificColumn.equals(parameter);
            } else if ("!=".equals(operator)) {
                return !specificColumn.equals(parameter);
            } else {
                System.out.println("This operator makes no sense with strings.");
                return false;
            }
        }
    }
}
