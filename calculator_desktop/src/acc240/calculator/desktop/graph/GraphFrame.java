package acc240.calculator.desktop.graph;

import acc240.math.Parser;
import acc240.utils.Variables;

import javax.swing.*;
import java.awt.*;

/**
 * The JFrame that will graph and display the functions passed in
 */
public class GraphFrame extends JFrame {

    public GraphFrame(String[] equations) {
        super("Graph");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JTextField minX = new JTextField();
        JTextField maxX = new JTextField();
        JTextField minY = new JTextField();
        JTextField maxY = new JTextField();
        panel.add(new JLabel("Minimum X:"));
        panel.add(minX);
        panel.add(new JLabel("Maximum X:"));
        panel.add(maxX);
        panel.add(new JLabel("Minimum Y:"));
        panel.add(minY);
        panel.add(new JLabel("Maximum Y:"));
        panel.add(maxY);
        int res = JOptionPane.showConfirmDialog(null, panel,
                "Axis Parameters", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        double miX = -10.0, maX = 10.0;
        double miY = -10.0, maY = 10.0;
        if(res == JOptionPane.YES_OPTION) {
            try {
                miX = Double.parseDouble(minX.getText());
                maX = Double.parseDouble(maxX.getText());
                miY = Double.parseDouble(minY.getText());
                maY = Double.parseDouble(maxY.getText());
            } catch(NumberFormatException e) {
                miX = -10.0; maX = 10.0;
                miY = -10.0; maY = 10.0;
                JOptionPane.showMessageDialog(null, "One or more values was not numeric. Default will be used.");
            }
        }

        setBackground(Color.WHITE);
        JPanel graph = new GraphPanel(equations, new double[]{miX, maX, miY, maY});
        getContentPane().add(graph);
        pack();
    }

    private class GraphPanel extends JPanel {

        private String[] equations;
        private double[] bounds;

        private final Color[] colors = {Color.RED, new Color(0xff6600), new Color(0xffcc00),
                new Color(0x006600), Color.CYAN, Color.BLUE, Color.MAGENTA, Color.PINK, Color.GRAY, Color.BLACK};

        public GraphPanel(String[] eqs, double[] size) {
            equations = eqs;
            bounds = size;

            setPreferredSize(new Dimension(500, 500));
            setBackground(Color.WHITE);
        }

        @Override
        public void paintComponent(Graphics g) {
            int width = getWidth();
            int height = getHeight();

            if(width <= 0)
                width = 500;
            if(height <= 0)
                height = 500;

            Parser.setVariables(new Variables());

            double[] graphInfo = drawAxis(g, width, height);
            for(int i = 0; i < equations.length; i++) {
                if(equations[i].length() > 0) {
                    if (i < colors.length)
                        g.setColor(colors[i]);
                    else
                        g.setColor(Color.BLACK);

                    int[] xs = new int[width + 1];
                    int[] ys = new int[width + 1];
                    int index = 0;
                    for (int x = 0; x <= width; x++) {
                        double xVal = graphInfo[0] + (x / graphInfo[2]);
                        Parser.addVariable('x', xVal);

                        double yVal = Parser.evaluateWithValue(equations[i]);
                        if(Double.isFinite(yVal)) {
                            int y = (int) ((graphInfo[1] - yVal) * graphInfo[3]);

                            if(index > 0 && Math.abs(y - ys[index - 1]) > height * 3) {
                                g.drawPolyline(xs, ys, index);
                                xs = new int[xs.length - index - 1];
                                ys = new int[ys.length - index - 1];
                                index = 0;
                            } else {
                                xs[index] = x;
                                ys[index] = y;
                                index++;
                            }
                        } else if(index > 0) {
                            g.drawPolyline(xs, ys, index);
                            xs = new int[xs.length - index - 1];
                            ys = new int[ys.length - index - 1];
                            index = 0;
                        }
                    }
                    if(index > 0)
                        g.drawPolyline(xs, ys, index);
                }
            }
        }

        private double[] drawAxis(Graphics g, int width, int height) {
            double tickX = width / (bounds[1] - bounds[0]);
            double tickY = width / (bounds[3] - bounds[2]);

            int zeroY = (int) (bounds[3] * tickY);
            int zeroX = (int) ((-1 * bounds[0]) * tickX);

            g.setColor(Color.BLACK);
            g.drawLine(0, zeroY, width, zeroY);
            g.drawLine(zeroX, 0, zeroX, height);

            double iX = (-1 * bounds[0]) * tickX;
            while(iX >= tickX)
                iX -= tickX;
            for(double x = iX; x <= width; x += tickX) {
                g.drawLine((int)x, zeroY - 2, (int)x, zeroY + 2);
            }

            double iY = bounds[3] * tickY;
            while(iY >= tickY)
                iY -= tickY;
            for(double y = iY; y <= height; y += tickY) {
                g.drawLine(zeroX - 2, (int)y, zeroX + 2, (int)y);
            }

            return new double[]{bounds[0], bounds[3], tickX, tickY};
        }
    }
}
