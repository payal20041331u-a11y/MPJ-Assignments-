import java.util.*;

enum ConnectionStatus { ACTIVE, SUSPENDED }
enum BillStatus { PAID, UNPAID }

// ================= PASSWORD STRENGTH =================
class PasswordUtil {

    static String strength(String p) {
        int score = 0;
        if (p.length() >= 8)                    score++;
        if (p.matches(".*[A-Z].*"))             score++;
        if (p.matches(".*[a-z].*"))             score++;
        if (p.matches(".*[0-9].*"))             score++;
        if (p.matches(".*[^a-zA-Z0-9].*"))      score++;

        if (score <= 2)      return "WEAK";
        else if (score <= 4) return "MEDIUM";
        else                 return "STRONG";
    }

    static boolean valid(String p) {
        return strength(p).equals("STRONG");
    }
}

// ================= MODELS =================
class Customer {
    int id;
    String name, phone, email, password;
    ConnectionStatus status = ConnectionStatus.SUSPENDED;

    Customer(int id, String n, String ph, String e, String pass) {
        this.id = id; name = n; phone = ph; email = e; password = pass;
    }
}

class Plan {
    int id; String name; double price;
    Plan(int id, String n, double p) { this.id = id; name = n; price = p; }
}

class Bill {
    int id, cid; double amt;
    BillStatus status = BillStatus.UNPAID;
    Bill(int id, int cid, double a) { this.id = id; this.cid = cid; amt = a; }
}

class Complaint {
    int id, cid; String issue;
    String status = "OPEN";
    String technician = "NOT ASSIGNED";
    Complaint(int id, int cid, String i) { this.id = id; this.cid = cid; issue = i; }
}

// ================= ADMIN MODEL =================
class Admin {
    String name, email, password;
    Admin(String name, String email, String password) {
        this.name = name; this.email = email; this.password = password;
    }
}

// ================= SERVICE =================
class ISPService {
    List<Customer> customers = new ArrayList<>();
    List<Plan> plans = new ArrayList<>();
    List<Bill> bills = new ArrayList<>();
    List<Complaint> comps = new ArrayList<>();

    // Hardcoded admins
    List<Admin> admins = new ArrayList<>(Arrays.asList(
        new Admin("Rajesh",  "rajeshu0707@gmail.com", "admin@01"),
        new Admin("Santosh", "santoshu555@gmail.com",  "admin@02")
    ));

    int cid = 1, bid = 1, compid = 1;

    ISPService() {
        plans.add(new Plan(1, "Basic", 499));
        plans.add(new Plan(2, "Standard", 799));
        plans.add(new Plan(3, "Premium", 1299));
    }

    // ---- Admin login ----
    Admin adminLogin(String email, String pass) {
        for (Admin a : admins)
            if (a.email.equals(email) && a.password.equals(pass))
                return a;
        return null;
    }

    // ---- Customer login ----
    Customer login(String email, String pass) {
        for (Customer c : customers)
            if (c.email.equals(email) && c.password.equals(pass))
                return c;
        return null;
    }

    void register(Customer c) { customers.add(c); }

    void showPlans() {
        for (Plan p : plans)
            System.out.println(p.id + ". " + p.name + " - Rs." + p.price);
    }

    void assignPlan(Customer c, int planId) {
        for (Plan p : plans) {
            if (p.id == planId) {
                c.status = ConnectionStatus.ACTIVE;
                System.out.println("Plan '" + p.name + "' assigned! Status: ACTIVE");
                return;
            }
        }
        System.out.println("Invalid Plan ID.");
    }

    void generateBill(int cid) {
        bills.add(new Bill(bid++, cid, 799));
        System.out.println("Bill Generated. Bill ID: " + (bid - 1));
    }

    void payBill(int id) {
        for (Bill b : bills)
            if (b.id == id) { b.status = BillStatus.PAID; System.out.println("Paid successfully!"); return; }
        System.out.println("Bill not found.");
    }

    void raiseComplaint(int cid, String issue) {
        comps.add(new Complaint(compid++, cid, issue));
    }

    void viewComplaints(int cid) {
        boolean found = false;
        for (Complaint c : comps)
            if (c.cid == cid) {
                System.out.println("ID: " + c.id + " | Issue: " + c.issue + " | Status: " + c.status + " | Technician: " + c.technician);
                found = true;
            }
        if (!found) System.out.println("No complaints found.");
    }

    void assignTechnician(int compId, String tech) {
        for (Complaint c : comps)
            if (c.id == compId) {
                c.technician = tech;
                c.status = "IN PROGRESS";
                System.out.println("Technician assigned: " + tech);
                return;
            }
        System.out.println("Complaint not found.");
    }

    void updateComplaintStatus(int compId, int statusChoice) {
        for (Complaint c : comps) {
            if (c.id == compId) {
                switch (statusChoice) {
                    case 1 -> c.status = "RESOLVED";
                    case 2 -> c.status = "IN PROGRESS";
                    case 3 -> c.status = "WAITING";
                    default -> { System.out.println("Invalid status choice."); return; }
                }
                System.out.println("Complaint status updated to: " + c.status);
                return;
            }
        }
        System.out.println("Complaint not found.");
    }

    void changePassword(Customer c, String newPass) {
        c.password = newPass;
    }

    // ---- Admin: View all customers ----
    void viewAllCustomers() {
        if (customers.isEmpty()) { System.out.println("No customers registered yet."); return; }
        System.out.println("\n--- All Customers ---");
        for (Customer c : customers)
            System.out.println("ID: " + c.id + " | Name: " + c.name + " | Phone: " + c.phone +
                               " | Email: " + c.email + " | Status: " + c.status);
    }

    // ---- Admin: View all bills ----
    void viewAllBills() {
        if (bills.isEmpty()) { System.out.println("No bills generated yet."); return; }
        System.out.println("\n--- All Bills ---");
        for (Bill b : bills)
            System.out.println("Bill ID: " + b.id + " | Customer ID: " + b.cid +
                               " | Amount: Rs." + b.amt + " | Status: " + b.status);
    }

    // ---- Admin: View all complaints ----
    void viewAllComplaints() {
        if (comps.isEmpty()) { System.out.println("No complaints raised yet."); return; }
        System.out.println("\n--- All Complaints ---");
        for (Complaint c : comps)
            System.out.println("ID: " + c.id + " | Customer ID: " + c.cid +
                               " | Issue: " + c.issue + " | Status: " + c.status +
                               " | Technician: " + c.technician);
    }

    // ---- Admin: Suspend or activate a customer ----
    void toggleCustomerStatus(int customerId) {
        for (Customer c : customers) {
            if (c.id == customerId) {
                if (c.status == ConnectionStatus.ACTIVE) {
                    c.status = ConnectionStatus.SUSPENDED;
                    System.out.println("Customer '" + c.name + "' has been SUSPENDED.");
                } else {
                    c.status = ConnectionStatus.ACTIVE;
                    System.out.println("Customer '" + c.name + "' has been ACTIVATED.");
                }
                return;
            }
        }
        System.out.println("Customer not found.");
    }
}

// ================= MAIN =================
public class AutoConnect_Updated {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ISPService s = new ISPService();

        while (true) {

            // ===== ENTRY: Admin or Customer =====
            System.out.println("\n========== AutoConnect ISP ==========");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Who are you? ");
            int role = sc.nextInt();

            // ===========================
            // ===== ADMIN FLOW ==========
            // ===========================
            if (role == 1) {
                System.out.print("Admin Email: ");
                String ae = sc.next();
                System.out.print("Admin Password: ");
                String ap = sc.next();

                Admin admin = s.adminLogin(ae, ap);

                if (admin == null) {
                    System.out.println("SECURITY THREAT DETECTED! Unauthorized access attempt. Access Denied.");
                    continue;
                }

                System.out.println("\nWelcome Admin " + admin.name + "!");

                while (true) {
                    System.out.println("\n===== Admin Dashboard =====");
                    System.out.println("1. View Yourself");
                    System.out.println("2. View All Customers");
                    System.out.println("3. View All Bills");
                    System.out.println("4. View All Complaints");
                    System.out.println("5. Assign Technician");
                    System.out.println("6. Update Complaint Status");
                    System.out.println("7. Suspend / Activate Customer");
                    System.out.println("8. Logout");

                    int aop = sc.nextInt();

                    switch (aop) {

                        case 1 -> {
                            System.out.println("\n--- Admin Details ---");
                            System.out.println("Name:  " + admin.name);
                            System.out.println("Email: " + admin.email);
                            System.out.println("Role:  ADMINISTRATOR");
                        }

                        case 2 -> s.viewAllCustomers();

                        case 3 -> s.viewAllBills();

                        case 4 -> s.viewAllComplaints();

                        case 5 -> {
                            System.out.print("Complaint ID: ");
                            int id = sc.nextInt();
                            System.out.print("Technician Name: ");
                            String t = sc.next();
                            s.assignTechnician(id, t);
                        }

                        case 6 -> {
                            System.out.print("Complaint ID: ");
                            int id = sc.nextInt();
                            System.out.println("Select new status:");
                            System.out.println("1. RESOLVED");
                            System.out.println("2. IN PROGRESS");
                            System.out.println("3. WAITING");
                            System.out.print("Choice: ");
                            int statusChoice = sc.nextInt();
                            s.updateComplaintStatus(id, statusChoice);
                        }

                        case 7 -> {
                            s.viewAllCustomers();
                            System.out.print("Enter Customer ID to toggle status: ");
                            int cid = sc.nextInt();
                            s.toggleCustomerStatus(cid);
                        }

                        case 8 -> System.out.println("Admin logged out.");

                        default -> System.out.println("Invalid choice.");
                    }

                    if (aop == 8) break;
                }
            }

            // ===========================
            // ===== CUSTOMER FLOW =======
            // ===========================
            else if (role == 2) {

                while (true) {
                    System.out.println("\n1.Register\n2.Login\n3.Back");
                    int ch = sc.nextInt();

                    // ===== REGISTRATION =====
                    if (ch == 1) {
                        System.out.print("Name: ");
                        String n = sc.next();

                        System.out.print("Phone: ");
                        String p = sc.next();

                        System.out.print("Email: ");
                        String e = sc.next();

                        // ---- PASSWORD WITH STRENGTH CHECK ----
                        String pass = "";

                        while (true) {
                            System.out.print("Password: ");
                            String entered = sc.next();

                            String str = PasswordUtil.strength(entered);
                            System.out.println("Password Strength: " + str);

                            if (str.equals("WEAK")) {
                                System.out.println("Password is too weak! Please enter a stronger password.");
                                System.out.println("Tip: Use uppercase, lowercase, number & special character (min 8 chars).");
                                continue;
                            } else if (str.equals("MEDIUM")) {
                                System.out.println("Password is medium strength. Do you want to use it or enter a stronger one?");
                                System.out.println("1. Use this password");
                                System.out.println("2. Enter a new password");
                                System.out.print("Choice: ");
                                int choice = sc.nextInt();
                                if (choice == 2) continue;
                                pass = entered;
                            } else {
                                pass = entered;
                            }

                            // ---- CONFIRM PASSWORD ----
                            while (true) {
                                System.out.print("Confirm Password: ");
                                String confirm = sc.next();
                                if (pass.equals(confirm)) break;
                                else System.out.println("Passwords do not match! Please re-enter confirm password.");
                            }

                            break;
                        }

                        s.register(new Customer(s.cid++, n, p, e, pass));
                        System.out.println("Registered Successfully!");
                    }

                    // ===== LOGIN =====
                    else if (ch == 2) {
                        System.out.print("Email: ");
                        String e = sc.next();

                        System.out.print("Password: ");
                        String p = sc.next();

                        Customer c = s.login(e, p);

                        if (c == null) {
                            System.out.println("Invalid credentials! Please register first.");
                            continue;
                        }

                        System.out.println("\nWelcome " + c.name + "!");
                        System.out.println("Phone: " + c.phone);
                        System.out.println("Email: " + c.email);

                        while (true) {
                            System.out.println("\n1.View Yourself");
                            System.out.println("2.Show Plans");
                            System.out.println("3.Assign Plan");
                            System.out.println("4.Generate Bill");
                            System.out.println("5.Pay Bill");
                            System.out.println("6.Raise Complaint");
                            System.out.println("7.View Complaint");
                            System.out.println("8.Assign Technician");
                            System.out.println("9.Update Complaint Status");
                            System.out.println("10.Change Password");
                            System.out.println("11.Exit");

                            int op = sc.nextInt();

                            switch (op) {

                                case 1 -> {
                                    System.out.println("\n--- Your Details ---");
                                    System.out.println("Name: " + c.name);
                                    System.out.println("Phone: " + c.phone);
                                    System.out.println("Email: " + c.email);
                                    System.out.println("Status: " + c.status);
                                }

                                case 2 -> s.showPlans();

                                case 3 -> {
                                    s.showPlans();
                                    System.out.print("Enter Plan ID to assign: ");
                                    int pid = sc.nextInt();
                                    s.assignPlan(c, pid);
                                }

                                case 4 -> s.generateBill(c.id);

                                case 5 -> {
                                    System.out.print("Bill ID: ");
                                    int billId = sc.nextInt();
                                    s.payBill(billId);
                                }

                                case 6 -> {
                                    sc.nextLine();
                                    System.out.print("Issue: ");
                                    String issue = sc.nextLine();
                                    s.raiseComplaint(c.id, issue);
                                    System.out.println("Complaint raised successfully!");
                                }

                                case 7 -> s.viewComplaints(c.id);

                                case 8 -> {
                                    System.out.print("Complaint ID: ");
                                    int id = sc.nextInt();
                                    System.out.print("Technician Name: ");
                                    String t = sc.next();
                                    s.assignTechnician(id, t);
                                }

                                case 9 -> {
                                    System.out.print("Complaint ID: ");
                                    int id = sc.nextInt();
                                    System.out.println("Select new status:");
                                    System.out.println("1. RESOLVED");
                                    System.out.println("2. IN PROGRESS");
                                    System.out.println("3. WAITING");
                                    System.out.print("Choice: ");
                                    int statusChoice = sc.nextInt();
                                    s.updateComplaintStatus(id, statusChoice);
                                }

                                case 10 -> {
                                    System.out.print("New Password: ");
                                    String np = sc.next();
                                    if (PasswordUtil.valid(np)) {
                                        s.changePassword(c, np);
                                        System.out.println("Password Changed Successfully!");
                                    } else {
                                        System.out.println("Weak password! Not updated.");
                                    }
                                }

                                case 11 -> System.out.println("Logging out...");

                                default -> System.out.println("Invalid choice");
                            }

                            if (op == 11) break;
                        }
                    }

                    // Back to main menu
                    else if (ch == 3) break;

                    else System.out.println("Invalid choice.");
                }
            }

            // ===== EXIT =====
            else if (role == 3) {
                System.out.println("Exiting AutoConnect. Goodbye!");
                return;
            }

            else {
                System.out.println("Invalid choice.");
            }
        }
    }
}