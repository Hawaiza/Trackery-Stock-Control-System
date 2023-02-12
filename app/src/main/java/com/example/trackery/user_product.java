package com.example.trackery;

import androidx.appcompat.app.AppCompatActivity;

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

public class user_product extends AppCompatActivity {
    public ListView productlist;
    SimpleCursorAdapter simpleCursorAdapter;
    productDBHandler DB;
    ImageButton search;
    EditText searchbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product);
        search=findViewById(R.id.searchButton);
        searchbar=findViewById(R.id.searchbar);

        productlist = findViewById(R.id.user_productlist);
        DB = new productDBHandler(this);
        try {
            setproductsadapter();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbar.setVisibility(view.INVISIBLE);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setFocusableInTouchMode(true);
                // back.setVisibility(View.VISIBLE);


                searchbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        simpleCursorAdapter = DB.search(charSequence);
                        productlist.setAdapter(simpleCursorAdapter);
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // search.setVisibility(View.VISIBLE);
                        //searchbar.setVisibility(View.INVISIBLE);
                        // back.setVisibility(View.INVISIBLE);
                        //setproductsadapter();
                    }
                });
            }
        });

        productlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter();
                Cursor cursor=(Cursor) simpleCursorAdapter.getItem(position);
                String pid= cursor.getString(0);
                String str_id=String.valueOf(pid);
                Intent open = new Intent(getApplicationContext(),user_productdetails.class);
                open.putExtra("pid",str_id);
                startActivity(open);
            }
        });
    }

    public void setproductsadapter()
    {
        simpleCursorAdapter=DB.getproductdata();
        productlist.setAdapter(simpleCursorAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleCursorAdapter=DB.getproductdata();
        productlist.setAdapter(simpleCursorAdapter);
    }
}