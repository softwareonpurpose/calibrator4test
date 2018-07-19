# calibrator4test
Calibrate an object of any complexity against an object with expected values.   

## Calibration results
Each detailed verification is performed without throwing an Assertion.  All failures are compiled into a single report.  An empty String indicates the object calibrated successfully.  Otherwise, a detailed report of calibration failures is returned.

Best practice is to include a single Assertion used by every test, which simple asserts that the value returned by a Calibrator is an empty String.

#### Design
Each concrete calibrator extends `abstract class Calibrator`.  Best practice is for the constructor to store both the expected and the actual objects as private members, which are referenced in 'verify' statements.

Each concrete calibrator should implement either of the following, but not both (though combining the two is supported):
 - detailed verifications defined in `executeVerifications()` (can be empty, resulting in a NO-OP)
 - child calibrators
 
This approach will result in each Calibrator being either:
 - detailed verification of an Object, or
 - parent calibrator orchestrating execution of child calibrators

#### Verifications
Implementation of `protected abstract void executeVerifications()`:
 - add an individual verification for each member of an object to be verified
 - for readability, verifications are written as 
```
verify("Member description", expected.getMember(), actual.getMember());
```
 - during execution, each verification will be logged to stdout, with each failure returned as part of a String value
 - best practice is to reconcile only non-primitives (e.g. Integer, String, Boolean), allowing for scenarios in which a 'null' may be acceptable
