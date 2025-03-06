package edu.uob.Tools;

import edu.uob.Interpreter.InterpreterCondition;

public class ConditionStringOperations extends InterpreterCondition {

    public static String[] splitCondition(String condition) {
        int depth = 0;
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else {
                boolean and = condition.startsWith("and", i);
                if (depth == 0 && (i < condition.length() - 3) &&
                        (and || condition.startsWith("or", i))) {
                    String op = and ? "and" : "or";
                    return new String[]{condition.substring(0, i), op, condition.substring(i + op.length())};
                }
            }
        }
        return null;
    }

    public static String mergeStrings(String first, String second){
        String[] arr1 = first.split("\\s+");
        String[] arr2 = second.split("\\s+");
        StringBuilder commonNumbers = new StringBuilder();
        for (String num1 : arr1) {
            for (String num2 : arr2) {
                if (num1.equals(num2)) {
                    commonNumbers.append(num1).append(" ");
                    break;
                }
            }
        }
        if (!commonNumbers.isEmpty()) {
            commonNumbers.deleteCharAt(commonNumbers.length() - 1);
        }else{
            System.out.println("Can not find any rows with the AND conditions.");
            return null;
        }
        return commonNumbers.toString();
    }

    public static String addStrings(String first, String second){
        String[] arr1 = first.split(" ");
        String[] arr2 = second.split(" ");
        StringBuilder commonNumbers = new StringBuilder();
        for (String num1 : arr1) {
            if (commonNumbers.toString().contains(num1)) {
                break;
            }else{
                commonNumbers.append(num1).append(" ");
            }
        }
        for (String num2 : arr2) {
            if (commonNumbers.toString().contains(num2)) {
                break;
            }else{
                commonNumbers.append(num2).append(" ");
            }
        }
        if (!commonNumbers.isEmpty()) {
            commonNumbers.deleteCharAt(commonNumbers.length() - 1);
        }else{
            System.out.println("Can not find any rows with the OR conditions.");
            return null;
        }
        return commonNumbers.toString();
    }

    public static boolean bracketsMatch(String condition) {
        int depth = 0;
        for (char c : condition.toCharArray()) {
            if (c == '(') depth++;
            if (c == ')') depth--;
            if (depth < 0) return false;
        }
        return depth == 0;
    }
}
