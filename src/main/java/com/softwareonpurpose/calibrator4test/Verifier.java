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
import org.apache.logging.log4j.LogManager;

/**
 * Logs and performs a verification, returning clear results formatted for a coverage report
 */
class Verifier {
    final static String PASS = "";
    private final static Integer RECONCILED = 0;
    private final static String failureFormat = "%s -- Expected: %s  Actual: %s%n";
    private final IndentManager indentManager;
    private final String description;
    private final String message;
    private final Object expected;
    private final Object actual;

    private Verifier(String description, Object expected, Object actual, IndentManager formatter) {
        indentManager = formatter;
        String expectedDescription = expected == null ? "<null>" : expected.toString();
        this.description = String.format("%s - %s", description, expectedDescription);
        message = String.format(failureFormat, description, expectedDescription, actual == null ? "<null>" : actual.toString());
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * Reconcile the expected and actual objects and return results formatted for Calibration report
     *
     * @return String formatted result
     * <p>
     * PASS (empty String):    Reconciliation successful
     * Populated String:       Details of reconciliation failure
     */
    static String verify(String description, Object expected, Object actual, IndentManager indentManager) {
        String expectedDescription = expected == null ? "<null>" : expected.toString();
        LogManager.getLogger("").info(indentManager.format(String.format("%s - %s", description, expectedDescription)));
        String failureMessage = String.format(failureFormat, description, expectedDescription, actual == null ? "<null>" : actual.toString());
        return RECONCILED.equals(Reconciler.reconcile(expected, actual)) ? PASS : failureMessage;
    }
}
