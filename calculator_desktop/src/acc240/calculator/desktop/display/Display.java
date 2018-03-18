package acc240.calculator.desktop.display;

import acc240.calculator.desktop.utils.Data;
import acc240.calculator.desktop.utils.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A display panel of the calculator.
 */
public abstract class Display extends JPanel {

    protected static final Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 35);

    public abstract void input(Data input);

    /**
     * Draws the specified equation onto the specified graphics
     *
     * The equation is an headed node list of Data elements.
     * @param g The graphics to draw the equation onto.
     * @param equation The headed node list of data
     * @param cursor The node that the cursor is located directly after
     * @param y The y-component where the equation should begin being drawn
     */
    protected final void drawEquation(Graphics g, Node equation, Node cursor, int y) {
        if(equation != null) {
            g.setColor(Color.GREEN);

            FontMetrics metrics = g.getFontMetrics(font);
            int x = -1 * metrics.stringWidth(" ") + 2;

            boolean lastNumber = false;

            Node node = equation.getNext(); // Skip the head
            while (node != null) {
                Data data = node.getData();
                String draw;
                if (data.isNumber()) {
                    if (lastNumber) {
                        draw = data.getRepresentation();
                    } else {
                        draw = " " + data.getRepresentation();
                    }
                    lastNumber = true;
                } else {
                    switch (data) {
                        case SQUARE_ROOT:
                            draw = " √(";
                            lastNumber = false;
                            break;
                        case PI:
                            draw = " π";
                            lastNumber = false;
                            break;
                        case MULTIPLY:
                            draw = " ×";
                            lastNumber = false;
                            break;
                        case ANSWER:
                            draw = " Ans";
                            lastNumber = false;
                            break;
                        default:
                            draw = " " + data.getRepresentation();
                            lastNumber = false;
                    }
                }

                g.drawString(draw, x, y);
                x += metrics.stringWidth(draw);

                if(node == cursor) {
                    g.setColor(Color.YELLOW);
                    g.drawLine(x, y-30, x, y);
                    g.setColor(Color.GREEN);
                }
                node = node.getNext();
            }
        }
    }

    protected final String getEquation(Node head) {
        String equation = "";
        boolean lastNumber = false;

        Node current = head.getNext(); // The first node is a header that needs to be skipped over.
        while(current != null) {
            Data data = current.getData();
            if(data.isNumber()) {
                if(lastNumber) {
                    equation += data.getRepresentation();
                } else {
                    equation += " " + data.getRepresentation();
                }
                lastNumber = true;
            } else {
                equation += " " + data.getRepresentation();
                lastNumber = false;
            }

            current = current.getNext();
        }

        equation = equation.trim();

        return equation;
    }
}
