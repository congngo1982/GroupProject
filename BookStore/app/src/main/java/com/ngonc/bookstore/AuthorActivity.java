package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ngonc.dbhelper.AuthorService;
import com.ngonc.dbhelper.DBHelper;
import com.ngonc.model.Author;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {



    EditText edNameAuthor, edDescription, edBirthday;
    Button btnAdd, btnDelete, btnUpdate;
    Spinner spinnerGenderAuthor;
    DBHelper dbHelper;
    AuthorService authorService;
    com.ngonc.model.Author author;
    ListView lvAuthor;
    ArrayAdapter<Author> adapter;
    List<Author> authorList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        edNameAuthor = (EditText) findViewById(R.id.edNameAuthor);
        edDescription = (EditText) findViewById(R.id.edDescription);
        edBirthday = (EditText) findViewById(R.id.edBirthDay);

        //set adapter gender
        spinnerGenderAuthor = (Spinner)findViewById(R.id.spinner_GenderAuthor);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        genderAdapter.add("Nam");
        genderAdapter.add("Nữ");
        spinnerGenderAuthor.setAdapter(genderAdapter);

        //set adapter listview
        lvAuthor = (ListView)findViewById(R.id.lv);
        dbHelper = new DBHelper(this.getApplicationContext());
        authorService = new AuthorService(dbHelper);
        authorList = authorService.getAllAuthor();
        adapter = new ArrayAdapter<>(AuthorActivity.this, android.R.layout.simple_list_item_1, authorList);
        lvAuthor.setAdapter(adapter);

        //binding listview
        lvAuthor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Author selectedAuthor = adapter.getItem(position);

                //view on Edittext
                edNameAuthor.setText(selectedAuthor.getName());
                edDescription.setText(selectedAuthor.getDescription());
                edBirthday.setText(selectedAuthor.getBirthday());
                spinnerGenderAuthor.setSelection(genderAdapter.getPosition(selectedAuthor.getGender()));
            }
        });

        //add author
        btnAdd = (Button) findViewById(R.id.btnAddAuthor);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAuthor();
            }
        });
        //delete author
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edNameAuthor.getText().toString();
                deleteAuthor(name);
            }
        });
        //update author
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAuthor();
            }
        });
    }
    private void updateDatabase(){
        //load new author list
        List<Author> authorList = authorService.getAllAuthor();
        adapter.clear();
        adapter.addAll(authorList);
        adapter.notifyDataSetChanged();
    }
    private void createAuthor(){
        String authorName = edNameAuthor.getText().toString();
        String birthDay = edBirthday.getText().toString();
        if(authorName.isEmpty() || authorName == ""){
            Toast.makeText(AuthorActivity.this, "Must be fill in Author name!", Toast.LENGTH_SHORT).show();
        }else {
            if(checkAuthorExistByName(authorList, authorName) == false){
                author = new Author();
                author.setName(authorName);
                author.setGender(spinnerGenderAuthor.getSelectedItem().toString());
                author.setDescription(edDescription.getText().toString());
                if(birthDay != null && !birthDay.isEmpty()){
                    if(validateBirthday(birthDay) == true){
                        author.setBirthday(birthDay);
                    }else {
                        Toast.makeText(AuthorActivity.this, "Birthday must be before 2006!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else if (birthDay == null|| birthDay.isEmpty()) {
                    author.setBirthday(birthDay);
                }
                //dialog confirm add
                AlertDialog.Builder builder = new AlertDialog.Builder(AuthorActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to add the author " + authorName + "?");
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        authorService.AddAuthor(author);
                        Toast.makeText(getApplicationContext(), "Add success Author", Toast.LENGTH_SHORT).show();

                        updateDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();


            }else {
                Toast.makeText(getApplicationContext(), "Name Author have Exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteAuthor(String name){
        AlertDialog.Builder builder = new AlertDialog.Builder(AuthorActivity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to Delete the author " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(checkAuthorExistByName(authorList, name) == true){
                    boolean rs = authorService.deleteAuthorByName(name);
                    if(rs){
                        edBirthday.setText("");
                        edDescription.setText("");
                        edNameAuthor.setText("");
                        spinnerGenderAuthor.setSelection(0);
                        Toast.makeText(AuthorActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
                        updateDatabase();
                    }else {
                        Toast.makeText(AuthorActivity.this, "Delete unsuccessfully!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Name Author haven't Exist or equal null!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    private void updateAuthor(){
        String name = edNameAuthor.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(AuthorActivity.this, "Name author must be filled!", Toast.LENGTH_SHORT).show();
        } else {
            if(checkAuthorExistByName(authorList, name)){
                AlertDialog.Builder builder = new AlertDialog.Builder(AuthorActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to update the author " + name + "?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newBirthDay = edBirthday.getText().toString();
                        String newGender = spinnerGenderAuthor.getSelectedItem().toString();
                        String newDescription = edDescription.getText().toString();

                        Author updatedAuthor = new Author();
                        updatedAuthor.setName(name);
                        updatedAuthor.setGender(newGender);
                        updatedAuthor.setDescription(newDescription);

                        if(newBirthDay != null && !newBirthDay.isEmpty()){
                            if(validateBirthday(newBirthDay)){
                                updatedAuthor.setBirthday(newBirthDay);
                            } else {
                                Toast.makeText(AuthorActivity.this, "Birthday must be before 2006!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            updatedAuthor.setBirthday(newBirthDay);
                        }

                        boolean result = authorService.UpdateAuthor(updatedAuthor);
                        if(result){
                            Toast.makeText(AuthorActivity.this, "Update author successfully!", Toast.LENGTH_SHORT).show();
                            updateDatabase();
                        } else {
                            Toast.makeText(AuthorActivity.this, "Update author failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            } else {
                Toast.makeText(AuthorActivity.this, "Author name doesn't exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateBirthday(String birthday) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(birthday);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2006, Calendar.JANUARY, 1); // Set 1st January 2006 as the minimum date
            Date minDate = calendar.getTime();
            return date.before(minDate);
        } catch (ParseException e) {
            return false;
        }
    }


    private boolean checkAuthorExistByName(List<Author> authors, String name){
        for (Author author: authors) {
            if (author.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}