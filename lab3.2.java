/* 
    Реалізувати абстрактний базовий клас з вказаними абстрактними
    методами. Створити підкласи(похідні класи) суперкласу( базового класу), в
    яких здійснити реалізацію всіх абстрактних методів. Самостійно визначити, які
    поля необхідні і які з них визначити в базовому класі, а які – в похідних.
    В похідних класах мають бути перевантажені методи toString та equal.
    Створити масив об’єктів . Проілюструвати роботу всіх методів
    підкласів(похідних класів). 

    Створити абстрактний базовий клас Series (прогресія) з абстрактними
    функціями обчислення вказаного члена прогресії та її суми. Визначити
    похідні класи Linear (арифметична прогресія) та Exponential (геометрична
    прогресія) з власною реалізацією вищенаведених функцій
*/


import java.util.Arrays;
import java.util.Scanner;

public class lab1 {

    static abstract class Series {
        protected int firstTerm;

        public Series(int firstTerm) {
            this.firstTerm = firstTerm;
        }

        public abstract double getTerm(int n);
        public abstract double getSum(int n);

        public abstract void Show();
    }

    static class Linear extends Series {
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
        public void Show() {
            System.out.println(this.toString());
        }

        @Override
        public String toString() {
            return "Linear [firstTerm=" + firstTerm + ", difference=" + difference + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Linear other = (Linear) obj;
            return firstTerm == other.firstTerm && difference == other.difference;
        }
    }

    static class Exponential extends Series {
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
        public void Show() {
            System.out.println(this.toString());
        }

        @Override
        public String toString() {
            return "Exponential [firstTerm=" + firstTerm + ", ratio=" + ratio + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Exponential other = (Exponential) obj;
            return firstTerm == other.firstTerm && ratio == other.ratio;
        }
    }

    public static void main(String[] args) {
        Series[] seriesArray = new Series[4];
        seriesArray[0] = new Linear(1, 2);           // 1, 3, 5, 7...
        seriesArray[1] = new Linear(5, 3);           // 5, 8, 11...
        seriesArray[2] = new Exponential(2, 2);     // 2, 4, 8, 16...
        seriesArray[3] = new Exponential(3, 3);     // 3, 9, 27...

        for (Series s : seriesArray) {
            s.Show();
            System.out.println("5-й член: " + s.getTerm(5));
            System.out.println("Сума перших 5 членів: " + s.getSum(5));
            System.out.println();
        }

        // Приклад equals і toString
        Linear l1 = new Linear(1, 2);
        System.out.println("l1 equals seriesArray[0]? " + l1.equals(seriesArray[0]));
        System.out.println("seriesArray[0] toString(): " + seriesArray[0].toString());
    }
}
