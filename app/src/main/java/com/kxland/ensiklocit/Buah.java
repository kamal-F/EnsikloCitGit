package com.kxland.ensiklocit;

import android.graphics.Bitmap;

public class Buah {
	//{Kategori, Buah, Gambar, Deskripsi }
	//private variables    
    int _IDx;
	String _kategori;
    String _buah;
    String _deskripsi;
    Bitmap _gambar;
    
    // Empty constructor
    public Buah(){
         
    }
    
    public void setIDx(int idx){
        this._IDx = idx;
    }
    public int getIDx(){
        return this._IDx;
    }
    public void setKategori(String kategori){
        this._kategori = kategori;
    }
    public String getKategori(){
        return this._kategori;
    }
    public void setBuah(String buah){
        this._buah = buah;
    }
    public String getBuah(){
        return this._buah;
    }
    public void setdeskripsi(String deskripsi){
        this._deskripsi = deskripsi;
    }
    public String getDeskripsi(){
        return this._deskripsi;
    }
    public void setGambar(Bitmap gambar){
        this._gambar = gambar;
    }
    public Bitmap getGambar(){
        return this._gambar;
    }
}
