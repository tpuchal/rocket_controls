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

        String retStr = sb.toString().trim()
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .replaceAll("\\s+", " ")
                .trim();

        //this is an arbitrary choice to stop user from putting some harmful input length
        return retStr.length() > 30 ? retStr.substring(0, 30) : retStr;
    }

    static int retreiveIdFromInput(String[] parts) {
        if (parts == null || parts.length < 2) {
            throw new IllegalArgumentException("No ID provided. Expected format: command <id>");
        }

        return sanitizeId(parts, 1);
    }

    static int[] retrieveTwoIdsFromInput(String[] parts) {
        if (parts == null || parts.length < 3) {
            throw new IllegalArgumentException("No IDs provided. Expected format: command <id> <id>");
        }

        int[] retArr = {sanitizeId(parts, 1), sanitizeId(parts, 2)};

        return retArr;
    }

    static void changeRocketStatus(String[] parts) {
        int id = sanitizeId(parts, 1);

    }

    static int sanitizeId(String[] parts, int index) {
        String rawId = parts[index];

        String sanitizedId;
        if (rawId.startsWith("-")) {
            sanitizedId = "-" + rawId.replaceAll("[^a-zA-Z0-9 ]", "");
        } else {
            sanitizedId = rawId.replaceAll("[^a-zA-Z0-9 ]", "");
        }
        sanitizedId = sanitizedId.trim();
        if (sanitizedId.length() > 30) {
            sanitizedId = sanitizedId.substring(0, 30);
        }

        int maxLength = sanitizedId.startsWith("-") ? 11 : 10;
        if (sanitizedId.length() > maxLength) {
            sanitizedId = sanitizedId.substring(0, maxLength);
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
