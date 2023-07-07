package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {
    private DBHelper dbHelper;
    private static final String TABLE = "order";
    private static final String ID = "id";
    private static final String ACCOUNT = "id_accounts";
    private static final String DATE = "date_created";
    private static final String ADDRESS = "address";
    private static final String TOTAL = "total_amount";

    public OrderService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void AddToOrder(Order order){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACCOUNT, order.getAccount().getUsername());
        values.put(DATE, order.getCreatedDate().toString());
        values.put(ADDRESS, order.getAddress());
        values.put(TOTAL, order.getTotalPrice());
        db.insert(TABLE, null, values);
        db.close();
    }

    public List<Order> GetOrder(String username){
        List<Order> orderList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ACCOUNT);
        AccountService accountService = new AccountService(dbHelper);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, new String[]{username});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Order order = new Order(
                    cursor.getInt(0),
                    accountService.GetAccountByUsername(cursor.getString(1)),
                    new Date(),
                    cursor.getString(3),
                    cursor.getFloat(4),
                    null
            );
        }
        return orderList;
    }
}
