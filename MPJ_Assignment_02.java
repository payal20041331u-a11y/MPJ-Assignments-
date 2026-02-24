

class Employee {
    double salary;

    Employee(double s) {
        salary = s;
    }

    void displaySalary() {
        System.out.println("Base Salary: " + salary);
    }
}

// Derived class 1
class FullTimeEmployee extends Employee {

    FullTimeEmployee(double s) {
        super(s);
    }

    void calculateSalary() {
        double newSalary = salary + (salary * 0.50);  // 50% hike
        System.out.println("FullTime Employee Salary after 50% hike: " + newSalary);
    }
}

// Derived class 2
class InternEmployee extends Employee {

    InternEmployee(double s) {
        super(s);
    }

    void calculateSalary() {
        double newSalary = salary + (salary * 0.25);  // 25% hike
        System.out.println("Intern Employee Salary after 25% hike: " + newSalary);
    }
}

public class Main {
    public static void main(String[] args) {

        System.out.println("---- Full Time Employee ----");
        FullTimeEmployee f1 = new FullTimeEmployee(10000);
        f1.displaySalary();
        f1.calculateSalary();

        System.out.println("\n---- Intern Employee ----");
        InternEmployee i1 = new InternEmployee(8000);
        i1.displaySalary();
        i1.calculateSalary();
    }
}


