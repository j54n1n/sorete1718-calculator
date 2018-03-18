package acc240.math;

/**
 * An exception to represent the failure of processing due to a
 * malformed postfix expression.
 *
 * @author Aaron Councilman
 */
public class MalformedPostfixExpression extends RuntimeException {
    public MalformedPostfixExpression() {
        super("A malformed postfix expression was encountered.");
    }
}
