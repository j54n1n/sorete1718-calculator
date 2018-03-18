package acc240.calculator.desktop.keyboard;

import acc240.calculator.desktop.display.GraphingDisplay;
import acc240.calculator.desktop.utils.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The keyboard for the graphing mode of the calculator
 */
public class GraphingKeyboard extends JPanel implements ActionListener, KeyListener {

    private GraphingDisplay display;

    private JButton add, subtract, multiply, divide;
    private JButton openParenthesis, closeParenthesis;
    private JButton exponent, squareRoot, log, naturalLog;
    private JButton absoluteValue, pi, e;
    private JButton sine, cosine, tangent;
    private JButton arcsine, arccosine, arctangent;
    private JButton plusMinus, period, calculate;
    private JButton x;
    private JButton forward, backward, backspace;

    private JButton[] numbers;

    public GraphingKeyboard(GraphingDisplay display) {
        this.display = display;

        addKeyListener(this);

        Font font = new Font(Font.SERIF, Font.PLAIN, 24);

        add = new JButton("+");
        add.setFont(font);
        add.addActionListener(this);
        add.putClientProperty("ID", Data.ADD);

        subtract = new JButton("-");
        subtract.setFont(font);
        subtract.addActionListener(this);
        subtract.putClientProperty("ID", Data.SUBTRACT);

        multiply = new JButton("×");
        multiply.setFont(font);
        multiply.addActionListener(this);
        multiply.putClientProperty("ID", Data.MULTIPLY);

        divide = new JButton("/");
        divide.setFont(font);
        divide.addActionListener(this);
        divide.putClientProperty("ID", Data.DIVIDE);

        openParenthesis = new JButton("(");
        openParenthesis.setFont(font);
        openParenthesis.addActionListener(this);
        openParenthesis.putClientProperty("ID", Data.OPEN_PARENTHESIS);

        closeParenthesis = new JButton(")");
        closeParenthesis.setFont(font);
        closeParenthesis.addActionListener(this);
        closeParenthesis.putClientProperty("ID", Data.CLOSE_PARENTHESIS);

        exponent = new JButton("^");
        exponent.setFont(font);
        exponent.addActionListener(this);
        exponent.putClientProperty("ID", Data.EXPONENT);

        squareRoot = new JButton("√");
        squareRoot.setFont(font);
        squareRoot.addActionListener(this);
        squareRoot.putClientProperty("ID", Data.SQUARE_ROOT);

        log = new JButton("log(");
        log.setFont(font);
        log.addActionListener(this);
        log.putClientProperty("ID", Data.LOG);

        naturalLog = new JButton("ln(");
        naturalLog.setFont(font);
        naturalLog.addActionListener(this);
        naturalLog.putClientProperty("ID", Data.NATURAL_LOG);

        absoluteValue = new JButton("abs(");
        absoluteValue.setFont(font);
        absoluteValue.addActionListener(this);
        absoluteValue.putClientProperty("ID", Data.ABSOLUTE_VALUE);


        pi = new JButton("π");
        pi.setFont(font);
        pi.addActionListener(this);
        pi.putClientProperty("ID", Data.PI);

        e = new JButton("e");
        e.setFont(font);
        e.addActionListener(this);
        e.putClientProperty("ID", Data.E);

        sine = new JButton("sin(");
        sine.setFont(font);
        sine.addActionListener(this);
        sine.putClientProperty("ID", Data.SINE);

        cosine = new JButton("cos(");
        cosine.setFont(font);
        cosine.addActionListener(this);
        cosine.putClientProperty("ID", Data.COSINE);

        tangent = new JButton("tan(");
        tangent.setFont(font);
        tangent.addActionListener(this);
        tangent.putClientProperty("ID", Data.TANGENT);

        arcsine = new JButton("asin(");
        arcsine.setFont(font);
        arcsine.addActionListener(this);
        arcsine.putClientProperty("ID", Data.ARCSINE);

        arccosine = new JButton("acos(");
        arccosine.setFont(font);
        arccosine.addActionListener(this);
        arccosine.putClientProperty("ID", Data.ARCCOSINE);

        arctangent = new JButton("atan(");
        arctangent.setFont(font);
        arctangent.addActionListener(this);
        arctangent.putClientProperty("ID", Data.ARCTANGENT);

        plusMinus = new JButton("±");
        plusMinus.setFont(font);
        plusMinus.addActionListener(this);
        plusMinus.putClientProperty("ID", Data.PLUS_MINUS);

        period = new JButton(".");
        period.setFont(font);
        period.addActionListener(this);
        period.putClientProperty("ID", Data.PERIOD);

        calculate = new JButton("Graph");
        calculate.setFont(font);
        calculate.addActionListener(this);
        calculate.putClientProperty("ID", Data.CALCULATE);

        x = new JButton("X");
        x.setFont(font);
        x.addActionListener(this);
        x.putClientProperty("ID", Data.X);

        forward = new JButton("->");
        forward.setFont(font);
        forward.addActionListener(this);
        forward.putClientProperty("ID", Data.FORWARD);

        backward = new JButton("<-");
        backward.setFont(font);
        backward.addActionListener(this);
        backward.putClientProperty("ID", Data.BACKWARD);

        backspace = new JButton("Backspace");
        backspace.setFont(font);
        backspace.addActionListener(this);
        backspace.putClientProperty("ID", Data.BACKSPACE);

        numbers = new JButton[16];
        Data[] nums = {Data.ZERO, Data.ONE,
                Data.TWO, Data.THREE, Data.FOUR,
                Data.FIVE, Data.SIX, Data.SEVEN,
                Data.EIGHT, Data.NINE, Data.TEN,
                Data.ELEVEN, Data.TWELVE, Data.THIRTEEN,
                Data.FOURTEEN, Data.FIFTEEN};
        for(int i = 0; i < 16; i++) {
            numbers[i] = new JButton(Integer.toHexString(i));
            numbers[i].setFont(font);
            numbers[i].addActionListener(this);
            numbers[i].putClientProperty("ID", nums[i]);
        }

        setLayout(new GridLayout(4, 9));
        setup();
    }

    /*
        log  cos  acos  X    7    8    9    +    ->
        ln   sin  asin  (    4    5    6    -    <-
        pi   tan  atan  )    1    2    3    *    back
        e    abs   ^   sqrt +/-   0    .    /    graph
     */
    private void setup() {
        add(log);
        add(cosine);
        add(arccosine);
        add(x);
        add(numbers[7]);
        add(numbers[8]);
        add(numbers[9]);
        add(add);
        add(forward);

        add(naturalLog);
        add(sine);
        add(arcsine);
        add(openParenthesis);
        add(numbers[4]);
        add(numbers[5]);
        add(numbers[6]);
        add(subtract);
        add(backward);

        add(pi);
        add(tangent);
        add(arctangent);
        add(closeParenthesis);
        add(numbers[1]);
        add(numbers[2]);
        add(numbers[3]);
        add(multiply);
        add(backspace);

        add(e);
        add(absoluteValue);
        add(exponent);
        add(squareRoot);
        add(plusMinus);
        add(numbers[0]);
        add(period);
        add(divide);
        add(calculate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Data data = (Data)((JComponent)e.getSource()).getClientProperty("ID");
        display.input(data);
        requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_0:
            case KeyEvent.VK_NUMPAD0:
                if(e.getKeyChar() == '0')
                    display.input(Data.ZERO);
                else if(e.getKeyChar() == ')')
                    display.input(Data.CLOSE_PARENTHESIS);
                break;
            case KeyEvent.VK_1:
            case KeyEvent.VK_NUMPAD1:
                display.input(Data.ONE);
                break;
            case KeyEvent.VK_2:
            case KeyEvent.VK_NUMPAD2:
                display.input(Data.TWO);
                break;
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:
                display.input(Data.THREE);
                break;
            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4:
                display.input(Data.FOUR);
                break;
            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5:
                display.input(Data.FIVE);
                break;
            case KeyEvent.VK_6:
            case KeyEvent.VK_NUMPAD6:
                if(e.getKeyChar() == '6')
                    display.input(Data.SIX);
                else if(e.getKeyChar() == '^')
                    display.input(Data.EXPONENT);
                break;
            case KeyEvent.VK_7:
            case KeyEvent.VK_NUMPAD7:
                display.input(Data.SEVEN);
                break;
            case KeyEvent.VK_8:
            case KeyEvent.VK_NUMPAD8:
                if(e.getKeyChar() == '8')
                    display.input(Data.EIGHT);
                else if(e.getKeyChar() == '*')
                    display.input(Data.MULTIPLY);
                break;
            case KeyEvent.VK_9:
            case KeyEvent.VK_NUMPAD9:
                if(e.getKeyChar() == '9')
                    display.input(Data.NINE);
                else if(e.getKeyChar() == '(')
                    display.input(Data.OPEN_PARENTHESIS);
                break;
            case KeyEvent.VK_LEFT:
                display.input(Data.BACKWARD);
                break;
            case KeyEvent.VK_RIGHT:
                display.input(Data.FORWARD);
                break;
            case KeyEvent.VK_BACK_SPACE:
                display.input(Data.BACKSPACE);
                break;
            case KeyEvent.VK_EQUALS:
                if(e.getKeyChar() == '=')
                    display.input(Data.CALCULATE);
                else if(e.getKeyChar() == '+')
                    display.input(Data.ADD);
                break;
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_ADD:
                display.input(Data.ADD);
                break;
            case KeyEvent.VK_MINUS:
            case KeyEvent.VK_SUBTRACT:
                display.input(Data.SUBTRACT);
                break;
            case KeyEvent.VK_ASTERISK:
            case KeyEvent.VK_MULTIPLY:
                display.input(Data.MULTIPLY);
                break;
            case KeyEvent.VK_SLASH:
            case KeyEvent.VK_DIVIDE:
                display.input(Data.DIVIDE);
                break;
            case KeyEvent.VK_LEFT_PARENTHESIS:
                display.input(Data.OPEN_PARENTHESIS);
                break;
            case KeyEvent.VK_RIGHT_PARENTHESIS:
                display.input(Data.CLOSE_PARENTHESIS);
                break;
            case KeyEvent.VK_ENTER:
                display.input(Data.CALCULATE);
                break;
            case KeyEvent.VK_E:
                display.input(Data.E);
                break;
            case KeyEvent.VK_P:
                display.input(Data.PI);
                break;
            case KeyEvent.VK_X:
                display.input(Data.X);
                break;
            case KeyEvent.VK_PERIOD:
                display.input(Data.PERIOD);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
