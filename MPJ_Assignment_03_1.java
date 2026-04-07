public class MPJ_Assignment_03_1 {

    double area;

    // Constructor Overloading (Circle)
    MPJ_Assignment_03_1(double r) {
        area = 3.14 * r * r;
        System.out.println("Area of Circle: " + area);
    }

    // Constructor Overloading (Rectangle)
    MPJ_Assignment_03_1(double l, double b) {
        area = l * b;
        System.out.println("Area of Rectangle: " + area);
    }

    // Method Overloading (Triangle)
    void area(double b, double h, String type) {
        area = 0.5 * b * h;
        System.out.println("Area of Triangle: " + area);
    }

    public static void main(String[] args) {
        new MPJ_Assignment_03_1(5);        // Circle
        new MPJ_Assignment_03_1(4, 6);     // Rectangle

        MPJ_Assignment_03_1 t = new MPJ_Assignment_03_1(0);
        t.area(4, 5, "triangle");          // Triangle
    }
}
