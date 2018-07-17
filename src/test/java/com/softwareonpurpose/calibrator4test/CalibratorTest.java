package com.softwareonpurpose.calibrator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CalibratorTest {

    @Test
    public void rootOnlyValidator_pass() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void rootOnlyValidator_fail() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(false, 0, "Not String");
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }


    @Test
    public void rootOnlyValidator_knownIssue() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        validator.addKnownIssue("Known Bug Issue");
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_pass() {
        AnObject child = AnObject.getInstance(true, 5, "Child String");
        AnObject expected = AnObject.getInstance(true, 9, "String", child);
        AnObject actual = AnObject.getInstance(true, 9, "String", child);
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void parentChildValidators_rootFail() {
        AnObject child = AnObject.getInstance(true, 5, "Child String");
        AnObject expected = AnObject.getInstance(true, 9, "String", child);
        AnObject actual = AnObject.getInstance(false, 9, "String", child);
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_childFail() {
        AnObject expectedChild = AnObject.getInstance(true, 5, "Child String");
        AnObject actualChild = AnObject.getInstance(false, 0, "No String");
        AnObject expected = AnObject.getInstance(true, 9, "String", expectedChild);
        AnObject actual = AnObject.getInstance(true, 9, "String", actualChild);
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_bothFail() {
        AnObject expectedChild = AnObject.getInstance(true, 5, "Child String");
        AnObject actualChild = AnObject.getInstance(false, 0, "No String");
        AnObject expected = AnObject.getInstance(true, 9, "String", expectedChild);
        AnObject actual = AnObject.getInstance(false, 2, "No String", actualChild);
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void expectedNull() {
        AnObject expected = null;
        AnObject actual = AnObject.getInstance(true, 9, "String");
        //noinspection ConstantConditions
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void actualNull() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = null;
        //noinspection ConstantConditions
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void bothNull() {
        AnObject expected = null;
        AnObject actual = null;
        //noinspection ConstantConditions
        AnObjectCalibrator validator = AnObjectCalibrator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void courseValidator() {
        Calibrator.setStyle(Calibrator.CalibrationLoggingStyle.BDD);
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        //noinspection ConstantConditions
        CourseCalibrator validator = CourseCalibrator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    private void confirmPass(String result) {
        Assert.assertTrue(result.equals(Calibrator.PASS), result);
    }

    private void confirmFail(String result) {
        Assert.assertFalse(result.equals(Calibrator.PASS), result);
    }
}
