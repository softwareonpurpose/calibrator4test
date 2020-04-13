package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class MockChildCalibrator extends Calibrator {
    private static final String DESCRIPTION = "Mock Child";
    private final String expected;
    private final String actual;

    /**
     * @param expected Object expected comparator
     * @param actual   Object actual compared
     */
    private MockChildCalibrator(String expected, String actual) {
        super(DESCRIPTION, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    static MockChildCalibrator getInstance(String expected, String actual) {
        return new MockChildCalibrator(expected, actual);
    }

    @Override
    protected void executeVerifications() {
        verify("Child Object", expected, actual);
    }
}
