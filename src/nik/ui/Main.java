package nik.ui;

import nik.orders.*;
import nik.products.Product;
import nik.store.ECommerceSystem;
import nik.users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        ECommerceSystem sys = new ECommerceSystem();
        sys.seed();

        System.out.println("=== Console E-Commerce ===");

        while (true) {
            System.out.println("\n1) Log in");
            System.out.println("2) Exit");
            System.out.print("Choose: ");
            String choice = in.nextLine();

            if ("1".equals(choice)) login(sys);
            else if ("2".equals(choice)) {
                System.out.println("Bye!");
                break;
            } else System.out.println("Invalid choice.");
        }
    }

    private static void login(ECommerceSystem sys) {
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("Password: ");
        String pass = in.nextLine();

        Optional<User> u = sys.auth(email, pass);
        if (u.isEmpty()) {
            System.out.println("Wrong credentials!");
            return;
        }

        User user = u.get();
        System.out.println("Logged in as: " + user.getName() + " (" + user.getRole() + ")");

        if ("employee".equals(user.getRole())) employeeMenu(sys, (Employee) user);
        else customerMenu(sys, (Customer) user);
    }

    private static void employeeMenu(ECommerceSystem sys, Employee e) {
        while (true) {
            System.out.println("\n[Employee Menu]");
            System.out.println("1) List products");
            System.out.println("2) Add product");
            System.out.println("3) List users");
            System.out.println("4) Add user");
            System.out.println("5) View orders");
            System.out.println("6) Logout");
            System.out.print("Choose: ");
            String c = in.nextLine();

            switch (c) {
                case "1" -> sys.getProducts().forEach(Product::displayInfo);
                case "2" -> addProduct(sys);
                case "3" -> sys.getUsers().forEach(User::displayInfo);
                case "4" -> addUser(sys);
                case "5" -> sys.getOrders().forEach(Order::printReceipt);
                case "6" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void customerMenu(ECommerceSystem sys, Customer c) {
        while (true) {
            System.out.println("\n[Customer Menu]");
            System.out.println("1) List products");
            System.out.println("2) Place order");
            System.out.println("3) View my orders");
            System.out.println("4) Logout");
            System.out.print("Choose: ");
            String x = in.nextLine();

            switch (x) {
                case "1" -> sys.getProducts().forEach(Product::displayInfo);
                case "2" -> placeOrder(sys, c);
                case "3" -> {
                    System.out.println("--- Your Orders ---");
                    sys.getOrders().stream()
                            .filter(o -> o.getCustomer().getId() == c.getId())
                            .forEach(Order::printReceipt);
                }

                case "4" -> { return; }
                default -> System.out.println("Invalid.");
            }
        }
    }

    private static void addProduct(ECommerceSystem sys) {
        System.out.println("1) Book  2) Electronics  3) Clothing");
        String type = in.nextLine();
        System.out.print("Name: "); String name = in.nextLine();
        System.out.print("Price: "); double price = Double.parseDouble(in.nextLine());
        System.out.print("Stock: "); int stock = Integer.parseInt(in.nextLine());

        switch (type) {
            case "1" -> {
                System.out.print("Author: "); String a = in.nextLine();
                sys.addBook(name, price, stock, a);
            }
            case "2" -> {
                System.out.print("Brand: "); String b = in.nextLine();
                System.out.print("Warranty (months): "); int wm = Integer.parseInt(in.nextLine());
                sys.addElectronics(name, price, stock, b, wm);
            }
            case "3" -> {
                System.out.print("Size: "); String s = in.nextLine();
                System.out.print("Material: "); String m = in.nextLine();
                sys.addClothing(name, price, stock, s, m);
            }
            default -> System.out.println("Invalid type.");
        }
    }

    private static void addUser(ECommerceSystem sys) {
        System.out.println("1) Customer  2) Employee");
        String t = in.nextLine();
        System.out.print("Name: "); String n = in.nextLine();
        System.out.print("Email: "); String e = in.nextLine();
        System.out.print("Password: "); String p = in.nextLine();

        if ("1".equals(t)) {
            System.out.print("Address: "); String addr = in.nextLine();
            sys.addCustomer(n, e, p, addr);
        } else if ("2".equals(t)) {
            System.out.print("Position: "); String pos = in.nextLine();
            sys.addEmployee(n, e, p, pos);
        }
    }

    private static void placeOrder(ECommerceSystem sys, Customer c) {
        List<OrderItem> items = new ArrayList<>();
        while (true) {
            sys.getProducts().forEach(Product::displayInfo);
            System.out.print("Product ID (0 to finish): ");
            int pid = Integer.parseInt(in.nextLine());
            if (pid == 0) break;

            Product p = sys.findProductById(pid);
            if (p == null) {
                System.out.println("Invalid ID.");
                continue;
            }
            System.out.print("Quantity: ");
            int qty = Integer.parseInt(in.nextLine());
            items.add(new OrderItem(p, qty));
        }
        if (items.isEmpty()) {
            System.out.println("No items selected.");
            return;
        }

        try {
            Order o = sys.placeOrder(c, items);
            o.printReceipt();
        } catch (Exception ex) {
            System.out.println("Order failed: " + ex.getMessage());
        }
    }
}
