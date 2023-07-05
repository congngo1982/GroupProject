package com.ngonc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.List;

public class AuthorActivity extends AppCompatActivity {



    EditText edNameAuthor, edDescription, edBirthday;
    Button btnAdd;
    Spinner spinnerGenderAuthor;
    DBHelper dbHelper;
    AuthorService authorService;
    com.ngonc.model.Author author;
    ListView lvAuthor;
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
        List<Author> authorList = authorService.GetAllAuthor();
        ArrayAdapter<Author> adapter = new ArrayAdapter<>(AuthorActivity.this, android.R.layout.simple_list_item_1, authorList);
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
                if(checkAuthorExistByName(authorList) == false){
                    author = new Author();
                    author.setName(edNameAuthor.getText().toString());
                    author.setGender(spinnerGenderAuthor.getSelectedItem().toString());
                    author.setDescription(edDescription.getText().toString());
                    author.setBirthday(edBirthday.getText().toString());
                    authorService.AddAuthor(author);
                    Toast.makeText(getApplicationContext(), "Add success Author", Toast.LENGTH_SHORT).show();

                    //load new author list
                    List<Author> authorList = authorService.GetAllAuthor();
                    adapter.clear();
                    adapter.addAll(authorList);
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(getApplicationContext(), "Name Author have Exist!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    
    private boolean checkAuthorExistByName(List<Author> authors){
        for (Author author: authors) {
            if (author.getName().equals(edNameAuthor.getText().toString())){
                return true;
            }
        }
        return false;
    }
}