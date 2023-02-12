package com.example.trackery;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class user_productdetails extends AppCompatActivity {
    SimpleCursorAdapter simpleCursorAdapter;
    productDBHandler productDbHandler;
    EditText id,name,quantity,cost,category;
    //ImageButton deleteicon;
    //ImageButton pencilicon;
    //Button updatebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_productdetails);

        //Intent intent=getIntent();

        name=(EditText) findViewById(R.id.updateentername);
        id=findViewById(R.id.updateenterid);
        quantity=(EditText) findViewById(R.id.updateenterquantity);
        cost=(EditText) findViewById(R.id.updateentercost);
        category=(EditText) findViewById(R.id.updateentercategory);
       // deleteicon=findViewById(R.id.deleteicon);
        //pencilicon=findViewById(R.id.pencil);
        //updatebutton=findViewById(R.id.updatebtn);
        //b=(Button) findViewById(R.id.updateproductsbutton);
        //id.setFocusable(false);
        productDbHandler=new productDBHandler(getApplicationContext());
        Intent i=getIntent();
        String str_id=i.getStringExtra("pid");
        Cursor res=productDbHandler.getproductdetails(str_id);
        productDbHandler = new productDBHandler(user_productdetails.this);

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



        /*deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a=new AlertDialog.Builder(user_productdetails.this);
                a.setMessage("Do you want to delete this product?");
                a.setTitle("Delete product");
                a.setIcon(R.drawable.deleteicon2);
                a.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String pid=id.getText().toString();
                        Boolean checkDelete= productDbHandler.deleteproduct(pid);
                        if(checkDelete==true) {
                            Toast.makeText(user_productdetails.this, "Product deleted", Toast.LENGTH_SHORT).show();
                            finish();   //to go back on the list activity
                        }
                        else
                        {
                            Toast.makeText(user_productdetails.this, "Product not deleted", Toast.LENGTH_SHORT).show();
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
        });*/

        /*pencilicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(user_productdetails.this, "edit enabled", Toast.LENGTH_SHORT).show();
                name.setFocusableInTouchMode(true);
                quantity.setFocusableInTouchMode(true);
                cost.setFocusableInTouchMode(true);
                category.setFocusableInTouchMode(true);
            }
        });*/

       /* updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid=id.getText().toString();
                String pname=name.getText().toString();
                String pquantity=quantity.getText().toString();
                String pcost=cost.getText().toString();
                String pcategory=category.getText().toString();

                Boolean i= productDbHandler.updateproduct(pname, pquantity, pcategory, pcost,pid);
                if(i==true)
                    Toast.makeText(user_productdetails.this, "update successful", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(user_productdetails.this, "update unsuccesfull", Toast.LENGTH_SHORT).show();

                id.setText("");
                name.setText("");
                quantity.setText("");
                cost.setText("");
                category.setText("");
            }
        });*/
    }
}