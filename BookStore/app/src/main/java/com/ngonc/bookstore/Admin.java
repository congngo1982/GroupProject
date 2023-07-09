package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ngonc.adapter.UserAdapter;
import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;

import java.util.List;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ListView listView = findViewById(R.id.userList);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        AccountService accountService = new AccountService(dbHelper);
        List<Account> accounts = accountService.GetAccount();
        UserAdapter userAdapter = new UserAdapter(accounts, getApplicationContext());
        listView.setAdapter(userAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Admin.this, UserInformation.class);
                intent.putExtra("USER", accounts.get(i).getUsername());
                startActivity(intent);
            }
        });

        Button logout = findViewById(R.id.btnAdminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this, Login.class);
                Utils.Logout(getApplicationContext());
                startActivity(intent);
                finish();
            }
        });
    }
}