# validator4test
Class used to define, manage and log validation of objects during automated test execution.

Impelement specific verifications in the executeVerifications() method.  Verify only primitive data types (e.g. String, Boolean, Integer) of the object being validated.  For non-primitive objects, add child validators.

Notes about known bugs or other issues causing validation failures an be introduced by including calls to addKnownIssue() in the executeVerifications() method.

For validation of non-primitive types, add child validators in the constructor using addChildValidator().  Child validators must including "this" as the parent validator in the arguments to ensure proper indentation management for logging and reporting.  

Using this approach, there can be multiple levels of nested validators.
