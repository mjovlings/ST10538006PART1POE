package com.mycompany.main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the static helper methods in {@link Main}.
 *
 * Notes:
 * - registerUser() and main() are not tested directly since they depend on
 *   interactive Scanner input (System.in). Instead, the validation methods
 *   they call (checkUserName, checkPasswordComplexity, checkCellPhoneNumber)
 *   are tested in isolation.
 * - loginUser() and returnLoginStatus() are tested by directly setting the
 *   package-private static "registered..." fields, since this test class
 *   lives in the same package (com.mycompany.main).
 */
class MainTest {

    @BeforeEach
    void resetState() {
        // Ensure no state leaks between tests
        Main.registeredUsername = null;
        Main.registeredPassword = null;
        Main.registeredCellPhone = null;
    }

    // ---------------------------------------------------------------
    // checkUserName
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("checkUserName")
    class CheckUserNameTests {

        @Test
        @DisplayName("valid: exactly 5 chars including an underscore")
        void validUsername() {
            assertTrue(Main.checkUserName("ab_cd"));
        }

        @Test
        @DisplayName("invalid: no underscore")
        void missingUnderscore() {
            assertFalse(Main.checkUserName("abcde"));
        }

        @Test
        @DisplayName("invalid: too short")
        void tooShort() {
            assertFalse(Main.checkUserName("a_c"));
        }

        @Test
        @DisplayName("invalid: too long")
        void tooLong() {
            assertFalse(Main.checkUserName("ab_cde"));
        }

        @Test
        @DisplayName("invalid: empty string")
        void emptyString() {
            assertFalse(Main.checkUserName(""));
        }

        @Test
        @DisplayName("valid: underscore at start")
        void underscoreAtStart() {
            assertTrue(Main.checkUserName("_abcd"));
        }

        @Test
        @DisplayName("valid: underscore at end")
        void underscoreAtEnd() {
            assertTrue(Main.checkUserName("abcd_"));
        }
    }

    // ---------------------------------------------------------------
    // checkPasswordComplexity
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("checkPasswordComplexity")
    class CheckPasswordComplexityTests {

        @Test
        @DisplayName("valid: meets all complexity rules")
        void validPassword() {
            assertTrue(Main.checkPasswordComplexity("Passw0rd!"));
        }

        @Test
        @DisplayName("invalid: too short even if complex")
        void tooShort() {
            assertFalse(Main.checkPasswordComplexity("P0w!"));
        }

        @Test
        @DisplayName("invalid: missing uppercase")
        void missingUppercase() {
            assertFalse(Main.checkPasswordComplexity("passw0rd!"));
        }

        @Test
        @DisplayName("invalid: missing lowercase")
        void missingLowercase() {
            assertFalse(Main.checkPasswordComplexity("PASSW0RD!"));
        }

        @Test
        @DisplayName("invalid: missing digit")
        void missingDigit() {
            assertFalse(Main.checkPasswordComplexity("Password!"));
        }

        @Test
        @DisplayName("invalid: missing special character")
        void missingSpecialChar() {
            assertFalse(Main.checkPasswordComplexity("Passw0rd"));
        }

        @Test
        @DisplayName("invalid: empty string")
        void emptyString() {
            assertFalse(Main.checkPasswordComplexity(""));
        }

        @Test
        @DisplayName("valid: exactly 8 characters at minimum length")
        void minimumLengthValid() {
            assertTrue(Main.checkPasswordComplexity("Aa1!aaaa"));
        }
    }

    // ---------------------------------------------------------------
    // checkCellPhoneNumber
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("checkCellPhoneNumber")
    class CheckCellPhoneNumberTests {

        @Test
        @DisplayName("valid: +27 followed by 9 digits")
        void validNumber() {
            assertTrue(Main.checkCellPhoneNumber("+27821234567"));
        }

        @Test
        @DisplayName("invalid: missing +27 prefix")
        void missingPrefix() {
            assertFalse(Main.checkCellPhoneNumber("0821234567"));
        }

        @Test
        @DisplayName("invalid: too few digits after +27")
        void tooFewDigits() {
            assertFalse(Main.checkCellPhoneNumber("+2782123456"));
        }

        @Test
        @DisplayName("invalid: too many digits after +27")
        void tooManyDigits() {
            assertFalse(Main.checkCellPhoneNumber("+278212345678"));
        }

        @Test
        @DisplayName("invalid: contains non-digit characters")
        void containsLetters() {
            assertFalse(Main.checkCellPhoneNumber("+2782abc4567"));
        }

        @Test
        @DisplayName("invalid: empty string")
        void emptyString() {
            assertFalse(Main.checkCellPhoneNumber(""));
        }

        @Test
        @DisplayName("invalid: wrong country code")
        void wrongCountryCode() {
            assertFalse(Main.checkCellPhoneNumber("+44821234567"));
        }
    }

    // ---------------------------------------------------------------
    // loginUser
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("loginUser")
    class LoginUserTests {

        @BeforeEach
        void setRegisteredDetails() {
            Main.registeredUsername = "ab_cd";
            Main.registeredPassword = "Passw0rd!";
        }

        @Test
        @DisplayName("valid: correct username and password")
        void correctCredentials() {
            assertTrue(Main.loginUser("ab_cd", "Passw0rd!"));
        }

        @Test
        @DisplayName("invalid: wrong username")
        void wrongUsername() {
            assertFalse(Main.loginUser("wrong", "Passw0rd!"));
        }

        @Test
        @DisplayName("invalid: wrong password")
        void wrongPassword() {
            assertFalse(Main.loginUser("ab_cd", "wrongpass"));
        }

        @Test
        @DisplayName("invalid: both wrong")
        void bothWrong() {
            assertFalse(Main.loginUser("wrong", "wrongpass"));
        }

        @Test
        @DisplayName("invalid: correct username, empty password")
        void emptyPassword() {
            assertFalse(Main.loginUser("ab_cd", ""));
        }
    }

    // ---------------------------------------------------------------
    // returnLoginStatus
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("returnLoginStatus")
    class ReturnLoginStatusTests {

        @Test
        @DisplayName("success message when login succeeded")
        void successMessage() {
            assertEquals(
                    "Welcome, it is great to see you again.",
                    Main.returnLoginStatus(true)
            );
        }

        @Test
        @DisplayName("failure message when login failed")
        void failureMessage() {
            assertEquals(
                    "Username or password incorrect, please try again.",
                    Main.returnLoginStatus(false)
            );
        }
    }
}
