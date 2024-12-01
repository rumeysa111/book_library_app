package com.example.book_library_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private EditText usernameEditText,passwordEditText;
    private Button loginButton;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        usernameEditText=findViewById(R.id.username_txt);
        passwordEditText=findViewById(R.id.password_txt2);
        loginButton=findViewById(R.id.button_login);
        TextView registerText= findViewById(R.id.register_text);

        dbHelper= new MyDatabaseHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this,"Please enter both username and password",Toast.LENGTH_SHORT).show();

                }else{
                    boolean isValidUser=dbHelper.checkUser(username,password);

                    if(isValidUser){

                        String userId= dbHelper.getUserId(username,password);

                        SharedPreferences sharedPreferences= getSharedPreferences("userPrefs",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.putString("userId",userId);
                        editor.putBoolean("isLoggedIn",true);
                        editor.apply();
                        Toast.makeText(Login.this,"Login successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,MainActivity.class));
                        finish();

                    }else{
                        Toast.makeText(Login.this,"Invalid username or password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}