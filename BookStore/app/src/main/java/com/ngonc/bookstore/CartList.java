package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ngonc.adapter.MyCartAdapter;
import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CartList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        BookService bookService = new BookService(dbHelper);
        AuthorService authorService = new AuthorService(dbHelper);
        List<Cart> cartList = Utils.GetCartContext(getApplicationContext());
        MyCartAdapter myCartAdapter = new MyCartAdapter(getApplicationContext(), cartList, this);
        ListView listView = findViewById(R.id.cartList);
        listView.setAdapter(myCartAdapter);
        SetTotal("Total: " + GetTotalPrice(cartList));

        ImageView back = findViewById(R.id.cartBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button addToOrder = findViewById(R.id.addToOrder);
        addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartList.this, OrderDetails.class);
                startActivity(intent);
            }
        });

    }

    public void SetTotal(String total){
        TextView txtTotal = findViewById(R.id.txtTotal);
        txtTotal.setText(total);
    }


    public double GetTotalPrice(List<Cart> cartList){
        double total = 0.0;
        if(cartList != null){
            int l = cartList.size();
            for(int i = 0; i< l; i++){
                total += cartList.get(i).getTotalPrice();
            }
        }
        return total;
    }
}