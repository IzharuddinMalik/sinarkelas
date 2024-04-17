package com.sinarkelas.util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sinarkelas.db";
    private SQLiteDatabase database;

    public static final String TABLE_KUIS = "tb_kuis";
    public static final String COLUMN_IDKUIS = "idkuis";
    public static final String COLUMN_JUDULKUIS = "judulkuis";
    public static final String COLUMN_CREATEAT = "create_at";
    public static final String COLUMN_TENGGATWAKTU = "tenggatwaktu";
    public static final String COLUMN_IDKELAS = "idkelas";
    public static final String COLUMN_IDDOSEN = "iddosen";
    public static final String COLUMN_TOTALPOINT = "totalpoint";

    public static final String TABLE_RELKUIS = "tb_relkuis";
    public static final String COLUMN_IDRELKUIS = "idrelkuis";
    public static final String COLUMN_RELIDKUIS = "idkuis";
    public static final String COLUMN_SOALKUIS = "soalkuis";
    public static final String COLUMN_POINTSOAL = "pointsoal";

    public static final String TABLE_JAWABANKUIS = "tb_jawabankuis";
    public static final String COLUMN_IDJAWABANKUIS = "idjawabankuis";
    public static final String COLUMN_IDRELKUISJAWABAN = "idrelkuis";
    public static final String COLUMN_JAWABANKUIS = "jawabankuis";
    public static final String COLUMN_STATUSJAWABAN = "statusjawaban";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper open() throws SQLException{
        this.database = this.getWritableDatabase();
        return this;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        final String SQL_KUIS = "CREATE TABLE " + TABLE_KUIS + " ( " +
                COLUMN_IDKUIS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_JUDULKUIS + " TEXT NOT NULL, " +
                COLUMN_CREATEAT + " TEXT NOT NULL, " +
                COLUMN_TENGGATWAKTU + " TEXT NOT NULL, " +
                COLUMN_IDKELAS + " TEXT NOT NULL, " +
                COLUMN_IDDOSEN + " TEXT NOT NULL, " +
                COLUMN_TOTALPOINT + " TEXT NOT NULL " + ")";

        final String SQL_RELKUIS = "CREATE TABLE " + TABLE_RELKUIS + " ( " +
                COLUMN_IDRELKUIS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RELIDKUIS + " TEXT NOT NULL, " +
                COLUMN_SOALKUIS + " TEXT NOT NULL, " +
                COLUMN_POINTSOAL + " TEXT NOT NULL " + ")";

        final String SQL_JAWABANKUIS = "CREATE TABLE " + TABLE_JAWABANKUIS + " ( " +
                COLUMN_IDJAWABANKUIS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IDRELKUISJAWABAN + " TEXT NOT NULL, " +
                COLUMN_JAWABANKUIS + " TEXT NOT NULL, " +
                COLUMN_STATUSJAWABAN + " TEXT NOT NULL " + ")";

        db.execSQL(SQL_KUIS);
        db.execSQL(SQL_RELKUIS);
        db.execSQL(SQL_JAWABANKUIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KUIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RELKUIS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JAWABANKUIS);

        onCreate(db);
    }

    public ArrayList<HashMap<String, String>> getKuis(){
        ArrayList<HashMap<String, String>> kuis;
        kuis = new ArrayList<>();
        String SELECTQUERY = "SELECT * FROM " + TABLE_KUIS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(SELECTQUERY, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_IDKUIS, cursor.getString(0));
                map.put(COLUMN_JUDULKUIS, cursor.getString(1));
                map.put(COLUMN_CREATEAT, cursor.getString(2));
                map.put(COLUMN_TENGGATWAKTU, cursor.getString(3));
                map.put(COLUMN_IDKELAS, cursor.getString(4));
                map.put(COLUMN_IDDOSEN, cursor.getString(5));
                map.put(COLUMN_TOTALPOINT, cursor.getString(6));

                kuis.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        return kuis;
    }

    public ArrayList<HashMap<String, String>> getRelKuis(String idKuis){
        ArrayList<HashMap<String, String>> relKuis;
        relKuis = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RELKUIS + " WHERE idkuis = '" + idKuis + "' ";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_IDRELKUIS, cursor.getString(0));
                map.put(COLUMN_RELIDKUIS, cursor.getString(1));
                map.put(COLUMN_SOALKUIS, cursor.getString(2));
                map.put(COLUMN_POINTSOAL, cursor.getString(3));

                relKuis.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        return relKuis;
    }

    public ArrayList<HashMap<String, String>> getJawaban(String idRelKuis){
        ArrayList<HashMap<String, String>> jawabanKuis;
        jawabanKuis = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_JAWABANKUIS + " WHERE idrelkuis = '" + idRelKuis + "' ";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_IDJAWABANKUIS, cursor.getString(0));
                map.put(COLUMN_IDRELKUISJAWABAN, cursor.getString(1));
                map.put(COLUMN_JAWABANKUIS, cursor.getString(2));
                map.put(COLUMN_STATUSJAWABAN, cursor.getString(3));

                jawabanKuis.add(map);
            } while (cursor.moveToNext());
        }

        database.close();
        return jawabanKuis;
    }

    public void insertKuis(String judulKuis, String createAt, String tenggatWaktu, String idKelas, String idDosen, String totalPoint){
        SQLiteDatabase database = this.getWritableDatabase();

        String queryValues = "INSERT INTO " + TABLE_KUIS + " (judulkuis, create_at, tenggatwaktu, idkelas, iddosen, totalpoint) " + "VALUES('" + judulKuis +"', '" + createAt + "', '" + tenggatWaktu + "', '" + idKelas + "', '" + idDosen + "', '" + totalPoint + "' ";
        Log.i("INSERT KUIS", " === " + queryValues);
        database.execSQL(queryValues);

        database.close();
    }

    public void insertRelKuis(String idKuis, String soalKuis, String pointSoal){
        SQLiteDatabase database = this.getWritableDatabase();

        String queryValues = "INSERT INTO " + TABLE_RELKUIS + " (idkuis, soalkuis, pointsoal)" + " VALUES('"+ idKuis +"', '" + soalKuis + "', '" + pointSoal + "')";
        Log.i("INSERTRELKUIS", " === " + queryValues);
        database.execSQL(queryValues);

        database.close();
    }

    public void insertJawabanSoal(String idRelKuis, String jawabanKuis, String statusJawaban){
        SQLiteDatabase database = this.getWritableDatabase();

        String queryValues = "INSERT INTO " + TABLE_JAWABANKUIS + " (idrelkuis, jawabankuis, statusjawaban)" + " VALUES('" + idRelKuis + "', '" + jawabanKuis + "', '" + statusJawaban + "')";
        Log.i("INSERTJAWABAN", " === " + queryValues);
        database.execSQL(queryValues);

        database.close();
    }
}
