package com.ngonc.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Books;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Books> books;
    private BookActivityListener listener;
    public void setBookActivityListener(BookActivityListener listener) {
        this.listener = listener;
    }


    public BookAdapter(Context context, int layout, List<Books> books) {
        this.context = context;
        this.layout = layout;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvName, tvAuthor, tvPrice, tvCategory;
        Spinner spCategory;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvNameBook);
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.tvAuthor);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            viewHolder.tvCategory = (TextView) convertView.findViewById(R.id.tvCategory);
            viewHolder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
            viewHolder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //get book by position into books
        Books book = books.get(position);
        DBHelper dpHelper = new DBHelper(context.getApplicationContext());
        AuthorService authorService = new AuthorService(dpHelper);

        viewHolder.tvName.setText(book.getName().toString());
        viewHolder.tvAuthor.setText(authorService.getAuthorById(book.getAuthorId()).getName().toString());
        viewHolder.tvPrice.setText(String.format("%.2f", book.getPrice()));
        viewHolder.tvCategory.setText(book.getCategory().toString());

        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.editBook(book.getId());
                }
            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.deleteBook(book.getId(), book.getName());
                }
            }
        });

        return convertView;
    }
}