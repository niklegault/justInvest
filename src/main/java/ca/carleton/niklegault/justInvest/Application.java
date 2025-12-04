package ca.carleton.niklegault.justInvest;

import ca.carleton.niklegault.justInvest.problem1.AccessControl;
import ca.carleton.niklegault.justInvest.problem1.Actions;
import ca.carleton.niklegault.justInvest.problem1.Roles;
import ca.carleton.niklegault.justInvest.problem1.User;
import ca.carleton.niklegault.justInvest.problem2.PasswordHashing;

import java.util.Scanner;

/**
 * Currently just a placeholder, will be the main method for the JustInvest system.
 * @author Nik Legault 101229919
 */
public class Application {
    private static boolean running;
    private static boolean loggedIn;
    private static User user;
    private static AccessControl accessControl;

    public static void main(String[] args) {
        running = true;
        loggedIn = false;
        accessControl = new AccessControl();
        user = null;
        while(running) {
            printOptions();
            takeInput();
        }

    }

    private static void printOptions() {
        System.out.println("justInvest System");
        System.out.println("----------------------------------------");
        System.out.println("Operations available on the system:");
        System.out.println("1. View account balance");
        System.out.println("2. View investment portfolio");
        System.out.println("3. Modify investment portfolio");
        System.out.println("4. View Financial Advisor contact info");
        System.out.println("5. View Financial Planner contact info");
        System.out.println("6. View money market instruments");
        System.out.println("7. View private consumer instruments\n");
        if(!loggedIn) {
            System.out.println("(S)ign up");
            System.out.println("(L)og in");
        }
        System.out.println("(E)xit\n");
        System.out.print("> ");
    }

    private static void takeInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        switch (input) {
            case "1":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_ACCOUNT_BALANCE)) {
                    System.out.println("Successfully viewed account balance\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "2":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_INVESTMENT_PORTFOLIO)) {
                    System.out.println("Successfully viewed investment portfolio\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "3":
                if(loggedIn && accessControl.hasAccess(user, Actions.MODIFY_INVESTMENT_PORTFOLIO)) {
                    System.out.println("Successfully modified investment portfolio\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "4":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_FINANCIAL_ADVISOR_INFO)) {
                    System.out.println("Successfully viewed Financial Advisor Info\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "5":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_FINANCIAL_PLANNER_INFO)) {
                    System.out.println("Successfully viewed Financial Planner Info\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "6":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_MONEY_MARKET_INSTRUMENTS)) {
                    System.out.println("Successfully viewed Money Market Instruments\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "7":
                if(loggedIn && accessControl.hasAccess(user, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS)) {
                    System.out.println("Successfully viewed Private Consumer Instruments\n");
                } else if (!loggedIn) {
                    System.out.println("Access denied, please log in to access operations\n");
                } else {
                    System.out.println("Access denied\n");
                }
                break;
            case "S":
            case "s":
                break;
            case "L":
            case "l":
                // @TODO Problem 4, log in users
                break;
            case "E":
            case "e":
                System.out.println("Exiting system...\n");
                running = false;
                break;
            default:
                System.out.println("Command not recognized, please enter a valid command\n");
        }
    }
}
