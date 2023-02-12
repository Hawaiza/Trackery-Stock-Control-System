package com.example.trackery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsActivity extends AppCompatActivity {
    public ListView productlist;
    SimpleCursorAdapter simpleCursorAdapter;
    productDBHandler DB;
    ImageButton search, back;
    EditText searchbar;
    TextView products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        FloatingActionButton add=(FloatingActionButton) findViewById(R.id.add);
        search=findViewById(R.id.searchButton);
        searchbar=findViewById(R.id.searchbar);
        products=findViewById(R.id.products);
        productlist=findViewById(R.id.productlist);
       // back=findViewById(R.id.backi);
        /*ImageButton update=(ImageButton) findViewById(R.id.update1);
        ImageButton delete=(ImageButton) findViewById(R.id.delete1);
        ImageButton viewlist=(ImageButton) findViewById(R.id.viewlist); */

        productlist = findViewById(R.id.productlist);
        DB = new productDBHandler(this);
        try {
            setproductsadapter();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        productlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //SimpleCursorAdapter simpleCursorAdapter=new SimpleCursorAdapter();
                Cursor cursor=(Cursor) simpleCursorAdapter.getItem(position);
                String pid= cursor.getString(0);
                String str_id=String.valueOf(pid);
                Intent i = new Intent(getApplicationContext(),finalupdateproduct.class);
                i.putExtra("pid",str_id);
                startActivity(i);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(),Add_Users.class);
                startActivity(i1);
            }

        });


       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4=new Intent(getApplicationContext(),delete_form.class);
                startActivity(i4);
            }
               *//* AlertDialog.Builder a=new AlertDialog.Builder(ProductsActivity.this);
                a.setMessage("Do you want to delete this User?")
                        .setPositiveButton("Delete",null)
                        .setTitle("Delete User")
                        .setIcon(R.drawable.deleteicon2)
                        .setNegativeButton("Cancel",null);
                AlertDialog c=a.create();
                c.show();
            }*//*
        });*/

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(getApplicationContext(), add_products_form.class);
                startActivity(i1);
            }

        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbar.setVisibility(view.INVISIBLE);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setFocusableInTouchMode(true);
               // back.setVisibility(View.VISIBLE);
                //products.setAlpha(0.0f);    //to make the PRODUCTS invisible on edittext click


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

       // products.setAlpha(1.0f);

        /*update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(getApplicationContext(),update_products_form.class);
                startActivity(i2);
            }
        });*/

        /*viewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3=new Intent(getApplicationContext(),view_products.class);
                startActivity(i3);
            }

        });*/
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
    /*public void hidekeyboard(View view) {
        InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }*/
}