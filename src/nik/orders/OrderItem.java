package nik.orders;

import nik.products.Product;

public class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public double lineTotal() { return product.getPrice() * quantity; }

    @Override
    public String toString() {
        return String.format("%s x%d = %.2f", product.getName(), quantity, lineTotal());
    }
}
