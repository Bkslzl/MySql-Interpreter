package edu.uob.Interpreter;

import edu.uob.DB.DatabasePrinter;

public class InterpreterSelect extends Interpreter{

    public static boolean selectKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        try {
            String[] tokens1 = newCommand.split("\\s+");
            String columns = extractColumns(newCommand);
            if (containsStringWhere(tokens1)) {
                return InterpreterCondition.conditionKeyWord(newCommand, "select");
            } else {
                String[] tokens2 = newCommand.split("from");
                String tableName = tokens2[1].toLowerCase().trim();
                return DatabasePrinter.printSelectedColumns(currentDatabase, tableName, columns, "*");
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("SELECT command error, the number of parameters is wrong.");
            return false;
        }
    }

    private static boolean containsStringWhere(String[] array) {
        for (String item : array) {
            if (item.equals("where")) {
                return true;
            }
        }
        return false;
    }
}
