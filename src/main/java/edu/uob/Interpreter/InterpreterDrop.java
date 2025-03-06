package edu.uob.Interpreter;

import edu.uob.DB.DatabaseDeleter;
import edu.uob.DB.DatabaseProperties;

public class InterpreterDrop extends Interpreter{
    public static boolean dropKeyWord(String newCommand){
        String[] tokens = newCommand.split("\\s+");
        try{
            String name = tokens[2].toLowerCase();
            if(tokens[1].equals("database")) {
                DatabaseProperties.deleteDatabaseProperties(name);
                return DatabaseDeleter.deleteDatabase(name);
            } else if (tokens[1].equals("table")) {
                if(isThereNotWorkingPath()){
                    return false;
                }
                DatabaseProperties.deleteTableProperties(currentDatabase, name);
                return DatabaseDeleter.deleteTable(currentDatabase, name);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DROP command error, the number of parameters is wrong.");
            return false;
        }
        System.out.println("The DROP grammar is wrong.");
        return false;
    }
}
