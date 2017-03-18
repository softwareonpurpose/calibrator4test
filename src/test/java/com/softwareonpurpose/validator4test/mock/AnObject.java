package com.softwareonpurpose.validator4test.mock;

import com.google.gson.Gson;

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

    Boolean getBoolean() {
        return aBoolean;
    }

    Integer getInteger() {
        return anInteger;
    }

    String getString() {
        return aString;
    }

    AnObject getObject() {
        return object;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != AnObject.class) {
            return false;
        }
        AnObject castObj = (AnObject) obj;
        boolean equals = ((this.getBoolean() == null && castObj.getBoolean() == null) || (this.getBoolean() != null && this.getBoolean().equals(castObj.getBoolean())));
        equals &= ((this.getInteger() == null && castObj.getInteger() == null) || (this.getInteger() != null && this.getInteger().equals(castObj.getInteger())));
        equals &= ((this.getString() == null && castObj.getString() == null) || (this.getString() != null && this.getString().equals(castObj.getString())));
        equals &= ((this.getObject() == null && castObj.getObject() == null) || (this.getObject() != null && this.getObject().equals(castObj.getObject())));
        return equals;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
