package nik.products;

public class Electronics extends Product {
    private String brand;
    private int warrantyMonths;

    public Electronics(String name, double price, int stock, String brand, int warrantyMonths) {
        super(name, price, stock);
        this.brand = brand;
        this.warrantyMonths = warrantyMonths;
    }

    @Override
    public String details() {
        return "Electronics{brand='" + brand + "', warranty=" + warrantyMonths + "m}";
    }
}
