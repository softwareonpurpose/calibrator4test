package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class AnotherObjectCalibrator extends Calibrator {

    private static final String description = "Object";
    private final AnObject expected;
    private final AnObject actual;

    private AnotherObjectCalibrator(AnObject expected, AnObject actual) {
        super(description, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    public static AnotherObjectCalibrator getInstance(AnObject expected, AnObject actual) {
        return new AnotherObjectCalibrator(expected, actual);
    }

    @Override
    protected void executeVerifications() {
        verify("Boolean", expected.getBoolean(), actual.getBoolean());
        verify("Integer", expected.getInteger(), actual.getInteger());
        verify("String", expected.getString(), actual.getString());
    }
}
