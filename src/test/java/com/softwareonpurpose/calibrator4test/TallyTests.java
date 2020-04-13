package com.softwareonpurpose.calibrator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class TallyTests {
    @Test
    public void testGetTally() {
        long expected = 0;
        Tally tally = Tally.getInstance();
        long actual = tally.getTally();
        Assert.assertEquals(actual, expected, "Failed to return expected value");
    }

    @Test
    public void testReset() {
        long expected = 0;
        Tally tally = Tally.getInstance();
        tally.increment();
        tally.increment();
        tally.reset();
        long actual = tally.getTally();
        Assert.assertEquals(actual, expected, "Failed to reset tally to zero");
    }

    @Test
    public void testIncrement() {
        long expected = 2;
        Tally tally = Tally.getInstance();
        tally.increment();
        tally.increment();
        long actual = tally.getTally();
        Assert.assertEquals(actual, expected, "Failed to increment tally as expected");
    }
}
