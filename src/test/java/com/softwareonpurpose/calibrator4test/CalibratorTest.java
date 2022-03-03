package com.softwareonpurpose.calibrator4test;

import com.softwareonpurpose.calibrator4test.mock.AnObject;
import com.softwareonpurpose.calibrator4test.mock.AnObjectCalibrator;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CalibratorTest {
    private static final String INCORRECT_VERIFICATION_COUNT = "Incorrect verification count";

    @Test
    public void nodeCalibrator_pass() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        Calibrator calibrator = AnObjectCalibrator.getInstance(expected, actual);
        Assert.assertEquals(calibrator.calibrate(), Calibrator.SUCCESS);
    }

    @Test
    public void nodeCalibrator_fail() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(false, 0, "Not String");
        Calibrator calibrator = AnObjectCalibrator.getInstance(expected, actual);
        Assert.assertEquals(calibrator.calibrate(), "CALIBRATION FAILED: \n" +
                "    AnObjectCalibrator: Boolean -- Expected: true  Actual: false\n" +
                "    AnObjectCalibrator: Integer -- Expected: 9  Actual: 0\n" +
                "    AnObjectCalibrator: String -- Expected: String  Actual: Not String\n\n");
    }

    @Test
    public void parentChildCalibrators_pass() {
        AnObject expectedParent = AnObject.getInstance(true, 9, "parent");
        AnObject actualParent = AnObject.getInstance(true, 9, "parent");
        AnObject expectedChild = AnObject.getInstance(true, 9, "child");
        AnObject actualChild = AnObject.getInstance(true, 9, "child");
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(expectedParent, actualParent);
        calibrator.addChildCalibrator(AnObjectCalibrator.getInstance(expectedChild, actualChild));
        Assert.assertEquals(calibrator.calibrate(), Calibrator.SUCCESS);
    }

    @Test
    public void parentChildCalibrators_rootFail() {
        AnObject object_1 = AnObject.getInstance(true, 5, "Child String");
        AnObject object_2 = AnObject.getInstance(false, 9, "String");
        Calibrator parent = AnObjectCalibrator.getInstance(object_1, object_2);
        parent.addChildCalibrator(AnObjectCalibrator.getInstance(object_1, object_1));
        String expected = "CALIBRATION FAILED: \n" +
                "    AnObjectCalibrator: Boolean -- Expected: true  Actual: false\n" +
                "    AnObjectCalibrator: Integer -- Expected: 5  Actual: 9\n" +
                "    AnObjectCalibrator: String -- Expected: Child String  Actual: String\n" +
                "\n";
        String actual = parent.calibrate();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void parentChildCalibrators_childFail() {
        AnObject object_1 = AnObject.getInstance(true, 5, "Child String");
        AnObject object_2 = AnObject.getInstance(false, 9, "String");
        Calibrator parent = AnObjectCalibrator.getInstance(object_1, object_1);
        parent.addChildCalibrator(AnObjectCalibrator.getInstance(object_1, object_2));
        String expected = "CALIBRATION FAILED: \n" +
                "      AnObjectCalibrator: Boolean -- Expected: true  Actual: false\n" +
                "      AnObjectCalibrator: Integer -- Expected: 5  Actual: 9\n" +
                "      AnObjectCalibrator: String -- Expected: Child String  Actual: String\n" +
                "\n";
        String actual = parent.calibrate();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void parentChildCalibrators_bothFail() {
        AnObject object_1 = AnObject.getInstance(true, 5, "Child String");
        AnObject object_2 = AnObject.getInstance(false, 9, "String");
        Calibrator parent = AnObjectCalibrator.getInstance(object_1, object_2);
        parent.addChildCalibrator(AnObjectCalibrator.getInstance(object_1, object_2));
        String expected = "CALIBRATION FAILED: \n" +
                "    AnObjectCalibrator: Boolean -- Expected: true  Actual: false\n" +
                "    AnObjectCalibrator: Integer -- Expected: 5  Actual: 9\n" +
                "    AnObjectCalibrator: String -- Expected: Child String  Actual: String\n" +
                "      AnObjectCalibrator: Boolean -- Expected: true  Actual: false\n" +
                "      AnObjectCalibrator: Integer -- Expected: 5  Actual: 9\n" +
                "      AnObjectCalibrator: String -- Expected: Child String  Actual: String\n" +
                "\n";
        String actual = parent.calibrate();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void parentGrandchildCalibrators_pass() {
        AnObject expectedParent = AnObject.getInstance(true, 9, "parent");
        AnObject actualParent = AnObject.getInstance(true, 9, "parent");
        AnObject expectedChild = AnObject.getInstance(false, 9, "child");
        AnObject actualChild = AnObject.getInstance(false, 9, "child");
        AnObject expectedGrandchild = AnObject.getInstance(true, 1, "grandchild");
        AnObject actualGrandchild = AnObject.getInstance(true, 1, "grandchild");
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(expectedParent, actualParent);
        AnObjectCalibrator childCalibrator = AnObjectCalibrator.getInstance(expectedChild, actualChild);
        AnObjectCalibrator grandchildCalibrator = AnObjectCalibrator.getInstance(expectedGrandchild, actualGrandchild);
        childCalibrator.addChildCalibrator(grandchildCalibrator);
        calibrator.addChildCalibrator(childCalibrator);
        Assert.assertEquals(calibrator.calibrate(), Calibrator.SUCCESS);
    }

    @Test
    public void expectedNull() {
        AnObject nullObject = null;
        AnObject anObject = AnObject.getInstance(true, 9, "String");
        //noinspection ConstantConditions
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(nullObject, anObject);
        String actual = calibrator.calibrate();
        String expected = "CALIBRATION FAILED: \n" +
                "    AnObjectCalibrator: Object exists -- Expected: false  Actual: true\n" +
                "\n";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void actualNull() {
        AnObject anObject = AnObject.getInstance(true, 9, "String");
        AnObject nullObject = null;
        //noinspection ConstantConditions
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(anObject, nullObject);
        String actual = calibrator.calibrate();
        String expected = "CALIBRATION FAILED: \n" +
                "    AnObjectCalibrator: Object exists -- Expected: true  Actual: false\n" +
                "\n";
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void bothNull() {
        AnObject nullObject = null;
        //noinspection ConstantConditions
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(nullObject, nullObject);
        String actual = calibrator.calibrate();
        String expected = Calibrator.SUCCESS;
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void verificationCountPreCalibration() {
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(AnObject.getInstance(true, 1, "1"), AnObject.getInstance(true, 1, "1"));
        long expected = 0;
        long actual = calibrator.getVerificationCount();
        Assert.assertEquals(actual, expected, INCORRECT_VERIFICATION_COUNT);
    }

    @Test
    public void verificationCountSingleCalibrationPass() {
        AnObject expectedObject = AnObject.getInstance(true, 0, "string");
        AnObject actualObject = AnObject.getInstance(true, 0, "string");
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(expectedObject, actualObject);
        long expected = 3;
        calibrator.calibrate();
        long actual = calibrator.getVerificationCount();
        Assert.assertEquals(actual, expected, INCORRECT_VERIFICATION_COUNT);
    }

    @Test
    public void verificationCountSingleVerificationFailure() {
        AnObject expectedObject = AnObject.getInstance(false, 0, "string");
        AnObject actualObject = AnObject.getInstance(true, 0, "string");
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(expectedObject, actualObject);
        long expected = 3;
        calibrator.calibrate();
        long actual = calibrator.getVerificationCount();
        Assert.assertEquals(actual, expected, INCORRECT_VERIFICATION_COUNT);
    }

    @Test
    public void verificationCountParentGrandchild() {
        AnObject expectedParent = AnObject.getInstance(true, 9, "parent");
        AnObject actualParent = AnObject.getInstance(true, 9, "parent");
        AnObject expectedChild = AnObject.getInstance(false, 9, "child");
        AnObject actualChild = AnObject.getInstance(false, 9, "child");
        AnObject expectedGrandchild = AnObject.getInstance(true, 1, "grandchild");
        AnObject actualGrandchild = AnObject.getInstance(true, 1, "grandchild");
        AnObjectCalibrator calibrator = AnObjectCalibrator.getInstance(expectedParent, actualParent);
        AnObjectCalibrator childCalibrator = AnObjectCalibrator.getInstance(expectedChild, actualChild);
        AnObjectCalibrator grandchildCalibrator = AnObjectCalibrator.getInstance(expectedGrandchild, actualGrandchild);
        childCalibrator.addChildCalibrator(grandchildCalibrator);
        calibrator.addChildCalibrator(childCalibrator);
        calibrator.calibrate();
        Assert.assertEquals(calibrator.getVerificationCount(), 9);
    }
}
