package com.example.book_library_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {
    EditText title_input,author_input,pages_input;
    Button add_button;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        title_input=findViewById(R.id.title_input);
        author_input=findViewById(R.id.author_input);
        pages_input=findViewById(R.id.pages_input);
        add_button= findViewById(R.id.add_button);
        add_button.setOnClickListener( new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDatabaseHelper= new MyDatabaseHelper(AddActivity.this);
                myDatabaseHelper.addBook(title_input.getText().toString().trim(),
                author_input.getText().toString().trim(),
                Integer.valueOf(pages_input.getText().toString().trim()));

            }
        });


    }
}