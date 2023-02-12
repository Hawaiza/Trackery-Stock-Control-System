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
import com.google.android.material.textfield.TextInputLayout;

public class Sales_List extends AppCompatActivity {
    EditText e1;
    TextInputLayout t1;
    DBforSales DB;
    ImageButton B1;
    Boolean chck = false;
    ListView lvsales;
    SimpleCursorAdapter simpleCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales_lists);

        lvsales = (ListView) findViewById(R.id.SalesList);
        DB = new DBforSales(this);
        simpleCursorAdapter = DB.populatelist();
        if(simpleCursorAdapter.getCount()>0) {
            lvsales.setAdapter(simpleCursorAdapter);
        }
        lvsales.setTextFilterEnabled(true);
        FloatingActionButton add=(FloatingActionButton) findViewById(R.id.add);
        t1 = (TextInputLayout) findViewById(R.id.serach_text);
        B1 = (ImageButton) findViewById(R.id.searchButton);
        e1 = (EditText) findViewById(R.id.search_value);
        lvsales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                int int_sid = cursor.getInt(0);
                String str_sid = String.valueOf(int_sid);
                Intent i = new Intent(getApplicationContext(),Update_Sales.class);
                i.putExtra("said",str_sid);
                startActivity(i);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Add_Sales.class);
                startActivity(i1);
            }

        });
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chck == false)
                {
                    t1.setVisibility(View.VISIBLE);
                    chck = true;
                }
                else
                {
                    t1.setVisibility(View.INVISIBLE);
                    chck = false;
                }
            }
        });
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
                simpleCursorAdapter = DB.searchlist(str);
                lvsales.setAdapter(simpleCursorAdapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        lvsales = (ListView) findViewById(R.id.SalesList);
        DB = new DBforSales(this);
        simpleCursorAdapter = DB.populatelist();
        lvsales.setAdapter(simpleCursorAdapter);

    }
}