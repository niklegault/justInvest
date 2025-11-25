package ca.carleton.niklegault.justInvest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;


class AccessControlTest {

    @Test
    public void testClientAccess() {
        AccessControl accessControl = new AccessControl();
        User client = new User(Roles.CLIENT);

        // Test client access
        Assertions.assertTrue(accessControl.hasAccess(client, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertTrue(accessControl.hasAccess(client, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertTrue(accessControl.hasAccess(client, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertFalse(accessControl.hasAccess(client, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertFalse(accessControl.hasAccess(client, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(client, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(client, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }

    @Test
    public void testPremiumClientAccess() {
        AccessControl accessControl = new AccessControl();
        User premiumClient = new User(Roles.PREMIUM_CLIENT);

        // Test premiumClient access
        Assertions.assertTrue(accessControl.hasAccess(premiumClient, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertTrue(accessControl.hasAccess(premiumClient, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertTrue(accessControl.hasAccess(premiumClient, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertTrue(accessControl.hasAccess(premiumClient, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertFalse(accessControl.hasAccess(premiumClient, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(premiumClient, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertTrue(accessControl.hasAccess(premiumClient, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }

    @Test
    public void testTellerAccessDuringBusinessHours() {
        Instant fixedTime = Instant.parse("2023-11-14T10:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        AccessControl accessControl = new AccessControl(fixedClock);

        User teller = new User(Roles.TELLER);

        // Test teller access
        Assertions.assertTrue(accessControl.hasAccess(teller, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertTrue(accessControl.hasAccess(teller, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }

    @Test
    public void testTellerAccessAfterBusinessHours() {
        Instant fixedTime = Instant.parse("2023-11-14T20:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        AccessControl accessControl = new AccessControl(fixedClock);

        User teller = new User(Roles.TELLER);

        // Test teller access
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertFalse(accessControl.hasAccess(teller, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }

    @Test
    public void testAdvisorAccess() {
        AccessControl accessControl = new AccessControl();
        User advisor = new User(Roles.FINANCIAL_ADVISOR);

        // Test advisor access
        Assertions.assertTrue(accessControl.hasAccess(advisor, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertTrue(accessControl.hasAccess(advisor, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertFalse(accessControl.hasAccess(advisor, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertFalse(accessControl.hasAccess(advisor, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertFalse(accessControl.hasAccess(advisor, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertTrue(accessControl.hasAccess(advisor, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertTrue(accessControl.hasAccess(advisor, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }

    @Test
    public void testPlannerAccess() {
        AccessControl accessControl = new AccessControl();
        User planner = new User(Roles.FINANCIAL_PLANNER);

        // Test planner access
        Assertions.assertTrue(accessControl.hasAccess(planner, Actions.VIEW_ACCOUNT_BALANCE));
        Assertions.assertTrue(accessControl.hasAccess(planner, Actions.VIEW_INVESTMENT_PORTFOLIO));
        Assertions.assertFalse(accessControl.hasAccess(planner, Actions.VIEW_FINANCIAL_ADVISOR_INFO));
        Assertions.assertFalse(accessControl.hasAccess(planner, Actions.VIEW_FINANCIAL_PLANNER_INFO));
        Assertions.assertTrue(accessControl.hasAccess(planner, Actions.VIEW_MONEY_MARKET_INSTRUMENTS));
        Assertions.assertTrue(accessControl.hasAccess(planner, Actions.VIEW_PRIVATE_CONSUMER_INSTRUMENTS));
        Assertions.assertTrue(accessControl.hasAccess(planner, Actions.MODIFY_INVESTMENT_PORTFOLIO));
    }
}