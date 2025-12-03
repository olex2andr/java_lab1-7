import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

abstract class Entity {
    protected double x, y;
    protected double targetX, targetY;
    protected double speed;
    protected Color color;
    protected boolean isMoving;

    public Entity(double startX, double startY, double speed, Color color) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.color = color;
        this.isMoving = true;
    }

    public abstract void setRandomTargetInZone(int width, int height);

    public void move() {
        if (!isMoving) return;

        double dx = targetX - x;
        double dy = targetY - y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= speed) {
            x = targetX;
            y = targetY;
            isMoving = false;
        } else {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillOval((int)x - 5, (int)y - 5, 10, 10);
    }
}

class LegalEntity extends Entity {

    public LegalEntity(double startX, double startY, int width, int height) {
        super(startX, startY, 3.0, Color.BLUE);
        setRandomTargetInZone(width, height);
        checkIfAlreadyInZone(width, height);
    }

    @Override
    public void setRandomTargetInZone(int w, int h) {
        Random rand = new Random();
        this.targetX = rand.nextDouble() * (w / 2.0);
        this.targetY = rand.nextDouble() * (h / 2.0);
    }

    private void checkIfAlreadyInZone(int w, int h) {
        if (x >= 0 && x <= w / 2.0 && y >= 0 && y <= h / 2.0) {
            this.isMoving = false;
        }
    }
}

class PhysicalEntity extends Entity {

    public PhysicalEntity(double startX, double startY, int width, int height) {
        super(startX, startY, 4.0, Color.RED);
        setRandomTargetInZone(width, height);
        checkIfAlreadyInZone(width, height);
    }

    @Override
    public void setRandomTargetInZone(int w, int h) {
        Random rand = new Random();
        this.targetX = (w / 2.0) + rand.nextDouble() * (w / 2.0);
        this.targetY = (h / 2.0) + rand.nextDouble() * (h / 2.0);
    }

    private void checkIfAlreadyInZone(int w, int h) {
        if (x >= w / 2.0 && x <= w && y >= h / 2.0 && y <= h) {
            this.isMoving = false;
        }
    }
}

class SimulationPanel extends JPanel implements ActionListener {
    private ArrayList<Entity> entities = new ArrayList<>();
    private Timer timer;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    public SimulationPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.WHITE);

        timer = new Timer(16, this);
        timer.start();
    }

    public void spawnEntities(int count) {
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            double startX = rand.nextDouble() * WIDTH;
            double startY = rand.nextDouble() * HEIGHT;

            if (rand.nextBoolean()) {
                entities.add(new LegalEntity(startX, startY, WIDTH, HEIGHT));
            } else {
                entities.add(new PhysicalEntity(startX, startY, WIDTH, HEIGHT));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(230, 230, 255));
        g2.fill(new Rectangle2D.Double(0, 0, w/2.0, h/2.0));
        g2.setColor(Color.BLUE);
        g2.drawString("Зона Юридичних осіб", 10, 20);

        g2.setColor(new Color(255, 230, 230));
        g2.fill(new Rectangle2D.Double(w/2.0, h/2.0, w/2.0, h/2.0));
        g2.setColor(Color.RED);
        g2.drawString("Зона Фізичних осіб", w/2 + 10, h/2 + 20);

        g2.setColor(Color.GRAY);
        g2.drawLine(w/2, 0, w/2, h);
        g2.drawLine(0, h/2, w, h/2);

        for (Entity e : entities) {
            e.draw(g2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Entity entity : entities) {
            entity.move();
        }
        repaint();
    }
}

public class SimulationTask {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Симуляція руху: Юр. та Фіз. особи");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulationPanel panel = new SimulationPanel();
        frame.add(panel, BorderLayout.CENTER);

        JButton spawnButton = new JButton("Додати 50 випадкових об'єктів");
        spawnButton.addActionListener(e -> panel.spawnEntities(50));
        frame.add(spawnButton, BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.spawnEntities(20);
    }
}