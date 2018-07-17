package com.softwareonpurpose.calibrator4test;

public abstract class ChildCalibrator extends Calibrator{
    protected ChildCalibrator(String description, Object expected, Object actual, Calibrator parent) {
        super(description, expected, actual, parent);
    }
}
