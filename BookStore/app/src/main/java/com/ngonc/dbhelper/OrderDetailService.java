package com.ngonc.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ngonc.model.Order;
import com.ngonc.model.OrderDetails;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailService {
    private DBHelper dbHelper;
    private static final String TABLE = "[orderdetail]";
    private static final String ID = "id";
    private static final String BOOK = "id_book";
    private static final String ORDER = "id_order";
    private static final String AMOUNT = "amount";
    private static final String PRICE = "price";

    public OrderDetailService(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void AddToOrderDetails(OrderDetails orderDetail, int bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK, orderDetail.getBooks().getId());
//            values.put(ORDER, orderDetail.getOrder().getId());
        values.put(ORDER, bookId);
        values.put(AMOUNT, orderDetail.getAmount());
        values.put(PRICE, orderDetail.getPrice());
        db.insert(TABLE, null, values);
        db.close();
    }

    public List<OrderDetails> GetOrderDetail(int orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        BookService bookService = new BookService(dbHelper);
        String query = String.format("SELECT * FROM %s WHERE %s = ?", TABLE, ORDER);
        Cursor cursor = db.rawQuery(query, new String[]{orderId + ""});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            OrderDetails orderDetail = new OrderDetails(
                    cursor.getInt(0),
                    bookService.getBookById(cursor.getInt(1)),
                    cursor.getInt(3),
                    cursor.getDouble(4),
                    null
            );
            orderDetails.add(orderDetail);
            cursor.moveToNext();
        }
        return orderDetails;
    }
}
