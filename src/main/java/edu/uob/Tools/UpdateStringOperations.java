package edu.uob.Tools;

import edu.uob.Interpreter.InterpreterUpdate;

public class UpdateStringOperations extends InterpreterUpdate {
    public static String[] getColumnCondition(String command){
        String newCommand = command.toLowerCase();
        String splitCommand = newCommand.split("where")[0];
        String setConditions = splitCommand.split("set")[1];
        if(setConditions.equals(splitCommand)){
            System.out.println("SET command error, the number of parameters is wrong.");
            return null;
        }
        return setWords(setConditions.trim());
    }

    private static String[] setWords(String input) {
        input = input.replaceAll(",", "").toLowerCase();
        String[] words = input.split("\\s+");
        int length = words.length / 3;
        if (words.length % 3 != 0) {
            System.out.println("The update condition words number is wrong. Between set and where.");
            return null;
        }
        String[] conditions = new String[length];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            sb.append(words[i]);
            if ((i + 1) % 3 == 0 || i == words.length - 1) {
                conditions[i / 3] = sb.toString();
                sb.setLength(0);
            } else {
                sb.append(" ");
            }
        }
        return conditions;
    }
}
