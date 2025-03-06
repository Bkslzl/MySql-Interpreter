package edu.uob.Interpreter;

import java.io.File;
import java.nio.file.Paths;

public class InterpreterUse extends Interpreter{

    public static boolean useKeyWord (String newCommand){
        String[] tokens = newCommand.split("\\s+");
        if(tokens.length!=2){
            System.out.println("The USE command format is wrong.");
            return false;
        }
        try{
            Interpreter.currentDatabase = tokens[1].toLowerCase();
            String storageFolderPath = Paths.get("databases").toAbsolutePath().toString();
            String currentDatabasePath = storageFolderPath + File.separator + tokens[1].toLowerCase();
            File folder = new File(currentDatabasePath);
            if(folder.exists()) {
                return true;
            }else{
                System.out.println("The database to work do not exist.");
                return false;
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("USE command error, the number of parameters is wrong.");
        }
        return false;
    }
}
