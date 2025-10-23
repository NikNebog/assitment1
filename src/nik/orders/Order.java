package nik.orders;

import nik.users.Customer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int NEXT_ID = 1000;

    private final int orderId;
    private final Customer customer;
    private final LocalDateTime createdAt;
    private final List<OrderItem> items = new ArrayList<>();

    public Order(Customer customer) {
        this.orderId = NEXT_ID++;
        this.customer = customer;
        this.createdAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) { items.add(item); }

    public Customer getCustomer() {
        return customer;
    }

    public double totalAmount() {
        return items.stream().mapToDouble(OrderItem::lineTotal).sum();
    }

    public void printReceipt() {
        System.out.println("====================================");
        System.out.printf("Order #%d | Customer: %s%n", orderId, customer.getName());
        System.out.println("Items:");
        for (OrderItem it : items) System.out.println(" - " + it);
        System.out.printf("TOTAL: %.2f %n", totalAmount());
        System.out.println("Created: " + createdAt);
        System.out.println("====================================");
    }
}
