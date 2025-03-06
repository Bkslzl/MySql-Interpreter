package edu.uob.Interpreter;

import edu.uob.DB.DatabaseRowAdder;

public class InterpreterInsert extends Interpreter{
    public static boolean insertKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        String[] tokens = newCommand.split("\\s+");
        String contentInBrackets = extractBracketsAndRemoveComma(newCommand);
        if(contentInBrackets!=null) {
            contentInBrackets = contentInBrackets.replaceAll("'", "");
        }else{
            System.out.println("INSERT command error, the number of parameters is wrong.");
            return false;
        }
        try {
            String tableName = tokens[2].toLowerCase().trim();
            if (tokens[1].equals("into") && tokens[3].equals("values") ) {
                return DatabaseRowAdder.insertData(currentDatabase, tableName, contentInBrackets);
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("INSERT command error, the number of parameters is wrong.");
        }
        System.out.println("INSERT command error, the number of parameters is wrong.");
        return false;
    }
}
