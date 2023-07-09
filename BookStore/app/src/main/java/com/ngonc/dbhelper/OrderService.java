package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderService {
    private DBHelper dbHelper;
    private static final String TABLE = "[order]";
    private static final String ID = "id";
    private static final String ACCOUNT = "id_accounts";
    private static final String DATE = "date_created";
    private static final String ADDRESS = "address";
    private static final String TOTAL = "total_amount";

    public OrderService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public int AddToOrder(Order order){
        int id = -1;
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT, order.getAccount().getUsername());
        values.put(DATE, formatter.format(order.getCreatedDate()));
        values.put(ADDRESS, order.getAddress());
        values.put(TOTAL, order.getTotalPrice());
        db.insert(TABLE, null, values);
        Cursor res = db.rawQuery(String.format("SELECT last_insert_rowid() ", TABLE), null);
        res.moveToFirst();
        if(!res.isAfterLast()){
            id = res.getInt(0);
        }
        db.close();
        return id;
    }

    public List<Order> GetOrder(String username){
        List<Order> orderList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ACCOUNT);
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");;
        AccountService accountService = new AccountService(dbHelper);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Date date = new Date();
            System.out.println("Date: " + cursor.getString(2));
            try{
                date = formatter.parse(cursor.getString(2));
            }catch (Exception ex){
                System.out.println("Exception: " + ex.getMessage());
            }
            Order order = new Order(
                    cursor.getInt(0),
                    accountService.GetAccountByUsername(cursor.getString(1)),
                    date,
                    cursor.getString(3),
                    cursor.getFloat(4),
                    null
            );
            orderList.add(order);
            cursor.moveToNext();
        }
        return orderList;
    }
}
