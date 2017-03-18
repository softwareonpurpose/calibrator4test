package com.softwareonpurpose.validator4test.mock;

import com.softwareonpurpose.validator4test.Validator;

public class CourseValidator extends Validator{
    private static final String DESCRIPTION = "Course Object";
    private final AnObject expected;
    private final AnObject actual;

    private CourseValidator(AnObject expected, AnObject actual) {
        super(DESCRIPTION, expected, actual);
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    protected void executeVerifications() {
        verify("An Object", expected, actual);
    }

    public static CourseValidator getInstance(AnObject expected, AnObject actual) {
        return new CourseValidator(expected, actual);
    }
}
