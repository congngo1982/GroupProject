package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button createSeller = (Button) findViewById(R.id.btnCreateSeller);
        Button createBuyer = (Button) findViewById(R.id.btnCreateBuyer);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        AccountService accountService = new AccountService(dbHelper);
        createSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAccount(accountService, "SELLER");
            }
        });
        createBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAccount(accountService, "BUYER");
            }
        });
    }

    public void AddAccount(AccountService accountService, String role) {
        EditText txtUsername = (EditText) findViewById(R.id.regisUsername);
        EditText txtPassword = (EditText) findViewById(R.id.regisPassword);
        EditText txtEmail = (EditText) findViewById(R.id.regisEmail);
        EditText txtPhone = (EditText) findViewById(R.id.regisPhone);
        EditText txtAddress = (EditText) findViewById(R.id.regisAddress);
        Account acc = accountService.GetAccountByUsername(txtUsername.getText().toString());
        if(acc != null){
            TextView error = findViewById(R.id.errorRegister);
            error.setText("Username is Existed !!!");
            return;
        }
            Account account = new Account(txtUsername.getText().toString(),
                    txtPassword.getText().toString(),
                    role,
                    txtEmail.getText().toString(),
                    txtPhone.getText().toString(),
                    txtAddress.getText().toString());
            accountService.AddAccount(account);
            System.out.println(accountService.GetAccount());
            Intent intent = new Intent();
            intent.putExtra("Info", account.getUsername());
            setResult(1, intent);
            finish();
    }
}