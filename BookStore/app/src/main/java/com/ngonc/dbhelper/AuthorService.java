package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private DBHelper dbHelper;
    private static final String TABLE = "authors";
    private static final String ID = "id";
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

    public boolean deleteAuthorByName(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(TABLE, "name LIKE ?", new String[]{String.valueOf(name)});
        db.close();
        return rowsAffected > 0;
    }

    public boolean UpdateAuthor(Author author) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, author.getName());
        values.put(GENDER, author.getGender());
        values.put(DESCRIPTION, author.getDescription());
        values.put(BIRTHDAY, author.getBirthday());

        int rowsAffected = db.update(TABLE, values, "name like ?", new String[] {String.valueOf(author.getName())});
        db.close();

        return rowsAffected > 0;
    }

    public List<Author> getAllAuthor() {
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
    public Author getAuthorById(int id) {
        //check id exist
        if(checkIdExist(id)){
            SQLiteDatabase db = this.dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + TABLE + " WHERE id = ?";
            String[] selectionArgs = {String.valueOf(id)};
            Cursor cursor = db.rawQuery(query, selectionArgs);
            Author author = null;
            if (cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex(ID);
                int nameColumnIndex = cursor.getColumnIndex(NAME);
                int genderColumnIndex = cursor.getColumnIndex(GENDER);
                int descriptionColumnIndex = cursor.getColumnIndex(DESCRIPTION);
                int birthdayColumnIndex = cursor.getColumnIndex(BIRTHDAY);
                if (idColumnIndex > -1 && nameColumnIndex > -1 && genderColumnIndex > -1) {
                    int authorId = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String gender = cursor.getString(genderColumnIndex);
                    String description = cursor.isNull(descriptionColumnIndex) ? null : cursor.getString(descriptionColumnIndex);
                    String birthday = cursor.isNull(birthdayColumnIndex) ? null : cursor.getString(birthdayColumnIndex);
                    author = new Author(authorId, name, gender, description, birthday);
                    return author;
                }
            }
            cursor.close();
        }
        return null;
    }

    private boolean checkIdExist(int id){
        for (Author author: getAllAuthor()) {
            if(author.getId() == id){
                return true;
            }
        }
        return false;
    }
}








//package com.ngonc.dbhelper;
//
//import android.content.ContentValues;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.ngonc.model.Author;
//
//public class AuthorService {
//    private DBHelper dbHelper;
//    private static final String TABLE = "authors";
//    private static final String ID = "id";
//    private static final String NAME = "name";
//    private static final String GENDER = "gender";
//    private static final String DESCRIPTION = "description";
//    private static final String BIRTHDAY = "birthday";
//
//    public AuthorService(DBHelper dbHelper) {
//        this.dbHelper = dbHelper;
//    }
//
//    public void AddAuthor(Author author) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(ID, author.getId());
//        values.put(NAME, author.getName());
//        values.put(GENDER, author.getGender());
//        values.put(DESCRIPTION, author.getDescription());
//        values.put(BIRTHDAY, author.getBirthday());
//        db.insert(TABLE, null, values);
//        db.close();
//    }
//
//    public Author GetAuthorByName(String name) {
//        Author author = null;
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String query = String.format("SELECT * FROM %s WHERE %s = ? ", TABLE, NAME);
//        Cursor cursor = db.rawQuery(query, new String[]{name});
//        try {
//            cursor.moveToFirst();
//            author = new Author(cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getString(3),
//                    cursor.getString(4));
//        } catch (Exception ex) {
//
//        }
//
//        return author;
//    }
//}
