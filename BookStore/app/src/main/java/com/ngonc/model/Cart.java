package com.ngonc.model;

public class Cart {
    private int id;
    private Books books;
    private int amount;
    private double price;
    private double totalPrice;

    public Cart() {
    }

    public Cart(Books books, int amount, double price) {
        this.books = books;
        this.amount = amount;
        this.price = books.getPrice();
        this.totalPrice = amount * this.price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return this.price * this.amount;
    }

//    public void setTotalPrice(double totalPrice) {
//        this.totalPrice = totalPrice;
//    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", books=" + books +
                ", amount=" + amount +
                ", price=" + price +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
