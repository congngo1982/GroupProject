package com.ngonc.bookstore;

public interface BookActivityListener {
    void deleteBook(int id, String name);
    void editBook(int id);
}