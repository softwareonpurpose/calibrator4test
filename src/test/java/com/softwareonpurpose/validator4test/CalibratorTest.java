package com.softwareonpurpose.validator4test;

import com.softwareonpurpose.validator4test.mock.AnObject;
import com.softwareonpurpose.validator4test.mock.AnObjectValidator;
import com.softwareonpurpose.validator4test.mock.CourseValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class CalibratorTest {

    @Test
    public void rootOnlyValidator_pass() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void rootOnlyValidator_fail() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(false, 0, "Not String");
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }


    @Test
    public void rootOnlyValidator_knownIssue() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        validator.addKnownIssue("Known Bug Issue");
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_pass() {
        AnObject child = AnObject.getInstance(true, 5, "Child String");
        AnObject expected = AnObject.getInstance(true, 9, "String", child);
        AnObject actual = AnObject.getInstance(true, 9, "String", child);
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void parentChildValidators_rootFail() {
        AnObject child = AnObject.getInstance(true, 5, "Child String");
        AnObject expected = AnObject.getInstance(true, 9, "String", child);
        AnObject actual = AnObject.getInstance(false, 9, "String", child);
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_childFail() {
        AnObject expectedChild = AnObject.getInstance(true, 5, "Child String");
        AnObject actualChild = AnObject.getInstance(false, 0, "No String");
        AnObject expected = AnObject.getInstance(true, 9, "String", expectedChild);
        AnObject actual = AnObject.getInstance(true, 9, "String", actualChild);
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void parentChildValidators_bothFail() {
        AnObject expectedChild = AnObject.getInstance(true, 5, "Child String");
        AnObject actualChild = AnObject.getInstance(false, 0, "No String");
        AnObject expected = AnObject.getInstance(true, 9, "String", expectedChild);
        AnObject actual = AnObject.getInstance(false, 2, "No String", actualChild);
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void expectedNull() {
        AnObject expected = null;
        AnObject actual = AnObject.getInstance(true, 9, "String");
        //noinspection ConstantConditions
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void actualNull() {
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = null;
        //noinspection ConstantConditions
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmFail(validator.validate());
    }

    @Test
    public void bothNull() {
        AnObject expected = null;
        AnObject actual = null;
        //noinspection ConstantConditions
        AnObjectValidator validator = AnObjectValidator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    @Test
    public void courseValidator() {
        Validator.setStyle(Validator.ValidationLoggingStyle.BDD);
        AnObject expected = AnObject.getInstance(true, 9, "String");
        AnObject actual = AnObject.getInstance(true, 9, "String");
        //noinspection ConstantConditions
        CourseValidator validator = CourseValidator.getInstance(expected, actual);
        confirmPass(validator.validate());
    }

    private void confirmPass(String result) {
        Assert.assertTrue(result.equals(Validator.PASS), result);
    }

    private void confirmFail(String result) {
        Assert.assertFalse(result.equals(Validator.PASS), result);
    }
}