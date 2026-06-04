package com.gildedrose.agingpolicy;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Aging Policies Unit Test Suite")
@SelectClasses({
        StandardItemAgingPolicyTest.class,
        AgedBrieAgingPolicyTest.class,
        ConjuredItemAgingPolicyTest.class,
        BackstagePassesAgingPolicyTest.class,
        NoOperationalItemAgingPolicyTest.class
})
public class AgingPolicyTestSuite {
}
