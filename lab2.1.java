/* 
    1. Створити клас vector3D, що задається трьома 
    координатами. Обов’язково реалізувати: додавання, 
    віднімання векторів, скалярний добуток векторів,
    множення на скаляр, порівняння векторів, обчислення 
    довжини вектора, порівняння довжини векторів. 
*/

public class lab1 {

    public static class Vector3D {
        private double x, y, z;

        public Vector3D(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Vector3D add(Vector3D v) {
            return new Vector3D(this.x + v.x, this.y + v.y, this.z + v.z);
        }

        public Vector3D subtract(Vector3D v) {
            return new Vector3D(this.x - v.x, this.y - v.y, this.z - v.z);
        }

        public double dot(Vector3D v) {
            return this.x * v.x + this.y * v.y + this.z * v.z;
        }

        public Vector3D multiply(double value) {
            return new Vector3D(this.x * value, this.y * value, this.z * value);
        }

        public double length() {
            return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        }


        public int compareLength(Vector3D vector) {
            return Double.compare(this.length(), vector.length());
        }

        @Override
        public boolean equals(Vector3D vector) {
            return this.x == vector.x && this.y == vector.y && this.z == vector.z;
        }
        
        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + z + ")";
        }
    }

    public static void main(String[] args) {
        Vector3D v1 = new Vector3D(1, 2, 3);
        Vector3D v2 = new Vector3D(4, 5, 6);

        System.out.println("v1 + v2 = " + v1.add(v2));
        System.out.println("v1 - v2 = " + v1.subtract(v2));
        System.out.println("Скалярний добуток = " + v1.dot(v2));
        System.out.println("v1 * 2 = " + v1.multiply(2));
        System.out.println("Довжина v1 = " + v1.length());
        System.out.println("Довжина v2 = " + v2.length());
        System.out.println("v1 == v2 = " + v1.equals(v2));

        System.out.println("compare: " + v1.compareLength(v2));
    }


}
