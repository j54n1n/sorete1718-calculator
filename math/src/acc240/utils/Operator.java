package acc240.utils;

/**
 * Mathematical operators.
 *
 * @author Aaron Councilman
 */
public enum Operator {
    ADDITION(1, " +"),
    SUBTRACTION(1, " -"),
    MULTIPLICATION(2, " *"),
    DIVISION(2, " /"),
    EXPONENTIATION(3, " ^", false),
    PARENTHESIS(0, " ("),
    SINE(0, " sin"),
    COSINE(0, " cos"),
    TANGENT(0, " tan"),
    ARCSINE(0, " asin"),
    ARCCOSINE(0, " acos"),
    ARCTANGENT(0, " atan"),
    SQUARE_ROOT(0, " sqrt"),
    ABSOLUTE_VALUE(0, " abs"),
    LOG(0, " log"),
    NATURAL_LOG(0, " ln");

    private int level;
    private String text;
    private boolean left;

    Operator(int level, String text) {
        this.level = level;
        this.text = text;
        left = true;
    }

    Operator(int level, String text, boolean left) {
        this.level = level;
        this.text = text;
        this.left = left;
    }

    /**
     * Gets the level of operator precedence.
     *
     * <b>Note:</b> levels increase (3 &gt; 2 &gt; 1) except that all functions and
     * parenthesis are level 0.
     *
     * @return The level of the operator
     */
    public int getLevel() {return level;}

    /**
     * Get whether or not the operator is left to right precedent.
     *
     * true if it is left to right, false if it is right to left.
     *
     * @return The left to right precedence of the operator.
     */
    public boolean getLeftAssociation() {return left;}

    /**
     * Get a string representation of the operator.
     *
     * All strings will include a space before the operator.
     *
     * @return A string representation of the operator.
     */
    @Override
    public String toString() {return text;}
}
