package nik.store;

import nik.orders.*;
import nik.products.*;
import nik.users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ECommerceSystem {
    private final List<User> users = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    public void addCustomer(String name, String email, String password, String address) {
        users.add(new Customer(name, email, password, address));
    }

    public void addEmployee(String name, String email, String password, String position) {
        users.add(new Employee(name, email, password, position));
    }

    public Optional<User> auth(String email, String password) {
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password))
                .findFirst();
    }

    public void addBook(String name, double price, int stock, String author) {
        products.add(new Book(name, price, stock, author));
    }

    public void addElectronics(String name, double price, int stock, String brand, int warrantyMonths) {
        products.add(new Electronics(name, price, stock, brand, warrantyMonths));
    }

    public void addClothing(String name, double price, int stock, String size, String material) {
        products.add(new Clothing(name, price, stock, size, material));
    }

    public Product findProductById(int id) {
        return products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    // ✅ Fixed and improved logic for stock checking
    public Order placeOrder(Customer customer, List<OrderItem> items) {
        // 1️⃣ Check stock availability for each product
        for (OrderItem it : items) {
            if (!it.getProduct().hasStock(it.getQuantity())) {
                throw new IllegalArgumentException(
                        "Not enough stock for: " + it.getProduct().getName() +
                                " (Available: " + it.getProduct().getStockQuantity() + ")"
                );
            }
        }

        // 2️⃣ Deduct stock for purchased products
        for (OrderItem it : items) {
            it.getProduct().decreaseStock(it.getQuantity());
        }

        // 3️⃣ Create new order and save it
        Order order = new Order(customer);
        items.forEach(order::addItem);
        orders.add(order);
        return order;
    }

    public List<User> getUsers() { return users; }
    public List<Product> getProducts() { return products; }
    public List<Order> getOrders() { return orders; }

    public void seed() {
        addEmployee("Admin", "admin@mail.ru", "admin", "Manager");
        addCustomer("Polina", "polina@mail.ru", "123", "Petropavl, KZ");

        addBook("Clean Code", 10_000, 6, "Robert C. Martin");
        addElectronics("Headphones X100", 25_000, 5, "SoundMax", 24);
        addClothing("Hoodie Classic", 12_000, 3, "L", "Cotton");
    }
}
