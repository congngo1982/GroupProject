package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ngonc.adapter.OrderDetailsAdapter;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.dbhelper.OrderDetailService;
import com.ngonc.dbhelper.OrderService;
import com.ngonc.model.Account;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;
import com.ngonc.model.Order;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    Button Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Order = findViewById(R.id.btnAddToOrder);
        List<Cart> cartList = Utils.GetCartContext(getApplicationContext());
        List<com.ngonc.model.OrderDetails> orderDetails = new ArrayList<>();
        String orderId = getIntent().getStringExtra("ORDERID");
        if (orderId != null) {
            Order.setEnabled(false);
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            OrderDetailService orderDetailService = new OrderDetailService(dbHelper);
            orderDetails = orderDetailService.GetOrderDetail(Integer.parseInt(orderId));
        } else {
            orderDetails = GetOrderDetailFromCart(cartList);
        }
        OrderDetailsAdapter adapter = new OrderDetailsAdapter(orderDetails, getApplicationContext());
        ListView listView = findViewById(R.id.orderDetailList);
        listView.setAdapter(adapter);
        TextView total = findViewById(R.id.txtOrderDetailTotal);
        total.setText("Total: " + GetTotalPrice(orderDetails));

        ImageView back = findViewById(R.id.detailBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                Account account = Utils.GetCurrentAccount(getApplicationContext());
                List<com.ngonc.model.OrderDetails> orderDetails = GetOrderDetailFromCart(cartList);
                if (orderDetails.size() > 0) {
                    com.ngonc.model.Order order = new Order(1,
                            account,
                            date,
                            account.getAddress(),
                            GetTotalPrice(orderDetails),
                            null);
                    System.out.println("Created: " + date);
                    DBHelper dbHelper = new DBHelper(getApplicationContext());
                    OrderService orderService = new OrderService(dbHelper);
                    int id = orderService.AddToOrder(order);
                    System.out.println("Order: " + id);
                    System.out.println(orderService.GetOrder("ngonc"));

                    OrderDetailService orderDetailService = new OrderDetailService(dbHelper);
                    int l = orderDetails.size();
                    for (int i = 0; i < l; i++) {
                        orderDetailService.AddToOrderDetails(orderDetails.get(i), id);
                    }
                    List<com.ngonc.model.OrderDetails> orderDetails1 = orderDetailService.GetOrderDetail(id);
                    System.out.println("Order Detail: " + orderDetails1);

                    ReloadAfterBuy();
                } else{
                    Toast.makeText(getApplicationContext(), "Cart is Empty !!!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public List<com.ngonc.model.OrderDetails> GetOrderDetailFromCart(List<Cart> cartList) {
        List<com.ngonc.model.OrderDetails> orderDetails = new ArrayList<>();
        if (cartList != null) {
            int l = cartList.size();
            for (int i = 0; i < l; i++) {
                Cart cart = cartList.get(i);
                Books books = cart.getBooks();
                int amount = cart.getAmount();
                double price = cart.getTotalPrice();
                com.ngonc.model.OrderDetails orderDetails1 = new com.ngonc.model.OrderDetails(1,
                        books,
                        amount,
                        price, null);
                orderDetails.add(orderDetails1);
            }
        }
        return orderDetails;
    }

    public double GetTotalPrice(List<com.ngonc.model.OrderDetails> orderDetails) {
        double total = 0;
        if (orderDetails != null) {
            int l = orderDetails.size();
            for (int i = 0; i < l; i++) {
                total += orderDetails.get(i).getPrice();
            }
        }
        return total;
    }

    public void ReloadAfterBuy() {
        Utils.SaveCartContext(getApplicationContext(), new ArrayList<>());
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);//Start the same Activity
        finish();
    }

}