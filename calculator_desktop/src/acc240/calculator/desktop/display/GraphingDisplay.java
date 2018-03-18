package acc240.calculator.desktop.display;

import acc240.calculator.desktop.graph.GraphFrame;
import acc240.calculator.desktop.utils.Data;
import acc240.calculator.desktop.utils.Node;
import acc240.math.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * The Display for the graphing calculator
 */
public class GraphingDisplay extends Display implements ActionListener {

    private Node[] equations;
    private Node cursor;

    private JButton up, down, graph;
    private JLabel equationNumber;
    private EquationPanel equation;

    public GraphingDisplay() {
        equations = new Node[10];
        for(int i = 0; i < 10; i++) {
            equations[i] = new Node(null, null, null);
        }
        cursor = equations[0];

        up = new JButton("↑");
        up.setAlignmentX(CENTER_ALIGNMENT);
        up.addActionListener(this);

        down = new JButton("↓");
        down.setAlignmentX(CENTER_ALIGNMENT);
        down.addActionListener(this);

        graph = new JButton("Graph");
        graph.setAlignmentX(CENTER_ALIGNMENT);
        graph.addActionListener(this);

        equationNumber = new JLabel("<html><div style='text-align: center;'>y<sub>1</sub></div></html>");

        equation = new EquationPanel();

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(Box.createGlue());
        leftPanel.add(up);
        leftPanel.add(down);
        leftPanel.add(graph);
        leftPanel.add(equationNumber);
        leftPanel.add(Box.createGlue());

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(equation, BorderLayout.CENTER);
    }

    @Override
    public void input(Data input) {
        switch (input) {
            case CALCULATE:
                graph();
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
                equation.repaint();
                break;
            case BACKWARD:
                if(cursor.getPrevious() != null)
                    cursor = cursor.getPrevious();
                equation.repaint();
                break;
            case FORWARD:
                if(cursor.getNext() != null)
                    cursor = cursor.getNext();
                equation.repaint();
                break;
            default:
                int index = equation.getIndex();
                if(equations[index].getNext() == null && isBinaryOperator(input)) {
                    cursor.setNext(new Node(Data.X, cursor.getNext(), cursor));
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
                equation.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()  == up) {
            equation.decrement();
            equationNumber.setText("<html><div style='text-align: center;'>y<sub>" + (equation.getIndex() + 1) + "</sub></div></html>");
            cursor = equations[equation.getIndex()];
        } else if(e.getSource() == down) {
            equation.increment();
            equationNumber.setText("<html><div style='text-align: center;'>y<sub>" + (equation.getIndex() + 1) + "</sub></div></html>");
            cursor = equations[equation.getIndex()];
        } else if(e.getSource() == graph) {
            graph();
        }
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

    private void graph() {
        String[] postFixes = new String[10];
        for(int i = 0; i < 10; i++)
            postFixes[i] = Parser.infixToPostfix(getEquation(equations[i]));
        GraphFrame graphFrame = new GraphFrame(postFixes);
        graphFrame.setVisible(true);
    }

    private class EquationPanel extends JPanel {
        private int index;

        public EquationPanel() {
            setBackground(Color.DARK_GRAY);
            index = 0;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(font);

            drawEquation(g, equations[index], cursor, 30);
        }

        public void setIndex(int i) {
            index = i;
        }

        public int getIndex() {
            return index;
        }

        public void increment() {
            if(index < equations.length - 1) {
                index++;
                repaint();
            }
        }

        public void decrement() {
            if(index > 0) {
                index--;
                repaint();
            }
        }
    }
}
