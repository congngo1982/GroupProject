package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private DBHelper dbHelper;
    private static final String TABLE = "accounts";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String ROLE = "role";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";

    public AccountService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void AddAccount(Account account) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, account.getUsername());
        values.put(PASSWORD, account.getPassword());
        values.put(ROLE, account.getRole());
        values.put(EMAIL, account.getEmail());
        values.put(PHONE, account.getPhone());
        values.put(ADDRESS, account.getAddress());
        db.insert(TABLE, null, values);
        db.close();
    }

    public List<Account> GetAccount() {
        List<Account> accounts = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Account account = new Account(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
            accounts.add(account);
            cursor.moveToNext();
        }
        cursor.close();
        return accounts;
    }

    public Account Login(String username, String password) {
        Account account = null;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ? ", TABLE, USERNAME, PASSWORD);
        Cursor cursor = db.rawQuery(query, new String[]{username, password});
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            account = new Account(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            cursor.close();
            return account;
        } catch (Exception ex) {

        } finally {
            cursor.close();
        }
        return account;
    }
    public Account GetAccountByUsername(String username) {
        Account account = null;
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, USERNAME);
        Cursor cursor = db.rawQuery(query, new String[]{username});
        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            account = new Account(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            cursor.close();
            return account;
        } catch (Exception ex) {

        } finally {
            cursor.close();
        }
        return account;
    }

    public void UpdateAccount(Account account, String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, account.getUsername());
        values.put(PASSWORD, account.getPassword());
        values.put(EMAIL, account.getEmail());
        values.put(PHONE, account.getPhone());
        values.put(ADDRESS, account.getAddress());
        db.update(TABLE, values, USERNAME + " = ?", new String[]{username});
    }
    public void UpdateAccountRole(Account account){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROLE, "ADMIN");
        db.update(TABLE, values, USERNAME + " = ?", new String[]{account.getUsername()});
    }
}
