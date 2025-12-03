/* 
    2. Клас «конус», який описує правильний 
    конус з заданою висотою та радіусом основи. 
    Методи класу дозволяють знаходити 
    площу бічної основи та об'єм конуса 
 */

public class lab1 {

  public static class Cone {

    private double radius;
    private double height;

    public Cone(double radius, double height) {
      this.radius = radius;
      this.height = height;
    }

    public double slantHeight() { // відстань від вершини до будь якої точки на краю основи
      return Math.sqrt(this.radius * this.radius + this.height * this.height);
    }

    public double lateralSurfaceArea() {
      return Math.PI * this.radius * slantHeight();
    }

    public double volume() {
      return (1.0 / 3) * Math.PI * this.radius * this.radius * this.height;
    }

    public double baseArea() {
      return Math.PI * this.radius * this.radius;
    }

    @Override
    public String toString() {
      return "Cone [radius=" + this.radius + ", height=" + this.height + "]";
    }

    @Override
    public boolean equals(Cone cone) {
      return this.radius == cone.radius && this.height == cone.height;
    }
  }

  public static void main(String[] args) {
    Cone cone1 = new Cone(3, 4);
    Cone cone2 = new Cone(3, 4);

    System.out.println("Площа основи: " + cone1.baseArea());
    System.out.println("Площа бічної поверхні: " + cone1.lateralSurfaceArea());
    System.out.println("Об'єм: " + cone1.volume());
    System.out.println("Конуси рівні: " + cone1.equals(cone2));
  }
}
