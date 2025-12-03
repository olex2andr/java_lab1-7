import java.io.*;
import java.util.*;

public class lab1 {
    public static void main(String[] args) {
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        List<String> files = new ArrayList<>();

        while (true) {
            try {
                System.out.print("Введіть шлях до файлу (пусто для завершення): ");
                String path = console.readLine();

                if (path == null || path.isBlank()) {
                    break;
                }

                File f = new File(path);

                if (!f.exists()) {
                    System.out.println("Файл не знайдено");
                    continue;
                }

                files.add(path);

            } catch (IOException e) {
                System.out.println("Помилка введення");
            }
        }

        System.out.println("\nРезультат:");

        for (String file : files) {
            System.out.println(file + " - " + countWordsInFile(file) + " слів");
        }
    }

    private static int countWordsInFile(String filename) {
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (!line.isEmpty()) {
                    String[] parts = line.split(" +");
                    count += parts.length;
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання");
        }

        return count;
    }
}
