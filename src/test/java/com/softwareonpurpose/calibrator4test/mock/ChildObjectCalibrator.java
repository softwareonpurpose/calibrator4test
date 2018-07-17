package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;
import com.softwareonpurpose.calibrator4test.ChildCalibrator;

public class ChildObjectCalibrator extends ChildCalibrator {
    private static final String DESCRIPTION = "Object";
    private final AnObject expected;
    private final AnObject actual;

    private ChildObjectCalibrator(AnObject expected, AnObject actual, Calibrator parent) {
        super(DESCRIPTION, expected, actual, parent);
        this.expected = expected;
        this.actual = actual;
    }

    public static ChildObjectCalibrator getInstance(AnObject expected, AnObject actual, Calibrator parent) {
        return new ChildObjectCalibrator(expected, actual, parent);
    }

    @Override
    protected void executeVerifications() {
        verify("Boolean", expected.getBoolean(), actual.getBoolean());
        verify("Integer", expected.getInteger(), actual.getInteger());
        verify("String", expected.getString(), actual.getString());
    }
}
