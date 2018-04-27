package com.kxland.ensiklocit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by kxm on 7/19/13.
 */
//public class dbOperation extends SQLiteOpenHelper {
public class dbOperation extends SQLiteAssetHelper {
	
    private static final int DATABASE_VERSION = 2;
    private static final String DB_NAME = "ensikoDb";
    
    
    public dbOperation(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    
    
    //insert skore    
    public void addSkore(String waktu, int nilai, String Lsalah, int lama) {
    	SQLiteDatabase db = this.getWritableDatabase();

    	ContentValues values = new ContentValues();
    	values.put("Tanggal", waktu); // Tanggal
    	values.put("Benar", nilai); // Benar
    	values.put("ListSalah", Lsalah); // list salah
    	values.put("Lama", lama); // lama main

    	// Inserting Row
    	db.insert("skore", null, values);
    	db.close(); // Closing database connection
    }

    
    //get skore stamp where tanggal =
    public SkoreStamp getSStamp(String tanggal){
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query("skore", new String[] { "Tanggal",
                "Benar", "ListSalah", "Lama" }, "Tanggal" + "=?",
                new String[] { String.valueOf(tanggal) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
     
        //Integer.parseInt(cursor.getString(0))
        //SkoreStamp(String tgl, String benar, String ls, int lama)
        SkoreStamp ss = new SkoreStamp(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getInt(3));
        
        cursor.close();
        db.close();
        
        return ss;
    }
    
    
    //getAllBuah
    public List<Buah> getAllBuah() {
        List<Buah> buahList = new ArrayList<Buah>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + "ensiko" + " WHERE jenis='buah'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	Buah buah = new Buah();            	
            	buah.setIDx(Integer.parseInt(cursor.getString(0)));
            	buah.setKategori(cursor.getString(1));
            	buah.setBuah(cursor.getString(2));            	
            	buah.setGambar(BitmapFactory.decodeByteArray( cursor.getBlob(3), 0, cursor.getBlob(3).length));
            	buah.setdeskripsi(cursor.getString(4));
                // Adding buah to list
            	buahList.add(buah);
            } while (cursor.moveToNext());
        }
     
        cursor.close();
        db.close();
        
        // return buah list
        return buahList;
    }
    

    
    // Getting list skore count
    public int getSkoreCount() {
    	String countQuery = "SELECT  * FROM " + "skore";
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor cursor = db.rawQuery(countQuery, null);
    	int count = cursor.getCount(); //added line here
    	
    	cursor.close();    	
    	db.close();
    	
    	return count;
    }

    //Ambil timestamp skore utk ditampilkan di nilai
    public List<String> getAllTimeStampSkore() {

    	List<String> timeStampSkore = new ArrayList<String>();

    	// Select All Query
    	String selectQuery = "SELECT  tanggal FROM " + "skore" + " ORDER BY IDx DESC";

    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);   

    	// looping through all rows and adding to list
    	if (cursor.moveToFirst()) {
    		do {
    			timeStampSkore.add(cursor.getString(0));

    		} while (cursor.moveToNext());
    	}

    	cursor.close();
    	db.close();

    	return timeStampSkore;
    }
   
    
    
    
    
    
        
        
   
}
