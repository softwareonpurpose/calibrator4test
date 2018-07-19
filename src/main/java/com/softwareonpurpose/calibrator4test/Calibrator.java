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

import com.softwareonpurpose.indentmanager.IndentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class Calibrator {

    @SuppressWarnings("WeakerAccess")
    public static final String SUCCESS = "";
    private final static String CALIBRATION_FORMAT = "CALIBRATION %s: %s";
    private final static String PASSED = "PASSED";
    private final static String FAILED = "FAILED";
    private final static String TO_REGRESS = "(known issues to be regressed)";
    private final static String KNOWN_ISSUES = "KNOWN ISSUES:";
    private final List<Calibrator> children = new ArrayList<>();
    private final StringBuilder failures = new StringBuilder();
    private final StringBuilder knownIssues = new StringBuilder();
    private final StringBuilder report = new StringBuilder();
    private final String description;
    private final String className;
    private final boolean expectedExists;
    private final boolean actualExists;
    private final Logger logger = LoggerFactory.getLogger("");
    private IndentManager indentManager = IndentManager.getInstance();

    /**
     * Calibrate an object against an expected object.
     * Calibration consists of verifying significant properties against expected values.
     * Child calibrators can be added representing more complex properties (e.g. common regions of a web page)
     *
     * @param description Description of object calibrated
     * @param expected    Expected object
     * @param actual      Actual object
     */
    protected Calibrator(String description, Object expected, Object actual) {
        this.expectedExists = expected != null;
        this.actualExists = actual != null;
        final String fullClassname = this.getClass().getName();
        this.className = fullClassname.substring(fullClassname.lastIndexOf(".") + 1);
        this.description = description;
    }

    /**
     * Calibrate the Actual object against the Expected object
     *
     * @return A complete report from a root calibrator OR failures from a child calibrator
     */
    @SuppressWarnings("WeakerAccess")
    public String calibrate() {
        if (indentManager.isAtRootLevel()) {
            logCalibration();
            indentManager.increment(2);
            executeCalibration();
            compileReport();
            indentManager.decrement();
            return report.toString();
        } else {
            indentManager.decrement();
            logCalibration();
            indentManager.increment(2);
            executeCalibration();
            indentManager.decrement();
            return getFailures();
        }
    }

    /**
     * Implemented in each concrete Calibrator.
     * Intended to contain ONLY calls to the verify() method.
     */
    protected abstract void executeVerifications();

    /**
     * Add a child calibrator
     *
     * @param calibrator An instance of a 'child' calibrator
     */
    protected void addChildCalibrator(Calibrator calibrator) {
        children.add(calibrator.withIndentManager(this.indentManager));
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
        String result = Verifier.getInstance(description, expected, actual, indentManager).verify();
        String formattedResult = result.length() > 0 ? String.format("%s: %s", className, result) : SUCCESS;
        failures.append(indentManager.format(formattedResult));
    }

    private Calibrator withIndentManager(IndentManager indentManager) {
        this.indentManager = indentManager;
        return this;
    }

    private void logCalibration() {
        logger.info("");
        if (indentManager.isAtRootLevel()) {
            logger.info("VALIDATE:");
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

    private String getFailures() {
        return failures.toString();
    }

    private void executeChildCalibrations() {
        for (Calibrator calibrator : children) {
            failures.append(calibrator.calibrate());
        }
        compileChildIssues();
    }

    private boolean isPassed() {
        return failures.length() == 0;
    }

    private void executeCalibration() {
        if (expectedExists && actualExists) {
            executeVerifications();
            executeChildCalibrations();
        } else {
            reportMissingComparator();
        }
    }

    private void reportMissingComparator() {
        String expected = ((Boolean) expectedExists).toString();
        String actual = ((Boolean) actualExists).toString();
        final String resultMessage = "%s exists";
        verify(String.format(resultMessage, description), expected, actual);
    }

    private void compileReport() {
        if (isPassed() && !issuesFound()) {
            return;
        }
        report.append(String.format(CALIBRATION_FORMAT, isPassed() ? PASSED : FAILED, issuesFound() ? TO_REGRESS : ""));
        report.append(String.format("%n%s%n", getFailures()));
        if (issuesFound()) {
            report.append(KNOWN_ISSUES);
            report.append(String.format("%n%s%n", getKnownIssues()));
        }
    }
}
