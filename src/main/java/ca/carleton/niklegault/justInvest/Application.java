package ca.carleton.niklegault.justInvest;

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
