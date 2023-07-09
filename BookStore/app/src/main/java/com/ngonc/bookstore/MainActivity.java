package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.ngonc.adapter.MainBookAdapter;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Books;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listBook  = findViewById(R.id.listBook);
        ImageView cart = findViewById(R.id.btnCart);
        ImageView shopping = findViewById(R.id.btnOrder);
        ImageView account = findViewById(R.id.btnAccount);
        ImageView search = findViewById(R.id.btnSearch);

        DBHelper dbHelper = new DBHelper(getApplicationContext());
        BookService bookService = new BookService(dbHelper);
        List<Books> booksList = bookService.GetAllBook();
        MainBookAdapter mainBookAdapter = new MainBookAdapter(getApplicationContext(), booksList);
        listBook.setAdapter(mainBookAdapter);

        listBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(booksList.get(i));
                String book = new Gson().toJson(booksList.get(i));
                Intent intent = new Intent(MainActivity.this, BookDetails.class);
                intent.putExtra("BOOK", book);
                startActivity(intent);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartList.class);
                startActivity(intent);
            }
        });
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Order.class);
                startActivity(intent);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Account.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtSearch = findViewById(R.id.txtSearch);
                String name = txtSearch.getText().toString();
                List<Books> books = bookService.SearchBook(name);
                MainBookAdapter mainBookAdapter = new MainBookAdapter(getApplicationContext(), books);
                listBook.setAdapter(mainBookAdapter);
            }
        });
    }
}