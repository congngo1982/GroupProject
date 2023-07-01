package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
        AccountService accountService = new AccountService(dbHelper);
//        accountService.AddAccount(new Account("ngonc", "19082002", "admin", "ngonc@gmail.com", "0000", "Gia Lai"));
//        accountService.AddAccount(new Account("ngo", "19082002", "user", "nguyencongngo@gmail.com", "1111", "Pleiku"));
//        List<Account> accounts = accountService.GetAccount();
//        for (Account acc : accounts) {
//            System.out.println(acc);
//        }
        System.out.println(accountService.GetAccountByUsername("ngo", "19082002"));
    }
}