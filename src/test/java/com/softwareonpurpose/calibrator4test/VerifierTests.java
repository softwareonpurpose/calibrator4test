package com.softwareonpurpose.calibrator4test;

import com.softwareonpurpose.indentmanager.IndentManager;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class VerifierTests {
    @Test
    public void testVerify_expectedAndActualNull() {
        String expected = Verifier.PASS;
        String actual = Verifier.verify("Verification description", null, null, null);
        Assert.assertEquals(actual, expected, "Failed when actual and expected both <null>");
    }

    @Test
    public void testVerify_expectedAndActualEqual() {
        String expected = Verifier.PASS;
        String actual = Verifier.verify("Verification description", "String", "String", null);
        Assert.assertEquals(actual, expected, "Failed when actual is equal to expected");
    }

    @Test
    public void testVerify_expectedAndActualDifferent() {
        String expected = String.format("Verification description -- Expected: String  Actual: 999%n");
        String actual = Verifier.verify("Verification description", "String", 999, null);
        Assert.assertEquals(actual, expected, "Failed to return expected message when actual and expected are different");
    }

    @Test
    public void testLogVerification_includeIndentManager() {
        IndentManager indentManager = IndentManager.getInstance(2);
        indentManager.increment();
        String expected = String.format("Verification description -- Expected: String  Actual: 999%n");
        String actual = Verifier.verify("Verification description", "String", 999, indentManager);
        Assert.assertEquals(actual, expected, "Failed to accept an instance of IndentManager");
    }

    @Test
    public void testVerify_expectedNull() {
        String expected = "Expected: <null>";
        String actual = Verifier.verify("Verification description", null, 999, null);
        Assert.assertTrue(actual.contains(expected), "Failed to include 'Expected: <null>' in message");
    }

    @Test
    public void testVerify_actualNull() {
        String expected = "Actual: <null>";
        String actual = Verifier.verify("Verification description", 999, null, null);
        String message = "Failed to include 'Actual: <null>' in message \"%s\"";
        Assert.assertTrue(actual.contains(expected), String.format(message, actual));
    }
}
