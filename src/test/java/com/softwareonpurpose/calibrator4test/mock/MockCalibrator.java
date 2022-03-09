package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class MockCalibrator extends Calibrator {
    private static final String DESCRIPTION = "Mock Object";
    private final Object expected;
    private final Object actual;

    private MockCalibrator(Object expected, Object actual) {
        super(DESCRIPTION, expected, actual);
        this.expected = expected;
        this.actual = actual;
        String expectedString = expected == null ? "" : expected.toString();
        String actualString = actual == null ? "" : actual.toString();
        addChildCalibrator(MockChildCalibrator.getInstance(expectedString, actualString));
    }

    /**
     * Creation method, instantiates new Mock Calibrator.
     *
     * @param expected Object expected comparator
     * @param actual   Object actual compared
     */
    public static MockCalibrator getInstance(Integer expected, Integer actual) {
        return new MockCalibrator(expected, actual);
    }

    @Override
    protected void executeVerifications() {
        verify("Object verification", expected, actual);
    }
}
