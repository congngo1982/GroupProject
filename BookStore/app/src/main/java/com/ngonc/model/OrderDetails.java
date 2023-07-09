package com.ngonc.model;

public class OrderDetails {
    private int id;
    private Books books;
    private int amount;
    private double price;
    private Order order;

    public OrderDetails() {
    }

    public OrderDetails(int id, Books books, int amount, double price, Order order) {
        this.id = id;
        this.books = books;
        this.amount = amount;
        this.price = price;
        this.order = order;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderDetails{" + "id=" + id + ", books=" + books + ", amount=" + amount + ", price=" + price + ", order=" + order + '}';
    }
}
