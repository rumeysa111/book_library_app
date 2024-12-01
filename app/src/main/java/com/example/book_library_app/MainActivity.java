package com.example.book_library_app;
// Uygulamanın paket adını belirtir. Bu, projeyi diğerlerinden ayırır.

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// Gerekli kütüphaneler import edilmiş. Bunlar; RecyclerView, veritabanı erişimi (Cursor), diyalog kutuları (AlertDialog) gibi işlevsellik sağlar.

public class MainActivity extends AppCompatActivity {
    // MainActivity sınıfı, uygulamanın ana ekranını temsil eder ve AppCompatActivity sınıfını genişletir.

    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;
    // Kullanılacak görsel bileşenler ve veritabanı, listeleme işlemleri için gerekli değişkenler tanımlanmış.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            setContentView(R.layout.activity_main);
        } else {
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            finish();
            return; // Add return to prevent further execution
        }

        // Ana aktivite başlatıldığında `activity_main` layout'u ekrana yüklenir.

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Araç çubuğu (toolbar) başlatılır.

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);
        // Layout'taki görsel elemanlar Java koduna bağlanır.

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        // "+" butonuna tıklandığında AddActivity ekranına geçiş yapılır.

        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();
        // Veritabanı yardımcı sınıfı oluşturulur ve kitap bilgileri için ArrayList'ler tanımlanır.

        storeDataInArrays();
        // Veritabanından veriler çekilip ArrayList'lere doldurulur.

        customAdapter = new CustomAdapter(MainActivity.this, this, book_id, book_title, book_author, book_pages);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        // RecyclerView için adapter ve layout yöneticisi ayarlanır.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            recreate();
        }
    }
    // Diğer aktivitelerden döndüğünde ekranı yeniden yükler.

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0)); // Kitap ID'si
                book_title.add(cursor.getString(1)); // Kitap adı
                book_author.add(cursor.getString(2)); // Yazar adı
                book_pages.add(cursor.getString(3)); // Sayfa sayısı
            }
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }
    // Veritabanındaki tüm veriler ArrayList'lere eklenir. Eğer veri yoksa "Boş veri" mesajı gösterilir.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Menü çubuğu oluşturulur.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    // Menüdeki "Tümünü sil" seçeneği seçilirse bir onay diyaloğu açılır.

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                // Tüm veriler veritabanından silinir.

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                // Aktivite yeniden başlatılarak ekran güncellenir.
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Hiçbir şey yapma.
            }
        });
        builder.create().show();
    }
    // Kullanıcı tüm verileri silmek istediğinde onay alır.
}
