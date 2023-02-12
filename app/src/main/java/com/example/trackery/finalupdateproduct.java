package com.example.trackery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class finalupdateproduct extends AppCompatActivity {
    public ListView productlist;
    SimpleCursorAdapter simpleCursorAdapter;
    productDBHandler productDbHandler;
    EditText id,name,quantity,cost,category;
    ImageButton deleteicon, pencilicon;
    Button updatebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalupdateproduct);
        Intent intent=getIntent();

        name=(EditText) findViewById(R.id.updateentername);
        id=findViewById(R.id.updateenterid);
        quantity=(EditText) findViewById(R.id.updateenterquantity);
        cost=(EditText) findViewById(R.id.updateentercost);
        category=(EditText) findViewById(R.id.updateentercategory);
        deleteicon=findViewById(R.id.deleteicon);
        pencilicon=findViewById(R.id.pencil);
        updatebutton=findViewById(R.id.updatebtn);
        //b=(Button) findViewById(R.id.updateproductsbutton);
        //id.setFocusable(false);
        productDbHandler=new productDBHandler(getApplicationContext());
        Intent i=getIntent();
        String str_id=i.getStringExtra("pid");
        Cursor res=productDbHandler.getproductdetails(str_id);
        productDbHandler = new productDBHandler(finalupdateproduct.this);

        if(res.getCount()>0) {
            res.moveToFirst();
            id.setText(res.getString(0));
            name.setText(res.getString(1));
            quantity.setText(res.getString(2));
            cost.setText(res.getString(4));
            category.setText(res.getString(3));
        }

        id.setFocusableInTouchMode(false);
        name.setFocusableInTouchMode(false);
        quantity.setFocusable(false);
        cost.setFocusableInTouchMode(false);
        category.setFocusableInTouchMode(false);



        deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a=new AlertDialog.Builder(finalupdateproduct.this);
                a.setMessage("Do you want to delete this product?");
                a.setTitle("Delete product");
                a.setIcon(R.drawable.deleteicon2);
                a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pid=id.getText().toString();
                        Boolean checkDelete= productDbHandler.deleteproduct(pid);
                        if(checkDelete==true) {
                            Toast.makeText(finalupdateproduct.this, "Product deleted", Toast.LENGTH_SHORT).show();
                            finish();   //to go back on the list activity
                        }
                        else
                        {
                            Toast.makeText(finalupdateproduct.this, "Product not deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                a.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog b=a.create();
                a.show();
            }
        });

        pencilicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(finalupdateproduct.this, "edit enabled", Toast.LENGTH_SHORT).show();
                name.setFocusableInTouchMode(true);
                quantity.setFocusableInTouchMode(true);
                cost.setFocusableInTouchMode(true);
                category.setFocusableInTouchMode(true);

                updatebutton.setVisibility(view.VISIBLE);
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid=id.getText().toString();
                String pname=name.getText().toString();
                String pquantity=quantity.getText().toString();
                String pcost=cost.getText().toString();
                String pcategory=category.getText().toString();

                Boolean i= productDbHandler.updateproduct(pname, pquantity, pcategory, pcost,pid);
                if(i==true) {
                    Toast.makeText(finalupdateproduct.this, "update successful", Toast.LENGTH_SHORT).show();
                    updatebutton.setVisibility(view.INVISIBLE);
                    name.setFocusable(false);
                    quantity.setFocusable(false);
                    cost.setFocusable(false);
                    category.setFocusable(false);
                    id.setFocusable(false);
                }

                else
                    Toast.makeText(finalupdateproduct.this, "update unsuccesfull", Toast.LENGTH_SHORT).show();

                /*id.setText("");
                name.setText("");
                quantity.setText("");
                cost.setText("");
                category.setText("");*/
            }
        });


        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid=id.getText().toString();
                String pname=name.getText().toString();
                String pquantity=quantity.getText().toString();
                String pcost=cost.getText().toString();
                String pcategory=category.getText().toString();

                Boolean i= productDbHandler.updateproduct(pname, pquantity, pcategory, pcost,pid);
                if(i==true)
                    Toast.makeText(update_products_form.this, "update successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(update_products_form.this, "update unsuccesfull", Toast.LENGTH_SHORT).show();

                id.setText("");
                name.setText("");
                quantity.setText("");
                cost.setText("");
                category.setText("");

               *//* if(name.getText().toString().isEmpty()) {
                    Toast.makeText(update_products_form.this, "please enter name", Toast.LENGTH_SHORT).show();
                }
                else if(quantity.getText().toString().isEmpty()) {
                    Toast.makeText(update_products_form.this, "please enter quantity", Toast.LENGTH_SHORT).show();
                }
                else if(cost.getText().toString().isEmpty()) {
                    Toast.makeText(update_products_form.this, "please enter cost", Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().toString().isEmpty()) {
                    Toast.makeText(update_products_form.this, "please enter category", Toast.LENGTH_SHORT).show();
                }*//*
            }
        });*/



    }


}