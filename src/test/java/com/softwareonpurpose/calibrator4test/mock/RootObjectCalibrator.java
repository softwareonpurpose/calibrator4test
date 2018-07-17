package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.RootCalibrator;

public class RootObjectCalibrator extends RootCalibrator {
    private static final String DESCRIPTION = "Object";
    private final AnObject expected;
    private final AnObject actual;

    private RootObjectCalibrator(AnObject expected, AnObject actual) {
        super(DESCRIPTION, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    public static RootObjectCalibrator getInstance(AnObject expected, AnObject actual) {
        return new RootObjectCalibrator(expected, actual);
    }

    @Override
    protected void executeVerifications() {
        verify("Boolean", expected.getBoolean(), actual.getBoolean());
        verify("Integer", expected.getInteger(), actual.getInteger());
        verify("String", expected.getString(), actual.getString());
    }
}
