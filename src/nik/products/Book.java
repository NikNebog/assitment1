package nik.products;

public class Book extends Product {
    private String author;

    public Book(String name, double price, int stock, String author) {
        super(name, price, stock);
        this.author = author;
    }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String details() {
        return "Book{author='" + author + "'}";
    }
}
