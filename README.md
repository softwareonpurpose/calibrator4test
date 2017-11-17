# validator4test
Define, manage and log detailed reconciliation of two Objects without throwing an Assertion.  

A returned empty String indicates success; any other values contains failure details (e.g. Member "Description -- [expected value]").  All test methods can end with the simple statement...
```
Assert.assertTrue("".equals(validationResult), validationResult);
```
...which will throw one assertion for any number of failures.  

#### Design
A validator is intended for comparison of an Object representing an Actual state, against an Object representing an Expected state.  Ideally the objects compared share an interface.  

Each concrete validator extends `abstract class Validator`.  Best practice is for the constructor to store both the expected and the actual objects as private members, which are referenced in the 'verify' statements.

Each concrete validator should implement either of the following, but not both (though combining the two is supported):
 - detailed verifications defined in `executeVerifications()` (can be empty, resulting in a NO-OP)
 - addition of child validators in the constructor

This approach will result in each Validator being either:
 - detailed verification of an Object, or
 - parent validator orchestrating execution of child validators

#### Verifications
Implementation of `protected abstract void executeVerifications()`:
 - add an individual verification for each member of an object to be reconciled
 - for readability, verifications are written as 
```
verify("Member description", expected.getMember(), actual.getMember());
```
 - during execution, each verification will be logged to stdout, with each failure returned as part of a returned String value
 - best practice is to reconcile only Object wrappers (e.g. Integer, String, Boolean), allowing for scenarios in which a 'null' may be acceptable
 - rather than calling this protected method from a concrete validator, `public String validate()` should be called which returns all failure details

#### Children
For validation of members that are NOT Object wrappers:
 - implement a Validator for the specific Type, which is considered a "child" of the Type being validated
 - in the constructor of the Validator for the "parent" Type, add the child validator
```
addChildValidator(ChildValidator.getInstance(expected.getChildMember(), actual.getChildMember());
``` 
 - child validators are added to a list which is processed when the public method `validate()` is called
 
#### Validation
 1. The Validator description is logged
 2. Each verification is logged (member description and expected value)
 3. Each verification failure is added appended to a 'failure' String (returned by `validate()`)
 4. Steps 1-3 are completed for each child validator (which can themselves contain child validators)
 5. The String containing the compiled list of all failures is returned

#### Known Issues (discouraged):
Though use is discouraged, `addKnownIssue()` can be used in cases where a test failure is acceptable.  The reason that this it is considered bad practice (and an impediment to Continuous Delivery) to allow failures to occur without addressing them and getting the project under test back to "green".  
At the time of validation, 
 - if a failure occurs, then the known issue will be included in the Assertion message.
 - if the known issue has been addressed so that the test SHOULD pass, the known issue will cause a test failure with a message indicating that the issue has been addressed and should be manually regressed
 - once confirmed, the addition of the 'known issue' must be removed from the validator to allow it to pass
