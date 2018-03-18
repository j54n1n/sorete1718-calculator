package acc240.calculator.desktop.display;

import acc240.calculator.desktop.utils.Data;
import acc240.calculator.desktop.utils.Node;
import acc240.math.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The display for the main calculator
 */
public class CalculatorDisplay extends Display {

    private Node lastLineStart;
    private Double lastAnswer;

    private Node thisLineStart;
    private Node cursor;

    private static final String[] variableList = {"b", "c", "d", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public CalculatorDisplay() {
        setPreferredSize(new Dimension(0, 120));
        setBackground(Color.DARK_GRAY);

        lastLineStart = null;
        lastAnswer = null;

        thisLineStart = new Node(null, null, null); // a header node
        cursor = thisLineStart;
    }

    @Override
    public void input(Data input) {
        switch (input) {
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
            case STORE:
                String[] options = {"last result", "current expression"};
                String mode = (String) JOptionPane.showInputDialog(this, "Store value of...", "Store Value",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                if(mode != null && mode.length() > 0) {
                    String variable = getVariable();

                    if(variable != null && variable.length() == 1) {
                        if(mode.equals(options[0])) {
                            if(lastAnswer != null)
                                Parser.addVariable(variable.charAt(0), lastAnswer);
                            else
                                JOptionPane.showMessageDialog(this, "Last answer does not exists. Cannot be stored",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            String equation = getEquation(thisLineStart);
                            if(equation.length() > 0) {
                                String postfix = Parser.infixToPostfix(equation);
                                double res = Parser.evaluateWithValue(postfix);
                                Parser.addVariable(variable.charAt(0), res);
                            } else {
                                JOptionPane.showMessageDialog(this, "Current expression is Empty. Cannot be evaluated",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                break;
            case VARIABLES:
                String choice = getVariable();
                if(choice != null && choice.length() == 1) {
                    cursor.setNext(new Node(Data.getVar(choice), cursor.getNext(), cursor));
                    cursor = cursor.getNext();
                    repaint();
                }
                break;
            default:
                if(thisLineStart.getNext() == null && isBinaryOperator(input)) {
                    cursor.setNext(new Node(Data.ANSWER, cursor.getNext(), cursor));
                    cursor = cursor.getNext();
                }
                if(input == Data.PLUS_MINUS) {
                    if(cursor.getData() == Data.PLUS_MINUS) {
                        Node last = cursor.getPrevious();
                        if(last != null) {
                            Node next = cursor.getNext();

                            last.setNext(next);
                            if(next != null)
                                next.setPrevious(last);

                            cursor = last;
                        }
                    } else {
                        cursor.setNext(new Node(input, cursor.getNext(), cursor));
                        cursor = cursor.getNext();
                    }
                } else {
                    cursor.setNext(new Node(input, cursor.getNext(), cursor));
                    cursor = cursor.getNext();
                }
                repaint();
        }
    }

    private String getVariable() {
        return (String) JOptionPane.showInputDialog(this, "Select variable",
                "Variable Input", JOptionPane.QUESTION_MESSAGE, null,
                variableList, variableList[0]);
    }

    private boolean isBinaryOperator(Data input) {
        switch(input) {
            case ADD:
            case SUBTRACT:
            case MULTIPLY:
            case DIVIDE:
            case EXPONENT:
                return true;
            default:
                return false;
        }
    }

    private void calculate() {
        if(thisLineStart.getNext() == null)
            thisLineStart = lastLineStart;

        String equation = getEquation(thisLineStart);

        String postfix = Parser.infixToPostfix(equation);
        double result = Parser.evaluateWithValue(postfix);

        lastLineStart = thisLineStart;
        lastAnswer = result;
        Parser.addVariable('a', lastAnswer);

        thisLineStart = new Node(null, null, null); // Header node
        cursor = thisLineStart; // Reset cursor

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(font);

        drawEquation(g, lastLineStart, null, 30);
        drawAnswer(g, lastAnswer, 70);
        drawEquation(g, thisLineStart, cursor, 110);
    }

    private void drawAnswer(Graphics g, Double answer, int y) {
        if(answer != null) {
            g.setColor(Color.CYAN);

            FontMetrics metrics = g.getFontMetrics(font);

            int x = getWidth();
            x -= metrics.stringWidth("" + answer);
            g.drawString("" + answer, x, y); // todo: Actually format the answer
        }
    }
}
