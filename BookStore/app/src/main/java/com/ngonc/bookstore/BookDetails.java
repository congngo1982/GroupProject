package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.util.List;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        TextView name = findViewById(R.id.bookName);
        TextView author = findViewById(R.id.bookAuthor);
        TextView cate = findViewById(R.id.bookCate);
        TextView ISBN = findViewById(R.id.bookISBN);
        TextView quantity = findViewById(R.id.bookQuantity);
        TextView price = findViewById(R.id.bookPrice);
        Button addToCart = findViewById(R.id.btnAddToCart);

        System.out.println("Cart: " + Utils.GetCartContext(getApplicationContext()));

        if(getIntent() != null){
            String book = getIntent().getStringExtra("BOOK");
            Books books = new Gson().fromJson(book, Books.class);
            name.setText(books.getName());
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            AuthorService authorService = new AuthorService(dbHelper);
            author.setText("Author: " + authorService.getAuthorById(books.getAuthorId()).getName());
            cate.setText("Category: " + books.getCategory());
            ISBN.setText("ISBN: " + books.getISBN());
            quantity.setText("Quantity: " + books.getQuantity());
            price.setText("Price: " + books.getPrice());
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Cart> cartList = Utils.GetCartContext(getApplicationContext());
                    for(int i = 0; i< cartList.size(); i++){
                        if(cartList.get(i).getBooks().getId() == books.getId()){
                            Toast.makeText(getApplicationContext(), "Book is Existed in Cart", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    Cart cart = new Cart(books, 1, books.getPrice());
                    cartList.add(cart);
                    Utils.SaveCartContext(getApplicationContext(), cartList);
                }
            });
        } else {
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "No Book Here !!!", Toast.LENGTH_LONG).show();
                }
            });
        }


    }
}