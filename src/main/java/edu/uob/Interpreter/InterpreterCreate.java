package edu.uob.Interpreter;

import edu.uob.DB.DatabaseCreater;

public class InterpreterCreate extends Interpreter{

    public static boolean createKeyWord(String newCommand){
        String[] tokens = newCommand.split("\\s+");
        String theName = tokens[2].toLowerCase();
        try{
            switch(tokens[1]){
                case "database": return DatabaseCreater.createDatabase(theName);
                case "table":
                    if(isThereNotWorkingPath()){
                        return false;
                    }
                    if(!newCommand.contains("(")) {
                        return DatabaseCreater.createTable(currentDatabase, theName, "");
                    }else {
                        String tableHeads = extractBracketsAndRemoveComma(newCommand);
                        return DatabaseCreater.createTable(currentDatabase, theName, tableHeads);//tableHead can be capitals.
                    }
                default:
                    System.out.println("The CREATE grammar is wrong.");
                    return false;
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("CREATE command error, the number of parameters is wrong.");
        }
        System.out.println("The CREATE grammar is wrong.");
        return false;
    }
}
