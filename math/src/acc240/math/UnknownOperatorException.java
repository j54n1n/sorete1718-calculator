package acc240.math;

/**
 * An exception that is generated if an unrecognized operator or function is processed by the system
 * and the code fails to convert it to an Operator.
 *
 * @author Aaron Councilman
 */
public class UnknownOperatorException extends RuntimeException {
    public UnknownOperatorException() {
        super("An attempt to process an unrecognized operator or function occurred.");
    }
}
