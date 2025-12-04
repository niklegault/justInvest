package ca.carleton.niklegault.justInvest;

import ca.carleton.niklegault.justInvest.problem1.AccessControl;
import ca.carleton.niklegault.justInvest.problem1.Actions;
import ca.carleton.niklegault.justInvest.problem1.Roles;
import ca.carleton.niklegault.justInvest.problem1.User;
import ca.carleton.niklegault.justInvest.problem2.PasswordHashing;

/**
 * Currently just a placeholder, will be the main method for the JustInvest system.
 * @author Nik Legault 101229919
 */
public class Application {
    public static void main(String[] args) {
        User client = new User(Roles.CLIENT, "test");
        User planner = new User(Roles.FINANCIAL_PLANNER, "test");

        AccessControl accessControl = new AccessControl();
        PasswordHashing hasher = new PasswordHashing();
        hasher.storePassword("pass", client);

        System.out.println("Client Accessing MONEY MARKET: " + accessControl.hasAccess(client, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        System.out.println("Planner Accessing MONEY MARKET: " + accessControl.hasAccess(planner, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));

    }
}
