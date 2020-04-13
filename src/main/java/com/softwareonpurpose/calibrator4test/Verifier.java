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
import org.slf4j.LoggerFactory;

/**
 * Logs and performs a verification, returning clear results formatted for a coverage report
 */
class Verifier {
    static final String PASS = "";
    private static final String failureFormat = "%s -- Expected: %s  Actual: %s%n";
    private static final int RECONCILED = 0;
    private static final int EXPECTED_NULL = 1;
    private static final int ACTUAL_NULL = 2;
    private static final int DISCREPANCY = 3;

    private Verifier() {
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
        logVerification(description, indentManager, expectedDescription);
        String failureMessage = String.format(failureFormat, description, expectedDescription, actual == null ? "<null>" : actual.toString());
        return RECONCILED == reconcile(expected, actual) ? PASS : failureMessage;
    }

    private static void logVerification(String description, IndentManager indentManager, String expectedDescription) {
        String verificationDescription = String.format("%s - %s", description, expectedDescription);
        if (indentManager != null) {
            verificationDescription = indentManager.format(verificationDescription);
        }
        LoggerFactory.getLogger("").info(verificationDescription);
    }

    /**
     * Reconcile two Objects
     *
     * @return Integer value indicating the result:
     * <p>
     * 0 - Reconciled
     * 1 - Expected is null
     * 2 - Actual is null
     * 3 - Discrepancy exists
     */
    private static Integer reconcile(Object expected, Object actual) {
        if (expected == null && actual == null)
            return RECONCILED;
        if (expected == null)
            return EXPECTED_NULL;
        if (actual == null)
            return ACTUAL_NULL;
        return actual.equals(expected) ? RECONCILED : DISCREPANCY;
    }
}
