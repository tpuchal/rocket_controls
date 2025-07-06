package org.tpuchal;

import junit.framework.TestCase;
import org.tpuchal.utils.UserInputUtils;

public class UserInputUtilsTest extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testJoinAndSanitizeArguments_ValidArguments() {
        String[] parts = {"command", "hello", "world"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should join arguments with spaces", "hello world", result);
    }

    public void testJoinAndSanitizeArguments_SingleArgument() {
        String[] parts = {"command", "test"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should handle single argument", "test", result);
    }

    public void testJoinAndSanitizeArguments_ArgumentsWithSpecialCharacters() {
        String[] parts = {"command", "hello!", "@world#"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should sanitize special characters", "hello world", result);
    }

    public void testJoinAndSanitizeArguments_ArgumentsWithNumbers() {
        String[] parts = {"command", "test123", "456test"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should preserve alphanumeric characters", "test123 456test", result);
    }

    public void testJoinAndSanitizeArguments_ArgumentsWithSpaces() {
        String[] parts = {"command", "hello world", "test"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should handle spaces in arguments", "hello world test", result);
    }

    public void testJoinAndSanitizeArguments_LongInput() {
        String[] parts = {"command", "this", "is", "a", "very", "long", "input", "that", "exceeds", "thirty", "characters"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertTrue("Should limit to 30 characters", result.length() <= 30);
        assertEquals("Should truncate correctly", "this is a very long input that", result);
    }

    public void testJoinAndSanitizeArguments_ExactlyThirtyCharacters() {
        String[] parts = {"command", "twelve345678901234567890123456"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should handle exactly 30 characters", 30, result.length());
        assertEquals("Should return full string", "twelve345678901234567890123456", result);
    }

    public void testJoinAndSanitizeArguments_OnlySpecialCharacters() {
        String[] parts = {"command", "!@#$", "%^&*()"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should return empty string for only special characters", "", result);
    }

    public void testJoinAndSanitizeArguments_WhitespaceHandling() {
        String[] parts = {"command", "  hello  ", "  world  "};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should trim whitespace properly", "hello world", result);
    }

    public void testJoinAndSanitizeArguments_NullArray() {
        try {
            UserInputUtils.joinAndSanitizeArguments(null);
            fail("Should throw IllegalArgumentException for null array");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for null",
                    "There must be at least 1 argument", e.getMessage());
        }
    }

    public void testJoinAndSanitizeArguments_EmptyArray() {
        String[] parts = {};
        try {
            UserInputUtils.joinAndSanitizeArguments(parts);
            fail("Should throw IllegalArgumentException for empty array");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for empty array",
                    "There must be at least 1 argument", e.getMessage());
        }
    }

    public void testJoinAndSanitizeArguments_OnlyCommand() {
        String[] parts = {"command"};
        try {
            UserInputUtils.joinAndSanitizeArguments(parts);
            fail("Should throw IllegalArgumentException for only command");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for only command",
                    "There must be at least 1 argument", e.getMessage());
        }
    }


    public void testRetreiveIdFromInput_ValidPositiveId() {
        String[] parts = {"command", "123"};
        int result = UserInputUtils.retreiveIdFromInput(parts);
        assertEquals("Should parse valid positive ID", 123, result);
    }

    public void testRetreiveIdFromInput_ValidNegativeId() {
        String[] parts = {"command", "-456"};
        int result = UserInputUtils.retreiveIdFromInput(parts);
        assertEquals("Should parse valid negative ID", -456, result);
    }

    public void testRetreiveIdFromInput_IdWithSpecialCharacters() {
        String[] parts = {"command", "12#3$4"};
        int result = UserInputUtils.retreiveIdFromInput(parts);
        assertEquals("Should sanitize and parse ID", 1234, result);
    }

    public void testRetreiveIdFromInput_NegativeIdWithSpecialCharacters() {
        String[] parts = {"command", "-12#3$"};
        int result = UserInputUtils.retreiveIdFromInput(parts);
        assertEquals("Should sanitize negative ID", -123, result);
    }

    public void testRetreiveIdFromInput_NullArray() {
        try {
            UserInputUtils.retreiveIdFromInput(null);
            fail("Should throw IllegalArgumentException for null array");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for null",
                    "No ID provided. Expected format: command <id>", e.getMessage());
        }
    }

    public void testRetreiveIdFromInput_NoId() {
        String[] parts = {"command"};
        try {
            UserInputUtils.retreiveIdFromInput(parts);
            fail("Should throw IllegalArgumentException for missing ID");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for missing ID",
                    "No ID provided. Expected format: command <id>", e.getMessage());
        }
    }

    public void testRetrieveTwoIdsFromInput_ValidIds() {
        String[] parts = {"command", "123", "456"};
        int[] result = UserInputUtils.retrieveTwoIdsFromInput(parts);
        assertEquals("Should return array of length 2", 2, result.length);
        assertEquals("First ID should be correct", 123, result[0]);
        assertEquals("Second ID should be correct", 456, result[1]);
    }

    public void testRetrieveTwoIdsFromInput_NegativeIds() {
        String[] parts = {"command", "-123", "-456"};
        int[] result = UserInputUtils.retrieveTwoIdsFromInput(parts);
        assertEquals("Should handle negative IDs", 2, result.length);
        assertEquals("First negative ID should be correct", -123, result[0]);
        assertEquals("Second negative ID should be correct", -456, result[1]);
    }

    public void testRetrieveTwoIdsFromInput_MixedIds() {
        String[] parts = {"command", "123", "-456"};
        int[] result = UserInputUtils.retrieveTwoIdsFromInput(parts);
        assertEquals("Should handle mixed positive/negative IDs", 2, result.length);
        assertEquals("Positive ID should be correct", 123, result[0]);
        assertEquals("Negative ID should be correct", -456, result[1]);
    }

    public void testRetrieveTwoIdsFromInput_IdsWithSpecialCharacters() {
        String[] parts = {"command", "12#3", "45$6"};
        int[] result = UserInputUtils.retrieveTwoIdsFromInput(parts);
        assertEquals("Should sanitize both IDs", 2, result.length);
        assertEquals("First sanitized ID should be correct", 123, result[0]);
        assertEquals("Second sanitized ID should be correct", 456, result[1]);
    }

    public void testRetrieveTwoIdsFromInput_NullArray() {
        try {
            UserInputUtils.retrieveTwoIdsFromInput(null);
            fail("Should throw IllegalArgumentException for null array");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for null",
                    "No IDs provided. Expected format: command <id> <id>", e.getMessage());
        }
    }

    public void testRetrieveTwoIdsFromInput_OnlyOneId() {
        String[] parts = {"command", "123"};
        try {
            UserInputUtils.retrieveTwoIdsFromInput(parts);
            fail("Should throw IllegalArgumentException for only one ID");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for missing second ID",
                    "No IDs provided. Expected format: command <id> <id>", e.getMessage());
        }
    }

    public void testRetrieveTwoIdsFromInput_NoIds() {
        String[] parts = {"command"};
        try {
            UserInputUtils.retrieveTwoIdsFromInput(parts);
            fail("Should throw IllegalArgumentException for no IDs");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for no IDs",
                    "No IDs provided. Expected format: command <id> <id>", e.getMessage());
        }
    }

    public void testSanitizeId_ValidPositiveId() {
        String[] parts = {"command", "123", "other"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should parse positive ID from index 1", 123, result);
    }

    public void testSanitizeId_ValidNegativeId() {
        String[] parts = {"command", "other", "-456"};
        int result = UserInputUtils.sanitizeId(parts, 2);
        assertEquals("Should parse negative ID from index 2", -456, result);
    }

    public void testSanitizeId_IdWithSpecialCharacters() {
        String[] parts = {"command", "12#3$4"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should sanitize special characters", 1234, result);
    }

    public void testSanitizeId_NegativeIdWithSpecialCharacters() {
        String[] parts = {"command", "-12#3$4"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should sanitize negative ID with special characters", -1234, result);
    }

    public void testSanitizeId_OnlySpecialCharacters() {
        String[] parts = {"command", "!@#$%"};
        try {
            UserInputUtils.sanitizeId(parts, 1);
            fail("Should throw IllegalArgumentException for only special characters");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for invalid characters",
                    "ID contains no valid characters", e.getMessage());
        }
    }

    public void testSanitizeId_OnlyMinusSign() {
        String[] parts = {"command", "-"};
        try {
            UserInputUtils.sanitizeId(parts, 1);
            fail("Should throw IllegalArgumentException for only minus sign");
        } catch (IllegalArgumentException e) {
            assertEquals("Should have correct error message for only minus",
                    "ID contains no valid characters", e.getMessage());
        }
    }

    public void testSanitizeId_NonNumericCharacters() {
        String[] parts = {"command", "abc123"};
        try {
            UserInputUtils.sanitizeId(parts, 1);
            fail("Should throw NumberFormatException for non-numeric characters");
        } catch (NumberFormatException e) {
            assertEquals("Should have correct error message for non-numeric",
                    "ID abc123 is not a valid integer", e.getMessage());
        }
    }

    public void testSanitizeId_WhitespaceHandling() {
        String[] parts = {"command", "  123  "};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should trim whitespace", 123, result);
    }

    public void testSanitizeId_ZeroValue() {
        String[] parts = {"command", "0"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should handle zero value", 0, result);
    }

    public void testSanitizeId_MaxIntegerValue() {
        String[] parts = {"command", "2147483647"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should handle max integer value", 2147483647, result);
    }

    public void testSanitizeId_LongIdTruncated() {
        String[] parts = {"command", "123456789012345678901234567890"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should truncate long positive ID to 10 characters", 1234567890, result);
    }

    public void testSanitizeId_LongIdWithinIntegerRange() {
        String[] parts = {"command", "1234567890"};
        int result = UserInputUtils.sanitizeId(parts, 1);
        assertEquals("Should parse long ID within integer range", 1234567890, result);
    }

    public void testIntegration_JoinAndSanitizeWithSpecialCharacters() {
        String[] parts = {"command", "hello!", "world@", "123#"};
        String result = UserInputUtils.joinAndSanitizeArguments(parts);
        assertEquals("Should join and sanitize multiple arguments", "hello world 123", result);
    }

    public void testIntegration_TwoIdsWithDifferentFormats() {
        String[] parts = {"command", "12#3", "-45$6"};
        int[] result = UserInputUtils.retrieveTwoIdsFromInput(parts);
        assertEquals("Should handle different ID formats", 123, result[0]);
        assertEquals("Should handle negative ID with special chars", -456, result[1]);
    }

    public void testSanitizeId_EmptyStringAfterSanitization() {
        String[] parts = {"command", "   "};
        try {
            UserInputUtils.sanitizeId(parts, 1);
            fail("Should throw IllegalArgumentException for whitespace only");
        } catch (IllegalArgumentException e) {
            assertEquals("Should detect empty string after sanitization",
                    "ID contains no valid characters", e.getMessage());
        }
    }
}