package com.example.trackery;

import static java.lang.Integer.parseInt;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Update_Sales extends AppCompatActivity {
    private EditText sid,cid,pid,quan,cost,pdate,cname;
    private Button b;
    boolean check_state = false;
    DBforSales DB;
    ImageButton del,edit;
    TextView msg;
    CustomerDBHelper CDB;
    private Boolean check_Name = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_sales);

        Intent intent=getIntent();      //calls the intent from previous activity

        sid=(EditText) findViewById(R.id.addSID);
        cid=(EditText) findViewById(R.id.addCID);
        quan=(EditText) findViewById(R.id.addQA);
        cost=(EditText) findViewById(R.id.addCOST);
        pid=(EditText) findViewById(R.id.addPID);
        pdate = (EditText) findViewById(R.id.adddate);
        b=(Button) findViewById(R.id.adduserbutton);
        del = (ImageButton) findViewById(R.id.sales_delete);
        edit = (ImageButton) findViewById(R.id.sales_update);
        msg = (TextView) findViewById(R.id.savemsg);
        cname = (EditText) findViewById(R.id.cusname);

        sid.setFocusableInTouchMode(false);
        cid.setFocusableInTouchMode(false);
        quan.setFocusableInTouchMode(false);
        cost.setFocusableInTouchMode(false);
        pid.setFocusableInTouchMode(false);
        pdate.setFocusableInTouchMode(false);
        cname.setFocusableInTouchMode(false);

        DB = new DBforSales(getApplicationContext());

        Intent i = getIntent();
        String string_sid = i.getStringExtra("said");
        int int_sid = parseInt(string_sid);

        Cursor res = DB.GetSingleSale(int_sid);
        if (res.getCount() > 0) {
            res.moveToFirst();
            sid.setText(res.getString(0));
            cid.setText(res.getString(1));
            pid.setText(res.getString(2));
            quan.setText(res.getString(3));
            cost.setText(res.getString(4));
            pdate.setText(res.getString(5));
        }

        //////////////////////////////////////////////////
        //Display name from customers
        int cusid = parseInt(res.getString(1));
        CDB = new CustomerDBHelper(getApplicationContext());
        Cursor cusname = CDB.GetNameForSales(cusid);
        if (cusname.getCount() > 0) {
            cusname.moveToFirst();
            cname.setText(cusname.getString(0));
        }
        /////////////////////////////////////////////////

        /////////////////////////////
        //Check If cutomers exists or not
        cid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!cid.getText().toString().isEmpty()) {
                    int cusid = parseInt(cid.getText().toString());
                    Cursor cusc = CDB.GetNameForSales(cusid);
                    if (cusc.getCount() > 0) {
                        check_Name = true;
                    }
                    else
                    {
                        check_Name = false;
                    }
                }
            }
        });
        ////////////////////////////





        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder a=new AlertDialog.Builder(Update_Sales.this);
                a.setMessage("Do you want to delete this Sale record?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DB.deletesales(int_sid);
                                Intent intent1 = new Intent(getApplicationContext(),Sales_List.class);
                                startActivity(intent1);
                            }
                        })
                        .setTitle("Delete Sale")
                        .setIcon(R.drawable.deleteicon2)
                        .setNegativeButton("Cancel",null);
                AlertDialog c=a.create();
                c.show();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_state == false) {
                    check_state = true;
                    msg.setVisibility(View.VISIBLE);
                    cid.setFocusableInTouchMode(true);
                    quan.setFocusableInTouchMode(true);
                    cost.setFocusableInTouchMode(true);
                    pid.setFocusableInTouchMode(true);
                    pdate.setFocusableInTouchMode(true);
                    edit.setBackgroundColor(Color.parseColor("#D2C2F7"));
                }
                else
                {
                    AlertDialog.Builder a=new AlertDialog.Builder(Update_Sales.this);
                    a.setMessage("Do you want Save changes? This action cannot be reversed.")
                            .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(sid.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please enter sales id", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(cid.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please customer name", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(quan.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please enter quantity", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(cost.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please enter cost", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(pid.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please enter product", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(pdate.getText().toString().isEmpty()) {
                                        Toast.makeText(Update_Sales.this, "please enter date", Toast.LENGTH_SHORT).show();
                                    }
                                    else if(check_Name == false)
                                    {
                                        Toast.makeText(Update_Sales.this, "Customer Does not exist", Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {
                                        check_state = false;
                                        msg.setVisibility(View.INVISIBLE);

                                        int salid = parseInt(sid.getText().toString());
                                        int cusid = parseInt(cid.getText().toString());
                                        int proid = parseInt(pid.getText().toString());
                                        int count = parseInt(quan.getText().toString());
                                        int amount = parseInt(cost.getText().toString());
                                        String purdate = pdate.getText().toString();

                                        //////////////////////////////////////////////////
                                        //Display name from customers afyer edit
                                        int customerid = parseInt(cid.getText().toString());
                                        Cursor cusname = CDB.GetNameForSales(customerid);
                                        if (cusname.getCount() > 0) {
                                            cusname.moveToFirst();
                                            cname.setText(cusname.getString(0));
                                        }
                                        /////////////////////////////////////////////////

                                        edit.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

                                        cid.setFocusable(false);
                                        quan.setFocusable(false);
                                        cost.setFocusable(false);
                                        pid.setFocusable(false);
                                        pdate.setFocusable(false);

                                        Boolean checkforupdate = DB.updatesales(salid,cusid,proid,count,amount,purdate);
                                        if(checkforupdate == true){
                                            Toast.makeText(getApplicationContext(), "Sale data Updated", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "Sale data failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            })
                            .setTitle("Update Sale")
                            .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    check_state = false;
                                    msg.setVisibility(View.INVISIBLE);

                                    if (res.getCount() > 0) {
                                        res.moveToFirst();
                                        sid.setText(res.getString(0));
                                        cid.setText(res.getString(1));
                                        pid.setText(res.getString(2));
                                        quan.setText(res.getString(3));
                                        cost.setText(res.getString(4));
                                        pdate.setText(res.getString(5));

                                        edit.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

                                        cid.setFocusable(false);
                                        quan.setFocusable(false);
                                        cost.setFocusable(false);
                                        pid.setFocusable(false);
                                        pdate.setFocusable(false);
                                    }
                                }
                            });
                    AlertDialog c=a.create();
                    c.show();

                }
            }
        });

    }
}