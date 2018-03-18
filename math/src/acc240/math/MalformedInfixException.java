package acc240.math;

/**
 * An exception to represent the failure of processing due to a
 * malformed infix expression.
 *
 * @author Aaron Councilman
 */
public class MalformedInfixException extends RuntimeException {
    public MalformedInfixException() {
        super("A malformed infix expression was encountered.");
    }
}
