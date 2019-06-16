package com.softwareonpurpose.calibrator4test;

import com.softwareonpurpose.calibrator4test.mock.AnObject;
import com.softwareonpurpose.calibrator4test.mock.AnObjectCalibrator;
import com.softwareonpurpose.calibrator4test.mock.AnotherObjectCalibrator;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class AnotherCalibratorTest {

    private static final String INCORRECT_VERIFICATION_COUNT = "Incorrect verification count";
    @Test
    public void verificationCountPostCalibrationMultipleTestClasses() {
        AnObjectCalibrator.resetCount();
        long expected = 6;
        AnObject expectedObject = AnObject.getInstance(true, 7, "string");
        AnObject actualObject = AnObject.getInstance(true, 7, "string");
        AnObjectCalibrator.getInstance(expectedObject, actualObject).calibrate();
        AnotherObjectCalibrator.getInstance(expectedObject, actualObject).calibrate();
        long actual = AnObjectCalibrator.getVerificationCount();
        Assert.assertEquals(actual, expected, INCORRECT_VERIFICATION_COUNT);
    }
}
