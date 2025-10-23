package nik.users;

public class Employee extends User {
    private String position;

    public Employee(String name, String email, String password, String position) {
        super(name, email, password, "employee");
        this.position = position;
    }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
}
