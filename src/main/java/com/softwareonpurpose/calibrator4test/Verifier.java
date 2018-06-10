/**
 * Copyright 2017 Craig A. Stockton
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

import org.slf4j.LoggerFactory;

class Verifier {

    final static String PASS = "";
    private final static Integer RECONCILED = 0;
    private final IndentManager indentManager;
    private final String verificationDescription;
    private final String message;
    @SuppressWarnings("SpellCheckingInspection")
    private Reconciler reconciler;

    private Verifier(String description, Object expected, Object actual, Reconciler reconciler, IndentManager
            formatter) {
        this.indentManager = formatter;
        String expectedDescription = expected == null ? "<null>" : expected.toString();
        this.verificationDescription = String.format("%s - %s", description, expectedDescription);
        this.message = String.format("%s -- Expected: %s  Actual: %s%n", description, expectedDescription, actual ==
                null ? "<null>" : actual.toString());
        this.reconciler = reconciler;
    }

    /**
     * Provides an instance of an Integer verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the Object being verified
     * @param expected      Expected Object value
     * @param actual        Actual Object value
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */
    static Verifier construct(String description, Object expected, Object actual, IndentManager indentManager) {
        return new Verifier(description, expected, actual, Reconciler.construct(expected, actual), indentManager);
    }

    /**
     * Verifies that the Expected and Actual values reconcile
     *
     * @return Empty String ("") if the values reconcile; a message containing the two values if there is a discrepancy
     */
    String verify() {
        LoggerFactory.getLogger("").info(indentManager.format(verificationDescription));
        if (RECONCILED.equals(reconciler.reconcile())) return PASS;
        return message;
    }
}
