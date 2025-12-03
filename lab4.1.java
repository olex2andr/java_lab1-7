import java.io.*;
import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        String inputFile, numbersFile, noDigitsFile = "";

        while (true) {
            try {
                System.out.print("Введіть шлях до вхідного файлу: ");
                inputFile = console.readLine();

                File f = new File(inputFile);
                if (!f.exists()) {
                    System.out.println("Файл не знайдено");
                    continue;
                }
                break;

            } catch (IOException e) {
                System.out.println("Помилка введення");
            }
        }

        numbersFile = requestOutputFile(console, "чисел");
        noDigitsFile = requestOutputFile(console, "рядків без цифр");

        List<Integer> numbers = new ArrayList<>();
        List<String> noDigitLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\D+");
                boolean hasDigits = false;

                for (String p : parts) {
                    if (!p.isEmpty()) {
                        numbers.add(Integer.parseInt(p));
                        hasDigits = true;
                    }
                }

                if (!hasDigits) {
                    noDigitLines.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Помилка читання");
            return;
        }

        System.out.println("Знайдені числа: " + numbers);

        int sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Сума = " + sum);

        Collections.sort(numbers);

        writeListToFile(numbersFile, numbers);
        writeListToFile(noDigitsFile, noDigitLines);
    }

    private static String requestOutputFile(BufferedReader console, String description) {
        while (true) {
            try {
                System.out.print("Введіть назву файлу для " + description + ": ");
                String filename = console.readLine();

                File f = new File(filename);

                if (!f.exists()) {
                    System.out.print("Файл не існує. Створити (y/n): ");
                    if (!console.readLine().equals("y")) {
                        continue;
                    }
                    return filename;
                }

                System.out.print("Файл існує. Перезаписати (w) або дописати в кінець (a) ");
                String line = console.readLine();

                if (line.equalsIgnoreCase("w") || line.equalsIgnoreCase("a")) {
                    return (line + ":" + filename);
                }
            } catch (IOException e) {
                System.out.println("Помилка введення.");
            }
        }
    }

    public static void writeListToFile(String modeAndFile, List<?> list) {
        boolean append = false;
        String filename = modeAndFile;

        if (modeAndFile.startsWith("w:") || modeAndFile.startsWith("a:")) {
            append = modeAndFile.startsWith("a:");
            filename = modeAndFile.substring(2);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, append))) {
            for (Object obj : list) {
                bw.write(obj.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Помилка запису: " + filename);
        }
    }
}
