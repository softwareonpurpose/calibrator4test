package com.softwareonpurpose.validator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

@Test
public class ReconcilerTest {

    @Test
    public void twoNullBooleans() {
        int result = Reconciler.getInstance((Boolean) null, (Boolean) null).reconcile();
        Assert.assertEquals(result, 0, "Two null Booleans returns 0");
    }

    @Test
    public void equalTrueBooleans() {
        int result = Reconciler.getInstance(true, true).reconcile();
        Assert.assertEquals(result, 0, "Two TRUE booleans returns 0");
    }

    @Test
    public void equalFalseBooleans() {
        int result = Reconciler.getInstance(false, false).reconcile();
        Assert.assertEquals(result, 0, "Two FALSE booleans returns 0");
    }

    @Test
    public void missingExpectedBoolean() {
        int result = Reconciler.getInstance(null, true).reconcile();
        Assert.assertEquals(result, 1, "Null 'Expected' boolean returns 1");
    }

    @Test
    public void missingActualBoolean() {
        int result = Reconciler.getInstance(true, null).reconcile();
        Assert.assertEquals(result, 2, "Null 'Actual' boolean returns 2");
    }

    @Test
    public void unequalBooleans() {
        int result = Reconciler.getInstance(true, false).reconcile();
        Assert.assertEquals(result, 3, "Unequal booleans returns 3");
    }

    @Test
    public void equalStrings() {
        int result = Reconciler.getInstance("Actual equals Expected", "Actual equals Expected").reconcile();
        Assert.assertEquals(result, 0, "Two equals Strings returns 0");
    }

    @Test
    public void missingExpectedString() {
        int result = Reconciler.getInstance(null, "Actual").reconcile();
        Assert.assertEquals(result, 1, "Null 'Expected' String returns 1");
    }

    @Test
    public void missingActualString() {
        int result = Reconciler.getInstance("Expected", null).reconcile();
        Assert.assertEquals(result, 2, "Null 'Actual' String returns 2");
    }

    @Test
    public void unequalStrings() {
        int result = Reconciler.getInstance("Expected", "Actual").reconcile();
        Assert.assertEquals(result, 3, "Null 'Actual' String returns 3");
    }

    @Test
    public void equalStringLists() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.getInstance(stringList, stringList).reconcile();
        Assert.assertEquals(result, 0, "Equal lists of String return 0");
    }

    @Test
    public void missingExpectedStringList() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.getInstance(null, stringList).reconcile();
        Assert.assertEquals(result, 1, "Null 'Expected' String list returns 1");
    }

    @Test
    public void missingActualStringList() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("String 1");
        stringList.add("String 2");
        int result = Reconciler.getInstance(stringList, null).reconcile();
        Assert.assertEquals(result, 2, "Null 'Actual' String list returns 2");
    }

    @Test
    public void stringListContentDiffers() {
        List<String> expectedStringList = new ArrayList<String>();
        expectedStringList.add("String 1");
        expectedStringList.add("String 2");
        List<String> actualStringList = new ArrayList<String>();
        actualStringList.add("String 1");
        actualStringList.add("String 3");
        int result = Reconciler.getInstance(expectedStringList, actualStringList).reconcile();
        Assert.assertEquals(result, 3, "Differing content of String lists returns 3");
    }

    @Test
    public void stringListContentOrderDiffers() {
        List<String> expectedStringList = new ArrayList<String>();
        expectedStringList.add("String 1");
        expectedStringList.add("String 2");
        List<String> actualStringList = new ArrayList<String>();
        actualStringList.add("String 2");
        actualStringList.add("String 1");
        int result = Reconciler.getInstance(expectedStringList, actualStringList).reconcile();
        Assert.assertEquals(result, 3, "Differing content of String lists returns 3");
    }

    @Test
    public void equalIntegers() {
        int result = Reconciler.getInstance(1, 1).reconcile();
        Assert.assertEquals(result, 0, "Two equal Integers returns 0");
    }

    @Test
    public void missingExpectedInteger() {
        int result = Reconciler.getInstance(null, 1).reconcile();
        Assert.assertEquals(result, 1, "Null 'Expected' Integer returns 1");
    }

    @Test
    public void missingActualInteger() {
        int result = Reconciler.getInstance(1, null).reconcile();
        Assert.assertEquals(result, 2, "Null 'Actual' Integer returns 2");
    }

    @Test
    public void unequalIntegers() {
        int result = Reconciler.getInstance(1, 2).reconcile();
        Assert.assertEquals(result, 3, "Unequal Integers returns 3");
    }

    @Test
    public void equalLongs() {
        int result = Reconciler.getInstance(1L, 1L).reconcile();
        Assert.assertEquals(result, 0, "Two equal Longs returns 0");
    }

    @Test
    public void missingExpectedLong() {
        int result = Reconciler.getInstance(null, 1L).reconcile();
        Assert.assertEquals(result, 1, "Null 'Expected' Long returns 1");
    }

    @Test
    public void missingActualLong() {
        int result = Reconciler.getInstance(1L, null).reconcile();
        Assert.assertEquals(result, 2, "Null 'Actual' Long returns 2");
    }

    @Test
    public void unequalLongs() {
        int result = Reconciler.getInstance(1L, 2L).reconcile();
        Assert.assertEquals(result, 3, "Unequal Long returns 3");
    }
}
