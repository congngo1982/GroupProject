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
import com.ngonc.model.Account;

public class UserInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        if (getIntent() != null) {
            String user = getIntent().getStringExtra("USER");
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            AccountService accountService = new AccountService(dbHelper);
            Account account = accountService.GetAccountByUsername(user);
            EditText name = findViewById(R.id.user_name);
            EditText role = findViewById(R.id.user_role);
            EditText mail = findViewById(R.id.user_mail);
            EditText phone = findViewById(R.id.user_phone);
            EditText address = findViewById(R.id.user_address);
            Button update = findViewById(R.id.btnUpdateUser);
            name.setText(account.getUsername());
            role.setText(account.getRole());
            mail.setText(account.getEmail());
            phone.setText(account.getPhone());
            address.setText(account.getAddress());

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    accountService.UpdateAccountRole(account);
                    Intent intent = new Intent(UserInformation.this, UserInformation.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                    finish();
                }
            });
            ImageView back = findViewById(R.id.user_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}