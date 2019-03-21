package com.softwareonpurpose.calibrator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class ReconcilerTest {

    @Test
    public void twoNullBooleans() {
        int result = Reconciler.reconcile(null, null);
        Assert.assertEquals(result, 0, "Two null Booleans returns 0");
    }

    @Test
    public void equalTrueBooleans() {
        int result = Reconciler.reconcile(true, true);
        Assert.assertEquals(result, 0, "Two TRUE booleans returns 0");
    }

    @Test
    public void equalFalseBooleans() {
        int result = Reconciler.reconcile(false, false);
        Assert.assertEquals(result, 0, "Two FALSE booleans returns 0");
    }

    @Test
    public void missingExpectedBoolean() {
        int result = Reconciler.reconcile(null, true);
        Assert.assertEquals(result, 1, "Null 'Expected' boolean returns 1");
    }

    @Test
    public void missingActualBoolean() {
        int result = Reconciler.reconcile(true, null);
        Assert.assertEquals(result, 2, "Null 'Actual' boolean returns 2");
    }

    @Test
    public void unequalBooleans() {
        int result = Reconciler.reconcile(true, false);
        Assert.assertEquals(result, 3, "Unequal booleans returns 3");
    }

    @Test
    public void equalStrings() {
        int result = Reconciler.reconcile("Actual equals Expected", "Actual equals Expected");
        Assert.assertEquals(result, 0, "Two equals Strings returns 0");
    }

    @Test
    public void missingExpectedString() {
        int result = Reconciler.reconcile(null, "Actual");
        Assert.assertEquals(result, 1, "Null 'Expected' String returns 1");
    }

    @Test
    public void missingActualString() {
        int result = Reconciler.reconcile("Expected", null);
        Assert.assertEquals(result, 2, "Null 'Actual' String returns 2");
    }

    @Test
    public void unequalStrings() {
        int result = Reconciler.reconcile("Expected", "Actual");
        Assert.assertEquals(result, 3, "Null 'Actual' String returns 3");
    }

    @Test
    public void equalStringLists() {
        List<String> stringList = new ArrayList<>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.reconcile(stringList, stringList);
        Assert.assertEquals(result, 0, "Equal lists of String return 0");
    }

    @Test
    public void missingExpectedStringList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.reconcile(null, stringList);
        Assert.assertEquals(result, 1, "Null 'Expected' String list returns 1");
    }

    @Test
    public void missingActualStringList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.reconcile(stringList, null);
        Assert.assertEquals(result, 2, "Null 'Actual' String list returns 2");
    }

    @Test
    public void stringListContentDiffers() {
        List<String> expectedStringList = new ArrayList<>();
        expectedStringList.add("String 1");
        expectedStringList.add("String 2");
        List<String> actualStringList = new ArrayList<>();
        actualStringList.add("String 1");
        actualStringList.add("String 3");
        int result = Reconciler.reconcile(expectedStringList, actualStringList);
        Assert.assertEquals(result, 3, "Differing content of String lists returns 3");
    }

    @Test
    public void stringListContentOrderDiffers() {
        List<String> expectedStringList = new ArrayList<>();
        expectedStringList.add("String 1");
        expectedStringList.add("String 2");
        List<String> actualStringList = new ArrayList<>();
        actualStringList.add("String 2");
        actualStringList.add("String 1");
        int result = Reconciler.reconcile(expectedStringList, actualStringList);
        Assert.assertEquals(result, 3, "Differing content of String lists returns 3");
    }

    @Test
    public void equalIntegers() {
        int result = Reconciler.reconcile(1, 1);
        Assert.assertEquals(result, 0, "Two equal Integers returns 0");
    }

    @Test
    public void missingExpectedInteger() {
        int result = Reconciler.reconcile(null, 1);
        Assert.assertEquals(result, 1, "Null 'Expected' Integer returns 1");
    }

    @Test
    public void missingActualInteger() {
        int result = Reconciler.reconcile(1, null);
        Assert.assertEquals(result, 2, "Null 'Actual' Integer returns 2");
    }

    @Test
    public void unequalIntegers() {
        int result = Reconciler.reconcile(1, 2);
        Assert.assertEquals(result, 3, "Unequal Integers returns 3");
    }

    @Test
    public void equalLongs() {
        int result = Reconciler.reconcile(1L, 1L);
        Assert.assertEquals(result, 0, "Two equal Longs returns 0");
    }

    @Test
    public void missingExpectedLong() {
        int result = Reconciler.reconcile(null, 1L);
        Assert.assertEquals(result, 1, "Null 'Expected' Long returns 1");
    }

    @Test
    public void missingActualLong() {
        int result = Reconciler.reconcile(1L, null);
        Assert.assertEquals(result, 2, "Null 'Actual' Long returns 2");
    }

    @Test
    public void unequalLongs() {
        int result = Reconciler.reconcile(1L, 2L);
        Assert.assertEquals(result, 3, "Unequal Long returns 3");
    }
}
