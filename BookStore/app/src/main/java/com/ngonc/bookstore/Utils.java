package com.ngonc.bookstore;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.model.Account;
import com.ngonc.model.Author;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static void SaveCartContext(Context context, List<Cart> cartList){
        SharedPreferences pref = context.getSharedPreferences("MYAPP", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String carts = new Gson().toJson(cartList);
        editor.putString("CARTLIST", carts);
        editor.commit();
    }

    public static List<Cart> GetCartContext(Context context){
        SharedPreferences pref = context.getSharedPreferences("MYAPP", 0); // 0 - for private mode
        pref.getString("CARTLIST", null);
        String cartString = pref.getString("CARTLIST", null);
        Type type = new TypeToken<List<Cart>>() {
        }.getType();
        List<Cart> cartList = new Gson().fromJson(cartString, type);
        return cartList;
    }

    public static void AddtoCart(BookService bookService, AuthorService authorService) {
        List<Cart> carts = new ArrayList<>();
        Books book1 = bookService.GetBookById(1, authorService);
        Books book2 = bookService.GetBookById(2, authorService);
        carts.add(new Cart(book1, 10, book1.getPrice()));
        carts.add(new Cart(book2, 5, book2.getPrice()));
        System.out.println(carts);
    }

    public static void AddAccount(AccountService accountService) {
        accountService.AddAccount(new Account("ngonc", "19082002", "admin", "ngonc@gmail.com", "0000", "Gia Lai"));
        accountService.AddAccount(new Account("ngo", "19082002", "user", "nguyencongngo@gmail.com", "1111", "Pleiku"));
        List<Account> accounts = accountService.GetAccount();
        for (Account acc : accounts) {
            System.out.println(acc);
        }
    }

    public static void AddAuthor(AuthorService authorService) {
        authorService.AddAuthor(new Author(2, "Author1", "Female", "Expert", "19/12/1212"));
        authorService.AddAuthor(new Author(1, "Author2", "Male", "Good", "19/12/1212"));
        authorService.AddAuthor(new com.ngonc.model.Author(3, "Author3", "Male", "Good", "19/12/1212"));
        authorService.AddAuthor(new com.ngonc.model.Author(4, "Author4", "Female", "Good", "19/12/1212"));
        authorService.AddAuthor(new Author(5, "Author5", "Male", "Good", "19/12/1212"));
    }

    public static void AddBook(BookService bookService, AuthorService authorService) {
        bookService.AddBook(new Books(2,
                "Shin Cau be but chi",
                authorService.GetAuthorByName("Author1"),
                "Comedy",
                "1111",
                16.0,
                10, true));
        bookService.AddBook(new Books(1,
                "Doraemon",
                authorService.GetAuthorByName("Author2"),
                "Comedy",
                "2222",
                16.0,
                20, true));
        bookService.AddBook(new Books(3,
                "Conan",
                authorService.GetAuthorByName("Author3"),
                "Comedy",
                "3333",
                16.0,
                10, true));
        bookService.AddBook(new Books(4,
                "Trang Quynh",
                authorService.GetAuthorByName("Author4"),
                "Comedy",
                "4444",
                16.0,
                20, true));
        bookService.AddBook(new Books(5,
                "Pokemon",
                authorService.GetAuthorByName("Author5"),
                "Comedy",
                "5555",
                16.0,
                10, true));
        bookService.AddBook(new Books(6,
                "Co 2 con meo ngoi ben cua so",
                authorService.GetAuthorByName("Author2"),
                "Comedy",
                "6666",
                16.0,
                20, true));
    }
    public static List<Cart> GetCart(BookService bookService, AuthorService authorService) {
        List<Cart> carts = new ArrayList<>();
        Books book1 = bookService.GetBookById(1, authorService);
        Books book2 = bookService.GetBookById(2, authorService);
        Books book3 = bookService.GetBookById(3, authorService);
        Books book4 = bookService.GetBookById(4, authorService);
        Books book5 = bookService.GetBookById(5, authorService);
        Books book6 = bookService.GetBookById(6, authorService);
        carts.add(new Cart(book1, 10, book1.getPrice()));
        carts.add(new Cart(book2, 5, book2.getPrice()));
        carts.add(new Cart(book3, 4, book3.getPrice()));
        carts.add(new Cart(book4, 3, book4.getPrice()));
        carts.add(new Cart(book5, 6, book5.getPrice()));
        carts.add(new Cart(book6, 1, book6.getPrice()));
        return carts;
    }
}
