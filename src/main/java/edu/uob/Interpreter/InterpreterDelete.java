package edu.uob.Interpreter;

public class InterpreterDelete extends Interpreter{

    public static boolean deleteKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        String[] tokens = newCommand.split("\\s+");
        try {
            if(!tokens[1].equals("from")){
                System.out.println("DELETE command error, the number of parameters is wrong.");
                return false;
            }
            return InterpreterCondition.conditionKeyWord(newCommand,"delete");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("DELETE command error, the number of parameters is wrong.");
            return false;
        }
    }
}
