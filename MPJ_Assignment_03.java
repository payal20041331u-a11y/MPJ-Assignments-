import java.util.Scanner;

// Parent Class (for Overriding)
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

// Child Class (Method Overriding)
class Dog extends Animal {
    void sound() {
        System.out.println("Dog barks");
    }
}

// Main Class (Overloading + Main Program)
public class MPJ_Assignment_03 {

    //   Method Overloading
    void add(int a) {
        System.out.println("Sum: " + a);
    }

    void add(int a, int b) {
        System.out.println("Sum: " + (a + b));
    }

    void add(int a, int b, int c) {
        System.out.println("Sum: " + (a + b + c));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        MPJ_Assignment_03 obj = new MPJ_Assignment_03();

        System.out.println("Enter number of values (1, 2 or 3): ");
        int choice = sc.nextInt();

        //   Calling Overloaded Methods
        if (choice == 1) {
            System.out.println("Enter 1 number:");
            int a = sc.nextInt();
            obj.add(a);
        }
        else if (choice == 2) {
            System.out.println("Enter 2 numbers:");
            int a = sc.nextInt();
            int b = sc.nextInt();
            obj.add(a, b);
        }
        else if (choice == 3) {
            System.out.println("Enter 3 numbers:");
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            obj.add(a, b, c);
        }
        else {
            System.out.println("Invalid choice");
        }

        //   Method Overriding
        Animal obj2 = new Dog();  // runtime polymorphism
        obj2.sound();

        sc.close();
    }
}