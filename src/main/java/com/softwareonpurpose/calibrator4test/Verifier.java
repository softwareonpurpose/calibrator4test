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
import org.slf4j.LoggerFactory;

/**
 * Provides clear logging of verification, and reconciliation rresults formatted for Calibration report
 */
class Verifier {

    final static String PASS = "";
    private final static Integer RECONCILED = 0;
    private final static String failureFormat = "%s -- Expected: %s  Actual: %s%n";
    private final IndentManager indentManager;
    private final String verificationDescription;
    private final String message;
    private final Object expected;
    private final Object actual;

    private Verifier(String description, Object expected, Object actual, IndentManager formatter) {
        this.indentManager = formatter;
        String expectedDescription = expected == null ? "<null>" : expected.toString();
        this.verificationDescription = String.format("%s - %s", description, expectedDescription);
        this.message = String.format(failureFormat, description, expectedDescription, actual == null ? "<null>" : actual.toString());
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * Provides an instance of an Verifier which utilizes the an IndentManager to format results
     *
     * @param description   Description of the Object being verified
     * @param expected      Expected Object value
     * @param actual        Actual Object value
     * @param indentManager IndentManager used to format results
     * @return Verifier
     */
    static Verifier construct(String description, Object expected, Object actual, IndentManager indentManager) {
        return new Verifier(description, expected, actual, indentManager);
    }

    /**
     * Return results of reconciliation formatted for Calibration report
     *
     * @return String Formatted result
     *
     *      Empty String (""):  Reconciliation successful
     *      Populated String:   Details of reconciliation failure
     */
    String verify() {
        LoggerFactory.getLogger("").info(indentManager.format(verificationDescription));
        if (RECONCILED.equals(Reconciler.reconcile(expected, actual))) return PASS;
        return message;
    }
}
