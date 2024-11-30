package com.example.book_library_app;

import android.content.ContentValues; // Veritabanına veri eklemek ve güncellemek için kullanılır.
import android.content.Context; // Uygulama bağlamını sağlar.
import android.database.Cursor; // Veritabanı sorgularının sonuçlarını temsil eder.
import android.database.sqlite.SQLiteDatabase; // Veritabanı işlemleri için ana sınıf.
import android.database.sqlite.SQLiteOpenHelper; // SQLite veritabanını yönetmek için temel sınıf.
import android.util.Log; // Loglama işlemleri için kullanılır.
import android.widget.Toast; // Kullanıcıya kısa mesaj göstermek için kullanılır.
import androidx.annotation.Nullable; // Nullable anotasyonu için kullanılır.

class MyDatabaseHelper extends SQLiteOpenHelper { // SQLiteOpenHelper'dan türetilmiş veritabanı yardımcı sınıfı.

    private Context context; // Uygulama bağlamını tutmak için kullanılan değişken.
    private static final String DATABASE_NAME = "BookLibrary.db"; // Veritabanının adı.
    private static final int DATABASE_VERSION = 1; // Veritabanının sürüm numarası.

    // Tablo ve sütun adları.
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";

    // Constructor: Veritabanı yardımcı sınıfını başlatır.
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // SQLiteOpenHelper constructor çağrılır.
        this.context = context; // Bağlamı değişkene atar.
    }

    // Veritabanı ilk oluşturulduğunda çağrılır.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tablo oluşturma sorgusu.
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + // Otomatik artan birincil anahtar.
                COLUMN_TITLE + " TEXT, " + // Kitap başlığı.
                COLUMN_AUTHOR + " TEXT, " + // Kitap yazarı.
                COLUMN_PAGES + " INTEGER);"; // Sayfa sayısı.
        db.execSQL(query); // SQL sorgusunu çalıştırır.
    }

    // Veritabanı güncellendiğinde çağrılır.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Mevcut tabloyu siler.
        onCreate(db); // Tabloyu yeniden oluşturur.
    }

    // Kitap eklemek için kullanılan yöntem.
    void addBook(String title, String author, int pages) {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı açılır.
        ContentValues cv = new ContentValues(); // Veri eklemek için kullanılır.
        cv.put(COLUMN_TITLE, title); // Başlık eklenir.
        cv.put(COLUMN_AUTHOR, author); // Yazar eklenir.
        cv.put(COLUMN_PAGES, pages); // Sayfa sayısı eklenir.
        long result = db.insert(TABLE_NAME, null, cv); // Veriler tabloya eklenir.

        // İşlem sonucuna göre kullanıcıya mesaj gösterilir.
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Tablodaki tüm verileri okumak için kullanılan yöntem.
    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME; // Tüm verileri seçen SQL sorgusu.
        SQLiteDatabase db = this.getReadableDatabase(); // Okunabilir veritabanı açılır.
        Cursor cursor = null; // Veritabanı sonuçlarını tutar.
        if (db != null) {
            cursor = db.rawQuery(query, null); // Sorguyu çalıştırır.
        }
        return cursor; // Sonuç döndürülür.
    }

    // Belirli bir veriyi güncellemek için kullanılan yöntem.
    void updateData(String row_id, String title, String author, String pages) {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı açılır.
        ContentValues cv = new ContentValues(); // Yeni veriler hazırlanır.
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        // Veritabanında veriyi günceller.
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        // İşlem sonucuna göre kullanıcıya mesaj gösterilir.
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Belirli bir satırı silmek için kullanılan yöntem.
    void deleteOneRow(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı açılır.
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id}); // Satır silinir.

        // İşlem sonucuna göre kullanıcıya mesaj gösterilir.
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Tablodaki tüm verileri silmek için kullanılan yöntem.
    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazılabilir veritabanı açılır.
        db.execSQL("DELETE FROM " + TABLE_NAME); // Tüm tablo içeriğini silen SQL sorgusu.
    }
}
