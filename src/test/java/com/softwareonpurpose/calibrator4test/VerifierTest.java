package com.softwareonpurpose.calibrator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.softwareonpurpose.calibrator4test.Verifier.PASS;

@Test
public class VerifierTest {

    @Test
    public void sameBooleanValue() {
        Assert.assertEquals(Verifier.getInstance("Boolean", true, true, IndentManager.construct()).verify(), PASS,
                "Same Boolean values return expected message");
    }

    @Test
    public void nullBooleanBoolean() {
        String failureMessage = "Null 'Expected' Boolean returns Expected = <null>";
        Assert.assertEquals(Verifier.getInstance("Boolean", null, true, IndentManager.construct()).verify(), String
                .format("%s -- Expected: <null>  Actual: %b%n", "Boolean", true), failureMessage);
    }

    @Test
    public void nullActualBoolean() {
        String failureMessage = "Null 'Actual' Boolean returns Actual = <null>";
        Assert.assertEquals(Verifier.getInstance("Boolean", false, null, IndentManager.construct()).verify(),
                String.format("%s -- Expected: %b  Actual: <null>%n", "Boolean", false), failureMessage);
    }

    @Test
    public void booleanDiscrepancy() {
        String failureMessage = "Different Boolean values return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("Boolean", false, true, IndentManager.construct()).verify(),
                String.format("%s -- Expected: %b  Actual: %b%n", "Boolean", false, true), failureMessage);
    }

    @Test
    public void sameIntegerValue() {
        String failureMessage = "Same Integer values return expected message";
        Assert.assertEquals(Verifier.getInstance("Integer", 1, 1, IndentManager.construct()).verify(), PASS,
                failureMessage);
    }

    @Test
    public void nullExpectedInteger() {
        String failureMessage = "Null 'Expected' returns Expected = <null>";
        Assert.assertEquals(Verifier.getInstance("Integer", null, 1, IndentManager.construct()).verify(), String
                .format("%s -- Expected: <null>  Actual: %d%n", "Integer", 1), failureMessage);
    }

    @Test
    public void nullActualInteger() {
        Assert.assertEquals(Verifier.getInstance("Integer", 1, null, IndentManager.construct()).verify(), String
                .format("%s -- Expected: %d  Actual: <null>%n", "Integer", 1), "Null 'Actual' returns Actual = <null>");
    }

    @Test
    public void integerDiscrepancy() {
        String failureMessage = "Different Integer values return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("Integer", 1, 2, IndentManager.construct()).verify(), String
                .format("%s -- Expected: %d  Actual: %d%n", "Integer", 1, 2), failureMessage);
    }

    @Test
    public void sameLongValue() {
        String failureMessage = "Same Long values return expected message";
        Assert.assertEquals(Verifier.getInstance("Long", 1L, 1L, IndentManager.construct()).verify(), PASS,
                failureMessage);
    }

    @Test
    public void nullExpectedLong() {
        String failureMessage = "Null 'Expected' Long returns Expected = <null>";
        Assert.assertEquals(Verifier.getInstance("Long", null, 1L, IndentManager.construct()).verify(), String
                .format("%s -- Expected: <null>  Actual: %d%n", "Long", 1L), failureMessage);
    }

    @Test
    public void nullActualLong() {
        String failureMessage = "Null 'Actual' Long returns Actual = <null>";
        Assert.assertEquals(Verifier.getInstance("Long", 1L, null, IndentManager.construct()).verify(), String
                .format("%s -- Expected: %d  Actual: <null>%n", "Long", 1L), failureMessage);
    }

    @Test
    public void longDiscrepancy() {
        String failureMessage = "Different Long values return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("Long", 1L, 2L, IndentManager.construct()).verify(), String.format
                ("%s -- Expected: %d  Actual: %d%n", "Long", 1L, 2L), failureMessage);
    }

    @Test
    public void sameStringValue() {
        Assert.assertEquals(Verifier.getInstance("String", "test string", "test string", IndentManager.construct())
                .verify(), PASS, "Same String values return expected message");
    }

    @Test
    public void nullExpectedString() {
        String failureMessage = "Null 'Expected' String returns Expected = <null>";
        Assert.assertEquals(Verifier.getInstance("String", null, "test string", IndentManager.construct()).verify()
                , String.format("%s -- Expected: <null>  Actual: %s%n", "String", "test string"), failureMessage);
    }

    @Test
    public void nullActualString() {
        String failureMessage = "Null 'Actual' String returns Actual = <null>";
        Assert.assertEquals(Verifier.getInstance("String", "test string", null, IndentManager.construct()).verify()
                , String.format("%s -- Expected: %s  Actual: <null>%n", "String", "test string"), failureMessage);
    }

    @Test
    public void stringDiscrepancy() {
        Assert.assertEquals(Verifier.getInstance("String", "test string", "string test", IndentManager.construct())
                .verify(), String.format("%s -- Expected: %s  Actual: %s%n", "String", "test string", "string test"),
                "Different String values return different 'Expected' and 'Actual' values");
    }

    @Test
    public void sameStringList() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        List<String> actualStringList = Arrays.asList("string 1", "string 2");
        Assert.assertEquals(Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager
                .construct()).verify(), PASS, "Same String values return expected message");
    }

    @Test
    public void nullExpectedStringList() {
        List<String> actualStringList = Arrays.asList("string 1", "string 2");
        String flatActualStringList = actualStringList.toString();
        Assert.assertEquals(Verifier.getInstance("String", null, actualStringList, IndentManager.construct())
                .verify(), String.format("%s -- Expected: <null>  Actual: %s%n", "String", flatActualStringList),
                "Null 'Expected' String returns Expected = <null>");
    }

    @Test
    public void nullActualStringList() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        Assert.assertEquals(Verifier.getInstance("String List", expectedStringList, null, IndentManager.construct()
        ).verify(), String.format("%s -- Expected: %s  Actual: <null>%n", "String List", flatExpectedStringList),
                "Null 'Actual' String List returns Actual = <null>");
    }

    @Test
    public void stringListDifferentSize() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Collections.singletonList("string 1");
        String flatActualStringList = actualStringList.toString();
        String failureMessage = "Different String Lists return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager
                .construct()).verify(), String.format("%s -- Expected: %s  Actual: %s%n", "String List",
                flatExpectedStringList, flatActualStringList), failureMessage);
    }

    @Test
    public void stringListDifferentValues() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Arrays.asList("string 3", "string 4");
        String flatActualStringList = actualStringList.toString();
        String failureMessage = "Different String Lists return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager
                .construct()).verify(), String.format("%s -- Expected: %s  Actual: %s%n", "String List",
                flatExpectedStringList, flatActualStringList), failureMessage);
    }

    @Test
    public void stringListDifferentOrder() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Arrays.asList("string 2", "string 1");
        String flatActualStringList = actualStringList.toString();
        String failureMessage = "Different String Lists return different 'Expected' and 'Actual' values";
        Assert.assertEquals(Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager
                .construct()).verify(), String.format("%s -- Expected: %s  Actual: %s%n", "String List",
                flatExpectedStringList, flatActualStringList), failureMessage);
    }
}
