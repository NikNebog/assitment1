package nik.products;

public class Clothing extends Product {
    private String size;
    private String material;

    public Clothing(String name, double price, int stock, String size, String material) {
        super(name, price, stock);
        this.size = size;
        this.material = material;
    }

    @Override
    public String details() {
        return "Clothing{size='" + size + "', material='" + material + "'}";
    }
}
