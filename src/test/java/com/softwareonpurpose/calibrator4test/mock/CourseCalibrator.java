package com.softwareonpurpose.calibrator4test.mock;

import com.softwareonpurpose.calibrator4test.Calibrator;

public class CourseCalibrator extends Calibrator {
    private static final String DESCRIPTION = "Course Object";
    private final AnObject expected;
    private final AnObject actual;

    private CourseCalibrator(AnObject expected, AnObject actual) {
        super(DESCRIPTION, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    protected void executeVerifications() {
        verify("An Object", expected, actual);
    }

    public static CourseCalibrator getInstance(AnObject expected, AnObject actual) {
        return new CourseCalibrator(expected, actual);
    }
}
