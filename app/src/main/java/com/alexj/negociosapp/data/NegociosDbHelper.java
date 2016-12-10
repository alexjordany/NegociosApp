package com.herprogramacion.negociosapp.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Manejador de la base de datos
 */
public class NegociosDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Negocios.db";

    public NegociosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NegociosContract.NegocioEntry.TABLE_NAME + " ("
                + NegociosContract.NegocioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NegociosContract.NegocioEntry.ID + " TEXT NOT NULL,"
                + NegociosContract.NegocioEntry.NAME + " TEXT NOT NULL,"
                + NegociosContract.NegocioEntry.CATEGORIA + " TEXT NOT NULL,"
                + NegociosContract.NegocioEntry.NUMERO + " TEXT NOT NULL,"
                + NegociosContract.NegocioEntry.BIO + " TEXT NOT NULL,"
                + NegociosContract.NegocioEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + NegociosContract.NegocioEntry.ID + "))");



        //Insertar datos ficticios para prueba inicial
        mockData(db);

    }

    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockNegocio(sqLiteDatabase, new Negocio("Envy", "BAR",
                "22222222", "Bar",
                "25000000000252578_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg1", "Restaurante",
                "22222222", "Bar",
                "1000000000237780_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg2", "Restaurante",
                "22222222", "Bar",
                "1000000000237792_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg3", "Restaurante",
                "22222222", "Bar",
                "13000000000253042_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg4", "Restaurante",
                "22222222", "Bar",
                "13000000000253048_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg5", "Restaurante",
                "22222222", "Bar",
                "26000000000361342_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg6", "Restaurante",
                "22222222", "Bar",
                "39000000000241930_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg7", "Restaurante",
                "22222222", "Bar",
                "44000000000239150_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg8", "Restaurante",
                "22222222", "Bar",
                "69000000000256726_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg9", "Restaurante",
                "22222222", "Bar",
                "73000000000255696_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg10", "Restaurante",
                "22222222", "Bar",
                "119000000000238968_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg11", "Restaurante",
                "22222222", "Bar",
                "120000000000254910_1920x1080.jpg"));

        mockNegocio(sqLiteDatabase, new Negocio("Neg12", "Restaurante",
                "22222222", "Bar",
                "350199579025612881landscape.jpg"));



    }

   public long mockNegocio(SQLiteDatabase db, Negocio negocio) {
        return db.insert(
                NegociosContract.NegocioEntry.TABLE_NAME,
                null,
                negocio.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }

    public long saveNegocio(Negocio negocio) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                NegociosContract.NegocioEntry.TABLE_NAME,
                null,
                negocio.toContentValues());

    }

    public Cursor getAllNegocios() {
        return getReadableDatabase()
                .query(
                        NegociosContract.NegocioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public Cursor getNegocioById(String NegocioId) {
        Cursor c = getReadableDatabase().query(
                NegociosContract.NegocioEntry.TABLE_NAME,
                null,
                NegociosContract.NegocioEntry.ID + " LIKE ?",
                new String[]{NegocioId},
                null,
                null,
                null);
        return c;
    }

    public int deleteNegocio(String negocioId) {
        return getWritableDatabase().delete(
                NegociosContract.NegocioEntry.TABLE_NAME,
                NegociosContract.NegocioEntry.ID + " LIKE ?",
                new String[]{negocioId});
    }

    public int updateNegocio(Negocio negocio, String negocioId) {
        return getWritableDatabase().update(
                NegociosContract.NegocioEntry.TABLE_NAME,
                negocio.toContentValues(),
                NegociosContract.NegocioEntry.ID + " LIKE ?",
                new String[]{negocioId}
        );
    }
}
