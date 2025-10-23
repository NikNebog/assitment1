package nik.users;

public class Customer extends User {
    private String shippingAddress;

    public Customer(String name, String email, String password, String shippingAddress) {
        super(name, email, password, "customer");
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
