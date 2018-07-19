package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class AnObjectCalibrator extends Calibrator {

    private static final String description = "Object";
    private final AnObject expected;
    private final AnObject actual;

    private AnObjectCalibrator(AnObject expected, AnObject actual) {
        super(description, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    public static AnObjectCalibrator getInstance(AnObject expected, AnObject actual) {
        return new AnObjectCalibrator(expected, actual);
    }

    @Override
    protected void executeVerifications() {
        verify("Boolean", expected.getBoolean(), actual.getBoolean());
        verify("Integer", expected.getInteger(), actual.getInteger());
        verify("String", expected.getString(), actual.getString());
    }
}
