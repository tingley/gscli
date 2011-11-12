package com.globalsight.tools.gscli;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestFileProfilesParser.class,
    TestJobsParser.class,
    TestTaskParser.class,
    TestActivityTypesParser.class,
    TestProjectsParser.class
})
public class AllTests {
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("com.globalsight.tools.AllTests");
    }
}

