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

/**
 * Reconcile an actual object with an expected object
 */
class Reconciler {
    private final static int RECONCILED = 0;
    private final static int EXPECTED_NULL = 1;
    private final static int ACTUAL_NULL = 2;
    private final static int DISCREPANCY = 3;

    private Reconciler() {
    }

    /**
     * Reconcile two Objects
     *
     * @return Integer value indicating the result:
     *
     * 0 - Reconciled
     * 1 - Expected is null
     * 2 - Actual is null
     * 3 - Discrepancy exists
     */
    static Integer reconcile(Object expected, Object actual) {
        if (expected == null && actual == null)
            return RECONCILED;
        if (expected == null)
            return EXPECTED_NULL;
        if (actual == null)
            return ACTUAL_NULL;
        return actual.equals(expected) ? RECONCILED : DISCREPANCY;
    }
}
