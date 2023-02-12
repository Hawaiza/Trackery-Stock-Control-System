package com.example.trackery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DBforSales extends SQLiteOpenHelper {
    Context context;
    public DBforSales(Context context) {
        super(context, "Salesdata.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase SDB) {
        SDB.execSQL("create table Salesdetails(saleid String primary key,custid Number,prodid Number,quantity Number,cost Number,purDate Date)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase SDB, int i, int i1) {
        SDB.execSQL("Drop table if exists Salesdetails");
    }

    public boolean insertsales(int saleid, int custid, int prodid, int quan, int cost) {
        SQLiteDatabase DB = this.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = sdf.format(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put("saleid", saleid);
        contentValues.put("custid", custid);
        contentValues.put("prodid", prodid);
        contentValues.put("quantity", quan);
        contentValues.put("cost", cost);
        contentValues.put("purDate", strDate);
        long result = DB.insert("Salesdetails", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean updatesales(int saleid, int custid, int prodid, int quan, int cost, String date) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("custid", custid);
        contentValues.put("prodid", prodid);
        contentValues.put("quantity", quan);
        contentValues.put("cost", cost);
        contentValues.put("purDate", date);
        Cursor cursor = DB.rawQuery("select * from Salesdetails where saleid = ?", new String[] {String.valueOf(saleid)});
        if (cursor.getCount()>0) {
            long result = DB.update("Salesdetails", contentValues, "saleid=?", new String[]{String.valueOf(saleid)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }
    public boolean deletesales(int saleid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Salesdetails where saleid = ?", new String[] {String.valueOf(saleid)});
        if (cursor.getCount()>0) {
            long result = DB.delete("Salesdetails", "saleid=?", new String[]{String.valueOf(saleid)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }
        else{
            return false;
        }
    }
    public Cursor getsales() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Salesdetails",null);
        return cursor;
    }
    public Cursor GetSingleSale(int sid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from Salesdetails where saleid=?",new String[]{String.valueOf(sid)});
        return cursor;
    }

    public SimpleCursorAdapter populatelist() {
        SQLiteDatabase DB = this.getWritableDatabase();
        String columns[] = {"saleid","custid","cost",};
        //Cursor cursor = DB.query("Salesdetails",columns,null,null,null,null,null);
        Cursor cursor = DB.rawQuery("select saleid As _id,custid,cost from Salesdetails ",null);
        String[] fieldnames = new String[]{
                "_id","custid","cost",
        };
        int[] ToviewIDs = new int[]{R.id.saleid_value, R.id.cusomerid_value, R.id.cost_value};
        SimpleCursorAdapter salesadapter = new SimpleCursorAdapter(
                context,
                R.layout.sales_single,
                cursor,
                fieldnames,
                ToviewIDs
        );
        return  salesadapter;
    }

    public SimpleCursorAdapter searchlist(CharSequence str) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String columns[] = {"saleid","custid","cost",};
        //Cursor cursor = DB.query("Salesdetails",columns,null,null,null,null,null);
        Cursor cursor = DB.rawQuery("select saleid As _id,custid,cost from Salesdetails where CAST(_id as CHAR) LIKE '"+str+"%'",null);
        String[] fieldnames = new String[]{
                "_id","custid","cost",
        };

        int[] ToviewIDs = new int[]{R.id.saleid_value, R.id.cusomerid_value, R.id.cost_value};
        SimpleCursorAdapter salesadapter = new SimpleCursorAdapter(
                context,
                R.layout.sales_single,
                cursor,
                fieldnames,
                ToviewIDs
        );
        return  salesadapter;
    }
}
