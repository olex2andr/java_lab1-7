import java.util.Scanner;

public class lab1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double x, y, r;
        int xi, yi, n;

        // task 1
        System.out.println("Завдання 1: ");

        System.out.print("Введіть значення x: ");
        if (!scanner.hasNextDouble()) return;
        x = scanner.nextDouble();
        xi = (int) x;

        System.out.print("Введіть значення y: ");
        if (!scanner.hasNextDouble()) return;
        y = scanner.nextDouble();
        yi = (int) y;

        System.out.println("\n1) Вхідні дійсного, результат дійсного: " + ((1.0 / (x * y)) + (1.0 / (Math.pow(x, 2) + Math.pow(y, 2))) * (x - y)));
        System.out.println("2) Вхідні цілого, результат дійсного: " + ((1.0 / (xi * yi)) + (1.0 / (Math.pow(xi, 2) + Math.pow(yi, 2))) * (xi - yi)));
        System.out.println("3) Вхідні дійсного, результат цілого: " + (int) ((1.0 / (x * y)) + (1.0 / (Math.pow(x, 2) + Math.pow(y, 2))) * (x - y)));

        // task 2
        System.out.println("\nЗавдання 2: ");
        System.out.print("Введіть радіус r: ");
        if (!scanner.hasNextDouble()) return;
        r = scanner.nextDouble();

        System.out.print("Введіть кількість точок n (не більше 200): ");
        if (!scanner.hasNextInt() || (n = scanner.nextInt()) > 200) return;

        double[] A = new double[2 * n];

        System.out.print("Введіть координати точок (a1, ..., a" + n + "): ");
        for (int i = 0; i < 2 * n; i++) {
            if (!scanner.hasNextDouble()) {
                continue;
            }

            A[i] = scanner.nextDouble();
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            double xa = A[2 * i];
            double ya = A[2 * i + 1];
            if (Math.sqrt(Math.pow(xa, 2) + Math.pow(ya, 2)) <= r) {
                count++;
            }
        }

        System.out.println("\nВсього точок: " + n);
        System.out.println("Кількість точок всередині круга: " + count);

        // task 3
        System.out.println("\nЗавдання 3:");

        int matrixSize;
        System.out.print("Введіть розмірність матриці n (n < 15): ");
        if (!scanner.hasNextInt()) return;
        matrixSize = scanner.nextInt();

        if (matrixSize > 15) return;

        int[][] matrix = new int[matrixSize][matrixSize];
        System.out.println("Введіть елементи матриці " + matrixSize + "x" + matrixSize + ":");

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (!scanner.hasNextInt()) return;
                matrix[i][j] = scanner.nextInt();
            }
        }

        System.out.println("\nВведена матриця:");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        boolean[] L = new boolean[matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            int positiveCount = 0;
            int negativeCount = 0;

            for (int j = 0; j < matrixSize; j++) {
                if (matrix[i][j] > 0) positiveCount++;
                else if (matrix[i][j] < 0) negativeCount++;
            }

            L[i] = (negativeCount > positiveCount);
        }

        System.out.println("\nВектор L:");
        for (int i = 0; i < matrixSize; i++) {
            System.out.println("L[" + i + "] = " + L[i]);
        }

        scanner.nextLine();

        // task 4
        System.out.println("\nЗавдання 4: ");
        System.out.print("Введіть текст: ");

        if (!scanner.hasNextLine()) return;
        String text = scanner.nextLine();

        String[] words = text.split("[\\s\\p{Punct}]+");
        int maxLength = 0;

        for (String word : words) {
            if (!word.isEmpty() && word.length() > maxLength) {
                maxLength = word.length();
            }
        }

        String result = text.replaceAll("\\b\\w{" + maxLength + "}\\b", "").trim();
        System.out.println("Фінальний текст: " + result);
    }

}
