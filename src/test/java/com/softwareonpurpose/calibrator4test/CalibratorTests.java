package com.softwareonpurpose.calibrator4test;

import com.softwareonpurpose.calibrator4test.mock.MockCalibrator;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CalibratorTests {
    @Test
    public void testCalibrate() {
        Integer comparator = 6;
        Integer toCalibrate = 6;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        String expected = Calibrator.SUCCESS;
        String actual = calibrator.calibrate();
        Assert.assertEquals(actual, expected, "Failed to pass calibration");
    }

    @Test
    public void testCompileReport_failure() {
        Integer comparator = 99;
        Integer toCalibrate = 1;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        String expected = "CALIBRATION FAILED:";
        String actual = calibrator.calibrate();
        Assert.assertTrue(actual.contains(expected), String.format("Failed: missing '%s' from message '%s'", expected, actual));
    }

    @Test
    public void testReportMissingComparator_expectedNull() {
        Integer comparator = null;
        Integer toCalibrate = 1;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        String expected = "Mock Object exists -- Expected: false  Actual: true";
        String actual = calibrator.calibrate();
        Assert.assertTrue(actual.contains(expected), String.format("Failed: missing '%s' from message '%s'", expected, actual));
    }

    @Test
    public void testReportMissingComparator_actualNull() {
        Integer comparator = 99;
        Integer toCalibrate = null;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        String expected = "Mock Object exists -- Expected: true  Actual: false";
        String actual = calibrator.calibrate();
        Assert.assertTrue(actual.contains(expected), String.format("Failed: missing '%s' from message '%s'", expected, actual));
    }

    @Test
    public void testWithIndentManager() {
        Integer comparator = 6;
        Integer toCalibrate = 6;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        String expected = Calibrator.SUCCESS;
        String actual = calibrator.calibrate();
        Assert.assertEquals(actual, expected, "Failed to pass calibration");
    }

    @Test
    public void testGetVerificationCount() {
        Integer comparator = 6;
        Integer toCalibrate = 6;
        Calibrator calibrator = MockCalibrator.getInstance(comparator, toCalibrate);
        long expected = 2;
        calibrator.calibrate();
        long actual = calibrator.getVerificationCount();
        Assert.assertEquals(actual, expected, "Failed to return accurate count of verifications");
    }
}
