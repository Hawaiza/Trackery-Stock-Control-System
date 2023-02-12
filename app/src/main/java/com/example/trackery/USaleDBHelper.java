package com.example.trackery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class USaleDBHelper extends SQLiteOpenHelper {

    Context context;

    public USaleDBHelper(Context context) {
        super(context, "sales_data.DB", null, 1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table saledetails(saleid integer primary key, sdate integer, cust_id number, prod_id number, qty integer,cost double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists saledetails");
        onCreate(DB);
    }

   /* public boolean inserdata(sales s){
            SQLiteDatabase DB = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("sdate",s.getdate());
            cv.put("cust_id",s.getCust_id());
            cv.put("prod_id",s.getProd_id());
            cv.put("qty",s.getQty());
            cv.put("cost",s.getCost());

            long res = DB.insert("saledetails", null,cv);
            if (res > 0 ){
                return true;
            }

        return false;
    }*/

    public boolean insertsale(String saleid,String sdate,String cust_id, String prod_id, String qty, String cost){
        SQLiteDatabase DB= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("saleid",saleid);
        cv.put("sdate",sdate);
        cv.put("cust_id",cust_id);
        cv.put("prod_id",prod_id);
        cv.put("qty",qty);
        cv.put("cost",cost);

        long res = DB.insert("saledetails",null,cv);
        if (res == -1){
            return false;
        } else {
            return true;
        }
    }

  /*  public ArrayList<sales> getdata(){
        ArrayList<sales> s = new ArrayList<>();
        String[] colums = {"saleid","sdate","cust_id","prod_id","qty","cost"};
        try {
            SQLiteDatabase DB = this.getWritableDatabase();
            Cursor c = DB.query("saledetails",colums,null,null,null,null,null);

            sales s1;
            if (c!=null){
                while (c.moveToNext()){
                    String s_date=c.getString(1);
                    String c_id=c.getString(2);
                    String p_id=c.getString(3);
                    String s_qty=c.getString(4);
                    String s_cost=c.getString(5);

                    s1= new sales();
                    s1.setDate(Integer.parseInt(s_date));
                    s1.setCust_id(Integer.parseInt(c_id));
                    s1.setProd_id(Integer.parseInt(p_id));
                    s1.setQty(Integer.parseInt(s_qty));
                    s1.setCost(Double.parseDouble(s_cost));

                    s.add(s1);
                }
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return s;
    }*/

    /*public Cursor viewsale(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String[] colums = {"saleid","sdate","cust_id","prod_id","qty","cost"};
       // Cursor c = DB.rawQuery("select *  from saledetails", null);
        Cursor c = DB.query("saledetails", colums,null,null,null,null,null);
        if (c!=null){
            c.moveToNext();
        }
        return c;
    }*/

    public SimpleCursorAdapter getsaledata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        String[] colums = {"saleid","sdate","cust_id","prod_id","qty","cost"};
      //  Cursor c = DB.query("saledetails", colums,null,null,null,null,null);
        Cursor c = DB.rawQuery("select saleid as _id, cust_id ,cost from saledetails", null);

        String[] rownames = new String[]{"_id","cust_id","cost"};
        int [] viewid = new int[]{R.id.cellid,R.id.cellcid,R.id.cellcost};
        SimpleCursorAdapter saleAdapter = new SimpleCursorAdapter(context,R.layout.activity_usale_cell,c,rownames,viewid);

        return  saleAdapter;
    }

    /*public boolean updatesale(String s_id, String s_date, String c_id) {

        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("s_date",s_date);
        contentValues.put("c_id",c_id);

      //  Cursor cursor = DB.rawQuery("Select * from saledetails where saleid=?", new String[]{s_id});
       // if (cursor.getCount()>0){
            long result= DB.update("saledetails", contentValues,"userid=?",new String[]{s_id});
            if (result==-1){
                return false;
            } else {
                return true;
            }
        }*/

    public boolean updatesales(String saleid,String sdate, String cust_id, String prod_id, String qty, String cost) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sdate", sdate);
        contentValues.put("cust_id", cust_id);
        contentValues.put("prod_id", prod_id);
        contentValues.put("qty", qty);
        contentValues.put("cost", cost);
        Cursor cursor = DB.rawQuery("select * from saledetails where saleid = ?", new String[] {String.valueOf(saleid)});
        if (cursor.getCount()>0) {
            long result = DB.update("saledetails", contentValues, "saleid=?", new String[]{String.valueOf(saleid)});
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


    public Cursor GetSingleSale(int sid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from saledetails where saleid=?",new String[]{String.valueOf(sid)});
        return cursor;

    }


    public boolean deletesale(String sid) {
        SQLiteDatabase DB = this.getWritableDatabase();
      //  Cursor cursor = DB.delete("aledetails",new String[]{String.valueOf(sid)});
            long result = DB.delete("saledetails", "saleid=?", new String[]{String.valueOf(sid)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }


    public SimpleCursorAdapter search(CharSequence sid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select saleid As _id,cust_id,cost from saledetails where _id LIKE '"+sid+"%'",null);
        String[] rownames = new String[]{"_id","cust_id","cost"};
        int [] viewid = new int[]{R.id.cellid,R.id.cellcid,R.id.cellcost};
        SimpleCursorAdapter saleAdapter = new SimpleCursorAdapter(context,R.layout.activity_usale_cell,cursor,rownames,viewid);
        return  saleAdapter;

    }

    public Cursor checksaleid(String saleid){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from saledetails where saleid=?", new String[]{String.valueOf(saleid)});
        return cursor;
    }

}
