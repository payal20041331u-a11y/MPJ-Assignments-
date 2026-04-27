// AutoConnect - ISP Automation System (Professional Terminal-Based OOP Version)
// Clean architecture: Model + Service + Main (Console UI)
// No Database, No GUI - Fully Console Driven

import java.util.*;

// ===================== ENUMS =====================
enum ConnectionStatus { ACTIVE, SUSPENDED }
enum BillStatus { PAID, UNPAID }

// ===================== MODEL CLASSES =====================
class Plan {
    private int id;
    private String name;
    private int speedMbps;
    private double price;

    public Plan(int id, String name, int speedMbps, double price) {
        this.id = id;
        this.name = name;
        this.speedMbps = speedMbps;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getSpeedMbps() { return speedMbps; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return id + ". " + name + " | " + speedMbps + " Mbps | ₹" + price;
    }
}

class Customer {
    private int id;
    private String name;
    private String phone;
    private Plan plan;
    private ConnectionStatus status;

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.status = ConnectionStatus.SUSPENDED;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Plan getPlan() { return plan; }
    public ConnectionStatus getStatus() { return status; }

    public void setPlan(Plan plan) {
        this.plan = plan;
        this.status = ConnectionStatus.ACTIVE;
    }

    public void suspend() {
        this.status = ConnectionStatus.SUSPENDED;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + phone + " | " + status +
                (plan != null ? " | Plan: " + plan.getName() : " | No Plan");
    }
}

class Bill {
    private int id;
    private int customerId;
    private double amount;
    private BillStatus status;

    public Bill(int id, int customerId, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = BillStatus.UNPAID;
    }

    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public double getAmount() { return amount; }
    public BillStatus getStatus() { return status; }

    public void pay() { this.status = BillStatus.PAID; }

    @Override
    public String toString() {
        return id + " | Customer: " + customerId + " | ₹" + amount + " | " + status;
    }
}

class Complaint {
    private int id;
    private int customerId;
    private String issue;
    private String status;

    public Complaint(int id, int customerId, String issue) {
        this.id = id;
        this.customerId = customerId;
        this.issue = issue;
        this.status = "OPEN";
    }

    public void resolve() { this.status = "RESOLVED"; }

    @Override
    public String toString() {
        return id + " | Customer: " + customerId + " | " + issue + " | " + status;
    }
}

// ===================== SERVICE LAYER =====================
class ISPService {
    private List<Customer> customers = new ArrayList<>();
    private List<Plan> plans = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();
    private List<Complaint> complaints = new ArrayList<>();

    private int customerIdCounter = 1;
    private int billIdCounter = 1;
    private int complaintIdCounter = 1;

    public ISPService() {
        plans.add(new Plan(1, "Basic", 50, 499));
        plans.add(new Plan(2, "Standard", 100, 799));
        plans.add(new Plan(3, "Premium", 300, 1299));
    }

    public void registerCustomer(String name, String phone) {
        customers.add(new Customer(customerIdCounter++, name, phone));
        System.out.println("Customer registered successfully!");
    }

    public void viewCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        customers.forEach(System.out::println);
    }

    public void showPlans() {
        plans.forEach(System.out::println);
    }

    public void assignPlan(int customerId, int planId) {
        Customer customer = findCustomer(customerId);
        if (customer == null) {
            System.out.println("Invalid Customer ID");
            return;
        }

        for (Plan p : plans) {
            if (p.getId() == planId) {
                customer.setPlan(p);
                System.out.println("Plan assigned successfully!");
                return;
            }
        }
        System.out.println("Invalid Plan ID");
    }

    public void generateBill(int customerId) {
        Customer c = findCustomer(customerId);
        if (c == null || c.getPlan() == null) {
            System.out.println("Customer not found or plan not assigned.");
            return;
        }

        Bill bill = new Bill(billIdCounter++, customerId, c.getPlan().getPrice());
        bills.add(bill);
        System.out.println("Bill Generated: " + bill);
    }

    public void payBill(int billId) {
        for (Bill b : bills) {
            if (b.getId() == billId) {
                b.pay();
                System.out.println("Payment successful!");
                return;
            }
        }
        System.out.println("Bill not found.");
    }

    public void raiseComplaint(int customerId, String issue) {
        complaints.add(new Complaint(complaintIdCounter++, customerId, issue));
        System.out.println("Complaint registered.");
    }

    public void viewComplaints() {
        complaints.forEach(System.out::println);
    }

    private Customer findCustomer(int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }
}

// ===================== MAIN (CONSOLE UI) =====================
public class AutoConnect {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ISPService service = new ISPService();

        while (true) {
            System.out.println("\n===== AutoConnect ISP System =====");
            System.out.println("1. Register Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Show Plans");
            System.out.println("4. Assign Plan");
            System.out.println("5. Generate Bill");
            System.out.println("6. Pay Bill");
            System.out.println("7. Raise Complaint");
            System.out.println("8. View Complaints");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.next();
                    System.out.print("Phone: ");
                    String phone = sc.next();
                    service.registerCustomer(name, phone);
                }
                case 2 -> service.viewCustomers();
                case 3 -> service.showPlans();
                case 4 -> {
                    System.out.print("Customer ID: ");
                    int cid = sc.nextInt();
                    System.out.print("Plan ID: ");
                    int pid = sc.nextInt();
                    service.assignPlan(cid, pid);
                }
                case 5 -> {
                    System.out.print("Customer ID: ");
                    service.generateBill(sc.nextInt());
                }
                case 6 -> {
                    System.out.print("Bill ID: ");
                    service.payBill(sc.nextInt());
                }
                case 7 -> {
                    System.out.print("Customer ID: ");
                    int cid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Issue: ");
                    String issue = sc.nextLine();
                    service.raiseComplaint(cid, issue);
                }
                case 8 -> service.viewComplaints();
                case 9 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
