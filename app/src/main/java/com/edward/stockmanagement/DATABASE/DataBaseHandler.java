package com.edward.stockmanagement.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edward.stockmanagement.GLOBAL.Global_Variables;
import com.edward.stockmanagement.OBJECTS.CLINIC;
import com.edward.stockmanagement.OBJECTS.MEDICATION;
import com.edward.stockmanagement.OBJECTS.STOCK;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Edward on 2017/04/04.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "StockManagement_Mezzanine_Ware";
    //Tables
    private static final String TABLE_CLINIC = "table_clinic";
    private static final String TABLE_MEDS = "table_meds";
    private static final String TABLE_STOCK = "table_stock";
    //Table Clinic
    private static final String KEY_CLINIC_ID = "id";
    private static final String KEY_CLINIC_NAME = "name";
    private static final String KEY_CLINIC_COUNTRY = "country";
    private static final String KEY_CLINIC_UUID = "uuid";

    //TABLE MEDS
    private static final String KEY_MEDS_ID = "id";
    private static final String KEY_MEDS_NAME = "name";
    private static final String KEY_MEDS_UUID = "uuid";
    //TABLE_STOCK
    private static final String KEY_STOCK_ID = "id";
    private static final String KEY_STOCK_CLINIC = "clinic";
    private static final String KEY_STOCK_MEDICATION = "medication";
    private static final String KEY_STOCK_CLINIC_UUID = "clinic_uuid";
    private static final String KEY_STOCK_MEDICATION_UUID = "medication_uuid";
    private static final String KEY_STOCK_COUNT = "count";



    public SQLiteDatabase sqlWrite = this.getWritableDatabase();
    public SQLiteDatabase sqlRead = this.getReadableDatabase();
    public SQLiteDatabase db;

    private static final String CREATE_TABLE_CLINIC = "CREATE TABLE " + TABLE_CLINIC + "(" +
            KEY_CLINIC_ID + " INTEGER PRIMARY KEY, " +
            KEY_CLINIC_NAME + " TEXT, " +
            KEY_CLINIC_COUNTRY + " TEXT, " +
            KEY_CLINIC_UUID + " TEXT" +
            ")";

    private static final String CREATE_TABLE_MEDS = "CREATE TABLE " + TABLE_MEDS + "(" +
            KEY_MEDS_ID + " INTEGER PRIMARY KEY, " +
            KEY_MEDS_NAME + " TEXT , " +
            KEY_MEDS_UUID + " TEXT" +
            ")";

    private static final String CREATE_TABLE_STOCK = "CREATE TABLE " + TABLE_STOCK + "(" +
            KEY_STOCK_ID + " INTEGER PRIMARY KEY, " +
            KEY_STOCK_CLINIC + " INTEGER, "+
            KEY_STOCK_MEDICATION + " INTEGER, "+
            KEY_STOCK_CLINIC_UUID + " TEXT, " +
            KEY_STOCK_MEDICATION_UUID + " TEXT, " +
            KEY_STOCK_COUNT + " INTEGER "
            +")";


    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CLINIC);
        sqLiteDatabase.execSQL(CREATE_TABLE_MEDS);
        sqLiteDatabase.execSQL(CREATE_TABLE_STOCK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CLINIC);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STOCK);
    }

    public void add_clinic (CLINIC clinic){
        db = sqlWrite;
        ContentValues values = new ContentValues();
        values.put(KEY_CLINIC_NAME, clinic.getC_name());
        values.put(KEY_CLINIC_COUNTRY,clinic.getC_country());
        values.put(KEY_CLINIC_UUID,clinic.getC_uuid());
        db.insert(TABLE_CLINIC,null,values);

    }

    public void add_medication(MEDICATION medication){
        db = sqlWrite;
        ContentValues values = new ContentValues();
        values.put(KEY_MEDS_NAME,medication.getM_name());
        values.put(KEY_MEDS_UUID,medication.getM_uuid());
        db.insert(TABLE_MEDS,null,values);
    }

    public void add_stock(STOCK stock){
        db = sqlWrite;
        ContentValues values = new ContentValues();
        values.put(KEY_STOCK_CLINIC,stock.getS_clinic());
        values.put(KEY_STOCK_MEDICATION,stock.getS_medication());
        values.put(KEY_STOCK_CLINIC_UUID,stock.getS_clinic_uuid());
        values.put(KEY_STOCK_MEDICATION_UUID,stock.getS_medication_uuid());
        values.put(KEY_STOCK_COUNT,stock.getS_stock_count());
        db.insert(TABLE_STOCK,null,values);
    }

    public CLINIC get_clinic(int id)
    {
        db = sqlRead;
        CLINIC clinic = new CLINIC();
        Cursor cursor = db.query(TABLE_CLINIC, new String[]{KEY_CLINIC_ID,KEY_CLINIC_NAME,KEY_CLINIC_COUNTRY,KEY_CLINIC_UUID},KEY_CLINIC_ID + "=?",
                new String[] {String.valueOf(id)},null,null,null);
        if (cursor != null && cursor.moveToFirst()){


        clinic.setC_id(cursor.getInt(0));
        clinic.setC_name(cursor.getString(1));
        clinic.setC_country(cursor.getString(2));
        clinic.setC_uuid(cursor.getString(3));
        cursor.close();}
        return clinic;
    }



    public MEDICATION get_medication(int id){
        db = sqlRead;
        MEDICATION medication = new MEDICATION();
        Cursor cursor = db.query(TABLE_MEDS, new String[]{KEY_MEDS_ID,KEY_MEDS_NAME,KEY_MEDS_UUID},KEY_MEDS_ID + "=?",new String[] {String.valueOf(id)},null,null,null);
        if (cursor != null && cursor.moveToFirst()){


        medication.setM_id(cursor.getInt(0));
        medication.setM_name(cursor.getString(1));
        medication.setM_uuid(cursor.getString(2));
        cursor.close();}
        return medication;
    }

   public STOCK get_stock(int id){
        db = sqlRead;
        STOCK stock = new STOCK();
        Cursor cursor = db.query(TABLE_STOCK,new String[]{KEY_STOCK_ID,KEY_STOCK_CLINIC,KEY_STOCK_MEDICATION,KEY_STOCK_CLINIC_UUID,KEY_STOCK_MEDICATION_UUID,KEY_STOCK_COUNT},KEY_STOCK_ID + "=?",new String[] {String.valueOf(id)},null,null,null);
       if (cursor != null && cursor.moveToFirst()){


       stock.setS_id(cursor.getInt(0));
       stock.setS_clinic(cursor.getInt(1));
       stock.setS_medication(cursor.getInt(2));
       stock.setS_clinic_uuid(cursor.getString(3));
       stock.setS_medication_uuid(cursor.getString(4));
       stock.setS_stock_count(cursor.getInt(5));
       cursor.close();}
       return stock;
   }

   public int update_clinic(CLINIC clinic){
       db = sqlWrite;
       ContentValues values = new ContentValues();
       values.put(KEY_CLINIC_NAME, clinic.getC_name());
       values.put(KEY_CLINIC_COUNTRY, clinic.getC_country() );
       int _id = clinic.getC_id();
       return db.update(TABLE_CLINIC,values,KEY_CLINIC_ID + " =?", new String[]{String.valueOf(_id)});
   }

   public int update_medication(MEDICATION medication){
       db = sqlWrite;
       ContentValues values = new ContentValues();
       values.put(KEY_MEDS_NAME, medication.getM_name());
       int _id = medication.getM_id();
       return db.update(TABLE_MEDS,values,KEY_MEDS_ID +"=?",new String[]{String.valueOf(_id)});
   }

   public int update_stock(STOCK stock){
       db = sqlWrite;
       ContentValues values = new ContentValues();
       values.put(KEY_STOCK_CLINIC,stock.getS_clinic());
       values.put(KEY_STOCK_MEDICATION,stock.getS_medication());
       values.put(KEY_STOCK_COUNT,stock.getS_stock_count());
       int _id = stock.getS_id();
       return db.update(TABLE_STOCK,values,KEY_STOCK_ID +"=?",new String[]{String.valueOf(_id)});
   }

   public void delete_clinic(int id){

       db = sqlWrite;
        Log.d("DELETE","CLINIC " + id);


       db.delete(TABLE_CLINIC,KEY_CLINIC_ID + " = ?",new String[]{String.valueOf(id)});

   }



    public void delete_medication(MEDICATION medication){
        db = sqlWrite;
        db.delete(TABLE_MEDS,KEY_MEDS_ID + " = ?",new String[]{String.valueOf(medication.getM_id())});
    }

    public void delete_stock(STOCK stock){
        db = sqlWrite;
        db.delete(TABLE_STOCK,KEY_STOCK_ID + " = ?",new String[]{String.valueOf(stock.getS_id())});
    }

    public int get_clinic_count(){
        db = sqlRead;
        String count_clinic_query = "SELECT * FROM " + TABLE_CLINIC;
        Cursor cursor = db.rawQuery(count_clinic_query,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int get_medication_count(){
        db = sqlRead;
        String count_clinic_query = "SELECT * FROM " + TABLE_MEDS;
        Cursor cursor = db.rawQuery(count_clinic_query,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int get_stock_count(){
        db = sqlRead;
        String count_clinic_query = "SELECT * FROM " + TABLE_STOCK;
        Cursor cursor = db.rawQuery(count_clinic_query,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public static boolean doesDatabaseExist(Context context) {
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    public int[] clinic_record_set(int id){

      String Query_Clinic_Stock = "SELECT * FROM " + TABLE_STOCK + " WHERE " + KEY_STOCK_CLINIC + "=?";
       db = sqlRead;
        int[] records = new int[0];
        int cursor_count = 0;
        Cursor cursor = db.rawQuery(Query_Clinic_Stock,new String[]{String.valueOf(id)});
        if (cursor !=null){
            records = new int[cursor.getCount()];
            if (cursor.moveToFirst()){
                do{
                    records[cursor_count] = cursor.getInt(0);
                    cursor_count++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        for (int i = 0; i < records.length;i++) {

            Log.d("CURSOR", String.valueOf(records[i]));

        }
        Global_Variables.setWarning_stock_id(records);
        return records;
    }
    public String Clinic_UUID_GENERATOR(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


    public CLINIC get_clinic_by_uuid(String uuid){
        db = sqlRead;
        CLINIC clinic = new CLINIC();
        String QUERY = "SELECT * from "+ TABLE_CLINIC +" where " +KEY_CLINIC_UUID + " = '" + uuid+"'";
        Cursor cursor = db.rawQuery(QUERY,null);
        try{
            cursor.moveToFirst();
            if (cursor != null && cursor.moveToFirst()) {
                clinic.setC_id(cursor.getInt(0));
                clinic.setC_name(cursor.getString(1));
                clinic.setC_country(cursor.getString(2));
                clinic.setC_uuid(cursor.getString(3));
                cursor.close();
            }}catch(Exception e){
            Log.e("EXCEPTION", e.toString());
        }
        return clinic;
    }
    public MEDICATION get_medication_by_uuid(String uuid){
        db = sqlRead;



        MEDICATION medication = new MEDICATION();
        String QUERY = "SELECT * from "+ TABLE_MEDS +" where " +KEY_MEDS_ID + " = '" + uuid+"'";
        Cursor cursor = db.rawQuery(QUERY,null);
        if (cursor != null && cursor.moveToFirst()){
            cursor.moveToFirst();

            medication.setM_id(cursor.getInt(0));
            medication.setM_name(cursor.getString(1));
            medication.setM_uuid(cursor.getString(2));
            cursor.close();}
        return medication;
    }

    public int[] medication_record_set(String uuid){

        String Query_Clinic_Stock = "SELECT * FROM " + TABLE_STOCK + " WHERE " + KEY_STOCK_MEDICATION_UUID + "=?";
        db = sqlRead;
        int[] records = new int[0];
        int cursor_count = 0;
        Cursor cursor = db.rawQuery(Query_Clinic_Stock,new String[]{String.valueOf(uuid)});
        if (cursor !=null){
            records = new int[cursor.getCount()];
            if (cursor.moveToFirst()){
                do{
                    records[cursor_count] = cursor.getInt(0);
                    cursor_count++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        for (int i = 0; i < records.length;i++) {

            Log.d("CURSOR", String.valueOf(records[i]));

        }
        Global_Variables.setWarning_stock_id(records);
        return records;
    }
}



