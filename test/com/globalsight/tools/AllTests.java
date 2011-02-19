package com.globalsight.tools;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestFileProfilesParser.class,
    TestJobsParser.class
})
public class AllTests {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("com.google.tools.AllTests");
    }
}

