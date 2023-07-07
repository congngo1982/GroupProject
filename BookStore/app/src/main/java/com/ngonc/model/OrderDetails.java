package com.ngonc.model;

public class OrderDetails {
    private int id;
    private Books books;
    private int amount;
    private int price;
    private Order order;

    public OrderDetails() {
    }

    public OrderDetails(int id, Books books, int amount, int price, Order order) {
        this.id = id;
        this.books = books;
        this.amount = amount;
        this.price = price;
        this.order = order;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
