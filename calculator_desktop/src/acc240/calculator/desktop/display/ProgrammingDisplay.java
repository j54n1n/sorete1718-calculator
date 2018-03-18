package acc240.calculator.desktop.display;

import acc240.calculator.desktop.keyboard.ProgrammingKeyboard;
import acc240.calculator.desktop.utils.Data;
import acc240.calculator.desktop.utils.Node;
import acc240.math.Parser;
import acc240.utils.Variables;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * The display for the programming mode of the calculator
 */
public class ProgrammingDisplay extends Display {

    public enum Base {
        BINARY, DECIMAL, HEXADECIMAL
    }
    private Base base;

    private Node equation, equationEnd, thisNumber, cursor;
    private ProgrammingKeyboard keyboard;
    private int answer = 0;

    public ProgrammingDisplay() {
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(0, 150));

        equation = new Node(null, null, null);
        equationEnd = equation;

        thisNumber = new Node(null, null, null);
        cursor = thisNumber;

        base = Base.DECIMAL;

        Parser.setVariables(new Variables());
    }

    public void setKeyboard(ProgrammingKeyboard keyboard) {
        this.keyboard = keyboard;

        int baseValue = 10;
        if(base == Base.DECIMAL)
            baseValue = 10;
        else if(base == Base.BINARY)
            baseValue = 2;
        else if(base == Base.HEXADECIMAL)
            baseValue = 16;
        keyboard.setBase(baseValue);
    }

    public Base getBase() {
        return base;
    }

    @Override
    public void input(Data input) {
        switch(input) {
            case CALCULATE:
                calculate();
                break;
            case BACKSPACE:
                Node previous = cursor.getPrevious();
                if(previous != null) {
                    Node next = cursor.getNext();

                    previous.setNext(next);
                    if(next != null)
                        next.setPrevious(previous);

                    cursor = previous;
                }
                repaint();
                break;
            case BACKWARD:
                if(cursor.getPrevious() != null)
                    cursor = cursor.getPrevious();
                repaint();
                break;
            case FORWARD:
                if(cursor.getNext() != null)
                    cursor = cursor.getNext();
                repaint();
                break;
            case CHANGE_BASE:
                if(thisNumber.getNext() == null) { // if only a head node
                    switch (base) {
                        case DECIMAL:
                            base = Base.BINARY;
                            keyboard.setBase(2);
                            break;
                        case BINARY:
                            base = Base.HEXADECIMAL;
                            keyboard.setBase(16);
                            break;
                        case HEXADECIMAL:
                            base = Base.DECIMAL;
                            keyboard.setBase(10);
                            break;
                        default:
                            throw new UnknownError("Unknown enum state encountered.");
                    }
                }
                repaint();
                break;
            default:
                if(input.isNumber()) {
                    cursor.setNext(new Node(input, cursor.getNext(), cursor));
                    cursor = cursor.getNext();
                } else {
                    if(equation.getNext() == null && thisNumber.getNext() == null) {
                        equation.setNext(new Node(Data.ANSWER, null, equation));
                        equationEnd = equation.getNext();
                    } else {
                        addPresentNumber();
                    }

                    equationEnd.setNext(new Node(input, null, equationEnd));
                    equationEnd = equationEnd.getNext();
                }
                repaint();
        }
    }

    private int getPresentNumber() {
        StringBuilder number = new StringBuilder();
        Node node = thisNumber.getNext();
        while(node != null) {
            number.append(node.getData().getRepresentation());
            node = node.getNext();
        }

        int baseValue;
        if(base == Base.BINARY)
            baseValue = 2;
        else if(base == Base.HEXADECIMAL)
            baseValue = 16;
        else
            baseValue = 10;

        if(number.length() > 0 && (number.charAt(0) != '-' || number.length() > 1)) {
            try {
                return Integer.parseInt(number.toString(), baseValue);
            } catch(NumberFormatException e) {
                int value = Integer.parseInt(number.substring(0, number.length() - 1), baseValue);
                node = thisNumber.getNext();
                while(node.getNext() != null)
                    node = node.getNext();
                node.getPrevious().setNext(null);
                cursor = cursor.getPrevious();
                return value;
            }
        } else
            return 0;
    }

    private void addPresentNumber() {
        if(thisNumber.getNext() != null) {
            int value = getPresentNumber();
            String baseTen = Integer.toString(value);

            for (int c = 0; c < baseTen.length(); c++) {
                switch (baseTen.charAt(c)) {
                    case '-':
                        equationEnd.setNext(new Node(Data.PLUS_MINUS, null, equationEnd));
                        break;
                    case '0':
                        equationEnd.setNext(new Node(Data.ZERO, null, equationEnd));
                        break;
                    case '1':
                        equationEnd.setNext(new Node(Data.ONE, null, equationEnd));
                        break;
                    case '2':
                        equationEnd.setNext(new Node(Data.TWO, null, equationEnd));
                        break;
                    case '3':
                        equationEnd.setNext(new Node(Data.THREE, null, equationEnd));
                        break;
                    case '4':
                        equationEnd.setNext(new Node(Data.FOUR, null, equationEnd));
                        break;
                    case '5':
                        equationEnd.setNext(new Node(Data.FIVE, null, equationEnd));
                        break;
                    case '6':
                        equationEnd.setNext(new Node(Data.SIX, null, equationEnd));
                        break;
                    case '7':
                        equationEnd.setNext(new Node(Data.SEVEN, null, equationEnd));
                        break;
                    case '8':
                        equationEnd.setNext(new Node(Data.EIGHT, null, equationEnd));
                        break;
                    case '9':
                        equationEnd.setNext(new Node(Data.NINE, null, equationEnd));
                        break;
                    default:
                        throw new UnknownError("Integer value is not a number");
                }
                equationEnd = equationEnd.getNext();
            }

            thisNumber = new Node(null, null, null);
            cursor = thisNumber;
        }
    }

    private void calculate() {
        addPresentNumber();

        if(equation.getNext() != null) {

            String str = getEquation(equation);

            String postfix = Parser.infixToPostfix(str);
            double result = Parser.evaluateWithValue(postfix);
            answer = (int) result;

            Parser.addVariable('a', (double) answer);

            equation = new Node(null, null, null); // Header node
            equationEnd = equation;
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);

        int value;
        if(thisNumber.getNext() == null)
            value = answer;
        else {
            value = getPresentNumber();
            answer = 0;
        }

        drawHex(g, value, 10, 40);
        drawDecimal(g, value, 10, 90);
        drawBinary(g, value, 10, 140);
    }

    private void drawHex(Graphics g, int value, int x, int y) {
        String str = Integer.toHexString(value);
        str = str.toUpperCase();

        if(str.length() > 4) {
            str = str.substring(0, str.length() - 4) + " " + str.substring(str.length() - 4);
        }

        g.setColor(base == Base.HEXADECIMAL ? new Color(0xEE76000) : Color.GREEN);
        g.drawString(str, x, y);

        if(base == Base.HEXADECIMAL) {
            int charCount = 0;
            Node node = thisNumber.getNext();

            if(node != null) {
                for (int c = 0; c < str.length(); c++) {
                    charCount++;
                    if (str.charAt(c) != ' ') {
                        if (node == cursor)
                            break;
                        node = node.getNext();
                    }
                }

                g.setColor(Color.YELLOW);
                int xVal = x + g.getFontMetrics().stringWidth(str.substring(0, charCount));
                g.drawLine(xVal, y, xVal, y - 30);
            }
        }
    }

    private void drawDecimal(Graphics g, int value, int x, int y) {
        DecimalFormat fmt = new DecimalFormat("#,###");
        String number = fmt.format(value);

        g.setColor(base == Base.DECIMAL ? new Color(0xEE76000) : Color.GREEN);
        g.drawString(number, x, y);

        if(base == Base.DECIMAL) {
            int charCount = 0;
            Node node = thisNumber.getNext();

            if(node != null) {
                for (int c = 0; c < number.length(); c++) {
                    charCount++;
                    if (number.charAt(c) != ',') {
                        if (node == cursor)
                            break;
                        node = node.getNext();
                    }
                }

                g.setColor(Color.YELLOW);
                int xVal = x + g.getFontMetrics().stringWidth(number.substring(0, charCount));
                g.drawLine(xVal, y, xVal, y - 30);
            }
        }
    }

    private void drawBinary(Graphics g, int value, int x, int y) {
        String str = Integer.toBinaryString(value);
        StringBuilder builder = new StringBuilder();

        int count = 0;
        for(int c = str.length() - 1; c >= 0; c--) {
            builder.insert(0, str.charAt(c));
            count++;
            if(count == 4) {
                builder.insert(0, ' ');
                count = 0;
            }
        }
        if(builder.charAt(0) == ' ')
            builder.deleteCharAt(0);

        g.setColor(base == Base.BINARY ? new Color(0xEE76000) : Color.GREEN);
        g.drawString(builder.toString(), x, y);

        if(base == Base.BINARY) {
            int charCount = 0;
            Node node = thisNumber.getNext();
            String string = builder.toString();

            if(node != null) {
                for (int c = 0; c < string.length(); c++) {
                    charCount++;
                    if (string.charAt(c) != ' ') {
                        if (node == cursor)
                            break;
                        node = node.getNext();
                    }
                }

                g.setColor(Color.YELLOW);
                int xVal = x + g.getFontMetrics().stringWidth(builder.substring(0, charCount));
                g.drawLine(xVal, y, xVal, y - 30);
            }
        }
    }
}
