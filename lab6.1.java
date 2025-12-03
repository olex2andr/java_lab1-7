import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

public class RotatingSegmentTask extends JPanel implements ActionListener {

    private final int pathX1 = 100, pathY1 = 100;
    private final int pathX2 = 500, pathY2 = 400;

    private final int rotatingSegmentLength = 150;

    private double t = 0;
    private double tStep = 0.005;
    private double angle = 0;
    private double angleStep = 0.05;

    private Timer timer;

    public RotatingSegmentTask() {
        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(pathX1, pathY1, pathX2, pathY2);

        double centerX = pathX1 + (pathX2 - pathX1) * t;
        double centerY = pathY1 + (pathY2 - pathY1) * t;

        g2d.setColor(Color.RED);
        int radius = 4;
        g2d.fillOval((int)centerX - radius, (int)centerY - radius, radius * 2, radius * 2);

        double halfLen = rotatingSegmentLength / 2.0;

        double x1 = centerX + halfLen * Math.cos(angle);
        double y1 = centerY + halfLen * Math.sin(angle);

        double x2 = centerX - halfLen * Math.cos(angle);
        double y2 = centerY - halfLen * Math.sin(angle);

        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += angleStep;

        t += tStep;

        if (t >= 1.0 || t <= 0.0) {
            tStep = -tStep;
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("lab6");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        frame.add(new RotatingSegmentTask());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
