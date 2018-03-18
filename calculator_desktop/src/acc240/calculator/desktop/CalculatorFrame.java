package acc240.calculator.desktop;

import acc240.calculator.desktop.display.CalculatorDisplay;
import acc240.calculator.desktop.display.GraphingDisplay;
import acc240.calculator.desktop.display.ProgrammingDisplay;
import acc240.calculator.desktop.keyboard.CalculatorKeyboard;
import acc240.calculator.desktop.keyboard.GraphingKeyboard;
import acc240.calculator.desktop.keyboard.ProgrammingKeyboard;
import acc240.math.Parser;
import acc240.utils.Variables;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The main class behind the calculator program.
 */
public class CalculatorFrame extends JFrame implements ChangeListener {

    public static void main(String[] args) {
        new CalculatorFrame();
    }

    private JTabbedPane tabbedPane;

    private CalculatorKeyboard calculatorKeyboard;
    private ProgrammingKeyboard programmingKeyboard;
    private GraphingKeyboard graphingKeyboard;

    public CalculatorFrame() {
        super("Calculator");
        Parser.setVariables(new Variables());

        tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);

        CalculatorDisplay calculatorDisplay = new CalculatorDisplay();
        ProgrammingDisplay programmingDisplay = new ProgrammingDisplay();
        GraphingDisplay graphingDisplay = new GraphingDisplay();

        calculatorKeyboard = new CalculatorKeyboard(calculatorDisplay);
        programmingKeyboard = new ProgrammingKeyboard(programmingDisplay);
        graphingKeyboard = new GraphingKeyboard(graphingDisplay);

        programmingDisplay.setKeyboard(programmingKeyboard);

        JPanel calculator = new JPanel();
        calculator.setLayout(new BoxLayout(calculator, BoxLayout.Y_AXIS));
        calculator.add(calculatorDisplay);
        calculator.add(calculatorKeyboard);
        tabbedPane.addTab("Calculator", calculator);

        JPanel programming = new JPanel();
        programming.setLayout(new BoxLayout(programming, BoxLayout.Y_AXIS));
        programming.add(programmingDisplay);
        programming.add(programmingKeyboard);
        tabbedPane.addTab("Programming", programming);

        JPanel graphing = new JPanel();
        graphing.setLayout(new BoxLayout(graphing, BoxLayout.Y_AXIS));
        graphing.add(graphingDisplay);
        graphing.add(graphingKeyboard);
        tabbedPane.addTab("Graphing", graphing);

        getContentPane().add(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

        calculatorKeyboard.requestFocus();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        switch(tabbedPane.getSelectedIndex()) {
            case 0:
                calculatorKeyboard.requestFocus();
                break;
            case 1:
                programmingKeyboard.requestFocus();
                break;
            case 2:
                graphingKeyboard.requestFocus();
                break;
            default:
                break;
        }
    }
}
