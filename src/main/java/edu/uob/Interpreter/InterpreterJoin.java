package edu.uob.Interpreter;

import edu.uob.DB.DatabaseJoiner;

public class InterpreterJoin extends Interpreter{

    public static boolean joinKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        String[] tokens = newCommand.split("\\s+");
        try {
            if(!tokens[2].equals("and") || !tokens[4].equals("on") || !tokens[6].equals("and")){
                System.out.println("JOIN command error, the number of parameters is wrong.");
                return false;
            }
            String table1 = tokens[1].toLowerCase();
            String table2 = tokens[3].toLowerCase();
            String column1 = tokens[5];
            String column2 = tokens[7];
            return DatabaseJoiner.joinTables(currentDatabase, table1, table2, column1, column2);
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("JOIN command error, the number of parameters is wrong.");
        }
        return false;
    }
}
