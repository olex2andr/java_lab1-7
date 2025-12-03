/*  
    На вхід задається арифметичний вираз, 
    який містить цілі невід’ємні числа і
    операції +, -, *. Обчислити та надрукувати 
    значення цього виразу враховуючи приорітет операцій 
*/

import java.util.Scanner;

public class lab1 {
    public static class Expression {
        private String expr;
        private int value;

        public Expression(String expr) {
            this.expr = expr.replaceAll("\\s+", "");
            this.value = calculate(this.expr);
        }

        private int calculate(String expression) {
            int result = 0;
            int current = 0;
            int last = 0;
            char op = '+';

            for (int i = 0; i < expression.length(); i++) {
                char ch = expression.charAt(i);
                if (Character.isDigit(ch)) {
                    current = current * 10 + (ch - '0');
                }

                if (!Character.isDigit(ch) || i == expression.length() - 1) {
                    if (op == '+') {
                        result += last;
                        last = current;
                    } else if (op == '-') {
                        result += last;
                        last = -current;
                    } else if (op == '*') {
                        last = last * current;
                    }
                    op = ch;
                    current = 0;
                }
            }
            result += last;

            return result;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "Expression: \"" + expr + "\", Value: " + value;
        }

        public boolean equals(Expression other) {
            if (other == null) return false;
            return this.value == other.value && this.expr.equals(other.expr);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть арифметичний вираз:");
        String input = scanner.nextLine();

        Expression exp = new Expression(input);
        System.out.println(exp);

        Expression exp2 = new Expression(input);
        System.out.println("Вирази рівні: " + exp.equals(exp2));
    }


}
