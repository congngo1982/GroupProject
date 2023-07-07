package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        MyCartAdapter myCartAdapter = new MyCartAdapter(getApplicationContext(), cartList);
        ListView listView = findViewById(R.id.cartList);
        listView.setAdapter(myCartAdapter);

    }

}