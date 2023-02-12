package com.example.trackery;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Add_Sales extends AppCompatActivity {
    private EditText sid,cid,pid,quan,cost;
    private Button b;
    DBforSales DB;
    productDBHandler PDB;
    Boolean check_ID = false;
    Boolean check_Name = false;

    private CustomerDBHelper CDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sales);

        Intent intent=getIntent();      //calls the intent from previous activity

        sid=(EditText) findViewById(R.id.addSID);
        cid=(EditText) findViewById(R.id.addCID);
        quan=(EditText) findViewById(R.id.addQA);
        cost=(EditText) findViewById(R.id.addCOST);
        pid=(EditText) findViewById(R.id.addPID);
        b=(Button) findViewById(R.id.adduserbutton);
        DB = new DBforSales(this);
        PDB = new productDBHandler(this);
        CDB = new CustomerDBHelper(this);

        //////////////////////////////
        //Get next Sales ID
        Cursor c1 = DB.getsales();
        if(c1.getCount()>0)
        {
            c1.moveToLast();
            int saleid = parseInt(c1.getString(0));
            saleid = saleid + 1;
            sid.setText(String.valueOf(saleid));
            sid.setFocusableInTouchMode(false);
        }
        /////////////////////////////

        /////////////////////////////
        //Get price for cutomers
        quan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence str, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!quan.getText().toString().isEmpty()) {
                    String prodid = pid.getText().toString();
                    int number = parseInt(quan.getText().toString());
                    Cursor ProCur = PDB.GetPriceForCustomer(prodid);
                    if (ProCur.getCount() > 0) {
                        ProCur.moveToFirst();
                        int costofproduct = parseInt(ProCur.getString(0));
                        int total = number * costofproduct;
                        cost.setText(String.valueOf(total));
                        check_ID = true;
                    }
                    else
                    {
                        check_ID = false;
                    }
                }
            }
        });
        ////////////////////////////

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


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sid.getText().toString().isEmpty()) {
                Toast.makeText(Add_Sales.this, "please enter sales id", Toast.LENGTH_SHORT).show();
                }
                else if(cid.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Sales.this, "please customer name", Toast.LENGTH_SHORT).show();
                }
                else if(quan.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Sales.this, "please enter quantity", Toast.LENGTH_SHORT).show();
                }
                else if(cost.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Sales.this, "please enter cost", Toast.LENGTH_SHORT).show();
                }
                else if(pid.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Sales.this, "please enter product", Toast.LENGTH_SHORT).show();
                }
                else if(check_Name == false)
                {
                    Toast.makeText(Add_Sales.this, "Customer Does not exist", Toast.LENGTH_SHORT).show();

                }
                else if(check_ID == false)
                {
                    Toast.makeText(Add_Sales.this, "Product ID does not exist", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    int salid = parseInt(sid.getText().toString());
                    int cusid = parseInt(cid.getText().toString());
                    int proid = parseInt(pid.getText().toString());
                    int count = parseInt(quan.getText().toString());
                    int amount = parseInt(cost.getText().toString());

                    Boolean checkforinsertion = DB.insertsales(salid,cusid,proid,count,amount);
                    if(checkforinsertion == true){
                        Toast.makeText(getApplicationContext(), "Sale data recorded", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Sale data failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }
}