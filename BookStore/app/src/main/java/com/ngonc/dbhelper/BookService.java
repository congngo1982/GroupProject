package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Author;
import com.ngonc.model.Books;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private DBHelper dbHelper;
    private static final String TABLE = "books";
    private static final String NAME = "name";
    private static final String AUTHOR = "author";
    private static final String CATEGORY = "category";
    private static final String ISBN = "ISBN";
    private static final String PRICE = "price";
    private static final String QUANTITY = "quantity";
    private static final String STATUS = "status";



    public BookService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public boolean AddBook(Books book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, book.getName());
        values.put(AUTHOR, book.getAuthorId());
        values.put(CATEGORY, book.getCategory());
        values.put(ISBN, book.getISBN());
        values.put(PRICE, book.getPrice());
        values.put(QUANTITY, book.getQuantity());
        values.put(STATUS, booleanToInt(book.isStatus()));

        long result = db.insert(TABLE, null, values);
        db.close();

        return result != -1;
    }

    public List<Books> GetAllBook() {
        List<Books> books = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Books book = new Books(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getInt(6),
                    intToBoolean(cursor.getInt(7))
            );
            books.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return books;
    }
    private boolean intToBoolean(int value) {
        return value == 1;
    }
    private int booleanToInt(boolean value) {
        return value ? 1 : 0;
    }


    public boolean deleteBook(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, 0);

        int rowsAffected = db.update(TABLE, values, "id=?", new String[]{String.valueOf(id)});
        return rowsAffected > 0;
    }

    public Books getBookById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE +
                " WHERE id= " + id;

        Cursor cursor = db.rawQuery(query, null);

        Books book = null;

        if (cursor.moveToFirst()) {
            int bookId = cursor.getInt(0);
            String name = cursor.getString(1);
            int authorId = cursor.getInt(2);
            String category = cursor.getString(3);
            String ISBN = cursor.getString(4);
            double price = cursor.getDouble(5);
            int quantity = cursor.getInt(6);
            boolean status = intToBoolean(cursor.getInt(7));

            book = new Books(bookId, name, authorId, category, ISBN, price, quantity, status);
        }

        cursor.close();
        return book;
    }

    public boolean UpdateBook(Books book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, book.getName());
        values.put(ISBN, book.getISBN());
        values.put(PRICE, book.getPrice());
        values.put(QUANTITY, book.getQuantity());
        values.put(AUTHOR, book.getAuthorId());
        values.put(CATEGORY, book.getCategory());
        values.put(STATUS, booleanToInt(book.isStatus()));

        int rowsAffected = db.update(TABLE, values, "id = ?", new String[] {String.valueOf(book.getId())});
        db.close();

        return rowsAffected > 0;
    }

    public List<Books> SearchBook(String name) {
        List<Books> books = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s LIKE ?", TABLE, NAME);
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{"%" + name + "%"});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Books book = new Books(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getInt(6),
                    intToBoolean(cursor.getInt(7))
            );
            books.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        return books;
    }

}












//package com.ngonc.dbhelper;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.ngonc.bookstore.Book;
//import com.ngonc.model.Author;
//import com.ngonc.model.Books;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookService {
//    private DBHelper dbHelper;
//    private static final String TABLE = "books";
//    private static final String ID = "id";
//    private static final String NAME = "name";
//    private static final String AUTHOR = "author";
//    private static final String CATEGORY = "category";
//    private static final String ISBN = "ISBN";
//    private static final String PRICE = "price";
//    private static final String QUANTITY = "quantity";
//    private static final String STATUS = "status";
//
//    public BookService(DBHelper dbHelper) {
//        this.dbHelper = dbHelper;
//    }
//
//    public void AddBook(Books books) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID, books.getId());
//        values.put(NAME, books.getName());
//        values.put(AUTHOR, books.getAuthor().getName());
//        values.put(CATEGORY, books.getCategory());
//        values.put(ISBN, books.getISBN());
//        values.put(PRICE, books.getPrice());
//        values.put(QUANTITY, books.getQuantity());
//        values.put(STATUS, books.isStatus() ? 1 : 0);
//        db.insert(TABLE, null, values);
//        db.close();
//    }
//
//    public List<Books> GetAllBook() {
//        AuthorService authorService = new AuthorService(dbHelper);
//        List<Books> booksList = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE;
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Books book = new Books(cursor.getInt(0),
//                    cursor.getString(1),
//                    authorService.GetAuthorByName(cursor.getString(2)),
//                    cursor.getString(3),
//                    cursor.getString(4),
//                    cursor.getDouble(5),
//                    cursor.getInt(6),
//                    cursor.getInt(7) == 1);
//            booksList.add(book);
//            cursor.moveToNext();
//        }
//        return booksList;
//    }
//
//    public Books GetBookById(int id, AuthorService authorService){
//        Books books = null;
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ID);
//        Cursor cursor = db.rawQuery(query, new String[] {id + ""});
//        try {
//            if(cursor != null){
//                cursor.moveToFirst();
//                books = new Books(cursor.getInt(0),
//                        cursor.getString(1),
//                        authorService.GetAuthorByName(cursor.getString(2)),
//                        cursor.getString(3),
//                        cursor.getString(4),
//                        cursor.getDouble(5),
//                        cursor.getInt(6),
//                        cursor.getInt(7) == 1);
//            }
//        }catch (Exception ex){
//
//        }
//        return books;
//    }
//}
