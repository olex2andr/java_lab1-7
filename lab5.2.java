import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class lab1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Множення двох многочленів ---");

        System.out.println("Многочлен 1:");
        ArrayList<Integer> poly1 = inputPolynomial(scanner);
        System.out.print("Ви ввели P(x) = ");
        printPolynomial(poly1);

        System.out.println("\nМногочлен 2:");
        ArrayList<Integer> poly2 = inputPolynomial(scanner);
        System.out.print("Ви ввели Q(x) = ");
        printPolynomial(poly2);

        ArrayList<Integer> resultPoly = multiplyPolynomials(poly1, poly2);

        System.out.println("\n-----------------------------");
        System.out.print("Результат R(x) = ");
        printPolynomial(resultPoly);
        System.out.println("Коефіцієнти результату (від x^0 до x^n): " + resultPoly);
    }

    public static ArrayList<Integer> multiplyPolynomials(ArrayList<Integer> p1, ArrayList<Integer> p2) {
        int n = p1.size();
        int m = p2.size();

        int resultSize = n + m - 1;

        ArrayList<Integer> result = new ArrayList<>(Collections.nCopies(resultSize, 0));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int product = p1.get(i) * p2.get(j);
                int currentPos = i + j;

                int currentVal = result.get(currentPos);
                result.set(currentPos, currentVal + product);
            }
        }
        return result;
    }

    public static ArrayList<Integer> inputPolynomial(Scanner scanner) {
        ArrayList<Integer> polynomial = new ArrayList<>();

        System.out.print("Введіть найвищий степінь многочлена (n): ");
        int degree = scanner.nextInt();

        for (int i = 0; i <= degree; i++) {
            System.out.print("Введіть коефіцієнт для x^" + i + ": ");
            polynomial.add(scanner.nextInt());
        }
        return polynomial;
    }

    public static void printPolynomial(List<Integer> poly) {
        boolean first = true;
        for (int i = 0; i < poly.size(); i++) {
            int coef = poly.get(i);

            if (coef != 0) {
                if (!first) {
                    if (coef > 0) System.out.print(" + ");
                    else System.out.print(" - ");
                } else {
                    if (coef < 0) System.out.print("-");
                }

                System.out.print(Math.abs(coef));
                if (i > 0) System.out.print("x^" + i);

                first = false;
            }
        }
        if (first) System.out.print("0");
        System.out.println();
    }
}
