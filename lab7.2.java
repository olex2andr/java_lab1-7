import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab7 extends JFrame {

    public static class State {
        protected String name;
        protected int population;

        public State(String name, int population) {
            this.name = name;
            this.population = population;
        }

        @Override
        public String toString() {
            return "Країна [назва=" + name + ", населення=" + population + "]";
        }
    }

    public static class Republic extends State {
        private String president;

        public Republic(String name, int population, String president) {
            super(name, population);
            this.president = president;
        }

        @Override
        public String toString() {
            return "Республіка: " + name + " (Президент: " + president + ")";
        }
    }

    public static class Monarchy extends State {
        private String monarch;

        public Monarchy(String name, int population, String monarch) {
            super(name, population);
            this.monarch = monarch;
        }

        @Override
        public String toString() {
            return "Монархія: " + name + " (Монарх: " + monarch + ")";
        }
    }

    public static class Kingdom extends State {
        private String king;

        public Kingdom(String name, int population, String king) {
            super(name, population);
            this.king = king;
        }

        @Override
        public String toString() {
            return "Королівство: " + name + " (Король: " + king + ")";
        }
    }

    public static class Country extends State {
        private String capital;

        public Country(String name, int population, String capital) {
            super(name, population);
            this.capital = capital;
        }

        @Override
        public String toString() {
            return "Держава: " + name + " (Столиця: " + capital + ")";
        }
    }

    class StateRunnable implements Runnable {
        private State state;
        private JTextArea logArea;
        private volatile boolean running = true;

        public StateRunnable(State state, JTextArea logArea) {
            this.state = state;
            this.logArea = logArea;
        }

        public void stop() {
            running = false;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    int currentPriority = Thread.currentThread().getPriority();

                    String message = String.format("[Prio: %d] %s працює...\n",
                            currentPriority, state.toString());

                    SwingUtilities.invokeLater(() -> {
                        logArea.append(message);
                        logArea.setCaretPosition(logArea.getDocument().getLength());
                    });

                    long sleepTime = 1200 - (currentPriority * 100);

                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
    }

    private JTextArea textArea;
    private Thread[] threads = new Thread[4];
    private StateRunnable[] runnables = new StateRunnable[4];

    public lab6() {
        setTitle("Лабораторна: Потоки та Пріоритети");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        State[] states = new State[4];
        states[0] = new Republic("Україна", 40000000, "Зеленський");
        states[1] = new Monarchy("Британія", 67000000, "Чарльз III");
        states[2] = new Kingdom("Іспанія", 47000000, "Філіп VI");
        states[3] = new Country("Польща", 38000000, "Варшава");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(4, 3, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Керування пріоритетами"));

        String[] priorities = {"MIN (1)", "LOW (3)", "NORM (5)", "HIGH (7)", "MAX (10)"};
        int[] priorityValues = {1, 3, 5, 7, 10};

        for (int i = 0; i < 4; i++) {
            JLabel label = new JLabel(states[i].getClass().getSimpleName() + ": " + states[i].name);

            JComboBox<String> priorityBox = new JComboBox<>(priorities);
            priorityBox.setSelectedIndex(2);

            runnables[i] = new StateRunnable(states[i], textArea);
            threads[i] = new Thread(runnables[i]);
            threads[i].setPriority(Thread.NORM_PRIORITY);

            threads[i].start();

            int finalI = i;
            priorityBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = priorityBox.getSelectedIndex();
                    int newPriority = priorityValues[selectedIndex];

                    threads[finalI].setPriority(newPriority);

                    textArea.append(">>> Змінено пріоритет для " + states[finalI].name + " на " + newPriority + "\n");
                }
            });

            controlPanel.add(label);
            controlPanel.add(new JLabel("Пріоритет:"));
            controlPanel.add(priorityBox);
        }

        add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new lab6().setVisible(true);
        });
    }
}