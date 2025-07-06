package org.tpuchal.utils;

public interface UserInputUtils {
    static String joinAndSanitizeArguments(String[] parts) {
        StringBuilder sb = new StringBuilder();
        if (parts == null || parts.length < 2) {
            throw new IllegalArgumentException("There must be at least 1 argument");
        }
        for (int i = 1; i < parts.length; i++) {
            sb.append(parts[i]);
            sb.append(" ");
        }

        String retStr = sb.toString().trim().replaceAll("[^a-zA-Z0-9 ]", "");

        //this is an arbitrary choice to stop user from putting some harmful input length
        return retStr.length() > 30 ? retStr.substring(0, 30) : retStr;
    }

    static int retreiveIdFromInput(String[] parts) {
        if (parts == null || parts.length < 2) {
            throw new IllegalArgumentException("No ID provided. Expected format: command <id>");
        }

        String rawId = parts[1];

        String sanitizedId;
        if (rawId.startsWith("-")) {
            sanitizedId = "-" + rawId.replaceAll("[^a-zA-Z0-9 ]", "");
        } else {
            sanitizedId = rawId.replaceAll("[^a-zA-Z0-9 ]", "");
        }
        sanitizedId = sanitizedId.trim();
        if (sanitizedId.length() > 30) {
            sanitizedId.substring(0, 30);
        }

        if (sanitizedId.isEmpty() || sanitizedId.equals("-")) {
            throw new IllegalArgumentException("ID contains no valid characters");
        }

        try {
            return Integer.parseInt(sanitizedId);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("ID " + sanitizedId + " is not a valid integer");
        }
    }
}
