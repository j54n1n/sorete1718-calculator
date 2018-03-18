package acc240.math;

import acc240.utils.Dictionary;
import acc240.utils.Operator;
import acc240.utils.OperatorStack;
import acc240.utils.ValueStack;

/**
 * A class of static methods to evaluate mathematical equations.
 * There are also methods to evaluate an expression for a certain value of a variable.
 *
 * @author Aaron Councilman
 */
public final class Parser {

    private Parser() {}

    private static Dictionary<Character, Double> variables;

    public static void setVariables(Dictionary<Character, Double> variables) {
        Parser.variables = variables;
    }

    public static void addVariable(Character key, Double val) {
        Parser.variables.add(key, val);
    }

    /**
     * Converts an infix expression to a postfix expression.
     *
     * The infix expression <b>must</b> separate operators and values by a single space.
     * Additionally functions must be followed directly by their parenthesis (no space)
     * and closed with a parenthesis at the end.
     * Failure to do so will result in incorrect postfix expressions, or exceptions.
     *
     * @param infix A mathematical expression in infix notation
     * @return A mathematical expression equivalent to infix in postfix notation.
     */
    public static String infixToPostfix(String infix) {
        String res = "";
        OperatorStack stack = new OperatorStack();

        String[] pieces = infix.split(" ");
        for(String piece : pieces) {
            if(piece.equals(")")) {
                boolean found = false;
                while(stack.length() > 0 && !found) {
                    Operator operator = stack.pop();
                    if(operator.getLevel() != Operator.PARENTHESIS.getLevel()) {
                        res += operator;
                    } else {
                        found = true;
                        if(operator != Operator.PARENTHESIS) {
                            res += operator;
                        }
                    }
                }
                if(stack.length() == 0 && !found) {
                    throw new MalformedInfixException();
                }
            } else if(piece.contains("(")) {
                Operator function = getOperator(piece);
                stack.push(function);
            } else {
                switch (piece) {
                    case "+":
                    case "-":
                    case "*":
                    case "/":
                    case "^":
                        Operator operator = getOperator(piece);
                        if(stack.length() == 0 || stack.peek().getLevel() < operator.getLevel()) {
                            stack.push(operator);
                        } else if(stack.peek().getLevel() == operator.getLevel() && !operator.getLeftAssociation()) {
                            stack.push(operator);
                        } else {
                            if(!operator.getLeftAssociation()) {
                                while(stack.peek().getLevel() > operator.getLevel())
                                    res += stack.pop();
                            } else {
                                while(stack.length() > 0 && stack.peek().getLevel() >= operator.getLevel())
                                    res += stack.pop();
                            }
                            stack.push(operator);
                        }
                        break;
                    default:
                        if(res.length() > 0)
                            res += " " + piece;
                        else
                            res += piece;
                }
            }
        }

        while(stack.length() > 0) {
            res += stack.pop();
        }

        return res;
    }

    /**
     * Evaluates a postfix expression and returns the value.
     *
     * The postfix expression <b>must</b> separate operators and values by a single space.
     * Functions must be denoted as simply the abbreviation of that function (eg. sin)
     * Failure to do so will result in incorrect results or exceptions.
     * No variables are allowed with this method. For that use evaluateWithValue.
     *
     * @param postfix A mathematical expression in postfix notation
     * @return The result of the expression
     */
    public static double evaluatePostfix(String postfix) {
        ValueStack stack = new ValueStack();
        String[] parts = postfix.split(" ");

        for(String part : parts) {
            double op1, op2;
            switch (part) {
                case "+":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 + op1);
                    break;
                case "-":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 - op1);
                    break;
                case "*":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 * op1);
                    break;
                case "/":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 / op1);
                    break;
                case "^":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(Math.pow(op2, op1));
                    break;
                case "sin":
                    op1 = stack.pop();
                    stack.push(Math.sin(op1));
                    break;
                case "cos":
                    op1 = stack.pop();
                    stack.push(Math.cos(op1));
                    break;
                case "tan":
                    op1 = stack.pop();
                    stack.push(Math.tan(op1));
                    break;
                case "asin":
                    op1 = stack.pop();
                    stack.push(Math.asin(op1));
                    break;
                case "acos":
                    op1 = stack.pop();
                    stack.push(Math.acos(op1));
                    break;
                case "atan":
                    op1 = stack.pop();
                    stack.push(Math.atan(op1));
                    break;
                case "sqrt":
                    op1 = stack.pop();
                    stack.push(Math.sqrt(op1));
                    break;
                case "abs":
                    op1 = stack.pop();
                    stack.push(Math.abs(op1));
                    break;
                case "log":
                    op1 = stack.pop();
                    stack.push(Math.log10(op1));
                    break;
                case "ln":
                    op1 = stack.pop();
                    stack.push(Math.log(op1));
                    break;
                case "pi":
                    stack.push(Math.PI);
                    break;
                case "e":
                    stack.push(Math.E);
                    break;
                default:
                    stack.push(Double.parseDouble(part));
            }
        }

        return stack.pop();
    }

    /**
     * Evaluates a postfix expression and returns the value.
     *
     * The postfix expression <b>must</b> separate operators and values by a single space.
     * Functions must be denoted as simply the abbreviation of that function (eg. sin)
     * Failure to do so will result in incorrect results or exceptions.
     * Any variables are based on the values in variables.
     *
     * @param postfix A mathematical expression in postfix notation
     * @return The result of the expression
     */
    public static double evaluateWithValue(String postfix) {
        ValueStack stack = new ValueStack();
        String[] parts = postfix.split(" ");

        for(String part : parts) {
            double op1, op2;
            switch (part) {
                case "+":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 + op1);
                    break;
                case "-":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 - op1);
                    break;
                case "*":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 * op1);
                    break;
                case "/":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(op2 / op1);
                    break;
                case "^":
                    op1 = stack.pop();
                    op2 = stack.pop();
                    stack.push(Math.pow(op2, op1));
                    break;
                case "sin":
                    op1 = stack.pop();
                    stack.push(Math.sin(op1));
                    break;
                case "cos":
                    op1 = stack.pop();
                    stack.push(Math.cos(op1));
                    break;
                case "tan":
                    op1 = stack.pop();
                    stack.push(Math.tan(op1));
                    break;
                case "asin":
                    op1 = stack.pop();
                    stack.push(Math.asin(op1));
                    break;
                case "acos":
                    op1 = stack.pop();
                    stack.push(Math.acos(op1));
                    break;
                case "atan":
                    op1 = stack.pop();
                    stack.push(Math.atan(op1));
                    break;
                case "sqrt":
                    op1 = stack.pop();
                    stack.push(Math.sqrt(op1));
                    break;
                case "abs":
                    op1 = stack.pop();
                    stack.push(Math.abs(op1));
                    break;
                case "log":
                    op1 = stack.pop();
                    stack.push(Math.log10(op1));
                    break;
                case "ln":
                    op1 = stack.pop();
                    stack.push(Math.log(op1));
                    break;
                case "pi":
                    stack.push(Math.PI);
                    break;
                case "e":
                    stack.push(Math.E);
                    break;
                default:
                    if(part.charAt(0) < 'a')
                        stack.push(Double.parseDouble(part));
                    else
                        stack.push(variables.getValue(part.charAt(0)));
            }
        }

        return stack.pop();
    }



    private static Operator getOperator(String operator) {
        switch(operator) {
            case "+":
                return Operator.ADDITION;
            case "-":
                return Operator.SUBTRACTION;
            case "*":
                return Operator.MULTIPLICATION;
            case "/":
                return Operator.DIVISION;
            case "^":
                return  Operator.EXPONENTIATION;
            case "(":
                return Operator.PARENTHESIS;
            case "sin(":
                return Operator.SINE;
            case "cos(":
                return Operator.COSINE;
            case "tan(":
                return Operator.TANGENT;
            case "asin(":
                return Operator.ARCSINE;
            case "acos(":
                return Operator.ARCCOSINE;
            case "atan(":
                return Operator.ARCTANGENT;
            case "sqrt(":
                return Operator.SQUARE_ROOT;
            case "abs(":
                return Operator.ABSOLUTE_VALUE;
            case "log(":
                return Operator.LOG;
            case "ln(":
                return Operator.NATURAL_LOG;
            default:
                throw new UnknownOperatorException();
        }
    }
}
