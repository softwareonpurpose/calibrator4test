/**
 * Copyright 2015 Craig A. Stockton
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
package com.craigstockton.validator4test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator {

    public static final String PASS = "";
    private final List<Validator> children = new ArrayList<>();
    private final IndentManager indentManager;
    private final StringBuilder failures = new StringBuilder();
    private final StringBuilder knownIssues = new StringBuilder();
    private final StringBuilder report = new StringBuilder();
    private final String description;
    private final ValidationBehavior validationBehavior;
    private final String className;

    /**
     * Constructor to be used by "Root" validators (NO parent)
     *
     * @param description A description of the object validated
     */
    protected Validator(String description) {
        this(description, null);
    }

    /**
     * Constructor to be used by "Child" validators (added as a child of at least one other validator4test)
     *
     * @param description     A description of the object validated
     * @param parentValidator The parent Validator
     */
    protected Validator(String description, Validator parentValidator) {
        IndentManager indentManager = parentValidator == null ? null : parentValidator.getIndentManager();
        final String fullClassname = this.getClass().getName();
        this.className = fullClassname.substring(fullClassname.lastIndexOf(".") + 1);
        if (indentManager == null) {
            validationBehavior = new RootBehavior();
            this.indentManager = IndentManager.getInstance();
        } else {
            validationBehavior = new ChildBehavior();
            this.indentManager = indentManager;
        }
        this.description = description;
    }

    /**
     * Execute a list of verifications of the Actual object using the Expected object.  Implemented in the inheritor of
     * Validator.  Intended to contain ONLY calls to the verify() methods.
     */
    protected abstract void executeVerifications();

    /**
     * Indicate whether the Actual object has been provided.  Implemented in the inheritor of Validator.
     *
     * @return The 'actual' validatee exists
     */
    protected abstract boolean actualExists();

    /**
     * Indicate whether the Expected object has been provided.  Implemented in the inheritor of Validator.
     *
     * @return An instance of the validatee with expected values exists
     */
    protected abstract boolean expectedExists();

    /**
     * Validate the Actual object using the Expected object
     *
     * @return For child validators, a list of verification failures; for root validators, a validation report
     */
    public String validate() {
        return validationBehavior.execute();
    }

    /**
     * Add a child validator4test
     *
     * @param validator An instance of a 'child' validator4test
     */
    protected void addChildValidator(Validator validator) {
        children.add(validator);
    }

    /**
     * Add a description of a known issue which accounts for a verification failure (e.g. bug).  This should be removed
     * once it is noticed that the verification failure NO longer occurs.
     *
     * @param description Free-form description of a known issue (e.g. "Bug #999 - login fails", "Config issues in 'Stage'")
     */
    protected void addKnownIssue(String description) {
        knownIssues.append(getIndentManager().format(String.format("%s -- %s", className, description)));
    }

    /**
     * Verifies an actual Boolean value, adding to 'Failure' message if verification fails
     *
     * @param description The Boolean field verified
     * @param expected    The expected Boolean value
     * @param actual      The actual Boolean value
     */
    protected void verify(String description, Boolean expected, Boolean actual) {
        String result = Verifier.getInstance(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    /**
     * Verifies an actual Integer value, adding to 'Failure' message if verification fails
     *
     * @param description The Integer field verified
     * @param expected    The expected Integer value
     * @param actual      The actual Integer value
     */
    protected void verify(String description, Integer expected, Integer actual) {
        String result = Verifier.getInstance(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    /**
     * Verifies an actual String list, adding to 'Failure' message if verification fails
     *
     * @param description The String list verified
     * @param expected    The expected String list
     * @param actual      The actual String list
     */
    protected void verify(String description, List<String> expected, List<String> actual) {
        String result = Verifier.getInstance(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    /**
     * Verifies an actual Long value, adding to 'Failure' message if verification fails
     *
     * @param description The Long field verified
     * @param expected    The expected Long value
     * @param actual      The actual Long value
     */
    protected void verify(String description, Long expected, Long actual) {
        String result = Verifier.getInstance(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    /**
     * Verifies an actual String value, adding to 'Failure' message if verification fails
     *
     * @param description The String field verified
     * @param expected    The expected String value
     * @param actual      The actual String value
     */
    protected void verify(String description, String expected, String actual) {
        String result = Verifier.getInstance(description, expected, actual, getIndentManager()).verify();
        recordIfFailed(result);
    }

    private IndentManager getIndentManager() {
        return indentManager;
    }

    private String getDescription() {
        return description;
    }

    private void logValidation() {
        String description = getIndentManager().isAtRootLevel() ?
                String.format("VALIDATE:%n%s", getDescription()) :
                getDescription();
        getLogger().info(getIndentManager().format(description));
    }

    private String getKnownIssues() {
        return knownIssues.toString();
    }

    private void compileChildIssues() {
        for (Validator child : getChildValidators()) {
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
        for (Validator validator : getChildValidators()) {
            failures.append(validator.validate());
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
        return LogManager.getLogger(this.getClass());
    }

    private void executeValidation() {
        if (expectedMissing() && actualMissing())
            verify(String.format("Actual '%s' missing", getDescription()), true, actualMissing());
        else if (expectedMissing() && actualExists())
            verify(String.format("Unexpected '%s' exists", getDescription()), false, expectedMissing());
        else if (expectedExists() && actualMissing())
            verify(String.format("Actual '%s' exists", getDescription()), true, actualExists());
        else if (expectedExists() && actualExists()) {
            executeVerifications();
            executeChildValidations();
        }
    }

    private List<Validator> getChildValidators() {
        return children;
    }

    private boolean actualMissing() {
        return !actualExists();
    }

    private boolean expectedMissing() {
        return !expectedExists();
    }

    private void compileReport() {
        if (isPassed() && !issuesFound())
            return;
        report.append(String.format("VALIDATION %s: %s", isPassed() ? "PASSED" : "FAILED",
                isPassed() && issuesFound() ? "(known issues to be regressed)" : ""));
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

}
