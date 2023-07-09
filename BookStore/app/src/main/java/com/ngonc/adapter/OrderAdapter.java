package com.ngonc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngonc.bookstore.R;
import com.ngonc.model.Order;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<Order> orderList;

    public OrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderList.size();
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
        view = layoutInflater.inflate(R.layout.order_layout, null);
        Order order = orderList.get(i);
        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
        String createdDate = formatter.format(order.getCreatedDate());
        TextView name = view.findViewById(R.id.order_layout_name);
        TextView date = view.findViewById(R.id.order_layout_date);
        TextView total = view.findViewById(R.id.order_layout_total);
        name.setText("Order " + order.getId());
        date.setText(createdDate);
        total.setText(order.getTotalPrice() + "");
        return view;
    }
}
