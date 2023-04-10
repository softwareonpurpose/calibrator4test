package com.softwareonpurpose.calibrator4test.mock.anobject;

public class AnObject {
    private final boolean aBoolean;
    private final int anInteger;
    private final String aString;

    public AnObject(boolean aBoolean, int anInteger, String aString) {
        this.aBoolean = aBoolean;
        this.anInteger = anInteger;
        this.aString = aString;
    }

    public static AnObject getInstance(boolean aBoolean, int anInteger, String aString) {
        return new AnObject(aBoolean, anInteger, aString);
    }

    public String getString() {
        return aString;
    }

    public Boolean getBoolean() {
        return aBoolean;
    }

    public Integer getInteger() {
        return anInteger;
    }
}
