package com.softwareonpurpose.calibrator4test.mock.anobject;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class AnObjectCalibrator extends Calibrator {
    private static final String DESCRIPTION = "Object";
    private final AnObject actual;
    private final AnObject expected;

    /**
     * @param expected Object expected comparator
     * @param actual   Object actual compared
     */
    private AnObjectCalibrator(AnObject expected, AnObject actual) {
        super(DESCRIPTION, expected, actual);
        this.actual = actual;
        this.expected = expected;
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
