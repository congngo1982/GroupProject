package com.ngonc.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "accountManagement";
    private static final int DATABASE_VERSION = 5;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createAccount = "CREATE TABLE accounts ( " +
                "username TEXT (50)  NOT NULL PRIMARY KEY, " +
                "password TEXT (50)  NOT NULL, " +
                "role  INTEGER NOT NULL, " +
                "email TEXT (50), " +
                "phone TEXT (12)  NOT NULL, " +
                "address  TEXT (200) NOT NULL)";
        String createAuthor = "CREATE TABLE authors (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT (50) NOT NULL, " +
                "gender TEXT, " +
                "description TEXT, " +
                "birthday TEXT)";
        String createBook = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "name TEXT (50) NOT NULL, " +
                "author INTEGER (50) NOT NULL  REFERENCES authors (id), " +
                "category TEXT (50) NOT NULL, " +
                "ISBN TEXT (12) NOT NULL,  " +
                "price REAL NOT NULL , " +
                "quantity INTEGER NOT NULL , " +
                "status INTEGER NOT NULL)";
        String createOrder = "CREATE TABLE [order] ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_accounts TEXT REFERENCES accounts (username), " +
                "date_created TEXT, " +
                "address TEXT (200), " +
                "total_amount REAL NOT NULL )";
        String createOrderDetail = "CREATE TABLE orderdetail ( " +
                "id INTEGER PRIMARY KEY,  " +
                "id_book INTEGER REFERENCES books (id), " +
                "id_order INTEGER REFERENCES [order] (id), " +
                "amount INTEGER NOT NULL, " +
                "price REAL NOT NULL)";
        sqLiteDatabase.execSQL(createAccount);
        sqLiteDatabase.execSQL(createAuthor);
        sqLiteDatabase.execSQL(createBook);
        sqLiteDatabase.execSQL(createOrder);
        sqLiteDatabase.execSQL(createOrderDetail);
        System.out.println("success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropAccount = "DROP TABLE IF EXISTS accounts";
        String dropAuthor = "DROP TABLE IF EXISTS authors";
        String dropBook = "DROP TABLE IF EXISTS books";
        String dropOrder = "DROP TABLE IF EXISTS [order]";
        String dropOrderDetail = "DROP TABLE IF EXISTS orderdetail";
        sqLiteDatabase.execSQL(dropOrderDetail);
        sqLiteDatabase.execSQL(dropOrder);
        sqLiteDatabase.execSQL(dropBook);
        sqLiteDatabase.execSQL(dropAuthor);
        sqLiteDatabase.execSQL(dropAccount);
        onCreate(sqLiteDatabase);
    }
}
