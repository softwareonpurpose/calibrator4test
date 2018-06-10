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
    @SuppressWarnings("WeakerAccess")
    private static String validationLoggingStyle = ValidationLoggingStyle.STANDARD;
    private final List<Calibrator> children = new ArrayList<>();
    private final IndentManager indentManager;
    private final StringBuilder failures = new StringBuilder();
    private final StringBuilder knownIssues = new StringBuilder();
    private final StringBuilder report = new StringBuilder();
    private final String description;
    private final ValidationBehavior validationBehavior;
    private final String className;
    private final boolean expectedExists;
    private final boolean actualExists;

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
            validationBehavior = new RootBehavior();
            this.indentManager = IndentManager.construct();
        } else {
            validationBehavior = new ChildBehavior();
            this.indentManager = parentCalibrator.getIndentManager();
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
     * @param style Calibrator.ValidationLoggingStyle
     */
    @SuppressWarnings("WeakerAccess")
    public static void setStyle(String style) {
        switch (style.toUpperCase()) {
            case ValidationLoggingStyle.BDD:
                validationLoggingStyle = ValidationLoggingStyle.BDD;
                return;
            default:
                validationLoggingStyle = ValidationLoggingStyle.STANDARD;
        }
    }

    /**
     * Execute a list of verifications of the Actual object using the Expected object.  Implemented in the inheritor of
     * Calibrator.  Intended to contain ONLY calls to the verify() methods.
     */
    protected abstract void executeVerifications();

    /**
     * Indicates whether the Actual object has been provided.
     *
     * @return The 'actual' validatee exists
     */
    protected boolean actualExists() {
        return actualExists;
    }

    /**
     * Indicates whether the Expected object has been provided.
     *
     * @return An instance of the validatee with expected values exists
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
        return validationBehavior.execute();
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
     * Add a description of a known issue which accounts for a verification failure (e.g. bug).  This should be removed
     * once it is noticed that the verification failure NO longer occurs.
     *
     * @param description Free-form description of a known issue (e.g. "Bug #999 - login fails", "Config issues in
     *                    'Stage'")
     */
    @SuppressWarnings("WeakerAccess")
    protected void addKnownIssue(@SuppressWarnings("SameParameterValue") String description) {
        knownIssues.append(getIndentManager().format(String.format("%s -- %s", className, description)));
    }

    /**
     * Verifies an actual Boolean value, adding to 'Failure' message if verification fails
     *
     * @param description Description of Object verified
     * @param expected    The expected value
     * @param actual      The actual value
     */
    protected void verify(String description, Object expected, Object actual) {
        String result = Verifier.construct(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    private IndentManager getIndentManager() {
        return indentManager;
    }

    private String getDescription() {
        return description;
    }

    private void logValidation() {
        getLogger().info("");
        if (getIndentManager().isAtRootLevel()) {
            getLogger().info(String.format("%s:", validationLoggingStyle));
            getLogger().info(getDescription());
        } else {
            getLogger().info(getIndentManager().format(getDescription()));
        }
    }

    private String getKnownIssues() {
        return knownIssues.toString();
    }

    private void compileChildIssues() {
        for (Calibrator child : getChildCalibrators()) {
            knownIssues.append(child.getKnownIssues());
        }
    }

    private boolean issuesFound() {
        return knownIssues.length() > 0;
    }

    private void incrementIndentation() {
        getIndentManager().increment(2);
    }

    private void decrementIndentation() {
        getIndentManager().decrement();
    }

    private String getFailures() {
        return failures.toString();
    }

    private void executeChildValidations() {
        for (Calibrator calibrator : getChildCalibrators()) {
            failures.append(calibrator.validate());
        }
        compileChildIssues();
    }

    private boolean isFailed() {
        return failures.length() > 0;
    }

    private boolean isPassed() {
        return !isFailed();
    }

    private Logger getLogger() {
        return LoggerFactory.getLogger("");
    }

    private void executeValidation() {
        if (expectedExists() && actualExists()) {
            executeVerifications();
            executeChildValidations();
        } else {
            String expected = ((Boolean) expectedExists()).toString();
            String actual = ((Boolean) actualExists()).toString();
            final String resultMessage = "%s exists";
            verify(String.format(resultMessage, getDescription()), expected, actual);
        }
    }

    private List<Calibrator> getChildCalibrators() {
        return children;
    }

    private void compileReport() {
        if (isPassed() && !issuesFound()) return;
        report.append(String.format("VALIDATION %s: %s", isPassed() ? "PASSED" : "FAILED", isPassed() ? "(known issues to be regressed)" : ""));
        report.append(String.format("%n%s%n", getFailures()));
        if (issuesFound()) {
            report.append("KNOWN ISSUES:");
            report.append(String.format("%n%s%n", getKnownIssues()));
        }
    }

    private void recordIfFailed(String result) {
        result = result.length() > 0 ? String.format("%s: %s", className, result) : PASS;
        failures.append(getIndentManager().format(result));
    }

    /*----  Private Validation Behaviors      -----*/

    private interface ValidationBehavior {

        String execute();
    }

    private class RootBehavior implements ValidationBehavior {

        @Override
        public String execute() {
            logValidation();
            incrementIndentation();
            executeValidation();
            compileReport();
            decrementIndentation();
            return report.toString();
        }
    }

    private class ChildBehavior implements ValidationBehavior {

        @Override
        public String execute() {
            decrementIndentation();
            logValidation();
            incrementIndentation();
            executeValidation();
            decrementIndentation();
            return getFailures();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public class ValidationLoggingStyle {
        public final static String BDD = "THEN";
        public final static String STANDARD = "VALIDATE";
    }
}
