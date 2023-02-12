package com.example.trackery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.ConstraintSet.Constraint;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Users extends AppCompatActivity {
    public static UsersDBHelper db;
    String a,b;
    ArrayAdapter<String> arrayAdapter;
    int s_id;
    ListView lv;
    SimpleCursorAdapter simpleCursorAdapter;
    ImageButton backtohome;
    Boolean LoginList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        final TextView u_id = (TextView) findViewById(R.id.u_id);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        ImageButton srch = (ImageButton) findViewById(R.id.searchButton);
        SearchView search = (SearchView) findViewById(R.id.searchbox);
        backtohome=(ImageButton) findViewById(R.id.backtohome);
        TextView users1=(TextView) findViewById(R.id.users1);
        ImageButton srchback = (ImageButton) findViewById(R.id.srchback);



        lv = (ListView) findViewById(R.id.ListView1);
        db = new UsersDBHelper(this);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sv = new Intent(Users.this,Admin_Home.class);
                sv.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sv);
            }
        });

        srchback.setVisibility(View.INVISIBLE);

        srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.VISIBLE);
                srch.setVisibility(View.INVISIBLE);
                srchback.setVisibility(View.VISIBLE);
                backtohome.setVisibility(View.VISIBLE);
                //  constLayout.setVisibility(View.INVISIBLE);
            }
        });

        srchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.INVISIBLE);
                srch.setVisibility(View.VISIBLE);
                backtohome.setVisibility(View.VISIBLE);
                srchback.setVisibility(View.INVISIBLE);
                //    linearLayout.setVisibility(View.VISIBLE);
            }
        });

        simpleCursorAdapter = db.populateview();
        lv.setAdapter(simpleCursorAdapter);
        // ArrayList<HashMap<String, String>> userList = db.GetUsers();
        // ListAdapter adapter = new SimpleAdapter(Users.this, userList, R.layout.singleitem, new String[]{"ID", "Name"}, new int[]{R.id.u_id, R.id.u_name});
        // lv.setAdapter(adapter);



        ArrayList<HashMap<String, String>> searchList = db.GetUsers();
        ListAdapter adapter1 = new SimpleAdapter(Users.this, searchList, R.layout.singleitem, new String[]{"ID", "Name"}, new int[]{R.id.u_id, R.id.u_name});
        lv.setAdapter(adapter1);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((SimpleAdapter) adapter1).getFilter().filter(newText);
                return false;
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), Add_Users.class);
                i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i1);
            }

        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) simpleCursorAdapter.getItem(position);
                int int_sid = cursor.getInt(0);
                String str_sid = String.valueOf(int_sid);
                Intent v = new Intent(getApplicationContext(), ViewUsers.class);
                v.putExtra("UserID", str_sid);
                v.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(v);


            }
        });
    }
}