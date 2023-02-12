package com.example.trackery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class productDBHandler extends SQLiteOpenHelper {
    Context context;
    private static final String DB_NAME = "productdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "products";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String QUANTITY_COL= "quantity";
    private static final String CATEGORY_COL = "category";
    private static final String COST_COL = "cost";


    public productDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY,"
                + NAME_COL + " TEXT,"
                + QUANTITY_COL + " TEXT,"
                + CATEGORY_COL + " TEXT,"
                + COST_COL + " TEXT)";

        db.execSQL(query);
    }

    public void addnewproducts(String pid,String pname, String pquantity, String pcategory, String pcost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_COL,pid);
        values.put(NAME_COL,pname);
        values.put(QUANTITY_COL,pquantity);
        values.put(CATEGORY_COL,pcategory);
        values.put(COST_COL,pcost);

        db.insert(TABLE_NAME, null, values);

        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /*public void updateproduct(String originalid, String pid,String pname, String pquantity, String pcategory, String pcost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ID_COL,pid);
        values.put(NAME_COL,pname);
        values.put(QUANTITY_COL,pquantity);
        values.put(CATEGORY_COL,pcategory);
        values.put(COST_COL,pcost);

        db.update(TABLE_NAME, values, "id=?", new String[] {originalid});
        db.close();
    }*/


    /*public boolean checkID (String pid) {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor c=DB.rawQuery("select id from "+TABLE_NAME+" where id=?", new String[] {pid});
        boolean exists=(c.getCount()>0);
        return exists;
    }*/

    public SimpleCursorAdapter getproductdata() {
        SQLiteDatabase DB=this.getWritableDatabase();
        String[] columns={"id","name","quantity","cost","category"};
        Cursor c=DB.rawQuery("select id as _id, name from products",null);
        String[] rownames=new String[] {"_id","name"};
        int[] viewid=new int[]{ R.id.product_id, R.id.product_name};
        SimpleCursorAdapter productAdapter=new SimpleCursorAdapter(context,R.layout.product_singleitem,c,rownames,viewid);
        return productAdapter;
    }

    public SimpleCursorAdapter search(CharSequence id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select id As _id,name from products where _id LIKE '"+id+"%'",null);
        String[] rownames = new String[]{"_id","name"};
        int [] viewid = new int[]{R.id.product_id,R.id.product_name};
        SimpleCursorAdapter productadapter=new SimpleCursorAdapter(context,R.layout.product_singleitem,cursor,rownames,viewid);
        return productadapter;
    }

    public boolean updateproduct(String pname, String pquantity, String pcategory, String pcost, String pid) {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(NAME_COL,pname);
        values.put(QUANTITY_COL,pquantity);
        values.put(CATEGORY_COL,pcategory);
        values.put(COST_COL,pcost);
        Cursor cursor=DB.rawQuery("select*from products where id=?",new String[] {pid});
        if(cursor.getCount()>0) {
            long r=DB.update("products",values,"id=?",new String[] {pid});
            if(r==-1) return false;
            else  return true;
        }
        else
            return false;
    }

    public boolean deleteproduct(String pid) {
        SQLiteDatabase DB=this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select*from products where id=?",new String[] {pid});
        if(cursor.getCount()>0) {
            long r=DB.delete("products","id=?",new String[] {pid});
            if(r==-1) return false;
            else
                return true;
        }
        else
            return false;
    }
    public Cursor getproductdetails(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select * from products where id=?",new  String[] {String.valueOf(id)});
        return cursor;
    }

    ////////////////////////////
    //get price of products
    public Cursor GetPriceForCustomer(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("select cost from products where id=?",new  String[] {String.valueOf(id)});
        return cursor;
    }
    ///////////////////////////


    public Cursor checkproid(String prod_id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from products where id=?", new String[]{String.valueOf(prod_id)});
        return cursor;
    }

    public boolean updateqty(String pid, String pquantity) {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(QUANTITY_COL,pquantity);
        Cursor cursor=DB.rawQuery("select*from products where id=?",new String[] {pid});
        if(cursor.getCount()>0) {
            long r=DB.update("products",values,"id=?",new String[] {pid});
            if(r==-1) return false;
            else  return true;
        }
        else
            return false;
    }


}
