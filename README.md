# SOEN 7481 Software Verification and Testing

## Fall 2021

### Assignment Description

BUG PATTERNS

1. Class defines hashCode() but not equals(). This class defines a hashCode() method but not an equals() method.
Therefore, the class may violate the invariant that equal objects must have equal hashcodes. From FindBugs1
2. equals() method does not check for null argument. This implementation of equals(Object) violates the contract
defined by java.lang.Object.equals() because it does not check for null being passed as the argument. All equals()
methods should return false if passed a null value. From FindBugs2
3. Inadequate logging information in catch blocks. Developers usually rely on logs for error diagnostics when
exceptions occur. However, sometimes, duplicate logging statements in different catch blocks of the same try
block may cause debugging difficulties since the logs fail to tell which exception occurred.
For example:
...
} catch (AlreadyClosedException closedException) {
s_logger.warn("Connection to AMQP service is lost.");
} catch (ConnectException connectException) {
s_logger.warn("Connection to AMQP service is lost.");