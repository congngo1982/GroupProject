package com.ngonc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngonc.bookstore.R;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.util.List;

public class MainBookAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    List<Books> books;

    public MainBookAdapter(Context context, List<Books> books) {
        this.layoutInflater = LayoutInflater.from(context);
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
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
        view = layoutInflater.inflate(R.layout.book_main, null);
        TextView name = view.findViewById(R.id.txtName);
        TextView category = view.findViewById(R.id.txtCategory);
        TextView price = view.findViewById(R.id.txtPrice);
        Books book = books.get(i);
        name.setText(book.getName());
        category.setText(book.getCategory());
        price.setText(book.getPrice() + "");
        return view;
    }
}
