package com.mycompany.main;

import java.util.Scanner;

public class Main {

    // Creates Scanner for user input
    static Scanner input = new Scanner(System.in);

    // Stores the user's entered details
    static String username;
    static String password;
    static String cellPhone;

    // Stores the successfully registered details
    static String registeredUsername;
    static String registeredPassword;
    static String registeredCellPhone;


    // Checks if the username is valid
    public static boolean checkUserName(String username) {

        // Username must have exactly 5 characters
        // and must contain an underscore
        if (username.length() == 5 && username.contains("_")) {
            return true;
        } else {
            return false;
        }
    }


    // Checks if the password is valid
    public static boolean checkPasswordComplexity(String password) {

        // Password must have at least 8 characters
        // and contain uppercase, lowercase, number and special character
        if (password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*")) {

            return true;

        } else {
            return false;
        }
    }


    // Checks if the cellphone number is valid
    public static boolean checkCellPhoneNumber(String number) {

        // Number must start with +27
        // followed by exactly 9 digits
        if (number.matches("^\\+27[0-9]{9}$")) {
            return true;
        } else {
            return false;
        }
    }


    // Handles the registration process
    public static void registerUser() {

        // Infinite loop for username
        while (true) {

            // Ask for username
            System.out.print("Enter your username: ");
            username = input.nextLine();

            // Check username
            boolean usernameCorrect = checkUserName(username);

            // Check if username is correct
            if (usernameCorrect) {
                break;
            } else {
                System.out.println("Username is incorrectly formatted. Please try again.");
            }
        }


        // Infinite loop for password
        while (true) {

            // Ask for password
            System.out.print("Enter your password: ");
            password = input.nextLine();

            // Check password
            boolean passwordCorrect = checkPasswordComplexity(password);

            // Check if password is correct
            if (passwordCorrect) {
                break;
            } else {
                System.out.println("Password is incorrectly formatted. Please try again.");
            }
        }


        // Infinite loop for cellphone number
        while (true) {

            // Ask for cellphone number
            System.out.print("Enter your cellphone number (+27): ");
            cellPhone = input.nextLine();

            // Check cellphone number
            boolean phoneCorrect = checkCellPhoneNumber(cellPhone);

            // Check if cellphone number is correct
            if (phoneCorrect) {
                break;
            } else {
                System.out.println("Cellphone number is incorrectly formatted. Please try again.");
            }
        }


        // Save the valid username
        registeredUsername = username;

        // Save the valid password
        registeredPassword = password;

        // Save the valid cellphone number
        registeredCellPhone = cellPhone;

        // Display success message
        System.out.println("User registered successfully.");
    }


    // Checks the login details
    public static boolean loginUser(String username, String password) {

        // Compare entered details with registered details
        if (username.equals(registeredUsername)
                && password.equals(registeredPassword)) {

            return true;

        } else {
            return false;
        }
    }


    // Returns the login status message
    public static String returnLoginStatus(boolean loginSuccessful) {

        // Check if login was successful
        if (loginSuccessful) {

            return "Welcome, it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }


    // Main method
    public static void main(String[] args) {

        // Call registration method
        registerUser();


        // Ask user to login
        System.out.print("Enter your username to login: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password to login: ");
        String loginPassword = input.nextLine();


        // Call login method
        boolean loginSuccessful = loginUser(loginUsername, loginPassword);

        // Call login status method
        System.out.println(returnLoginStatus(loginSuccessful));
    }
}