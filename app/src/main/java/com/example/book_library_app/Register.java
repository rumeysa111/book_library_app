package com.example.book_library_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    private EditText usernameTxt, passwordTxt, emailTxt, firstNameTxt, lastNameTxt;
    private Button registerButton;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        dbHelper= new MyDatabaseHelper(this);
        //ui elemanlarını bağlama

        usernameTxt=findViewById(R.id.username_txt);
        passwordTxt=findViewById(R.id.password_txt);
        emailTxt=findViewById(R.id.email_txt);
        firstNameTxt=findViewById(R.id.isim_txt);
        lastNameTxt=findViewById(R.id.soyisim_txt);
        registerButton=findViewById(R.id.button_register);
        TextView girisText= findViewById(R.id.giris_text);

        registerButton.setOnClickListener(v ->{
            String username=usernameTxt.getText().toString();
            String password=passwordTxt.getText().toString();
            String email=emailTxt.getText().toString();
            String firstName=firstNameTxt.getText().toString();
            String lastName= lastNameTxt.getText().toString();

            dbHelper.addUser(username,password,email,firstName,lastName);
        });
        girisText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}