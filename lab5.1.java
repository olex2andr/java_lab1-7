import java.io.*;
import java.util.*;

abstract class Series {
    protected int firstTerm;

    public Series(int firstTerm) {
        this.firstTerm = firstTerm;
    }

    public abstract double getTerm(int n);
    public abstract double getSum(int n);
    public abstract void show();

    public int getFirstTerm() {
        return firstTerm;
    }
}

class Linear extends Series implements Comparable<Linear> {
    private int difference;

    public Linear(int firstTerm, int difference) {
        super(firstTerm);
        this.difference = difference;
    }

    @Override
    public double getTerm(int n) {
        return firstTerm + (n - 1) * difference;
    }

    @Override
    public double getSum(int n) {
        return n * (2 * firstTerm + (n - 1) * difference) / 2.0;
    }

    @Override
    public void show() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Linear [a1=" + firstTerm + ", d=" + difference + "]";
    }

    @Override
    public int compareTo(Linear other) {
        return Integer.compare(this.firstTerm, other.firstTerm);
    }
}

class Exponential extends Series implements Comparable<Exponential> {
    private int ratio;

    public Exponential(int firstTerm, int ratio) {
        super(firstTerm);
        this.ratio = ratio;
    }

    @Override
    public double getTerm(int n) {
        return firstTerm * Math.pow(ratio, n - 1);
    }

    @Override
    public double getSum(int n) {
        if (ratio == 1) return firstTerm * n;
        return firstTerm * (Math.pow(ratio, n) - 1) / (ratio - 1);
    }

    @Override
    public void show() {
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        return "Exponential [b1=" + firstTerm + ", q=" + ratio + "]";
    }

    @Override
    public int compareTo(Exponential other) {
        return Integer.compare(this.firstTerm, other.firstTerm);
    }
}

class SeriesComparator implements Comparator<Series> {
    @Override
    public int compare(Series s1, Series s2) {
        double sum1 = s1.getSum(5);
        double sum2 = s2.getSum(5);
        return Double.compare(sum1, sum2);
    }
}

public class lab1 {

    static final String FILE_LINEAR = "linear_input.txt";
    static final String FILE_EXP = "exponential_input.txt";
    static final String FILE_OUTPUT = "sorted_series_output.txt";

    public static void main(String[] args) {
        createTestFiles();

        try {
            Scanner scanner = new Scanner(System.in);

            ArrayList<Linear> linearList = new ArrayList<>();
            ArrayList<Exponential> exponentialList = new ArrayList<>();

            readLinearFile(FILE_LINEAR, linearList);
            readExponentialFile(FILE_EXP, exponentialList);

            System.out.println("--- Прочитані дані ---");
            printCollection(linearList);
            printCollection(exponentialList);

            Collections.sort(linearList);
            Collections.sort(exponentialList);

            System.out.println("\n--- Відсортовані дані (за першим членом) ---");
            printCollection(linearList);
            printCollection(exponentialList);

            System.out.println("\n--- Додавання нових записів ---");

            System.out.println("Введіть Linear (firstTerm difference):");
            if(scanner.hasNextInt()) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                linearList.add(new Linear(a, b));
            }

            System.out.println("Введіть Exponential (firstTerm ratio):");
            if(scanner.hasNextInt()) {
                int a = scanner.nextInt();
                int b = scanner.nextInt();
                exponentialList.add(new Exponential(a, b));
            }

            Collections.sort(linearList);
            Collections.sort(exponentialList);
            System.out.println("\n--- Дані після додавання та сортування ---");
            printCollection(linearList);
            printCollection(exponentialList);

            ArrayList<Series> generalList = new ArrayList<>();
            generalList.addAll(linearList);
            generalList.addAll(exponentialList);

            Collections.sort(generalList, new SeriesComparator());

            System.out.println("\n--- Загальний список (відсортований за сумою S5) ---");
            for (Series s : generalList) {
                System.out.printf("%s | S5 = %.2f%n", s.toString(), s.getSum(5));
            }

            writeListToFile(FILE_OUTPUT, generalList);
            System.out.println("\nРезультат записано у файл " + FILE_OUTPUT);

        } catch (IOException e) {
            System.out.println("Помилка файлової системи: " + e.getMessage());
        }
    }

    public static void printCollection(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static void readLinearFile(String filename, ArrayList<Linear> list) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                list.add(new Linear(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
        reader.close();
    }

    public static void readExponentialFile(String filename, ArrayList<Exponential> list) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length == 2) {
                list.add(new Exponential(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            }
        }
        reader.close();
    }

    public static void writeListToFile(String filename, ArrayList<Series> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Series s : list) {
            writer.write(s.toString() + " | Sum(5)=" + s.getSum(5));
            writer.newLine();
        }
        writer.close();
    }

    public static void createTestFiles() {
        try {
            FileWriter fw1 = new FileWriter(FILE_LINEAR);
            fw1.write("10 2\n5 3\n100 10\n1 1");
            fw1.close();

            FileWriter fw2 = new FileWriter(FILE_EXP);
            fw2.write("2 2\n3 3\n10 2\n5 1");
            fw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
