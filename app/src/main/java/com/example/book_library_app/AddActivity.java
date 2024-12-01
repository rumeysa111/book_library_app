package com.example.book_library_app;

import androidx.appcompat.app.AppCompatActivity; // Temel bir Activity sınıfı sağlar.
import androidx.appcompat.widget.Toolbar; // Toolbar bileşeni için gerekli sınıf.

import android.content.SharedPreferences;
import android.os.Bundle; // Activity'nin yaşam döngüsü yönetimi için gerekli.
import android.view.View; // Görüntü (View) bileşenlerini tanımlar.
import android.widget.Button; // Button bileşenini temsil eder.
import android.widget.EditText; // Kullanıcıdan metin girişini almak için kullanılan bileşen.
import android.widget.Toast;

public class AddActivity extends AppCompatActivity { // Kitap ekleme işlemleri için kullanılan Activity sınıfı.

    EditText title_input, author_input, pages_input; // Kullanıcının kitap bilgilerini gireceği metin alanları.
    Button add_button; // Kitap ekleme işlemini başlatacak düğme.

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Activity oluşturulurken çağrılan metod.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add); // activity_add.xml dosyasını bu Activity'ye bağlar.

        // Toolbar'ı tanımlayıp desteklenen bir ActionBar olarak ayarlıyoruz.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // XML'deki bileşenleri Java koduna bağlamak.
        title_input = findViewById(R.id.title_input); // Kitap başlığı için giriş alanı.
        author_input = findViewById(R.id.author_input); // Kitap yazarı için giriş alanı.
        pages_input = findViewById(R.id.pages_input); // Kitap sayfa sayısı için giriş alanı.
        add_button = findViewById(R.id.add_button); // Kitap ekleme düğmesi.

        // Ekleme düğmesine tıklandığında ne yapılacağını tanımlıyoruz.
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences= getSharedPreferences("userPrefs",MODE_PRIVATE);
                String userId= sharedPreferences.getString("userId",null);

                if(userId !=null){
                    // Tıklama işlemi tetiklendiğinde:

                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this); // Veritabanı yardımcı sınıfını oluşturuyoruz.
                    // Kullanıcıdan alınan verileri veritabanına ekliyoruz.
                    myDB.addBook(title_input.getText().toString().trim(), // Başlık
                            author_input.getText().toString().trim(), // Yazar
                            Integer.valueOf(pages_input.getText().toString().trim()),userId); // Sayfa sayısı
                }else{
                    Toast.makeText(AddActivity.this,"Please log in first",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
