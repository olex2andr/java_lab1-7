import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

class MatrixSizeException extends ArithmeticException {
    public MatrixSizeException(String message) {
        super(message);
    }
}

public class Lab6Swing extends JFrame {

    private JTextField filePathField;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JButton loadButton;
    private JButton createTestFileButton;
    private JLabel statusLabel;

    public Lab6Swing() {
        setTitle("lab6");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(new JLabel("Ім'я файлу:"));

        filePathField = new JTextField("matrix.txt", 15);
        topPanel.add(filePathField);

        loadButton = new JButton("Завантажити та обчислити");
        topPanel.add(loadButton);

        createTestFileButton = new JButton("Створити тест. файл");
        topPanel.add(createTestFileButton);

        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        resultTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Очікування дій...");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(statusLabel, BorderLayout.SOUTH);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processFile();
            }
        });

        createTestFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDummyFile();
            }
        });
    }

    private void processFile() {
        String filename = filePathField.getText();
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            if (!scanner.hasNextInt()) {
                throw new NumberFormatException("Файл порожній або не містить розмірності");
            }
            int n = scanner.nextInt();

            if (n >= 15) {
                throw new MatrixSizeException("Розмір матриці (" + n + ") завеликий! Має бути < 15.");
            }
            if (n <= 0) {
                throw new MatrixSizeException("Розмір матриці має бути більше 0.");
            }

            for (int i = 0; i < n; i++) {
                tableModel.addColumn("Col " + (i + 1));
            }
            tableModel.addColumn("Result L[i]");

            int[][] matrix = new int[n][n];

            for (int i = 0; i < n; i++) {
                int positiveCount = 0;
                int negativeCount = 0;

                Object[] rowData = new Object[n + 1];

                for (int j = 0; j < n; j++) {
                    if (!scanner.hasNextInt()) {
                        throw new NumberFormatException("Недостатньо даних у матриці");
                    }
                    int val = scanner.nextInt();
                    matrix[i][j] = val;
                    rowData[j] = val;

                    if (val > 0) positiveCount++;
                    else if (val < 0) negativeCount++;
                }

                boolean lValue = (negativeCount > positiveCount);
                rowData[n] = lValue;

                tableModel.addRow(rowData);
            }

            scanner.close();
            statusLabel.setForeground(new Color(0, 100, 0));
            statusLabel.setText("Успішно завантажено! Розмір: " + n + "x" + n);

        } catch (FileNotFoundException ex) {
            showError("Файл не знайдено: " + filename);
        } catch (NumberFormatException ex) {
            showError("Помилка даних: файл має містити лише цілі числа. " + ex.getMessage());
        } catch (MatrixSizeException ex) {
            showError("Помилка логіки: " + ex.getMessage());
        } catch (Exception ex) {
            showError("Невідома помилка: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        statusLabel.setForeground(Color.RED);
        statusLabel.setText("Помилка!");
        JOptionPane.showMessageDialog(this, message, "Помилка", JOptionPane.ERROR_MESSAGE);
    }

    private void createDummyFile() {
        try (PrintWriter writer = new PrintWriter("matrix.txt")) {
            writer.println("3");
            writer.println("1 -5 2");
            writer.println("-5 -6 1");
            writer.println("0 0 0");
            JOptionPane.showMessageDialog(this, "Файл matrix.txt створено!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Lab5Swing().setVisible(true);
        });
    }
}
