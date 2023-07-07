package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;
import com.ngonc.model.Author;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button cart = findViewById(R.id.viewCart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartList.class);
                startActivity(intent);
            }
        });
    }
}