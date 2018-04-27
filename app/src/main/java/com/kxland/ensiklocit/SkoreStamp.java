package com.kxland.ensiklocit;

public class SkoreStamp {
	
	String _tanggal;
	String _benar;
	String _listsalah;
	int _lama;
	
	// Empty constructor
    public SkoreStamp(){
         
    }
    
    public SkoreStamp(String tgl, String benar, String ls, int lama){
        this._tanggal = tgl;
        this._benar = benar;
        this._listsalah = ls;
        this._lama = lama;        
    }
    
    public void setTanggal(String tanggal){
        this._tanggal = tanggal;
    }
    public String getTanggal(){
        return this._tanggal;
    }
    
    //
    public void setBenar(String benar){
        this._benar = benar;
    }
    public String getBenar(){
        return this._benar;
    }
    
    //
    public void setListSalah(String listsalah){
        this._listsalah = listsalah;
    }
    public String getListSalah(){
        return this._listsalah;
    }
    
    //
    public void setLama(int lama){
        this._lama = lama;
    }
    public int getlama(){
        return this._lama;
    }
    
    
}
