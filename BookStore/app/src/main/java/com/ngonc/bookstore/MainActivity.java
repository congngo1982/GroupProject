package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ngonc.dbhelper.AccountService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Account;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private static final String DATABASE_NAME = "accountManagement";
//    private static final int DATABASE_VERSION = 1;
//    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

//        DBHelper dbHelper = new DBHelper(this.getApplicationContext());
//        AccountService accountService = new AccountService(dbHelper);
//        accountService.AddAccount(new Account("ngonc", "19082002", "admin", "ngonc@gmail.com", "0000", "Gia Lai"));
//        accountService.AddAccount(new Account("ngo", "19082002", "user", "nguyencongngo@gmail.com", "1111", "Pleiku"));
//        List<Account> accounts = accountService.GetAccount();
//        for (Account acc : accounts) {
//            System.out.println(acc);
//        }
//        System.out.println(accountService.GetAccountByUsername("ngo", "19082002"));

//        dbHelper = new DBHelper(this);
//
//        // Gọi phương thức xóa cơ sở dữ liệu và tạo lại
//        deleteAndRecreateDatabase();


    }
//    private void deleteAndRecreateDatabase() {
//        // Đóng tất cả các kết nối cơ sở dữ liệu hiện tại nếu có
//        dbHelper.close();
//
//        // Xóa cơ sở dữ liệu bằng cách xóa tệp cơ sở dữ liệu
//        Context context = getApplicationContext();
//        context.deleteDatabase(DATABASE_NAME);
//
//        // Tạo lại cơ sở dữ liệu bằng cách khởi tạo DBHelper mới
//        dbHelper = new DBHelper(context);
//
//        // Sau khi tạo lại cơ sở dữ liệu, bạn có thể sử dụng nó bình thường
//    }
}