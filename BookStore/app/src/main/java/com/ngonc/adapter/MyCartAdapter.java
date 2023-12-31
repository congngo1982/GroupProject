package com.ngonc.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ngonc.bookstore.CartList;
import com.ngonc.bookstore.R;
import com.ngonc.bookstore.Utils;
import com.ngonc.model.Books;
import com.ngonc.model.Cart;

import java.util.List;

public class MyCartAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<Cart> cartList;

    CartList cartListActivity;
    public MyCartAdapter(Context context, List<Cart> cartList, CartList cartActivity) {
        this.layoutInflater = LayoutInflater.from(context);
        this.cartList = cartList;
        this.context = context;
        this.cartListActivity = cartActivity;
    }

    @Override
    public int getCount() {
        return cartList.size();
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

        view = layoutInflater.inflate(R.layout.cart_layout, null);
        TextView content = view.findViewById(R.id.cartContent);
        TextView price = view.findViewById(R.id.cartPrice);
        TextView quantity = view.findViewById(R.id.cartQuantity);
        Button increase = view.findViewById(R.id.btnUp);
        Button decrease = view.findViewById(R.id.btnDown);
        ImageView deleteCart = view.findViewById(R.id.btnDeleteCart);
        Cart cart = cartList.get(i);
        content.setText(cart.getBooks().getName());
        price.setText(cart.getBooks().getPrice() + "");
        quantity.setText(cart.getAmount() + "");
//        cartListActivity.SetTotal("Total: " + GetTotalPrice());
        Books books = cart.getBooks();

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getAmount() < books.getQuantity()) {
                    cart.setAmount(cart.getAmount() + 1);
                    quantity.setText(cart.getAmount() + "");
                    cartListActivity.SetTotal("Total: " + GetTotalPrice());
//                    price.setText(cart.getTotalPrice() + "");
                    Utils.SaveCartContext(context, cartList);
                } else {
                    Toast.makeText(context, "Quantity in Stock is not enough !!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cart.getAmount() > 1) {
                    cart.setAmount(cart.getAmount() - 1);
                    quantity.setText(cart.getAmount() + "");
                    cartListActivity.SetTotal("Total: " + GetTotalPrice());
//                    price.setText(cart.getTotalPrice() + "");
                    Utils.SaveCartContext(context, cartList);
                } else {
                    Toast.makeText(context, "Quantity must higher than 0", Toast.LENGTH_SHORT).show();
                }

            }
        });
        deleteCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartList.remove(i);
//                SaveCart();
                Utils.SaveCartContext(context, cartList);
                notifyDataSetChanged();
                cartListActivity.SetTotal("Total: " + GetTotalPrice());
            }
        });
        return view;
    }

    public void SaveCart() {
        SharedPreferences pref = context.getSharedPreferences("MYAPP", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String carts = new Gson().toJson(cartList);
        editor.putString("CARTLIST", carts);
        editor.commit();
    }

    public double GetTotalPrice(){
        double total = 0.0;
        if(cartList != null){
            int l = cartList.size();
            for(int i = 0; i< l; i++){
                total += cartList.get(i).getTotalPrice();
            }
        }
        return total;
    }
}
