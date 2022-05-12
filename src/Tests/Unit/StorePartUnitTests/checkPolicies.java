package Tests.Unit.StorePartUnitTests;

import Store.StorePurchase.Policies.Policy;
import Store.StorePurchase.Policies.PolicyBuilder;

import java.time.LocalDateTime;

public class checkPolicies {
    PolicyBuilder builder = new PolicyBuilder();
    Policy NowHour = builder.newOnHoursOfTheDayPolicy(LocalDateTime.now().minusHours(1),LocalDateTime.now().plusHours(1));
    Policy notNowHour = builder.newOnHoursOfTheDayPolicy(LocalDateTime.now().plusHours(1),LocalDateTime.now().plusHours(2));
    Policy nowDay = builder.newOnDaysOfTheWeekPolicy(LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(1));
    Policy notNowDay = builder.newOnDaysOfTheWeekPolicy(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));
}
