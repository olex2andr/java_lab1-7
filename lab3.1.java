/*  

    Побудувати ієрархію класів відповідно до варіанта завдання.
    Згідно завдання вибрати суперклас (базовий клас) та підкласи 
    (похідні класи). В класах задати поля, які характерні для 
    кожного класу. Для всіх класів розробити метод Show(), який виводить 
    дані про об’єкт класу. Розробити програму, яка вводить інформацію 
    про об’єкти заданих сутностей згідно варіанту в масив типу суперкласу 
    та друкує введений масив (з використанням методу Show()).

    Республіка, монархія, королівство, держава
*/

import java.util.Scanner;

public class lab1 {
    public static class State {
        protected String name;
        protected int population;

        public State(String name, int population) {
            this.name = name;
            this.population = population;
        }

        public void Show() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Країна [назва=" + name + ", населення=" + population + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            State other = (State) obj;
            return population == other.population && name.equals(other.name);
        }
    }

    public static class Republic extends State {
        private String president;

        public Republic(String name, int population, String president) {
            super(name, population);
            this.president = president;
        }

        @Override
        public void Show() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Республіка [назва=" + name + ", населення=" + population + ", президент=" + president + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false;
            Republic other = (Republic) obj;
            return president.equals(other.president);
        }
    }

    public static class Monarchy extends State {
        private String monarch;

        public Monarchy(String name, int population, String monarch) {
            super(name, population);
            this.monarch = monarch;
        }

        @Override
        public void Show() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Монархія [name=" + name + ", населення=" + population + ", монарх=" + monarch + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false;
            Monarchy other = (Monarchy) obj;
            return monarch.equals(other.monarch);
        }
    }

    public static class Kingdom extends State {
        private String king;

        public Kingdom(String name, int population, String king) {
            super(name, population);
            this.king = king;
        }

        @Override
        public void Show() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Королівство [назва=" + name + ", населення=" + population + ", король=" + king + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false;
            Kingdom other = (Kingdom) obj;
            return king.equals(other.king);
        }
    }

    public static class Country extends State {
        private String capital;

        public Country(String name, int population, String capital) {
            super(name, population);
            this.capital = capital;
        }

        @Override
        public void Show() {
            System.out.println(toString());
        }

        @Override
        public String toString() {
            return "Держава [назва=" + name + ", населення=" + population + ", столиця=" + capital + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false;
            Country other = (Country) obj;
            return capital.equals(other.capital);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        State[] states = new State[4];

        System.out.println("Введіть дані для Республіки (назва населення президент):");
        states[0] = new Republic(scanner.next(), scanner.nextInt(), scanner.next());

        System.out.println("Введіть дані для Монархії (назва населення монарх):");
        states[1] = new Monarchy(scanner.next(), scanner.nextInt(), scanner.next());

        System.out.println("Введіть дані для Королівства (назва населення король):");
        states[2] = new Kingdom(scanner.next(), scanner.nextInt(), scanner.next());

        System.out.println("Введіть дані для Держави (назва населення столиця):");
        states[3] = new Country(scanner.next(), scanner.nextInt(), scanner.next());

        for (State s : states) {
            s.Show();
        }
    }


}
