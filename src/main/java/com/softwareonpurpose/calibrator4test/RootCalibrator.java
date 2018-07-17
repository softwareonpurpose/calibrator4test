package com.softwareonpurpose.calibrator4test;

public abstract class RootCalibrator extends Calibrator {
    protected RootCalibrator(String description, Object expected, Object actual) {
        super(description, expected, actual);
    }

    @Override
    public String validate() {
        logCalibration();
        incrementIndentation();
        executeCalibration();
        compileReport();
        decrementIndentation();
        return report.toString();
    }
}
