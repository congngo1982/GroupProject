package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ngonc.adapter.OrderAdapter;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.dbhelper.OrderService;
import com.ngonc.model.Account;

import java.text.SimpleDateFormat;
import java.util.List;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        OrderService orderService = new OrderService(dbHelper);
        Account account = Utils.GetCurrentAccount(getApplicationContext());
        List<com.ngonc.model.Order> orderList = orderService.GetOrder(account.getUsername());
        OrderAdapter orderAdapter = new OrderAdapter(orderList, getApplicationContext());
        ListView listView = findViewById(R.id.orderList);
        listView.setAdapter(orderAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = orderList.get(i).getId();
                Intent intent = new Intent(Order.this, OrderDetails.class);
                intent.putExtra("ORDERID", id + "");
                startActivity(intent);
            }
        });

        ImageView back = findViewById(R.id.orderBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}