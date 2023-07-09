package com.ngonc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ngonc.bookstore.R;
import com.ngonc.model.OrderDetails;

import java.util.List;

public class OrderDetailsAdapter extends BaseAdapter {
    List<OrderDetails> orderDetails;
    LayoutInflater layoutInflater;

    public OrderDetailsAdapter(List<OrderDetails> orderDetails, Context context) {
        this.orderDetails = orderDetails;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderDetails.size();
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
        view = layoutInflater.inflate(R.layout.orderdetail_list_layout, null);
        TextView orderName = view.findViewById(R.id.orderName);
        TextView orderCate = view.findViewById(R.id.orderCategory);
        TextView orderISBN = view.findViewById(R.id.orderISBN);
        TextView orderPrice = view.findViewById(R.id.orderTotal);
        TextView orderQuantity = view.findViewById(R.id.orderQuantity);
        OrderDetails orderDetail = orderDetails.get(i);
        orderName.setText(orderDetail.getBooks().getName());
        orderCate.setText("Category: " + orderDetail.getBooks().getCategory());
        orderISBN.setText("ISBN: " + orderDetail.getBooks().getISBN());
        orderPrice.setText("Price: " + orderDetail.getPrice());
        orderQuantity.setText("Quantity: " + orderDetail.getAmount() + "");
        return view;
    }
}
