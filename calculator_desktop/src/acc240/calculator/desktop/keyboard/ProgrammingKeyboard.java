package acc240.calculator.desktop.keyboard;

import acc240.calculator.desktop.display.ProgrammingDisplay;
import acc240.calculator.desktop.utils.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The keyboard for the programmer calculator mode
 */
public class ProgrammingKeyboard extends JPanel implements ActionListener, KeyListener {

    private ProgrammingDisplay display;

    private JButton add, subtract, multiply, divide;
    private JButton openParenthesis, closeParenthesis;
    private JButton plusMinus, calculate, toggleBase;
    private JButton forward, backward, backspace;

    private JButton[] numbers;

    public ProgrammingKeyboard(ProgrammingDisplay display) {
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

        plusMinus = new JButton("±");
        plusMinus.setFont(font);
        plusMinus.addActionListener(this);
        plusMinus.putClientProperty("ID", Data.PLUS_MINUS);

        calculate = new JButton("=");
        calculate.setFont(font);
        calculate.addActionListener(this);
        calculate.putClientProperty("ID", Data.CALCULATE);

        toggleBase = new JButton("Change Base");
        toggleBase.setFont(font);
        toggleBase.addActionListener(this);
        toggleBase.putClientProperty("ID", Data.CHANGE_BASE);

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
        for (int i = 0; i < 16; i++) {
            numbers[i] = new JButton(Integer.toHexString(i).toUpperCase());
            numbers[i].setFont(font);
            numbers[i].addActionListener(this);
            numbers[i].putClientProperty("ID", nums[i]);
        }

        setLayout(new GridLayout(4, 7));
        setup();
    }

    /*
        +/-   +    C    D    E    F    ->
         (    -    8    9    A    B    <-
         )    *    4    5    6    7    back
        base  /    0    1    2    3     =
     */
    private void setup() {
        add(plusMinus);
        add(add);
        add(numbers[12]);
        add(numbers[13]);
        add(numbers[14]);
        add(numbers[15]);
        add(forward);

        add(openParenthesis);
        add(subtract);
        add(numbers[8]);
        add(numbers[9]);
        add(numbers[10]);
        add(numbers[11]);
        add(backward);

        add(closeParenthesis);
        add(multiply);
        add(numbers[4]);
        add(numbers[5]);
        add(numbers[6]);
        add(numbers[7]);
        add(backspace);

        add(toggleBase);
        add(divide);
        add(numbers[0]);
        add(numbers[1]);
        add(numbers[2]);
        add(numbers[3]);
        add(calculate);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Data data = (Data) ((JComponent) e.getSource()).getClientProperty("ID");
        display.input(data);
        requestFocus();
    }

    public void setBase(int base) {
        for (int i = 0; i < base; i++) {
            numbers[i].setEnabled(true);
        }
        for (int i = base; i < numbers.length; i++) {
            numbers[i].setEnabled(false);
        }
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
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.TWO);
                break;
            case KeyEvent.VK_3:
            case KeyEvent.VK_NUMPAD3:
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.THREE);
                break;
            case KeyEvent.VK_4:
            case KeyEvent.VK_NUMPAD4:
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.FOUR);
                break;
            case KeyEvent.VK_5:
            case KeyEvent.VK_NUMPAD5:
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.FIVE);
                break;
            case KeyEvent.VK_6:
            case KeyEvent.VK_NUMPAD6:
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.SIX);
                break;
            case KeyEvent.VK_7:
            case KeyEvent.VK_NUMPAD7:
                if(display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.SEVEN);
                break;
            case KeyEvent.VK_8:
            case KeyEvent.VK_NUMPAD8:
                if(e.getKeyChar() == '8' && display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.EIGHT);
                else if(e.getKeyChar() == '*')
                    display.input(Data.MULTIPLY);
                break;
            case KeyEvent.VK_9:
            case KeyEvent.VK_NUMPAD9:
                if(e.getKeyChar() == '9' && display.getBase() != ProgrammingDisplay.Base.BINARY)
                    display.input(Data.NINE);
                else if(e.getKeyChar() == '(')
                    display.input(Data.OPEN_PARENTHESIS);
                break;
            case KeyEvent.VK_A:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.TEN);
                break;
            case KeyEvent.VK_B:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.ELEVEN);
                break;
            case KeyEvent.VK_C:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.TWELVE);
                break;
            case KeyEvent.VK_D:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.THIRTEEN);
                break;
            case KeyEvent.VK_E:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.FOURTEEN);
                break;
            case KeyEvent.VK_F:
                if(display.getBase() == ProgrammingDisplay.Base.HEXADECIMAL)
                    display.input(Data.FIFTEEN);
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
            case KeyEvent.VK_UP:
                display.input(Data.CHANGE_BASE);
                display.input(Data.CHANGE_BASE);
                break;
            case KeyEvent.VK_DOWN:
                display.input(Data.CHANGE_BASE);
                break;
            case KeyEvent.VK_ENTER:
                display.input(Data.CALCULATE);
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
