package com.craigstockton.validator4test.mock;

import com.craigstockton.validator4test.Validator;

public class AnObjectValidator extends Validator {
    private static final String description = "Object";
    private final AnObject expected;
    private final AnObject actual;

    private AnObjectValidator(AnObject expected, AnObject actual) {
        super(description);
        this.expected = expected;
        this.actual = actual;
        if (expectedExists() && actualExists()) {
            addChildValidator(new AnObjectValidator(expected.getObject(), actual.getObject(), this));
        }
    }

    public AnObjectValidator(AnObject expected, AnObject actual, Validator parentValidator) {
        super(description, parentValidator);
        this.expected = expected;
        this.actual = actual;
    }

    public static AnObjectValidator getInstance(AnObject expected, AnObject actual) {
        return new AnObjectValidator(expected, actual);
    }


    @Override
    protected void executeVerifications() {
        verify("Boolean", expected.getBoolean(), actual.getBoolean());
        verify("Integer", expected.getInteger(), actual.getInteger());
        verify("String", expected.getString(), actual.getString());
    }

    @Override
    protected boolean actualExists() {
        return actual != null;
    }

    @Override
    protected boolean expectedExists() {
        return expected != null;
    }
}
