package com.ngonc.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.BookService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Author;
import com.ngonc.model.Books;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements BookActivityListener{


    ListView lvBook;
    List<Books> books;
    BookAdapter bookAdapter;
    Button btnAddBook;
    DBHelper dbHelper;
    BookService bookService;
    EditText edNameBook, edPrice, edQuantity, edISBN;
    Spinner spAuthor, spCategory, spStatus;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //set book Adapter
        dbHelper = new DBHelper(getApplicationContext());
        bookService = new BookService(dbHelper);
        lvBook = (ListView)findViewById(R.id.lvBook);
        books = listBookByStatus(bookService.GetAllBook());
        bookAdapter = new BookAdapter(this, R.layout.books, books);
        lvBook.setAdapter(bookAdapter);

        //set toolbar
        toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        //set interface to delete and edit button by icon
        bookAdapter.setBookActivityListener(this);

    }

    private List<Books> listBookByStatus(List<Books> books){
        List<Books> rs = new ArrayList<>();
        if(books != null){
            for (Books book: books) {
                if(book.isStatus() == true){
                    rs.add(book);
                }
            }
        }
        return rs;
    }

    private void updateDatabase(){
        books.clear();
        books.addAll(listBookByStatus(bookService.GetAllBook()));
        bookAdapter.notifyDataSetChanged();
    }

    private void createBook(){
        //dialog add book
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.add_book, null);
        builder.setView(dialogView);
        builder.setTitle("Add Book");
        //input new book
        edNameBook = (EditText)dialogView.findViewById(R.id.edNameBook);
        edISBN = (EditText) dialogView.findViewById(R.id.edISBN);
        edPrice = (EditText)dialogView.findViewById(R.id.edPrice);
        edQuantity = (EditText)dialogView.findViewById(R.id.edQuantity);

        //set adapter author
        spAuthor = (Spinner)dialogView.findViewById(R.id.spinner_Author);
        setAdapterAuthor(spAuthor);

        //set adapter Category
        spCategory = (Spinner)dialogView.findViewById(R.id.spinner_Category);
        setAdapterCategory(spCategory);

        //show dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //add Book
        btnAddBook = (Button)dialogView.findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = edNameBook.getText().toString();
                String isbn = edISBN.getText().toString();
                String priceStr = edPrice.getText().toString();
                String quantityStr = edQuantity.getText().toString();

                if (bookName.isEmpty() || isbn.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if(bookName.equals("")|| bookName == null){
                    Toast.makeText(getApplicationContext(), "Please input Name Book!", Toast.LENGTH_SHORT).show();
                }else if(isbn.equals("")|| isbn == null){
                    Toast.makeText(getApplicationContext(), "Please input ISBN Book!", Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(priceStr) <= 0){
                    Toast.makeText(getApplicationContext(), "Price must be > 0!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(quantityStr) <= 0){
                    Toast.makeText(getApplicationContext(), "Quantity must be > 0!", Toast.LENGTH_SHORT).show();
                }else {
                    if (checkBookExistByName(books) == false && checkISBNExist(books) == false) {
                        // Selected author id
                        int selectedAuthorPosition = spAuthor.getSelectedItemPosition();
                        Author selectedAuthor = setAdapterAuthor(spAuthor).get(selectedAuthorPosition);

                        Books book = new Books();
                        book.setName(bookName);
                        book.setAuthorId(selectedAuthor.getId());
                        book.setCategory(spCategory.getSelectedItem().toString());
                        book.setISBN(isbn);
                        book.setPrice(Double.parseDouble(priceStr));
                        book.setQuantity(Integer.parseInt(quantityStr));
                        book.setStatus(true);
                        boolean isBookAdded = bookService.AddBook(book);
                        if (isBookAdded) {
                            Toast.makeText(getApplicationContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();

                            // Update books
                            updateDatabase();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to add book!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Name Book or ISBN already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void editBook(int id) {
        //find book by id
        Books book = bookService.getBookById(id);
        //dialog edit book
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.edit_book, null);
        builder.setView(dialogView);
        builder.setTitle("Update Book");
        //show this book
        edNameBook = (EditText)dialogView.findViewById(R.id.edNewNameBook);
        edISBN = (EditText) dialogView.findViewById(R.id.edNewISBN);
        edPrice = (EditText)dialogView.findViewById(R.id.edNewPrice);
        edQuantity = (EditText)dialogView.findViewById(R.id.edNewQuantity);

        edNameBook.setText(book.getName());
        edISBN.setText(book.getISBN());
        edPrice.setText(String.valueOf(book.getPrice()));
        edQuantity.setText(String.valueOf(book.getQuantity()));

        //set adapter author
        spAuthor = (Spinner)dialogView.findViewById(R.id.spinner_Author);
        List<Author> authors =  setAdapterAuthor(spAuthor);
        int selectedAuthorPosition = getSelectedAuthorPosition(authors, book.getAuthorId());
        spAuthor.setSelection(selectedAuthorPosition);

        //set adapter Category
        spCategory = (Spinner)dialogView.findViewById(R.id.spinner_Category);
        setAdapterCategory(spCategory);
        int selectedCategoryPosition = getCategoryPosition(book.getCategory());
        spCategory.setSelection(selectedCategoryPosition);

        //set adapter status
        spStatus = (Spinner)dialogView.findViewById(R.id.spinner_StatusBook);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_spinner_item);
        statusAdapter.add("True");
        statusAdapter.add("False");
        spStatus.setAdapter(statusAdapter);
        int selectedStatusPosition = book.isStatus() ? 0 : 1;
        spStatus.setSelection(selectedStatusPosition);

        //show dialog
        AlertDialog dialog = builder.create();
        dialog.show();


        Button btnEditBook = (Button) dialogView.findViewById(R.id.btnEditBook);
        btnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get new info book from dialog
                String newName = edNameBook.getText().toString();
                String newISBN = edISBN.getText().toString();
                String newPrice = edPrice.getText().toString();
                String newQuantity = edQuantity.getText().toString();
                int newAuthorId = authors.get(spAuthor.getSelectedItemPosition()).getId();
                String newCategory = spCategory.getSelectedItem().toString();
                boolean newStatus = spStatus.getSelectedItemPosition() == 0;


                if (newPrice.isEmpty() || newQuantity.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(newPrice) <= 0){
                    Toast.makeText(getApplicationContext(), "Price must be > 0!", Toast.LENGTH_SHORT).show();
                }else if(Integer.parseInt(newQuantity) <= 0){
                    Toast.makeText(getApplicationContext(), "Quantity must be > 0!", Toast.LENGTH_SHORT).show();
                }
                // update book
                book.setName(newName);
                book.setISBN(newISBN);
                book.setPrice(Double.parseDouble(newPrice));
                book.setQuantity(Integer.parseInt(newQuantity));
                book.setAuthorId(newAuthorId);
                book.setCategory(newCategory);
                book.setStatus(newStatus);

                boolean isBookUpdated = bookService.UpdateBook(book);
                if (isBookUpdated) {
                    Toast.makeText(getApplicationContext(), "Book updated successfully!", Toast.LENGTH_SHORT).show();

                    //update books
                    updateDatabase();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update book!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
    @Override
    public void deleteBook(int id, String name) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure delete this " + name + "?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDelete = bookService.deleteBook(id);
                if(isDelete){
                    Toast.makeText(BookActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();
                    updateDatabase();
                }else {
                    Toast.makeText(BookActivity.this, "Delete unsuccessfully!", Toast.LENGTH_SHORT).show();

                }
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }


    private boolean checkBookExistByName(List<Books> books){
        for (Books book: books) {
            if (book.getName().equals(edNameBook.getText().toString())){
                return true;
            }
        }
        return false;
    }
    private boolean checkISBNExist(List<Books> books){
        for (Books book: books) {
            if (book.getISBN().equals(edISBN.getText().toString())){
                return true;
            }
        }
        return false;
    }

    private void setAdapterCategory(Spinner spinner) {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_spinner_item);
        categoryAdapter.add("Textbook");
        categoryAdapter.add("Novel");
        categoryAdapter.add("Comic");
        categoryAdapter.add("Poem");
        categoryAdapter.add("Thriller book");
        spinner.setAdapter(categoryAdapter);
    }
    private List<Author> setAdapterAuthor(Spinner spinner) {
        AuthorService authorService = new AuthorService(dbHelper);
        List<Author> authors = authorService.GetAllAuthor();
        List<String> authorNames = new ArrayList<>();
        for (Author author : authors) {
            authorNames.add(author.getName());
        }
        ArrayAdapter<String> authorAdapter = new ArrayAdapter<>(BookActivity.this, android.R.layout.simple_spinner_item, authorNames);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(authorAdapter);
        return authors;
    }
    private int getSelectedAuthorPosition(List<Author> authors, int authorId) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId() == authorId) {
                return i;
            }
        }
        return 0;
    }

    private int getCategoryPosition(String category) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spCategory.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(category)) {
                return i;
            }
        }
        return 0;
    }


    private List<Books> originalBooks;
    //set toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_book, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_search_book);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search book.....");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Books> bookList = books;
                if (originalBooks == null) {
                    originalBooks = new ArrayList<>(books);
                }
                if (newText.isEmpty()) {
                    updateDatabase();
                } else {
                    List<Books> filteredBooks = search(bookList, newText);
                    books.clear();
                    books.addAll(filteredBooks);
                    bookAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_add_author){
            Intent intent = new Intent(BookActivity.this, AuthorActivity.class);
            startActivity(intent);
            return true;
        }
        if(id == R.id.menu_add_book){
            createBook();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public List<Books> search (List<Books> bookList, String search){
        List<Books> newBooks = new ArrayList<>();
        AuthorService authorService = new AuthorService(dbHelper);
        for (Books book: bookList) {
            int authorId = book.getAuthorId();
            Author author = authorService.getAuthorById(authorId);
            if(book.getName().toLowerCase().contains(search.toLowerCase())
                    || author != null
                    && author.getName().toLowerCase().contains(search.toLowerCase())
                    && book.isStatus() == true){
                newBooks.add(book);
            }
        }
        return newBooks;
    }

}
