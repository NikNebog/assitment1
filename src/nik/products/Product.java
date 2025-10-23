package nik.products;

public abstract class Product {
    private static int NEXT_ID = 1;

    private final int id;
    private String name;
    private double price;
    private int stockQuantity;

    protected Product(String name, double price, int stockQuantity) {
        this.id = NEXT_ID++;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    public boolean hasStock(int qty) { return stockQuantity >= qty; }
    public void decreaseStock(int qty) { this.stockQuantity -= qty; }

    public abstract String details();

    public void displayInfo() {
        System.out.printf("#%d %s | %.2f | stock=%d | %s%n",
                id, name, price, stockQuantity, details());
    }
}
