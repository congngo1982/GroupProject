package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.DBHelper;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        EditText name = findViewById(R.id.accName);
        EditText pass = findViewById(R.id.accPass);
        EditText mail = findViewById(R.id.accMail);
        EditText phone = findViewById(R.id.accPhone);
        EditText address = findViewById(R.id.accAddress);
        Button update = findViewById(R.id.btnUpdateAccount);
        Button logout = findViewById(R.id.btnLogout);
        ImageView back = findViewById(R.id.accBack);
        com.ngonc.model.Account account = Utils.GetCurrentAccount(getApplicationContext());
        name.setText(account.getUsername());
        pass.setText(account.getPassword());
        mail.setText(account.getEmail());
        phone.setText(account.getPhone());
        address.setText(account.getAddress());

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                com.ngonc.model.Account account1 = new com.ngonc.model.Account(
                        name.getText().toString(),
                        pass.getText().toString(),
                        account.getRole(),
                        mail.getText().toString(),
                        phone.getText().toString(),
                        address.getText().toString());
                DBHelper dbHelper = new DBHelper(getApplicationContext());
                AccountService accountService = new AccountService(dbHelper);
                accountService.UpdateAccount(account1, account.getUsername());
                Utils.SaveCurrentUser(getApplicationContext(), account1);
                Intent intent = new Intent(Account.this, Account.class);
                startActivity(intent);
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Account.this, Login.class);
                Utils.Logout(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}