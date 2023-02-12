package com.example.trackery;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Customer extends AppCompatActivity {
    CustomerDBHelper custDB;
    public ListView lv;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        ImageButton search = (ImageButton) findViewById(R.id.searchButton);
        EditText searchView = (EditText) findViewById(R.id.searchbox);
         lv = findViewById(R.id.custlv);
        custDB = new CustomerDBHelper(this);
        try{
            setcustadapter();
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                String c_id = cursor.getString(0);
                String str_id = String.valueOf(c_id);
                Intent intent = new Intent(getApplicationContext(), Update_Customer.class);
                intent.putExtra("CID", str_id);
                startActivity(intent);
            }
        });

     /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor= (Cursor) simpleCursorAdapter.getItem(i);
                int int_cid= cursor.getInt(0);
                String str_cid= String.valueOf(int_cid);
               Intent intent= new Intent(getApplicationContext(),Update_Customer.class);
                intent.putExtra("CID",str_cid);
                startActivity(intent);
            }
        });*/

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);

                searchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        simpleCursorAdapter = custDB.searching(charSequence);
                        lv.setAdapter(simpleCursorAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

            }

        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Add_Customer.class);
                startActivity(i1);
            }

        });

    }

    public void setcustadapter() {
        simpleCursorAdapter=custDB.getcustomerdata();
        lv.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onResume(){
        super.onResume();
        simpleCursorAdapter=custDB.getcustomerdata();
        lv.setAdapter(simpleCursorAdapter);

    }
}
