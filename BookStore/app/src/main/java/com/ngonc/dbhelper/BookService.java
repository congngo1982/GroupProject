package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.bookstore.Book;
import com.ngonc.model.Author;
import com.ngonc.model.Books;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    private DBHelper dbHelper;
    private static final String TABLE = "books";
    private static final String ID = "id";
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

    public void AddBook(Books books) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, books.getId());
        values.put(NAME, books.getName());
        values.put(AUTHOR, books.getAuthor().getName());
        values.put(CATEGORY, books.getCategory());
        values.put(ISBN, books.getISBN());
        values.put(PRICE, books.getPrice());
        values.put(QUANTITY, books.getQuantity());
        values.put(STATUS, books.isStatus() ? 1 : 0);
        db.insert(TABLE, null, values);
        db.close();
    }

    public List<Books> GetAllBook() {
        AuthorService authorService = new AuthorService(dbHelper);
        List<Books> booksList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Books book = new Books(cursor.getInt(0),
                    cursor.getString(1),
                    authorService.GetAuthorByName(cursor.getString(2)),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getDouble(5),
                    cursor.getInt(6),
                    cursor.getInt(7) == 1);
            booksList.add(book);
            cursor.moveToNext();
        }
        return booksList;
    }

    public Books GetBookById(int id, AuthorService authorService){
        Books books = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ID);
        Cursor cursor = db.rawQuery(query, new String[] {id + ""});
        try {
            if(cursor != null){
                cursor.moveToFirst();
                books = new Books(cursor.getInt(0),
                        cursor.getString(1),
                        authorService.GetAuthorByName(cursor.getString(2)),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getDouble(5),
                        cursor.getInt(6),
                        cursor.getInt(7) == 1);
            }
        }catch (Exception ex){

        }
        return books;
    }
}
