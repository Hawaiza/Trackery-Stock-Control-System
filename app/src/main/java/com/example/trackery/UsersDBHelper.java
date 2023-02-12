package com.example.trackery;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersDBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "data.DB";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_MOBILE = "Mobile_No";
    private static final String KEY_EMAIL = "Email_ID";
    private static final String KEY_DESG = "Designation";
    private static final String KEY_PASSWORD = "Password";
    private Context context;


    public UsersDBHelper(Context context){

        super(context,DB_NAME, null, DB_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_MOBILE + " NUMBER," + KEY_EMAIL + " TEXT,"
                + KEY_DESG + " TEXT," + KEY_PASSWORD + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        onCreate(db);
    }


    void insertUserDetails(String Name, String Mobile_No, String Email_ID, String Designation, String Password){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, Name);
        cValues.put(KEY_MOBILE, Mobile_No);
        cValues.put(KEY_EMAIL, Email_ID);
        cValues.put(KEY_DESG, Designation);
        cValues.put(KEY_PASSWORD, Password);

        long newRowId = db.insert(TABLE_Users,null, cValues);
        db.close();
    }

    public boolean updateuser(String ID, String Name, String Mobile_No, String Email_ID, String Designation, String Password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, Name);
        contentValues.put(KEY_MOBILE, Mobile_No);
        contentValues.put(KEY_EMAIL, Email_ID);
        contentValues.put(KEY_DESG, Designation);
        contentValues.put(KEY_PASSWORD, Password);
        Cursor cursor = db.rawQuery("Select * from userdetails where "+ KEY_ID +"=?", new String[]{ID});
        if (cursor.getCount()>0){
            long result= db.update("userdetails", contentValues, KEY_ID +"=?",new String[]{ID});
            if (result==-1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public boolean deleteuser(String ID){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userdetails where " + KEY_ID +"=?", new String[]{ID});
        if (cursor.getCount()>0){
            long result=DB.delete("userdetails", KEY_ID +"=?",new String[] {ID});
            if (result==-1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    @SuppressLint("Range")
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT ID, Name FROM "+ TABLE_Users;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("ID",cursor.getString(cursor.getColumnIndex(KEY_ID)));
            user.put("Name",cursor.getString(cursor.getColumnIndex(KEY_NAME)));

            userList.add(user);
        }
        return  userList;
    }

    public Cursor GetUsersLogin(String Email_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userdetails where Email_ID=?", new String[]{Email_ID});
        return cursor;
    }

    public Cursor GetEmail(String Email_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from userdetails where Email_ID=?", new String[]{Email_ID});
        return res;
    }

    public Cursor GetMobileNo(String Mobile_No){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from userdetails where Mobile_No=?", new String[]{Mobile_No});
        return cursor;
    }


    public SimpleCursorAdapter populateview() {
        SQLiteDatabase DB = this.getWritableDatabase();
        String columns[] = {"ID"};
        Cursor cursor = DB.rawQuery("select ID as _id from userdetails ",null);
        String[] fieldnames = new String[]{"_id",};
        int[] ToviewIDs = new int[]{R.id.u_id};
        SimpleCursorAdapter usersadapter = new SimpleCursorAdapter(
                context,
                R.layout.singleitem,
                cursor,
                fieldnames,
                ToviewIDs
        );
        return usersadapter;
    }

    public Cursor viewData() {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        Cursor cursor =  sqldb.rawQuery( "select * from userdetails where ID="+KEY_ID+"", null );
        return cursor;
    }

    public Cursor GetSingleUser(int u_id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userdetails where ID=?",new String[]{String.valueOf(u_id)});
        return cursor;

    }
}