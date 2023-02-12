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
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class USales_Home extends AppCompatActivity {

    public ListView salelist;
    ImageButton search,back;
    EditText searchbar;
    SimpleCursorAdapter simpleCursorAdapter;
    USaleDBHelper DB;
    productDBHandler PDB;
    TextView sale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usales);

        salelist = (ListView) findViewById(R.id.listv);
        search = (ImageButton) findViewById(R.id.searchButton);
        sale = (TextView) findViewById(R.id.users1);
        back = (ImageButton) findViewById(R.id.backButton);
       // searchbar = (EditText) findViewById(R.id.search_bar)

        DB= new USaleDBHelper(this);
        PDB = new productDBHandler(this);

        setSaleAdapter();
        //simpleCursorAdapter = DB.getsaledata();
        //salelist.setAdapter(simpleCursorAdapter);

       salelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
               Cursor c = (Cursor) simpleCursorAdapter.getItem(position);
               String sid = c.getString(0);
               Intent intent = new Intent(USales_Home.this, Update_USales.class);
               intent.putExtra("sid",sid);
               startActivity(intent);
           }
       });

        FloatingActionButton add=(FloatingActionButton) findViewById(R.id.add);
        ImageButton view = (ImageButton) findViewById(R.id.searchButton);

      //  DB = new DBHelper(getApplicationContext());



       add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(USales_Home.this, Add_USales.class);
                startActivity(i1);
            }

        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sale.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                searchbar = (EditText) findViewById(R.id.searchbar);
                searchbar.setVisibility(View.VISIBLE);
                searchbar.setFocusableInTouchMode(true);
                back.setVisibility(View.VISIBLE);
                searchbar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        simpleCursorAdapter = DB.search(charSequence);
                        salelist.setAdapter(simpleCursorAdapter);

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sale.setVisibility(View.VISIBLE);
                        search.setVisibility(View.VISIBLE);
                        searchbar.setVisibility(View.INVISIBLE);
                        back.setVisibility(View.INVISIBLE);
                        setSaleAdapter();
                    }
                });
            }
        });


    }



    public void setSaleAdapter() {
      ///SaleAdapter saleAdapter = new SaleAdapter(Sales_Home.this, sales.salesArrayList);
     //salelist.setAdapter(saleAdapter);
        simpleCursorAdapter = DB.getsaledata();
        salelist.setAdapter(simpleCursorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //simpleCursorAdapter = DB.getsaledata();
        //salelist.setAdapter(simpleCursorAdapter);
        setSaleAdapter();
    }
}


