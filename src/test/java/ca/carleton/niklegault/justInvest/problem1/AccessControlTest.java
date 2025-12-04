package ca.carleton.niklegault.justInvest.problem1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;


class AccessControlTest {

    @Test
    public void testClientAccess() {
        AccessControl accessControl = new AccessControl();
        User client = new User(Roles.CLIENT, "test");

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
        User premiumClient = new User(Roles.PREMIUM_CLIENT, "test");

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
    public void testTellerAccessDuringBusinessHoursAndBusinessDay() {
        Instant fixedTime = Instant.parse("2023-11-14T10:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        Calendar fixedCal = Calendar.getInstance();
        fixedCal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        AccessControl accessControl = new AccessControl(fixedClock, fixedCal);

        User teller = new User(Roles.TELLER, "test");

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
    public void testTellerAccessAfterBusinessHoursAndBusinessDay() {
        Instant fixedTime = Instant.parse("2023-11-14T20:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        Calendar fixedCal = Calendar.getInstance();
        fixedCal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
        AccessControl accessControl = new AccessControl(fixedClock, fixedCal);

        User teller = new User(Roles.TELLER, "test");

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
    public void testTellerAccessDuringBusinessHoursNotBusinessDay() {
        Instant fixedTime = Instant.parse("2023-11-14T10:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        Calendar fixedCal = Calendar.getInstance();
        fixedCal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        AccessControl accessControl = new AccessControl(fixedClock, fixedCal);

        User teller = new User(Roles.TELLER, "test");

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
    public void testTellerAccessAfterBusinessHoursNotBusinessDay() {
        Instant fixedTime = Instant.parse("2023-11-14T20:00:00Z");
        Clock fixedClock = Clock.fixed(fixedTime, ZoneId.of("UTC"));
        Calendar fixedCal = Calendar.getInstance();
        fixedCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        AccessControl accessControl = new AccessControl(fixedClock, fixedCal);

        User teller = new User(Roles.TELLER, "test");

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
        User advisor = new User(Roles.FINANCIAL_ADVISOR, "test");

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
        User planner = new User(Roles.FINANCIAL_PLANNER, "test");

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