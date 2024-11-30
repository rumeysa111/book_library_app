package com.example.book_library_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    // Bu EditText'ler kullanıcıdan kitap bilgilerini almak için kullanılacak
    EditText title_input, author_input, pages_input;

    // Güncelleme ve silme işlemleri için kullanılan butonlar
    Button update_button, delete_button;

    // Kitap bilgilerini tutacak değişkenler
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Toolbar'ı buluyor ve uygulamaya ekliyoruz
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // UI elemanlarını buluyoruz
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        // getAndSetIntentData methodunu çağırarak Intent'ten veri alıyoruz
        getAndSetIntentData();

        // ActionBar başlığını ayarlıyoruz
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(title); // Kitap başlığını ActionBar'da gösteriyoruz
        }

        // Güncelleme butonuna tıklanma olayını ekliyoruz
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Güncelleme işlemini başlatıyoruz
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);

                // EditText'lerden kullanıcıdan alınan verileri alıyoruz
                title = title_input.getText().toString().trim();
                author = author_input.getText().toString().trim();
                pages = pages_input.getText().toString().trim();

                // Veritabanını güncelliyoruz
                myDB.updateData(id, title, author, pages);
            }
        });

        // Silme butonuna tıklanma olayını ekliyoruz
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Silme işlemi için bir onay diyaloğu gösteriyoruz
                confirmDialog();
            }
        });
    }

    // Intent'ten veriyi alıp, EditText'lerde gösteren method
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
            // Intent'ten veriyi alıyoruz
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            // EditText'lerde bu verileri gösteriyoruz
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

            // Debug için log yazıyoruz
            Log.d("stev", title+" "+author+" "+pages);
        }else{
            // Eğer veri yoksa kullanıcıya uyarı veriyoruz
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Silme işlemi için onay diyalogu gösteren method
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Diyalog başlığını ve mesajını ayarlıyoruz
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");

        // Pozitif buton (Evet)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Silme işlemi gerçekleştirilir
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id); // Kitap ID'sine göre silme işlemi yapılır
                finish(); // Aktiviteyi kapatıyoruz
            }
        });

        // Negatif buton (Hayır)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Hiçbir işlem yapılmaz
            }
        });

        // Diyalog penceresini oluşturup gösteriyoruz
        builder.create().show();
    }
}
