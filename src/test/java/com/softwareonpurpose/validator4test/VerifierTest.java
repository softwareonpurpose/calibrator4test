package com.softwareonpurpose.validator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Test
public class VerifierTest {

    @Test
    public void sameBooleanValue() {
        Assert.assertEquals(Verifier.getInstance("Boolean", true, true, IndentManager.getInstance()).verify(), "",
                "Same Boolean values return expected message");
    }

    @Test
    public void nullBooleanBoolean() {
        Assert.assertEquals(Verifier.getInstance("Boolean", null, true, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: <null>  Actual: %b%n", "Boolean", true),
                "Null 'Expected' Boolean returns Expected = <null>");
    }

    @Test
    public void nullActualBoolean() {
        Assert.assertEquals(Verifier.getInstance("Boolean", false, null, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %b  Actual: <null>%n", "Boolean", false),
                "Null 'Actual' Boolean returns Actual = <null>");
    }

    @Test
    public void booleanDiscrepancy() {
        Assert.assertEquals(Verifier.getInstance("Boolean", false, true, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %b  Actual: %b%n", "Boolean", false, true),
                "Different Boolean values return different 'Expected' and 'Actual' values");
    }

    @Test
    public void sameIntegerValue() {
        Assert.assertEquals(Verifier.getInstance("Integer", 1, 1, IndentManager.getInstance()).verify(), "",
                "Same Integer values return expected message");
    }

    @Test
    public void nullExpectedInteger() {
        Assert.assertEquals(Verifier.getInstance("Integer", null, 1, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: <null>  Actual: %d%n", "Integer", 1),
                "Null 'Expected' returns Expected = <null>");
    }

    @Test
    public void nullActualInteger() {
        Assert.assertEquals(Verifier.getInstance("Integer", 1, null, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %d  Actual: <null>%n", "Integer", 1),
                "Null 'Actual' returns Actual = <null>");
    }

    @Test
    public void integerDiscrepancy() {
        Assert.assertEquals(Verifier.getInstance("Integer", 1, 2, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %d  Actual: %d%n", "Integer", 1, 2),
                "Different Integer values return different 'Expected' and 'Actual' values");
    }

    @Test
    public void sameLongValue() {
        Assert.assertEquals(Verifier.getInstance("Long", 1L, 1L, IndentManager.getInstance()).verify(), "",
                "Same Long values return expected message");
    }

    @Test
    public void nullExpectedLong() {
        Assert.assertEquals(Verifier.getInstance("Long", null, 1L, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: <null>  Actual: %d%n", "Long", 1L),
                "Null 'Expected' Long returns Expected = <null>");
    }

    @Test
    public void nullActualLong() {
        Assert.assertEquals(Verifier.getInstance("Long", 1L, null, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %d  Actual: <null>%n", "Long", 1L),
                "Null 'Actual' Long returns Actual = <null>");
    }

    @Test
    public void longDiscrepancy() {
        Assert.assertEquals(Verifier.getInstance("Long", 1L, 2L, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %d  Actual: %d%n", "Long", 1L, 2L),
                "Different Long values return different 'Expected' and 'Actual' values");
    }

    @Test
    public void sameStringValue() {
        Assert.assertEquals(
                Verifier.getInstance("String", "test string", "test string", IndentManager.getInstance()).verify(), "",
                "Same String values return expected message");
    }

    @Test
    public void nullExpectedString() {
        Assert.assertEquals(Verifier.getInstance("String", null, "test string", IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: <null>  Actual: %s%n", "String", "test string"),
                "Null 'Expected' String returns Expected = <null>");
    }

    @Test
    public void nullActualString() {
        Assert.assertEquals(Verifier.getInstance("String", "test string", null, IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %s  Actual: <null>%n", "String", "test string"),
                "Null 'Actual' String returns Actual = <null>");
    }

    @Test
    public void stringDiscrepancy() {
        Assert.assertEquals(
                Verifier.getInstance("String", "test string", "string test", IndentManager.getInstance()).verify(),
                String.format("%s -- Expected: %s  Actual: %s%n", "String", "test string", "string test"),
                "Different String values return different 'Expected' and 'Actual' values");
    }

    @Test
    public void sameStringList() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        List<String> actualStringList = Arrays.asList("string 1", "string 2");
        Assert.assertEquals(
                Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager.getInstance())
                        .verify(), "", "Same String values return expected message");
    }

    @Test
    public void nullExpectedStringList() {
        List<String> actualStringList = Arrays.asList("string 1", "string 2");
        String flatActualStringList = actualStringList.toString();
        Assert.assertEquals(
                Verifier.getInstance("String", null, actualStringList, IndentManager.getInstance())
                        .verify(), String.format("%s -- Expected: <null>  Actual: %s%n", "String", flatActualStringList),
                "Null 'Expected' String returns Expected = <null>");
    }

    @Test
    public void nullActualStringList() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        Assert.assertEquals(
                Verifier.getInstance("String List", expectedStringList, null, IndentManager.getInstance())
                        .verify(),
                String.format("%s -- Expected: %s  Actual: <null>%n", "String List", flatExpectedStringList),
                "Null 'Actual' String List returns Actual = <null>");
    }

    @Test
    public void stringListDifferentSize() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Collections.singletonList("string 1");
        String flatActualStringList = actualStringList.toString();
        Assert.assertEquals(
                Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager.getInstance())
                        .verify(),
                String.format("%s -- Expected: %s  Actual: %s%n", "String List", flatExpectedStringList,
                        flatActualStringList), "Different String Lists return different 'Expected' and 'Actual' values");
    }

    @Test
    public void stringListDifferentValues() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Arrays.asList("string 3", "string 4");
        String flatActualStringList = actualStringList.toString();
        Assert.assertEquals(
                Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager.getInstance())
                        .verify(),
                String.format("%s -- Expected: %s  Actual: %s%n", "String List", flatExpectedStringList,
                        flatActualStringList), "Different String Lists return different 'Expected' and 'Actual' values");
    }

    @Test
    public void stringListDifferentOrder() {
        List<String> expectedStringList = Arrays.asList("string 1", "string 2");
        String flatExpectedStringList = expectedStringList.toString();
        List<String> actualStringList = Arrays.asList("string 2", "string 1");
        String flatActualStringList = actualStringList.toString();
        Assert.assertEquals(
                Verifier.getInstance("String List", expectedStringList, actualStringList, IndentManager.getInstance())
                        .verify(),
                String.format("%s -- Expected: %s  Actual: %s%n", "String List", flatExpectedStringList,
                        flatActualStringList), "Different String Lists return different 'Expected' and 'Actual' values");
    }
}
