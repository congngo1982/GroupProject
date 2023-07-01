package com.ngonc.model;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private Account account;
    private Date createdDate;
    private String address;
    private int totalPrice;
    private List<OrderDetails> orderDetails;

    public Order() {
    }

    public Order(int id, Account account, Date createdDate, String address, int totalPrice, List<OrderDetails> orderDetails) {
        this.id = id;
        this.account = account;
        this.createdDate = createdDate;
        this.address = address;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
