import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 * Simple plotting class for Lab 11.
 *
 * @see <a href="https://cs125.cs.illinois.edu/lab/11/">Lab 11 Description</a>
 */
public class GraphPlotter extends JPanel {

    /** Number of Y values to plot. */
    private int dataSize = 0;

    /** Data to plot. */
    private int[] data;

    /** Default amount of padding to use. */
    private static final int DEFAULT_PADDING = 20;

    /**
     * Create a new plot.
     *
     * @param yValues holds the Y values
     */
    public GraphPlotter(final int[] yValues) {
        dataSize = Math.min(yValues.length, data.length);
        data = new int[dataSize];
        for (int i = 0; i < dataSize; i++) {
            data[i] = yValues[i];
        }
    }

    /**
     * Draw the plot.
     *
     * @param g graphics object to draw on
     */
    @SuppressWarnings("checkstyle:magicnumber")
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the height and width of this component
        int w = getWidth();
        int h = getHeight();

        // Draw ordinate axis
        g2.draw(new Line2D.Double(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING,
                h - DEFAULT_PADDING));
        // Draw abcissa axis
        g2.draw(new Line2D.Double(DEFAULT_PADDING, h - DEFAULT_PADDING, w - DEFAULT_PADDING,
                h - DEFAULT_PADDING));

        // Draw labels.
        Font font = g2.getFont();
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics("0", frc);
        float sh = lm.getAscent() + lm.getDescent();

        // Draw the Ordinate label
        String s = "Time Taken";
        float sy = DEFAULT_PADDING + ((h - 2 * DEFAULT_PADDING) - s.length() * sh) / 2
                + lm.getAscent();
        for (int i = 0; i < s.length(); i++) {
            String letter = String.valueOf(s.charAt(i));
            float sw = (float) font.getStringBounds(letter, frc).getWidth();
            float sx = (DEFAULT_PADDING - sw) / 2;
            g2.drawString(letter, sx, sy);
            sy += sh;
        }

        // Draw the Abcissa label
        s = "Input Size";
        sy = h - DEFAULT_PADDING + (DEFAULT_PADDING - sh) / 2 + lm.getAscent();
        float sw = (float) font.getStringBounds(s, frc).getWidth();
        float sx = (w - sw) / 2;
        g2.drawString(s, sx, sy);

        // Draw lines between consecutive data points (x1,y1) and (x2,y2)
        double xinterval = (double) (w - 2 * DEFAULT_PADDING) / (dataSize - 1);
        double scale = (double) (h - 2 * DEFAULT_PADDING) / getMax();

        // Set the color of the line
        g2.setPaint(Color.blue.darker());
        for (int i = 0; i < dataSize - 1; i++) {
            // ith point
            double x1 = DEFAULT_PADDING + i * xinterval;
            double y1 = h - DEFAULT_PADDING - scale * data[i];

            // (i+1)th point
            double x2 = DEFAULT_PADDING + (i + 1) * xinterval;
            double y2 = h - DEFAULT_PADDING - scale * data[i + 1];

            // This function draws the line
            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }

        // Mark data points on the graph
        g2.setPaint(Color.red);
        for (int i = 0; i < dataSize; i++) {
            // Prepare the co-ordinates of the ith point
            double x = DEFAULT_PADDING + i * xinterval;
            double y = h - DEFAULT_PADDING - scale * data[i];
            // This function plots the point
            g2.fill(new Ellipse2D.Double(x - 2, y - 2, 4, 4));
        }
    }

    /**
     * This function computes and returns the maximum value in the data.
     *
     * @return the maximum value in the data
     */
    private int getMax() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < dataSize; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }
}
