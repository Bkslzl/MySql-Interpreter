package edu.uob.Tools;

import edu.uob.Tools.Output;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KeyWordsOperations extends Output {

    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "use", "create", "drop", "alter", "insert", "select", "update",
            "delete", "join", "from", "on", "values", "and", "or", "add",
            "drop", "where", "into", "set", "table", "database"
    ));
    public static String convertKeywordsToLowercase(String input) {
        Pattern pattern = Pattern.compile("\\b(\\w+)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String word = matcher.group(1);
            matcher.appendReplacement(sb, KEYWORDS.contains(word.toLowerCase()) ? word.toLowerCase() : word);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    public static boolean isKeyWord(String input) {
        String lowerCaseInput = input.toLowerCase();
        for (String keyword : KEYWORDS) {
            String pattern = "\\b" + keyword + "\\b";
            if (lowerCaseInput.matches(".*" + pattern + ".*")) {
                return true;
            }
        }
        return false;
    }
}
