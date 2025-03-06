package edu.uob.Interpreter;

import edu.uob.DB.DatabaseColumnAdder;
import edu.uob.DB.DatabaseColumnDeleter;

public class InterpreterAlter extends Interpreter{
    public static boolean alterKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        String[] tokens = newCommand.split("\\s+");
        try {
            String tableName = tokens[2].toLowerCase();
            String alterationType = tokens[3];
            String columnName = tokens[4];
            if (tokens[1].equals("table")) {
                if(alterationType.equals("add")){
                    return DatabaseColumnAdder.addColumn(currentDatabase, tableName, columnName);
                } else if (alterationType.equals("drop")) {
                    return DatabaseColumnDeleter.deleteColumn(currentDatabase, tableName, columnName);
                }
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ALTER command error, the number of parameters is wrong.");
            return false;
        }
        System.out.println("The ALTER grammar is wrong.");
        return false;
    }
}
