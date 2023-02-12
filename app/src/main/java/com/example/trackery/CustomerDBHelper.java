package com.example.trackery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

public class CustomerDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME= "customer.DB";
    public static final String TABLE_CUSTOMER= "CustomerDetails";
    public static final String ID="CID";
    public static final String NAME="CNAME";
    public static final String MOBILE="CMOBILE";
    public static final String EMAIL="CEMAIL";
    private Context context;


    public CustomerDBHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE CustomerDetails(CID integer primary key, CNAME text, CMOBILE text, CEMAIL text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CUSTOMER);
        onCreate(db);
    }

    public boolean insertCustData(String id, String name, String mobile, String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CID",id);
        contentValues.put("CNAME", name);
        contentValues.put("CMOBILE", mobile);
        contentValues.put("CEMAIL", email);
        long result = db.insert(TABLE_CUSTOMER, null, contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }
    }

    public boolean updatecust(String cu_id, String cu_name, String cu_mobile, String cu_email){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(ID,cu_id);
        contentValues.put(NAME,cu_name);
        contentValues.put(MOBILE,cu_mobile);
        contentValues.put(EMAIL,cu_email);
        Cursor cursor= db.rawQuery("select * from CustomerDetails where CID=?",new String[] {cu_id});
        if (cursor.getCount()>0) {
            long result = db.update("CustomerDetails", contentValues, "CID=?", new String[] {cu_id});
            if (result == -1)
                return false;
            else
                return true;
        }
         else
            return false;

    }

    public Cursor SingleCustomer(String ID){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select * from CustomerDetails where CID=?", new String[]{String.valueOf(ID)});
        return cursor;
    }

    public SimpleCursorAdapter getcustomerdata(){
        SQLiteDatabase db= this.getWritableDatabase();
        String[] columns={"CID","CNAME","CMOBILE","CEMAIL"};
        Cursor cursor= db.rawQuery("select CID as _id, CNAME from CustomerDetails", null);
        String[] rownames= new String[]{"_id","CNAME"};
        int[] viewid= new int[] { R.id.c_id, R.id.c_name };
        SimpleCursorAdapter customerAdapter= new SimpleCursorAdapter(context,R.layout.customer_singleitem,cursor,rownames,viewid);
        return customerAdapter;
    }

    public SimpleCursorAdapter searching(CharSequence CID){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery("select CID As _id,CNAME from CustomerDetails where _id LIKE'"+CID+"%'", null);
        String[] rownames= new String[]{"_id","CNAME"};
        int[] view= new int[]{R.id.c_id, R.id.c_name};
        SimpleCursorAdapter customeradapter= new SimpleCursorAdapter(context,R.layout.customer_singleitem,cursor,rownames,view);
        return customeradapter;
    }

    public boolean delete(String CID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from CustomerDetails where CID=?", new String[]{CID});
        if (cursor.getCount() > 0) {
            long l = db.delete("CustomerDetails", "CID=?", new String[]{CID});
            if (l == -1)
                return false;
            else
                return true;
        }
        else
            return false;
    }

    public SimpleCursorAdapter populatelist(){
        SQLiteDatabase db= this.getWritableDatabase();
        String columns[]={"CID","CNAME",};
        Cursor cursor= db.rawQuery("Select CID As _id,CNAME from CustomerDetails", null);
        String[] fieldnames= new String[]{"_id","CNAME",};
        int[] toviewid= new int[]{R.id.c_id, R.id.c_name};
        SimpleCursorAdapter customeradapter= new SimpleCursorAdapter(context,R.layout.customer_singleitem,cursor,fieldnames,toviewid);
        return  customeradapter;
    }

    //Access Name for Sales
    public Cursor GetNameForSales(int custid) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select CNAME from CustomerDetails where CID=?",new String[]{String.valueOf(custid)});
        return cursor;
    }

    public Cursor checkcusid(String cust_id){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from CustomerDetails where CID=?", new String[]{String.valueOf(cust_id)});
        return cursor;
    }


}
