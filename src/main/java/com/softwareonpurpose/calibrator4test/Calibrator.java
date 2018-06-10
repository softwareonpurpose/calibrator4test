/**
 * Copyright 2018 Craig A. Stockton
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.softwareonpurpose.calibrator4test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Calibrator {

    @SuppressWarnings("WeakerAccess")
    public static final String PASS = "";
    private final static String CALIBRATION_FORMAT = "VALIDATION %s: %s";
    private final static String PASSED = "PASSED";
    private final static String FAILED = "FAILED";
    private final static String TO_REGRESS = "(known issues to be regressed)";
    private final static String KNOWN_ISSUES = "KNOWN ISSUES:";
    @SuppressWarnings("WeakerAccess")
    private static String validationLoggingStyle = CalibrationLoggingStyle.STANDARD;
    private final List<Calibrator> children = new ArrayList<>();
    private final IndentManager indentManager;
    private final StringBuilder failures = new StringBuilder();
    private final StringBuilder knownIssues = new StringBuilder();
    private final StringBuilder report = new StringBuilder();
    private final String description;
    private final CalibrationBehavior calibrationBehavior;
    private final String className;
    private final boolean expectedExists;
    private final boolean actualExists;
    private final Logger logger = LoggerFactory.getLogger("");

    /**
     * Constructor to be used by "Child" calibrators (added as a child of at least one other calibrator)
     *
     * @param description      A description of the object validated
     * @param expected         Object representing an expected state
     * @param actual           Object representing an actual state
     * @param parentCalibrator Parent Calibrator
     */
    protected Calibrator(String description, Object expected, Object actual, Calibrator parentCalibrator) {
        this.expectedExists = expected != null;
        this.actualExists = actual != null;
        final String fullClassname = this.getClass().getName();
        this.className = fullClassname.substring(fullClassname.lastIndexOf(".") + 1);
        if (parentCalibrator == null) {
            calibrationBehavior = new RootBehavior();
            this.indentManager = IndentManager.construct();
        } else {
            calibrationBehavior = new ChildBehavior();
            this.indentManager = parentCalibrator.indentManager;
        }
        this.description = description;
    }

    /**
     * Constructor to be used by "Root" calibrators (NO parent)
     *
     * @param description A description of the object validated
     * @param expected    Object representing an expected state
     * @param actual      Object representing an actual state
     */
    protected Calibrator(String description, Object expected, Object actual) {
        this(description, expected, actual, null);
    }

    /**
     * Set the 'Style' to be used in logging the Validation event.
     *
     * @param style Calibrator.CalibrationLoggingStyle
     */
    @SuppressWarnings("WeakerAccess")
    public static void setStyle(String style) {
        switch (style.toUpperCase()) {
            case CalibrationLoggingStyle.BDD:
                validationLoggingStyle = CalibrationLoggingStyle.BDD;
                return;
            default:
                validationLoggingStyle = CalibrationLoggingStyle.STANDARD;
        }
    }

    /**
     * Execute a list of verifications of the Actual object using the Expected object.  Implemented in the inheritor of
     * Calibrator.  Intended to contain ONLY calls to the verify() methods.
     */
    protected abstract void executeVerifications();

    /**
     * Indicates whether an Actual object has been provided.
     *
     * @return boolean
     */
    protected boolean actualExists() {
        return actualExists;
    }

    /**
     * Indicates whether an Expected object has been provided.
     *
     * @return boolean
     */
    protected boolean expectedExists() {
        return expectedExists;
    }

    /**
     * Validate the Actual object using the Expected object
     *
     * @return For child calibrators, a list of verification failures; for root calibrators, a validation report
     */
    @SuppressWarnings("WeakerAccess")
    public String validate() {
        return calibrationBehavior.execute();
    }

    /**
     * Add a child calibrator
     *
     * @param calibrator An instance of a 'child' calibrator
     */
    protected void addChildCalibrator(Calibrator calibrator) {
        children.add(calibrator);
    }

    /**
     * Add a description of a known issue which accounts for a verification failure (e.g. bug).
     * This should be removed once it is noticed that the verification failure NO longer occurs.
     *
     * @param description Free-form description of known issue (e.g. "Bug #999 - login fails")
     */
    @SuppressWarnings("WeakerAccess")
    protected void addKnownIssue(@SuppressWarnings("SameParameterValue") String description) {
        knownIssues.append(indentManager.format(String.format("%s -- %s", className, description)));
    }

    /**
     * Verifies an actual object with an expected object, adding to 'Failure' message if verification fails
     *
     * @param description Description of Object verified
     * @param expected    Expected object
     * @param actual      Actual object
     */
    protected void verify(String description, Object expected, Object actual) {
        String result = Verifier.construct(description, expected, actual, indentManager).verify();
        String formattedResult = result.length() > 0 ? String.format("%s: %s", className, result) : PASS;
        failures.append(indentManager.format(formattedResult));
    }

    private void logCalibration() {
        logger.info("");
        if (indentManager.isAtRootLevel()) {
            logger.info(String.format("%s:", validationLoggingStyle));
            logger.info(description);
        } else {
            logger.info(indentManager.format(description));
        }
    }

    private String getKnownIssues() {
        return knownIssues.toString();
    }

    private void compileChildIssues() {
        for (Calibrator child : children) {
            knownIssues.append(child.getKnownIssues());
        }
    }

    private boolean issuesFound() {
        return knownIssues.length() > 0;
    }

    private void incrementIndentation() {
        indentManager.increment(2);
    }

    private void decrementIndentation() {
        indentManager.decrement();
    }

    private String getFailures() {
        return failures.toString();
    }

    private void executeChildCalibrations() {
        for (Calibrator calibrator : children) {
            failures.append(calibrator.validate());
        }
        compileChildIssues();
    }

    private boolean isPassed() {
        return failures.length() == 0;
    }

    private void executeCalibration() {
        if (expectedExists() && actualExists()) {
            executeVerifications();
            executeChildCalibrations();
        } else {
            reportMissingComparator();
        }
    }

    private void reportMissingComparator() {
        String expected = ((Boolean) expectedExists()).toString();
        String actual = ((Boolean) actualExists()).toString();
        final String resultMessage = "%s exists";
        verify(String.format(resultMessage, description), expected, actual);
    }

    private void compileReport() {
        if (isPassed() && !issuesFound()) return;
        report.append(String.format(CALIBRATION_FORMAT, isPassed() ? PASSED : FAILED, issuesFound() ? TO_REGRESS : ""));
        report.append(String.format("%n%s%n", getFailures()));
        if (issuesFound()) {
            report.append(KNOWN_ISSUES);
            report.append(String.format("%n%s%n", getKnownIssues()));
        }
    }

    private interface CalibrationBehavior {

        String execute();
    }

    /*----  Private Calibration Behaviors      -----*/

    /***
     * Provide values externally to indicate the type of logging to use
     */
    @SuppressWarnings("WeakerAccess")
    public class CalibrationLoggingStyle {
        public final static String BDD = "THEN";
        public final static String STANDARD = "VALIDATE";
    }

    private class RootBehavior implements CalibrationBehavior {

        @Override
        public String execute() {
            logCalibration();
            incrementIndentation();
            executeCalibration();
            compileReport();
            decrementIndentation();
            return report.toString();
        }
    }

    private class ChildBehavior implements CalibrationBehavior {

        @Override
        public String execute() {
            decrementIndentation();
            logCalibration();
            incrementIndentation();
            executeCalibration();
            decrementIndentation();
            return getFailures();
        }
    }
}
