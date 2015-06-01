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

import java.util.List;

public class Verifier {

    private final static String passedMessage = "";
    private final static int RECONCILED = 0;
    private final IndentManager indentManager;
    private final String description;
    private final String message;
    private Reconciler reconciler;

    private Verifier(String description, Object expected, Object actual, Reconciler reconciler, IndentManager formatter) {
        this.indentManager = formatter;
        this.description = description;
        this.message = String
                .format("%s -- Expected: %s  Actual: %s%n", description, expected == null ? "<null>" : expected.toString(),
                        actual == null ? "<null>" : actual.toString());
        this.reconciler = reconciler;
    }

    /**
     * Provides an instance of an Integer verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the Integer being verified
     * @param expected      Expected Integer value
     * @param actual        Actual Integer value
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */
    public static Verifier getInstance(String description, Integer expected, Integer actual, IndentManager indentManager) {
        Reconciler reconciler = Reconciler.getInstance(expected, actual);
        return new Verifier(description, expected, actual, reconciler, indentManager);
    }

    /**
     * Provides an instance of an Boolean verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the Boolean being verified
     * @param expected      Expected Boolean value
     * @param actual        Actual Boolean value
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */
    public static Verifier getInstance(String description, Boolean expected, Boolean actual, IndentManager indentManager) {
        Reconciler reconciler = Reconciler.getInstance(expected, actual);
        return new Verifier(description, expected, actual, reconciler, indentManager);
    }

    /**
     * Provides an instance of an Long verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the Long being verified
     * @param expected      Expected Long value
     * @param actual        Actual Long value
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */

    public static Verifier getInstance(String description, Long expected, Long actual, IndentManager indentManager) {
        Reconciler reconciler = Reconciler.getInstance(expected, actual);
        return new Verifier(description, expected, actual, reconciler, indentManager);
    }

    /**
     * Provides an instance of an String verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the String being verified
     * @param expected      Expected String value
     * @param actual        Actual String value
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */
    public static Verifier getInstance(String description, String expected, String actual, IndentManager indentManager) {
        Reconciler reconciler = Reconciler.getInstance(expected, actual);
        return new Verifier(description, expected, actual, reconciler, indentManager);
    }

    /**
     * Provides an instance of an String List verifier which utilizes the provided IndentManager to format results
     *
     * @param description   Description of the String List being verified
     * @param expected      Expected String List
     * @param actual        Actual String List
     * @param indentManager IndentManager to use to format results
     * @return New instance of Verifier
     */
    public static Verifier getInstance(String description, List<String> expected, List<String> actual,
            IndentManager indentManager) {
        String flatExpectedList = getFlatList(expected);
        String flatActualList = getFlatList(actual);
        Reconciler reconciler = Reconciler.getInstance(expected, actual);
        return new Verifier(description, flatExpectedList, flatActualList, reconciler, indentManager);
    }

    private static String getFlatList(List<String> stringList) {
        if (stringList == null)
            return "<null>";
        String flatExpectedList = "";
        for (String string : stringList)
            flatExpectedList += String.format("%s|", string);
        return flatExpectedList.substring(0, flatExpectedList.lastIndexOf("|"));
    }

    /**
     * Verifies that the Expected and Actual values reconcile
     *
     * @return Empty String ("") if they values reconcile; a message containing the two values if there is a discrepancy
     */
    protected String verify() {
        LogManager.getLogger(this.getClass()).info(indentManager.format(description));
        Integer reconciliation = reconciler.reconcile();
        if (reconciliation.equals(RECONCILED))
            return passedMessage;
        return message;
    }
}
