/*
  Copyright 2019 Craig A. Stockton
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.softwareonpurpose.calibrator4test;

import com.softwareonpurpose.indentmanager.IndentManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchestrate calibration of complex objects for testing purposes
 */
public abstract class Calibrator {
    @SuppressWarnings("WeakerAccess")
    public static final String SUCCESS = "";
    private final static String CALIBRATION_FORMAT = "CALIBRATION FAILED: ";
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
     * @param description String description of object to calibrate
     * @param expected    Object expected comparator
     * @param actual      Object actual compared
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
     * @return String report from a root calibrator, OR detailed failures from a child calibrator
     */
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
     * Implemented in each concrete Calibrator;
     * to contain calls to verify() representing detailed verifications
     */
    protected abstract void executeVerifications();

    /**
     * Add a child calibrator
     *
     * @param calibrator Calibrator child (e.g. region of a page)
     */
    protected void addChildCalibrator(Calibrator calibrator) {
        children.add(calibrator);
    }

    /**
     * Executes verification that an actual Object reconciles with an expected Object,
     * logging that verification and compiling failure details
     *
     * @param description String description of Object verified
     * @param expected    Object expected (comparator)
     * @param actual      Object actual (compared)
     */
    protected void verify(String description, Object expected, Object actual) {
        String result = Verifier.verify(description, expected, actual, indentManager);
        String formattedResult = result.length() > 0 ? String.format("%s: %s", className, result) : SUCCESS;
        failures.append(indentManager.format(formattedResult));
    }

    private void withIndentManager(IndentManager indentManager) {
        this.indentManager = indentManager;
    }

    private void logCalibration() {
        logger.info("");
        if (indentManager.isAtRootLevel()) {
            logger.info("CALIBRATE:");
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

    private String getFailures() {
        return failures.toString();
    }

    private void executeChildCalibrations() {
        for (Calibrator calibrator : children) {
            calibrator.withIndentManager(indentManager);
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
        if (isPassed()) {
            return;
        }
        report.append(CALIBRATION_FORMAT);
        report.append(String.format("%n%s%n", getFailures()));
    }
}
