package acc240.calculator.desktop.utils;

/**
 * The possible keyboard inputs, most of which may appear in equations
 */
public enum Data {
    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"),
    OPEN_PARENTHESIS("("), CLOSE_PARENTHESIS(")"),
    EXPONENT("^"), SQUARE_ROOT("sqrt("), PLUS_MINUS(true, "-"),
    PERIOD(true, "."), CALCULATE(""), X("x"), ANSWER("a"),
    STORE("ERR"), VARIABLES("ERR"), E("e"), PI("pi"),
    ABSOLUTE_VALUE("abs("), NATURAL_LOG("ln("), LOG("log("),
    SINE("sin("), COSINE("cos("), TANGENT("tan("),
    ARCSINE("asin("), ARCCOSINE("acos("), ARCTANGENT("atan("),
    ZERO(true, "0"), ONE(true, "1"), TWO(true, "2"),
    THREE(true, "3"), FOUR(true, "4"), FIVE(true, "5"),
    SIX(true, "6"), SEVEN(true, "7"), EIGHT(true, "8"),
    NINE(true, "9"), TEN(true, "A"), ELEVEN(true, "B"),
    TWELVE(true, "C"), THIRTEEN(true, "D"), FOURTEEN(true, "E"),
    FIFTEEN(true, "F"), BACKWARD("ERR"), FORWARD("ERR"),
    BACKSPACE("ERR"), VAR_B("b"), VAR_C("c"), VAR_D("d"),
    VAR_F("f"), VAR_G("g"), VAR_H("h"), VAR_I("i"), VAR_J("j"),
    VAR_K("k"), VAR_L("l"), VAR_M("m"), VAR_N("n"), VAR_O("o"),
    VAR_P("p"), VAR_Q("q"), VAR_R("r"), VAR_S("s"), VAR_T("t"),
    VAR_U("u"), VAR_V("v"), VAR_W("w"), VAR_X("x"), VAR_Y("y"),
    VAR_Z("z"), CHANGE_BASE("ERR");

    private boolean number;
    private String representation;

    Data(String representation) {
        this(false, representation);
    }

    Data(boolean number, String representation) {
        this.number = number;
        this.representation = representation;
    }

    public boolean isNumber() { return number; }

    public String getRepresentation() { return representation; }

    public static Data getVar(String var) {
        switch (var.charAt(0)) {
            case 'b':
                return VAR_B;
            case 'c':
                return VAR_C;
            case 'd':
                return VAR_D;
            case 'f':
                return VAR_F;
            case 'g':
                return VAR_G;
            case 'h':
                return VAR_H;
            case 'i':
                return VAR_I;
            case 'j':
                return VAR_J;
            case 'k':
                return VAR_K;
            case 'l':
                return VAR_L;
            case 'm':
                return VAR_M;
            case 'n':
                return VAR_N;
            case 'o':
                return VAR_O;
            case 'p':
                return VAR_P;
            case 'q':
                return VAR_Q;
            case 'r':
                return VAR_R;
            case 's':
                return VAR_S;
            case 't':
                return VAR_T;
            case 'u':
                return VAR_U;
            case 'v':
                return VAR_V;
            case 'w':
                return VAR_W;
            case 'x':
                return VAR_X;
            case 'y':
                return VAR_Y;
            case 'z':
                return VAR_Z;
            default:
                throw new IllegalArgumentException("Variable '" + var + "' does not exists.");
        }
    }
}
