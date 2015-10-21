package com.softwareonpurpose.validator4test.mock;

public class AnObject {

    private final boolean aBoolean;
    private final int anInteger;
    private final String aString;
    private final AnObject object;

    private AnObject(boolean aBoolean, int anInteger, String aString, AnObject object) {
        this.aBoolean = aBoolean;
        this.anInteger = anInteger;
        this.aString = aString;
        this.object = object;
    }

    public static AnObject getInstance(boolean aBoolean, int anInteger, String aString) {
        return new AnObject(aBoolean, anInteger, aString, null);
    }

    public static AnObject getInstance(boolean aBoolean, int anInteger, String aString, AnObject anObject) {
        return new AnObject(aBoolean, anInteger, aString, anObject);
    }

    public boolean getBoolean() {
        return aBoolean;
    }

    public int getInteger() {
        return anInteger;
    }

    public String getString() {
        return aString;
    }

    public AnObject getObject() {
        return object;
    }
}
