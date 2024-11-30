package com.example.book_library_app;

// Gerekli kütüphaneler projeye dahil ediliyor.
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// RecyclerView.Adapter sınıfını özelleştirerek bir CustomAdapter sınıfı oluşturuyoruz.
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    // Değişkenler: Activity, Context ve kitap bilgilerini tutan ArrayList'ler.
    private Context context;
    private Activity activity;
    private ArrayList book_id, book_title, book_author, book_pages;

    // Constructor: Adapter'ı oluştururken gerekli verileri alıyoruz.
    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                  ArrayList book_pages) {
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    // ViewHolder oluşturma işlemi. my_row.xml dosyası layout olarak atanıyor.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context); // LayoutInflater, özel layout'u inflate etmek için kullanılır.
        View view = inflater.inflate(R.layout.my_row, parent, false); // "my_row" layout dosyası inflate ediliyor.
        return new MyViewHolder(view); // Yeni bir ViewHolder döndürülüyor.
    }

    // Her bir liste elemanını doldurmak için kullanılıyor.
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        // Veriler ArrayList'ten alınarak TextView'lere atanıyor.
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));

        // Liste elemanına tıklanıldığında yapılacak işlemler.
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class); // UpdateActivity'ye geçiş yapılacak.
                // Intent'e veri gönderiliyor.
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                activity.startActivityForResult(intent, 1); // Aktivite başlatılıyor.
            }
        });
    }

    // Toplam liste elemanı sayısını döndürür.
    @Override
    public int getItemCount() {
        return book_id.size(); // Kitap ID'lerinin boyutuna göre liste uzunluğu belirlenir.
    }

    // ViewHolder sınıfı: Tek bir liste elemanını temsil eder.
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Her bir liste elemanı için TextView'ler ve Layout referansları.
        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // itemView içindeki bileşenler tanımlanıyor.
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            // RecyclerView için animasyon ayarlanıyor.
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
