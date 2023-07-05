package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Account;
import com.ngonc.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private DBHelper dbHelper;
    private static final String TABLE = "authors";
    private static final String NAME = "name";
    private static final String GENDER = "gender";
    private static final String DESCRIPTION = "description";
    private static final String BIRTHDAY = "birthday";

    public AuthorService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public void AddAuthor(Author author) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, author.getName());
        values.put(GENDER, author.getGender());
        values.put(DESCRIPTION, author.getDescription());
        values.put(BIRTHDAY, author.getBirthday());
        db.insert(TABLE, null, values);
        db.close();
    }
    public List<Author> GetAllAuthor() {
        List<Author> authors = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Author author = new Author(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            authors.add(author);
            cursor.moveToNext();
        }
        cursor.close();
        return authors;
    }
}