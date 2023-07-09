package com.ngonc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngonc.bookstore.R;
import com.ngonc.model.Account;

import java.util.List;

public class UserAdapter extends BaseAdapter {
    List<Account> accountList;
    LayoutInflater layoutInflater;

    public UserAdapter(List<Account> accountList, Context context) {
        this.accountList = accountList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.user_layout, null);
        TextView name = view.findViewById(R.id.username);
        TextView role = view.findViewById(R.id.userRole);
        TextView address = view.findViewById(R.id.userAddress);
        Account account = accountList.get(i);
        name.setText(account.getUsername());
        role.setText(account.getRole());
        address.setText(account.getAddress());
        return view;
    }
}
