package edu.uob.Interpreter;

public class InterpreterUpdate extends Interpreter{

    public static boolean updateKeyWord(String newCommand){
        if(isThereNotWorkingPath()){
            return false;
        }
        try {
            return InterpreterCondition.conditionKeyWord(newCommand, "update");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("UPDATE command error, the number of parameters is wrong.");
            return false;
        }
    }
}
