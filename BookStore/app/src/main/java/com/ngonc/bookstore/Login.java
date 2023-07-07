package com.ngonc.bookstore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;
import com.ngonc.model.Cart;

import java.util.List;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView register = (TextView) findViewById(R.id.txtRegister);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        AccountService accountService = new AccountService(dbHelper);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login(accountService, txtUsername.getText().toString(), txtPassword.getText().toString());
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String username = "";
        if (requestCode == 1) {
            username = getIntent().getStringExtra("Info");
        }
        EditText txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtUsername.setText(username);
    }

    public void Login(AccountService accountService, String username, String password) {
        Account account = accountService.Login(username, password);
        if (account != null) {
            Intent intent = new Intent(Login.this, MainActivity.class);

            DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            AuthorService authorService = new AuthorService(dbHelper);
            BookService bookService = new BookService(dbHelper);
            Utils.SaveCartContext(getApplicationContext(), Utils.GetCart(bookService, authorService));

            startActivity(intent);
        } else {
            TextView error = (TextView) findViewById(R.id.loginError);
            error.setText("Username or Password is Incorrect");
        }
    }
}